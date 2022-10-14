package ca.ubc.gpec.tmadb

import java.io.BufferedWriter;

import ca.ubc.gpec.tmadb.download.DownloadUtils;
import ca.ubc.gpec.tmadb.download.tma.CopyCoreImageScriptGenerator;
import ca.ubc.gpec.tmadb.download.tma.CopyCoreImageScriptGeneratorException;
import ca.ubc.gpec.tmadb.download.tma.DownloadSingleCoreScores;
import ca.ubc.gpec.tmadb.download.tma.DownloadScoringSessionScores;
import ca.ubc.gpec.tmadb.download.tma.DownloadScoringSessionNucleiSelections;
import ca.ubc.gpec.tmadb.download.tma.model.*;
import ca.ubc.gpec.tmadb.util.MiscUtil;

/**
 * responsible for processing download data from database
 * 
 * @author samuelc
 *
 */
class DownloadDataController {

    def index = { 	
        redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params) 
    }
	
    def list = {}
	
    def tmaScores = {
        render(view: "tmaScores")
    }
    
    def tmaImages = {
        render(view: "tmaImages")
    }
    
    def downloadSingleTmaScores = {
        DownloadSingleCoreScores dscs = new DownloadSingleCoreScores(log, Users.findByLogin(session.user.login));
        bindData(dscs, params);
		
        try {
            dscs.retreiveData();
			
            // write retrieve data to http response ...
            response.setContentType(DownloadUtils.getOuputFileContentType(params.file_format));
            response.setHeader(
				"Content-disposition",
				"attachment; filename=deconvoluted."+DownloadUtils.getOutputFileExtension(params.file_format))
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            dscs.writeDataFrame(bw, params.file_format);
            bw.flush();
            bw.close();
            response.flushBuffer();
        } catch (IOException ioe) {
            render(ioe.toString())	
        } catch (DataFrameReadException dfre) {
            render(dfre.toString())
        } catch (DataFrameWriteException dfwe) {
            render(dfwe.toString())
        }
    }
	
    /**
     * download score data from a single scoring session
     * TODO: currently only support scoring session with either only tma_scorings or only whole_section_scorings i.e. do not support mix
     */
    def downloadScoringSessionScores = {
        Scoring_sessions scoring_session = Scoring_sessions.get(params.id);
        
        DownloadScoringSessionScores dsss;
        ArrayList<String> desiredVariableNames = new ArrayList<String>();
        if (scoring_session.showContainsOnlyTma_scorings()) {
            dsss = new DownloadScoringSessionScores(log, Users.findByLogin(session.user.login), DownloadUtils.TABLE_FORMAT_ONE_ROW_PER_CORE_IMAGE,     DownloadUtils.FILE_FORMAT_TEXT_TAB, scoring_session);
            // specify variable orders - must match order in DownloadCoreScores.groovy
            desiredVariableNames.add(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_CORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME);
            if (scoring_session.showTma_scoring_referencesAvailable() && scoring_session.showSubmitted()) {
                desiredVariableNames.add(Scoring_sessions.EXPORT_REFERENCE_SCORE_NAME);
            }
            desiredVariableNames.add(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME);
        } else if (scoring_session.showContainsOnlyWhole_section_scorings()) {
            dsss = new DownloadScoringSessionScores(log, Users.findByLogin(session.user.login), DownloadUtils.TABLE_FORMAT_ONE_ROW_PER_SURGICAL_BLOCK, DownloadUtils.FILE_FORMAT_TEXT_TAB, scoring_session);
            // specify variable orders - must match order in DownloadCoreScores.groovy
            desiredVariableNames.add(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_COMMENT_HEADER_NAME);
        } else {
            render "Currently only support exporting scoring_session with either ALL tma_scorings or ALL whole_section_scorings.";
            return;
        }
        try {
            dsss.retreiveData();
			
            // write retrieve data to http response ...
            response.setContentType(DownloadUtils.getOuputFileContentType(dsss.file_format));
            response.setHeader(
				"Content-disposition",
				"attachment; filename=scoring_session_scores_id"+scoring_session.id+"."+DownloadUtils.getOutputFileExtension(dsss.file_format))
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));    
            dsss.writeDataFrameWithDesiredVariableNames(bw, dsss.file_format, desiredVariableNames);
            bw.flush();
            bw.close();
            response.flushBuffer();
        } catch (IOException ioe) {
            render(ioe.toString())
        } catch (DataFrameReadException dfre) {
            render(dfre.toString())
        } catch (DataFrameWriteException dfwe) {
            render(dfwe.toString())
        }
    }
        
    def downloadScoringSessionNucleiSelections = {
        Scoring_sessions scoring_session = Scoring_sessions.get(params.id);
        
        DownloadScoringSessionNucleiSelections dssns;
        ArrayList<String> desiredVariableNames = new ArrayList<String>();
        
        if (scoring_session.showContainsOnlyTma_scorings()) {    
            dssns = new DownloadScoringSessionNucleiSelections(log, Users.findByLogin(session.user.login), DownloadUtils.TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW, DownloadUtils.FILE_FORMAT_TEXT_TAB, scoring_session);
            // specify variable orders - must match order in DownloadCoreScores.groovy
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_CORE_ID_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_CORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME);
        } else if (scoring_session.showContainsOnlyWhole_section_scorings()) {
            dssns = new DownloadScoringSessionNucleiSelections(log, Users.findByLogin(session.user.login), DownloadUtils.TABLE_FORMAT_ONE_NUCLEI_SELECTION_PER_ROW, DownloadUtils.FILE_FORMAT_TEXT_TAB, scoring_session);
            // specify variable orders - must match order in DownloadCoreScores.groovy
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_X_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_Y_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_DIAMETER_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_WHOLE_SECTION_REGION_IHC_SCORE_CATEGORY_NAME_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_TYPE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_SCORE_DATE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_X_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME);
            desiredVariableNames.add(Scoring_sessions.EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME);
        } else {
            render "Currently only support exporting scoring_session with either ALL tma_scorings or ALL whole_section_scorings.";
            return;
        }
        
        try {
            dssns.retreiveData();
			
            // write retrieve data to http response ...
            response.setContentType(DownloadUtils.getOuputFileContentType(dssns.file_format));
            response.setHeader(
				"Content-disposition",
				"attachment; filename=scoring_session_nuclei_selections_id"+scoring_session.id+"."+DownloadUtils.getOutputFileExtension(dssns.file_format))
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
                       
            dssns.writeDataFrameWithDesiredVariableNames(bw, dssns.file_format, desiredVariableNames);
            bw.flush();
            bw.close();
            response.flushBuffer();
        } catch (IOException ioe) {
            render(ioe.toString())
        } catch (DataFrameReadException dfre) {
            render(dfre.toString())
        } catch (DataFrameWriteException dfwe) {
            render(dfwe.toString())
        }
    }	
    
    /**
     * generate and return a script for one to copy tma core images to a separate folder on the server
     * note: this method does not actually download the image, only the script is downloaded.
     *       one would then need to run this script on the server to copy the images
     * example of the content in the script:
     * cp /var/www/html/images/bliss/02-008/02-008_HE_E12_v3_s1/02-008_HE_E12_v3_s1_122_r11c2.jpg [some folder path]               
     */
    def downloadTmaCoreImageSelectionCopyScript = {
        try {
            CopyCoreImageScriptGenerator ccisg = new CopyCoreImageScriptGenerator(Users.findByLogin(session.user.login));
            bindData(ccisg, params);
            // write retrieve data to http response ...
            response.setContentType(DownloadUtils.FILE_FORMAT_PLAIN_TEXT);
            response.setHeader(
                "Content-disposition",
                "attachment; filename=coreImageCopyScript_staining_dtails_id"+ccisg.getStaining_details_id()+".sh");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            ccisg.generateCopyScript(bw,"/media/data/www/html/",","); // HARD CODE THIS FOR NOW!!!
            bw.flush();
            bw.close();
            response.flushBuffer();
            render(ccisg.getStaining_details_id());
        } catch (IOException ioe) {
            render(ioe.toString())
        } catch (CopyCoreImageScriptGeneratorException ccisge) {
            render(ccisge.toString())
        }
    }
}
