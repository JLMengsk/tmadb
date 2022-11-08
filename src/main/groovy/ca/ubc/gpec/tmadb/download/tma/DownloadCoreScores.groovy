package ca.ubc.gpec.tmadb.download.tma

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.text.DateFormat;

import ch.qos.logback.classic.Logger;

import ca.ubc.gpec.tmadb.Tma_results;
import ca.ubc.gpec.tmadb.Tma_result_names;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Staining_details;
import ca.ubc.gpec.tmadb.Tma_cores;
import ca.ubc.gpec.tmadb.Tma_blocks;
import ca.ubc.gpec.tmadb.Tma_arrays;
import ca.ubc.gpec.tmadb.Tma_projects;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.download.DataNotSupportedException;
import ca.ubc.gpec.tmadb.download.DownloadUtils;
import ca.ubc.gpec.tmadb.download.tma.model.*;
import ca.ubc.gpec.tmadb.download.tma.writer.DataFrameWriter;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Ihc_score_categories;

/**
 * responsible for getting the core scores for download
 * @author samuelc
 *
 */
class DownloadCoreScores {

    DataFrame dataFrame;

    Logger log;
    Users user;
    String table_format;
    String file_format;
		
    /**
     * constructor
     * @param log
     * @param user
     */
    public DownloadCoreScores(Logger log, Users user) {
        this.log = log;
        this.user = user;
        dataFrame = new DataFrame();
    }
	
    public void setTable_format(String table_format) {this.table_format = table_format;}
    public void setFile_format(String file_format){this.file_format = file_format;}
		
    /**
     * prepare to retrieve data
     * @param staining_detail
     */
    protected void prepareToRetrieveDataWithStaining_detail(Staining_details staining_detail) {						
        // add id column 
        addIdColumnWithStaining_detail(staining_detail) // add a variable containing the id
    }
	
    /**
     * prepare to retrieve data
     * @param scoring_session
     */
    protected void prepareToRetrieveDataWithScoring_session(Scoring_sessions scoring_session) {
        // add id column
        addIdColumnWithScoring_session(scoring_session) // add a variable containing the id
    }
	
    /**
     * prepare to retrieve data
     * @param scoring_session
     */
    protected void prepareToRetrieveDataWithScoring_sessionNucleiSelections(Scoring_sessions scoring_session) {
        // add id column
        addIdColumnWithScoring_sessionNucleiSelections(scoring_session) // add a variable containing the id
    }
        
    /**
     * add ID column to data frame
     * e.g. if TABLE_FORMAT_ONE_ROW_PER_CORE, id column would be core id
     * @param staining_detail
     */
    private void addIdColumnWithStaining_detail(Staining_details staining_detail) {
		
        if (table_format.equals(DownloadUtils.TABLE_FORMAT_ONE_ROW_PER_CORE)) {
            dataFrame.setIdName(staining_detail.getAvailableTma_project(user).getCore_id_name());
				
            // add unique core ID's
            // first need to search for all unique core name from this project ...
            TreeSet<String> coreIds = new TreeSet<String>();
			
            ArrayList<Tma_cores> tma_cores =  staining_detail.getAvailableTma_cores(user).toArray();
			
            for (Tma_cores tma_core : tma_cores) {
                coreIds.add(tma_core.getCore_id())
            }
			
            for (String coreId : coreIds) {
                dataFrame.addCase(coreId)
            }						
        }
    }
	
