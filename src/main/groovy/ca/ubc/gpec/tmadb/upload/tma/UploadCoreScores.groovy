/**
 * upload tma scores
 * 
 * IMPORTANT NOTES:
 * 
 * - scores are NOT case sensitive (make all scores capital letters)
 * 
 */
package ca.ubc.gpec.tmadb.upload.tma

import java.util.List;
import java.util.TreeSet;
import java.text.*
import org.apache.commons.logging.Log

import ca.ubc.gpec.tmadb.Tma_blocks;
import ca.ubc.gpec.tmadb.Tma_slices;
import ca.ubc.gpec.tmadb.Tma_cores;
import ca.ubc.gpec.tmadb.Tma_core_images;
import ca.ubc.gpec.tmadb.Tma_results;
import ca.ubc.gpec.tmadb.Tma_result_names;
import ca.ubc.gpec.tmadb.Scanner_infos;
import ca.ubc.gpec.tmadb.Tma_scorers;
import ca.ubc.gpec.tmadb.Ihc_score_categories;
import ca.ubc.gpec.tmadb.Ihc_score_category_groups;

class UploadCoreScores {

    // some constants core_id	row	col	score
    static final String HNAME_CORE_ID = "core_id"
    static final String HNAME_ROW = "row"
    static final String HNAME_COL = "col"
    static final String HNAME_SCORE = "score"
    static final String HNAME_COMMENT = "comment"
	
    public static String SCORE_TYPE_TOTAL_NUCLEI_COUNT = "total_nuclei_count"
    public static String SCORE_TYPE_VISUAL_PERCENT_POSITIVE_ESTIMATE                             = "visual_percent_positive_estimate"
    public static String SCORE_TYPE_VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE                      = "visual_percent_positive_nuclei_estimate"
    public static String SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE                 = "visual_percent_positive_cytoplasmic_estimate"
    public static String SCORE_TYPE_VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE                    = "visual_percent_positive_membrane_estimate"
    public static String SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE = "visual_percent_positive_cytoplasmic_and_or_membrane_estimate"
    public static String SCORE_TYPE_POSITIVE_ITIL_COUNT = "positive_itil_count"
    public static String SCORE_TYPE_POSITIVE_STIL_COUNT = "positive_stil_count"
    public static String SCORE_TYPE_IHC_SCORE_CATEGORICAL = "ihc_score_category"

    // instance variables
    Log log
    int tma_array_id;
    int tma_block_id;
    int tma_slice_id;
    int tma_core_image_id; // this is only the FIRST tma_core_image id NOT the whole set for score upload
    int scanner_info_id=-1;
    Date scanning_date=null;
    Integer tma_scorer1_id;
    Integer tma_scorer2_id;
    Integer tma_scorer3_id;
    int ihc_score_category_group_id;
    int[] missing_ihc_score_category_id_arr;
    String scoring_date_year;
    String scoring_date_month;
    String scoring_date_day;
    String received_date_year;
    String received_date_month;
    String received_date_day;
    String score_type;
    Integer tma_result_name_id;

    byte[] myFile;
    List<Tma_results> tma_resultsArr;

    /**
     * constructor
     * @param log
     */
    public UploadCoreScores(Log log) {
        this.log = log
    }
	
    /**
     * find item in inputArr and return index (starts with 0)
     * - returns -1 if not found
     * @param item
     * @param input
     * @return
     */
    private int find(String item, String[] inputArr) throws UploadCoreScoresParseFileException {
        int index=0;
        while(!item.trim().equalsIgnoreCase(inputArr[index].trim())) {
            index++;
            if (index == inputArr.length) {
                throw new UploadCoreImagesParseFileException("column: "+item+", not found in input file!!!")
            }
        }
        return(index)
    }


    public int getScanner_info_id() {
        if (scanner_info_id == -1) {
            // scanner_info_id is not initialized yet ...
            scanner_info_id = (Tma_core_images.get(tma_core_image_id)).scanner_info.getId()
        }
        return scanner_info_id
    }

    public Date getScanning_date() {
        if (scanning_date == null) {
            // scanning_date is not initialized yet ...
            scanning_date = Tma_core_images.get(tma_core_image_id).getScanning_date();
        } 
        return scanning_date
    }

    /**
     * parse score file
     * @param sep
     * @throws UploadCoreScoresParseFileException
     */
    public void parseFileSingleScore(String sep) throws UploadCoreScoresParseFileException, UploadCoreScoresSaveException {
        //  get some objects before readering/parsing file ...
        Scanner_infos scanner_info = Scanner_infos.get(scanner_info_id)
        Tma_blocks tma_block = Tma_blocks.get(tma_block_id)
        Tma_slices tma_slice = Tma_slices.get(tma_slice_id)
        Tma_scorers tma_scorer1 = null; if (tma_scorer1_id != null) {tma_scorer1 = Tma_scorers.get(tma_scorer1_id.intValue())};
        Tma_scorers tma_scorer2 = null; if (tma_scorer2_id != null) {tma_scorer2 = Tma_scorers.get(tma_scorer2_id.intValue())};
        Tma_scorers tma_scorer3 = null; if (tma_scorer3_id != null) {tma_scorer3 = Tma_scorers.get(tma_scorer3_id.intValue())};
        Tma_result_names tma_result_name = Tma_result_names.get(tma_result_name_id.intValue());
        Date scoring_date  = new SimpleDateFormat("yyyy-MM-dd").parse(scoring_date_year+ "-"+scoring_date_month+ "-"+scoring_date_day)
        Date received_date = new SimpleDateFormat("yyyy-MM-dd").parse(received_date_year+"-"+received_date_month+"-"+received_date_day)
        // get scanner info and scanning date
        getScanner_info_id()
        getScanning_date()

        // missing scores category
        ArrayList<Ihc_score_categories> missing_score_categories = []
        ArrayList<String> missing_score_category_names = new ArrayList<String>()
        for (int i in 0..(missing_ihc_score_category_id_arr.length-1)) {
            missing_score_categories.add(Ihc_score_categories.get(missing_ihc_score_category_id_arr[i]))
            missing_score_category_names.add(missing_score_categories[i].getName().toUpperCase())
        }
				
        // categorical IHC scores ONLY
        ArrayList<Ihc_score_categories> interpretable_score_categories = null
        ArrayList<String> interpretable_score_category_names = new ArrayList<String>()
        if (score_type.equals(UploadCoreScores.SCORE_TYPE_IHC_SCORE_CATEGORICAL)) {
            interpretable_score_categories = Ihc_score_categories.findAllByIhc_score_category_group(Ihc_score_category_groups.get(ihc_score_category_group_id))
            if (interpretable_score_categories.size() == 0) {
                throw new UploadCoreScoresParseFileException("no IHC score categories for selected IHC score category group: "+(Ihc_score_category_groups.get(ihc_score_category_group_id)))
            }
            interpretable_score_categories.each {
                interpretable_score_category_names.add(it.getName().toUpperCase())
            }
        }
        // start reading and parsing file ...
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(myFile)))

        tma_resultsArr = new ArrayList<Tma_results>()

        String line = br.readLine();
        if (line == null) {
            throw new UploadCoreScoresParseFileException("file is empty!!!")
        }
        // skip blank lines ...
        while (line.trim().length()==0) {
            line = br.readLine();
            if (line == null) {
                // end of file reached but no non-blank lines!!!
                throw new UploadCoreScoresParseFileException("file is empty!!!")
            }
        }

        // this is the header line ... look for the header columns
        String[] headerLineArr = line.split(sep)
        int indexCoreId  = find(HNAME_CORE_ID, headerLineArr);
        int indexRow     = find(HNAME_ROW,     headerLineArr);
        int indexCol     = find(HNAME_COL,     headerLineArr);
        int indexScore   = find(HNAME_SCORE,   headerLineArr);
        int indexComment = find(HNAME_COMMENT,   headerLineArr);
		
        line = br.readLine();
        while(line != null) {
            String[] lineArr = line.split(sep)

            String coreId = lineArr[indexCoreId]
            String score = lineArr[indexScore].toUpperCase().trim()
            String comment = null;
            if (indexComment >= 0) { 
                // comment provided
                if (lineArr.length > indexComment) {
                    // make sure comment is provided for this case
                    // i.e. it could be comment provided for some
                    // cases but not all
                    comment = lineArr[indexComment]
                }
            }
			
            int row = -1
            int col = -1
            try {
                row = Integer.parseInt(lineArr[indexRow])
                col = Integer.parseInt(lineArr[indexCol])
            } catch (NumberFormatException nfe) {
                throw new UploadCoreScoresParseFileException("invalid row/col number: row="+line[indexRow]+", col="+line[indexCol]+" for core_id="+coreId)
            }

            // find tma core in database ...
            def c = Tma_cores.createCriteria()
            ArrayList tma_coresArr = c {
                eq("tma_block",tma_block)
                eq("row", row)
                eq("col", col)
            }

            // not really possible to have more than one core for tma_block/row/col ... would be database inconsistency ...
            if (tma_coresArr.size() > 1) {
                throw new UploadCoreScoresParseFileException("more than one TMA cores found for tma_block="+tma_block+" row="+row+" col="+col)
            }
            if (tma_coresArr.size() == 0) {
                throw new UploadCoreScoresParseFileException("TMA core record NOT found for tma_block="+tma_block+" row="+row+" col="+col)
            }
            def tma_core = tma_coresArr.get(0)

            // find tma core image in database ...
            c = Tma_core_images.createCriteria()
            ArrayList tma_core_imagesArr = c {
                eq("tma_core", tma_core)
                eq("tma_slice", tma_slice)
                eq("scanning_date", scanning_date)
                eq("scanner_info", scanner_info)
            }
			
            // hopefully not really possible to have more than one core image for same tma core/slice on same scanner and same date
            if (tma_core_imagesArr.size() > 1) {
                throw new UploadCoreScoresParseFileException("TMA core image record NOT found for tma_block="+tma_block+" row="+row+" col="+col+" slice="+tma_slice+" scanning_info="+scanner_info)
            }
            if (tma_core_imagesArr.size() == 0) {
                throw new UploadCoreScoresParseFileException("TMA core image record NOT found for "+
					" tma_block="+tma_block+
					" row="+row+
					" col="+col+
					" slice="+tma_slice+
					" scanning_date"+scanning_date+
					" scanner_info"+scanner_info)
            }
            def tma_core_image = tma_core_imagesArr.get(0)
			
            // ready to create tma_results!!!
            // write some log first ...
            log.info("trying to insert tma_results: tma_core_image="+tma_core_image+
					" tma_core="+tma_core+
					" row="+row+
					" col="+col+
					" scanning_date="+scanning_date+
					" scanner_info="+scanner_info+
					" scoring_date="+scoring_date+
					" received date="+received_date+
					" tma scorer(s)="+tma_scorer1+"/"+tma_scorer2+"/"+tma_scorer3+
					" tma result name="+tma_result_name.getName()+
                                        " score type="+score_type+
					"\n"
            )
            Tma_results tma_result = new Tma_results()
            tma_result.setTma_core_image(tma_core_image)
            if (tma_scorer1 != null) {tma_result.setTma_scorer1(tma_scorer1)}
            if (tma_scorer2 != null) {tma_result.setTma_scorer2(tma_scorer2)}
            if (tma_scorer3 != null) {tma_result.setTma_scorer3(tma_scorer3)}
            // check score
            int index = -1 // if not categorical ihc score, want to see if it is a missing category score first
            Double visualPercentPositiveNucleiEstimate= null
            if (score_type.equals(UploadCoreScores.SCORE_TYPE_IHC_SCORE_CATEGORICAL)) {
                index = interpretable_score_category_names.indexOf(score)
            } 
            if (index != -1) {
                // interpretable score !!!
                tma_result.setIhc_score_category(interpretable_score_categories[index])
            } else {
                index = missing_score_category_names.indexOf(score)
                if (index != -1) {
                    // missing score found !!!
                    tma_result.setIhc_score_category(missing_score_categories[index])
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_ESTIMATE)) {
                    // this is a continuous visual percent positive estimate
                    try {
                        tma_result.setVisual_percent_positive_estimate(Float.parseFloat(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
						"Unknown score (caused NumberFormatException) found for"+
						" score="+score+
						" tma_block_id="+tma_block_id+
						" row="+row+
						" col="+col+
						" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE)) {
                    // this is a continuous visual percent positive nuclei estimate
                    try {
                        tma_result.setVisual_percent_positive_nuclei_estimate(Float.parseFloat(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
						"Unknown score (caused NumberFormatException) found for"+
						" score="+score+
						" tma_block_id="+tma_block_id+
						" row="+row+
						" col="+col+
						" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE)) {
                    // this is a continuous visual percent positive cytoplasmic estimate
                    try {
                        tma_result.setVisual_percent_positive_cytoplasmic_estimate(Float.parseFloat(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
							"Unknown score (caused NumberFormatException) found for"+
							" score="+score+
							" tma_block_id="+tma_block_id+
							" row="+row+
							" col="+col+
							" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE)) {
                    // this is a continuous visual percent positive membrane estimate
                    try {
                        tma_result.setVisual_percent_positive_membrane_estimate(Float.parseFloat(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
							"Unknown score (caused NumberFormatException) found for"+
							" score="+score+
							" tma_block_id="+tma_block_id+
							" row="+row+
							" col="+col+
							" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE)) {
                    // this is a continuous visual percent positive cytoplasmic and or membrane estimate
                    try {
                        tma_result.setVisual_percent_positive_cytoplasmic_and_or_membrane_estimate(Float.parseFloat(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
							"Unknown score (caused NumberFormatException) found for"+
							" score="+score+
							" tma_block_id="+tma_block_id+
							" row="+row+
							" col="+col+
							" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_POSITIVE_ITIL_COUNT)) {
                    // this is positive itil count (integer)
                    try {
                        tma_result.setPositive_itil_count(Integer.parseInt(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
							"Unknown score (trying to import positive_itil_count; caused NumberFormatException) found for"+
							" score="+score+
							" tma_block_id="+tma_block_id+
							" row="+row+
							" col="+col+
							" slice="+tma_slice)
                    }
                } else if (score_type.equals(UploadCoreScores.SCORE_TYPE_POSITIVE_STIL_COUNT)) {
                    // this is positive stil count (integer)
                    try {
                        tma_result.setPositive_stil_count(Integer.parseInt(score))
                    } catch (NumberFormatException nfe) {
                        throw new UploadCoreScoresParseFileException(
							"Unknown score (trying to import positive_stil_count; caused NumberFormatException) found for"+
							" score="+score+
							" tma_block_id="+tma_block_id+
							" row="+row+
							" col="+col+
							" slice="+tma_slice)
                    }
                } else {
                    // unknown score found !!!
                    throw new UploadCoreScoresParseFileException(
					"Unknown score found for"+
					" score="+score+
					" tma_block_id="+tma_block_id+
					" row="+row+
					" col="+col+
					" slice="+tma_slice)
                }
            }
            tma_result.setScoring_date(scoring_date)
            tma_result.setReceived_date(received_date)
            if (comment != null) {
                tma_result.setComment(comment)
            }
			
            // add tma_result_name
            tma_result.setTma_result_name(tma_result_name);
			
            tma_resultsArr.add(tma_result) // add tma_result to list

            // insert core image ...
            log.info("inserting tma_results: tma_core_image="+tma_core_image+
					" tma_core="+tma_core+
					" row="+row+
					" col="+col+
					" scanning_date="+scanning_date+
					" scanner_info="+scanner_info+
					" scoring_date="+scoring_date+
					" received date="+received_date+
					" tma scorer(s)="+tma_scorer1+"/"+tma_scorer2+"/"+tma_scorer3+
					" tma result name="+tma_result_name.getName()+
                                        " score type="+score_type+
					"\n"
            )

            line = br.readLine();
        }

        // save tma_core_images to database
        log.info("finished parsing file ... "+tma_resultsArr.size()+" images specified.")
        tma_resultsArr.each {
            log.info("saving ... score for "+it.getTma_core_image())
            if(!it.save(flush:true)) {// save tma_results to database ... no return !!!
                String errMsg = "Grails/Hibernate error message while performing save operation:<br>"
                it.errors.allErrors.each {
                    errMsg = errMsg + it + "\n"  
                }
                throw new UploadCoreScoresSaveException("Save FAILED !!! for "+it.getTma_core_image()+"; database is now inconsistent!!! Clean up first!!!<br><br>"+errMsg)
            }
        }
        log.info("finished saving "+tma_resultsArr.size()+" scores to database.")
    }

}