    /**
     * add ID column to data frame - for Scoring_session
     * 
     * NOTE: Ki67-QC phase 3, whole section data will be downloaded here as well ... easier to write the codes
     *       and the Ki67-QC phase 3 results may not be very generalizable in the future anyways.
     * 
     * TODO: currently ONLY support either ALL Tma_scorings OR all whole_section_scorings
     * 
     * For TMA there will be two id
     * 1. core_id - for stat software; this may not be unique e.g. for duplicate TMA
     * 2. tma_core_image_id - must be unique
     * 
     * For whole section score, there will be two id
     * 1. surgical number
     * 2. whole_section_image_id
     * 
     * this method add the score values as well
     * 
     * @param scoring_session
     */
    private void addIdColumnWithScoring_session(Scoring_sessions scoring_session) {
	
        if (scoring_session.showContainsOnlyTma_scorings()) {       
            if (table_format.equals(DownloadUtils.TABLE_FORMAT_ONE_ROW_PER_CORE_IMAGE)) {
                dataFrame.setIdName(Scoring_sessions.EXPORT_TMA_CORE_IMAGE_ID_HEADER_NAME);
		
                // NEED TO ADD ALL CASES FIRST before addVariable() !!!
                scoring_session.getTma_scorings().each {
                    dataFrame.addCase(""+it.getTma_core_image().getId());
                }

                dataFrame.addVariable(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_CORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME, "");
                boolean referenceAvailable = scoring_session.showTma_scoring_referencesAvailable() && scoring_session.showSubmitted();
                if (referenceAvailable) {
                    dataFrame.addVariable(Scoring_sessions.EXPORT_REFERENCE_SCORE_NAME,"");
                }
                dataFrame.addVariable(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME,"");
            
                scoring_session.getTma_scorings().each {
                    int index = dataFrame.getIndex(""+it.getTma_core_image().getId());
				
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME,    index, it.tma_core_image.tma_core.core_id);
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_CORE_HEADER_NAME,       index, it.tma_core_image.tma_core.toString());
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME, index, it.showScoreType());     
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_HEADER_NAME,      index, it.showScore());
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME, index, DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(it.showScoringScoring_date()));
                    if (referenceAvailable) {
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_REFERENCE_SCORE_NAME, index, it.showAveragePercentPositiveTma_scoring_referencesText()+"(%+ve)");
                    }
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME,    index, MiscUtil.replaceNewLineWithBr(it.showScoringComment()));
                }
            } else {
                throw new DataNotSupportedException("unsupported table format for exporting tma_scorings: "+table_format);
            }
        } else if (scoring_session.showContainsOnlyWhole_section_scorings()) {
            if (table_format.equals(DownloadUtils.TABLE_FORMAT_ONE_ROW_PER_SURGICAL_BLOCK)) {
                dataFrame.setIdName(Scoring_sessions.EXPORT_WHOLE_SECTION_IMAGE_ID_HEADER_NAME);
                
                // NEED TO ADD ALL CASES FIRST before addVariable() !!!
                scoring_session.getWhole_section_scorings().each {
                    dataFrame.addCase(""+it.getWhole_section_image().getId());
                }
                
                dataFrame.addVariable(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME,"");
                
                scoring_session.getWhole_section_scorings().each {
                    int index = dataFrame.getIndex(""+it.getWhole_section_image().getId());
                     
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME, index, it.getWhole_section_image().getWhole_section_slice().getParaffin_block().getSpecimen_number());
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME,            index, it.showScoreType());     
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_HEADER_NAME,                 index, it.showScore());
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME,            index, DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(it.showScoringScoring_date()));
                    dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME,               index, MiscUtil.replaceNewLineWithBr(it.showScoringComment()));
                }
                
            } else {
                throw new DataNotSupportedException("unsupported table format for exporting whole_section_scorings: "+table_format);
            }
        } else {
            throw new DataNotSupportedException("Currently only support exporting scoring_session with either ALL tma_scorings or ALL whole_section_scorings.");
        }
    }
	
        
    /**
     * add ID column to data frame - for Scoring_session with nuclei selections
     * there will be three id
     * 1. core_id - for stat software; this may not be unique e.g. for duplicate TMA
     * 2. tma_core_image_id (ID from MySQL table)
     * 3. id from MySQL table nuclei_selections - must be unique
     * 
     * this method add the score values as well as the nuclei selections
     * 
     * @param scoring_session
     */
    private void addIdColumnWithScoring_sessionNucleiSelections(Scoring_sessions scoring_session) {
        if (scoring_session.showContainsOnlyTma_scorings()) {  
            if (table_format.equals(DownloadUtils.TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW)) {
                dataFrame.setIdName(Scoring_sessions.EXPORT_TMA_CORE_IMAGE_ID_HEADER_NAME);
		
                // NEED TO ADD ALL CASES FIRST before addVariable() !!!
                scoring_session.tma_scorings.each { tma_scoring -> 
                    tma_scoring.showScoringNuclei_selections().each {
                        dataFrame.addCase(""+it.id);
                    }
                }

                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_CORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME, "");
						
                scoring_session.tma_scorings.each { tma_scoring -> 
                    tma_scoring.showScoringNuclei_selections().each {
                        int index = dataFrame.getIndex(""+it.id);
	    
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME,              index, ""+it.id);
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME,                       index, tma_scoring.tma_core_image.tma_core.core_id);
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_CORE_HEADER_NAME,                          index, tma_scoring.tma_core_image.tma_core.toString());
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME,                    index, tma_scoring.showScoreType());     
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_HEADER_NAME,                         index, tma_scoring.showScore());
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME,                    index, DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(it.scoring_date));
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME,            index, ""+it.getX());
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME,            index, ""+it.getY());
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME,        index, it.getState());
                        dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME, index, ""+it.getSelect_order());
                    }            
                }
            } else {
                throw new DataNotSupportedException("unsupported table format for exporting tma_scorings nuclei_selections: "+table_format);
            }
        } else if (scoring_session.showContainsOnlyWhole_section_scorings()) {
            if (table_format.equals(DownloadUtils.TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW)) {
                dataFrame.setIdName(Scoring_sessions.EXPORT_WHOLE_SECTION_IMAGE_ID_HEADER_NAME);
		
                // NEED TO ADD ALL CASES FIRST before addVariable() !!!
                scoring_session.getWhole_section_scorings().each { whole_section_scoring ->
                    whole_section_scoring.getWhole_section_region_scorings().each { whole_section_region_scoring ->
                        whole_section_region_scoring.showScoringNuclei_selections().each {
                            dataFrame.addCase(""+it.id);
                        }
                    }
                }
                
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_X_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_Y_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_DIAMETER_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_IHC_SCORE_CATEGORY_NAME_HEADER_NAME,"");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME, "");
                dataFrame.addVariable(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME, "");
                
                scoring_session.getWhole_section_scorings().each { whole_section_scoring ->
                    whole_section_scoring.getWhole_section_region_scorings().each { whole_section_region_scoring ->
                        whole_section_region_scoring.showScoringNuclei_selections().each {
                            int index = dataFrame.getIndex(""+it.id);
                            Ihc_score_categories ihc_score_category = whole_section_region_scoring.getIhc_score_category();
                            String ihc_score_category_name = ihc_score_category == null ? ViewConstants.NA : ihc_score_category.getName();
	    
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME,                             index, ""+it.id);                 
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME,                        index, whole_section_scoring.getWhole_section_image().getWhole_section_slice().getParaffin_block().getSpecimen_number());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_X_HEADER_NAME,                       index, ""+whole_section_region_scoring.getX());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_Y_HEADER_NAME,                       index, ""+whole_section_region_scoring.getY());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_DIAMETER_HEADER_NAME,                index, ""+whole_section_region_scoring.getField_diameter_pixel());                            
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_IHC_SCORE_CATEGORY_NAME_HEADER_NAME, index, ihc_score_category_name);
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME,                    index, whole_section_scoring.showScoreType());     
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_HEADER_NAME,                         index, whole_section_scoring.showScore());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME,                    index, DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(it.scoring_date));
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME,            index, ""+it.getX());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME,            index, ""+it.getY());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME,        index, it.getState());
                            dataFrame.addValueAtIndex(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME, index, ""+it.getSelect_order());
                        }            
                    }
                }
            } else {
                throw new DataNotSupportedException("unsupported table format for exporting whole_section_scorings nuclei_selections: "+table_format);
            }
        } else {
            throw new DataNotSupportedException("Currently only support exporting scoring_session with either ALL tma_scorings or ALL whole_section_scorings.");
        }
    }
 
    
    /**
     * add variables to dataFrame
     * @param tma_result_names
     */
    private void addVariablesOneRowPerCore(ArrayList<Tma_result_names> tma_result_names) throws DataFrameReadException, DataFrameWriteException {
		
        // write some log ...
        String logText = "Download attempt: user="+user+" tma_result_names: ";
        for (Tma_result_names tma_result_name : tma_result_names) {
            logText = logText + tma_result_name+", ";
        }
        log.info(logText);
		
        for (Tma_result_names tma_result_name : tma_result_names) {
            String variableName = tma_result_name.getName()
			
            // add variables
            dataFrame.addVariable(variableName , tma_result_name.getDescription());
						
            // find values 
            def tma_results = Tma_results.findAllByTma_result_name(tma_result_name)
			
            for (Tma_results tma_result : tma_results) {
                dataFrame.addValueAtIndex(variableName,					
                    dataFrame.getIndex(tma_result.getTma_core_image().getTma_core().getCore_id()),
                    tma_result.showScoreName(false))
            }
        }	
    }
		
    /**
     * write data frame to BufferedWriter
     * @param bw
     * @param outputFileFormat
     */
    public void writeDataFrame(BufferedWriter bw, String outputFileFormat) throws IOException, DataFrameReadException {	
        DataFrameWriter dfw = new DataFrameWriter(bw,dataFrame);
        if (outputFileFormat.equals(DownloadUtils.FILE_FORMAT_TEXT_TAB)) {
            dfw.writeDelimitedText(DownloadUtils.DELIMITER_TAB);
        } else if (outputFileFormat.equals(DownloadUtils.FILE_FORMAT_TEXT_CSV)) {
            dfw.writeDelimitedText(DownloadUtils.DELIMITER_COMMA);
        }
    }
	
    public void writeDataFrameWithDesiredVariableNames(BufferedWriter bw, String outputFileFormat, ArrayList<String> desiredVariableNames) throws IOException, DataFrameReadException {
        DataFrameWriter dfw = new DataFrameWriter(bw,dataFrame);
        dfw.setDesiredVariableNames(desiredVariableNames);
        if (outputFileFormat.equals(DownloadUtils.FILE_FORMAT_TEXT_TAB)) {
            dfw.writeDelimitedText(DownloadUtils.DELIMITER_TAB);
        } else if (outputFileFormat.equals(DownloadUtils.FILE_FORMAT_TEXT_CSV)) {
            dfw.writeDelimitedText(DownloadUtils.DELIMITER_COMMA);
        }
    }
	
    public String toString() {
        return "dataFrame ... \n"+dataFrame.toString();
    }

}
