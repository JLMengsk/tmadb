package ca.ubc.gpec.tmadb;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer;

class Scoring_sessions implements SecuredMethods, Comparable<Scoring_sessions> {

    // some constants
    public static final int STATUS_NOT_STARTED = 0;
    public static final int STATUS_STARTED = 1;
    public static final int STATUS_COMPLETED = 2;
    public static final int STATUS_SUBMITTED = 3;
    // all status >10 are considered submitted.  they are special submitted status based on types of test
    // WARNING: since status is tinyint cannot go beyone 127
    // DO NOT CHANGE THE NUMERIC CODE!!! difference between the numeric codes are used
    // in the determination of status!!!!!
    public static final int STATUS_SUBMITTED_KI67_QC_CALIBRATOR_FAILED = 11;
    public static final int STATUS_SUBMITTED_KI67_QC_CALIBRATOR_PASSED = 12;
    
    public static final String VIEW_ORDER_TYPE_SPECIMEN_NUMBER = "order_by_specimen_number";
    public static final String VIEW_ORDER_TYPE_NUMERIC_CORE_ID = "order_by_numeric_core_id";
    
    public static final String VIEW_OPTION_HIDE_FROM_SCORER = "option_hide_from_scorer";
    
    public static final String EXPORT_CORE_ID_HEADER_NAME = "TMA core ID"; // name for scoring session export
    public static final String EXPORT_WHOLE_SECTION_IMAGE_ID_HEADER_NAME = "Whole section image ID"; // name for scoring session export
    public static final String EXPORT_WHOLE_SECTION_REGION_X_HEADER_NAME = "Whole section field (x-coordinate)";
    public static final String EXPORT_WHOLE_SECTION_REGION_Y_HEADER_NAME = "Whole section field (y-coordinate)";
    public static final String EXPORT_WHOLE_SECTION_REGION_DIAMETER_HEADER_NAME = "Whole section field (diameter pixel)";    
    public static final String EXPORT_WHOLE_SECTION_REGION_IHC_SCORE_CATEGORY_NAME_HEADER_NAME = "Whole section field level";
    public static final String EXPORT_SURGICAL_BLOCK_NUMBER_HEADER_NAME = "Surgical number"; // name for scoring session export
    public static final String EXPORT_SCORE_TYPE_HEADER_NAME = "Score type"; // name for scoring session export
    public static final String EXPORT_SCORE_HEADER_NAME = "Score"; // name for scoring session export
    public static final String EXPORT_TMA_CORE_IMAGE_ID_HEADER_NAME = "TMA core image ID"; // name for scoring session export
    public static final String EXPORT_CORE_HEADER_NAME = "TMA core name"; // name for scoring session export
    public static final String EXPORT_SCORE_DATE_HEADER_NAME = "Scoring date/time"; // name for scoring session export
    public static final String EXPORT_REFERENCE_SCORE_NAME = "Reference score"; // name for scoring session export
    public static final String EXPORT_COMMENT_HEADER_NAME = "comment"; // comment for scoring session export
    public static final String EXPORT_NUCLEI_SELECTION_HEADER_NAME = "Nuclei selection id"; // name for scoring session export
    public static final String EXPORT_NUCLEI_SELECTION_X_HEADER_NAME = "Nuclei selection (x-coordinate)"; // name for scoring session export 
    public static final String EXPORT_NUCLEI_SELECTION_Y_HEADER_NAME = "Nuclei selection (y-coordinate)"; // name for scoring session export
    public static final String EXPORT_NUCLEI_SELECTION_STATE_HEADER_NAME = "Nuclei selection (state)"; // name for scoring session export
    public static final String EXPORT_NUCLEI_SELECTION_SELECT_ORDER_HEADER_NAME = "Nuclei selection (selection order)"; // name for scoring session export
    
    public static final double KI67_QC_CALIBRATOR_PASSING_RMSE = 0.6d;
    public static final double KI67_QC_CALIBRATOR_PASSING_MAXDEV = 1d;
    
    String name;
    String description;
    Date start_date;
    Date submitted_date; // capture the time when user submit the scoring_session
    Tma_scorers tma_scorer;
    SortedSet<Tma_scorings> tma_scorings;
    SortedSet<Whole_section_scorings> whole_section_scorings;
    int status;
    String view_order_type; // this is used by tma_scoring's compareTo method.
    String view_option; // a field for some possible view options
    SortedSet<Scoring_session_questions> scoring_session_questions;
    
    static constraints = {
        description (nullable: true)
        tma_scorer (nullable: true)
        view_order_type (nullable: true)
        view_option (nullable: true)
        submitted_date (nullable: true)
    }

    static mapping = { tma_scorer column:'tma_scorer_id' }

    static hasMany = [ tma_scorings:Tma_scorings, whole_section_scorings:Whole_section_scorings, scoring_session_questions:Scoring_session_questions ]

    /**
     * for Comparable interface
     * 1. tma_scorer
     * 2. start_date (descending order)
     * DO NOT SORT BY NAME!!!
     * 3. id (descending order)
     **/
    public int compareTo(Scoring_sessions other) {
        int compareByTma_scorer = this.tma_scorer.compareTo(other.tma_scorer);
        if (compareByTma_scorer != 0) {
            return compareByTma_scorer;
        }
        int compareByStart_date = this.start_date.compareTo(other.start_date); 
        return compareByStart_date == 0 ? (-1 * (this.getId() - other.getId())) : (-1 * compareByStart_date); // want descending order
    }
    
    /**
     * check to see if this is available to the user
     *
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}

        Tma_scorers ts = user.getTma_scorer();
        
        // availlable only if this user is scoring this session
        // return false if tma_scorer is null
        return ts == null ? false : ts.compareTo(this.tma_scorer)==0;
    }
    
    /**
     * check to see if there are any unanswered questions
     * i.e. scoring_session_questions
     **/
    public boolean showHasUnansweredQuestions() {
        boolean result = false;
        scoring_session_questions.each {
            if (!it.showIsAnswered()) {
                result = true;
            }
        }
        return result;
    }
    
    /**
     * change the status to submitted
     * WARNING: 
     * - does NOT check current status
     * - does NOT save domain class
     */
    public void changeStatusToSubmitted() {
        status = STATUS_SUBMITTED;
    }
    
    /**
     * print out the status
     * @return
     */
    public String showStatusNotice() {
        if (status >= STATUS_SUBMITTED) {
            if (status == STATUS_SUBMITTED_KI67_QC_CALIBRATOR_FAILED) {
                return "not passed";
            } else if (status == STATUS_SUBMITTED_KI67_QC_CALIBRATOR_PASSED) {
                return "passed";
            } else {
                return "submitted";
            }
        } else if (status == STATUS_COMPLETED) {
            return "completed";
        } else if (status == STATUS_NOT_STARTED) {
            return "not started";
        } else {
            return "started";
        }
    }
    
    /**
     * show if scoring session is started or not
     */
    public boolean showStarted() {
        return status >= STATUS_STARTED;
    }
    
    /**
     * show if scoring session is completed or not
     * @return
     */
    public boolean showCompleted() {
        // if submitted, it must be completed
        return status >= STATUS_COMPLETED;
    }
    
    public boolean showSubmitted() {
        return status >= STATUS_SUBMITTED;
    }

    /**
     * check if all linked ki67 qc phase 3 scorings are completed.
     * 
     * NOTE: if No ki67 qc phase 3 scorings are linked to this scoring_session, return true
     *   
     */
    public boolean showIsAllKi67QcPhase3ScoringsCompleted() {
        // iterate through all whole_section_scorings and see if it is completed
        for (Whole_section_scorings whole_section_scoring:whole_section_scorings) {
            if (!whole_section_scoring.showIsAllKi67QcPhase3ScoringsCompleted()) {
                return false;
            }
        }
        return true; // if here, all ki67 qc scoring completed
    }
    
    /**
     * check to see if this scoring session contains only tma_scorings
     */
    public boolean showContainsOnlyTma_scorings() {
        return tma_scorings.size() > 0 && whole_section_scorings.isEmpty();
    }
    
    /**
     * check to see if this scoring session contains only whole_section_scorings
     */
    public boolean showContainsOnlyWhole_section_scorings() {
        return whole_section_scorings.size() > 0 && tma_scorings.isEmpty();
    }
    
    /**
     * check to see if there are any nuclei selections in this scoring session
     **/
    public boolean showHasNucleiSelection() {
        Iterator<Tma_scorings> itr = tma_scorings.iterator();
        while(itr.hasNext()) {
            Tma_scorings tma_scoring = itr.next();
            if (tma_scoring.showScoringType() != null) {
                if (tma_scoring.showScoringType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT)) {
                    return true;
                }
            }
        }
        Iterator<Whole_section_scorings> itrWs = whole_section_scorings.iterator();
        while(itrWs.hasNext()) {
            Whole_section_scorings whole_section_scoring = itrWs.next();
            if (whole_section_scoring.showScoringType() != null) {
                if (whole_section_scoring.showScoringType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * check to see if there are any nuclei selections (count w/ no coord) in this scoring session
     **/
    public boolean showHasNucleiCountNoCoord() {
        Iterator<Tma_scorings> itr = tma_scorings.iterator();
        while(itr.hasNext()) {
            Tma_scorings tma_scoring = itr.next();
            if (tma_scoring.showScoringType() != null) {
                if (tma_scoring.showScoringType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
                    return true;
                }
            }
        }
        Iterator<Whole_section_scorings> itr2 = whole_section_scorings.iterator();
        while(itr2.hasNext()) {
            Whole_section_scorings whole_section_scoring = itr2.next();
            if (whole_section_scoring.showScoringType() != null) {
                if (whole_section_scoring.showScoringType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD) || whole_section_scoring.showScoringType().equals(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * show how many images are remained to be scored
     * NOTE: tma_scorings first, then whole_section_scorings
     * @return
     */
    public int showRemainingImageCount() {
        int remainingImageCount = 0;
        tma_scorings.each {
            if (it.showScoringScoring_date() == null) {
                remainingImageCount++;
            }
        }
        whole_section_scorings.each {
            if (it.showScoringScoring_date() == null) {
                remainingImageCount++;
            }
        }
        return remainingImageCount;
    }

    /**
     * show the next visit order
     * NOTE: tma_scorings first, then whole_section_scorings
     * @return
     */
    public int showNextVisit_order() {
        int visit_order = -1;
        tma_scorings.each{
            if(it.showScoringVisit_order() != null) {
                if (it.showScoringVisit_order() > visit_order) {
                    visit_order = it.showScoringVisit_order();
                }
            }
        }
        whole_section_scorings.each{
            if(it.showScoringVisit_order() != null) {
                if (it.showScoringVisit_order() > visit_order) {
                    visit_order = it.showScoringVisit_order();
                }
            }
        }
        return visit_order+1; // the first visit order will be 0
    }

    /**
     * get a unscored Scoring (can be a Tma_scoring, Whole_section_scoring ... etc) object
     * return null if all images are scored
     * order of image shown is according to compareTo method of Tma_scorings
     * @return
     */
    public GenericScorings showUnscoredScoring() {
        if (status >= STATUS_COMPLETED) {
            // completed or submitted
            return null;
        }
        if (status == STATUS_NOT_STARTED) {
            status = STATUS_STARTED;
            if (!this.save(flush:true)) {
                return null; // something went wrong!!!
            }
        }
		
        // first, search through the tma_scorings array 
        Tma_scorings latest_visited_tma_scoring = null;
        tma_scorings.each{
            if (it.showScoringVisit_order() != null) {
                if (latest_visited_tma_scoring == null) {
                    // this must be the first encountered tma_scoring
                    // with a non-null visit_order
                    latest_visited_tma_scoring = it
                } else if (it.showScoringVisit_order() > latest_visited_tma_scoring.showScoringVisit_order()) {
                    latest_visited_tma_scoring = it;
                }
            }
        }
	// second, search through the whole_section_scorings array	
        Whole_section_scorings latest_visited_whole_section_scoring = null; 
        whole_section_scorings.each{
            if (it.showScoringVisit_order() != null) {
                if (latest_visited_whole_section_scoring == null) {
                    // this must be the first encountered tma_scoring
                    // with a non-null visit_order
                    latest_visited_whole_section_scoring = it
                } else if (it.showScoringVisit_order() > latest_visited_whole_section_scoring.showScoringVisit_order()) {
                    latest_visited_whole_section_scoring = it;
                }
            }
        }
        
        ///////////////////////////////////////////////////
        // NOTE: display order ...                      ///
        // 1. tma_scoring                               ///
        // 2. whole_section scoring                     ///
        ///////////////////////////////////////////////////
        if (latest_visited_tma_scoring != null) {
            if (latest_visited_tma_scoring.showScoringScoring_date() == null) {
                // the last visited tma_scoring (core image) has not been scored yet
                // score this image first!
                return latest_visited_tma_scoring;
            }
        } else if (latest_visited_whole_section_scoring != null) {
            if (latest_visited_whole_section_scoring.showScoringScoring_date() == null) {
                // the last visited whole_section_scoring (whole section or core biopsy image) has not been scored yet
                // score this image first!
                return latest_visited_whole_section_scoring;
            }
        }
		
        // EITHER 
        // - the last visited tma/whole_section_scoring (image) has been scored already
        // OR
        // - no visited tma/whole_section_scoring
        // find the next available image
        // CURRENTLY BASED ON TMA_SCORING order
        // NOTE: currently try to score ALL tma_scoring FIRST then whole_section images
        //       this is NOT a good idea ... however, currently (2013) ... each scoring
        //       session should have only ONE type of scoring
        Iterator<Tma_scorings> itr = tma_scorings.iterator();
        while (itr.hasNext()) {
            Tma_scorings tma_scoring = itr.next();
            if (tma_scoring.showScoringScoring_date() == null) {
                return (GenericScorings)tma_scoring;
            }
        }
        Iterator<Whole_section_scorings> wItr = whole_section_scorings.iterator();
        while (wItr.hasNext()) {
            Whole_section_scorings whole_section_scoring = wItr.next();
            if (whole_section_scoring.showScoringScoring_date() == null) {
                return (GenericScorings)whole_section_scoring;
            }
        }
	
        // every image must have been scored
        status = STATUS_COMPLETED;
        this.save(flush:true);
        return null;
    }

    /**
     * show the previous tma_scoring
     * return null if this is the first tma_scoring
     * @param curr
     * @return
     */
    public Tma_scorings showPrevTma_scoring(Tma_scorings curr) {
        Tma_scorings latest = null;
        if (curr.showScoringVisit_order() != null) {
            tma_scorings.each{
                if (it.showScoringVisit_order() != null) {
                    if (it.showScoringVisit_order() < curr.showScoringVisit_order()) {
                        if (latest == null) {
                            // this must be the first encountered tma_scoring
                            // with a non-null scoring date
                            latest = it
                        } else if (it.showScoringVisit_order() > latest.showScoringVisit_order()) {
                            latest = it;
                        }
                    }
                }
            }
        } else {
            tma_scorings.each{
                if (it.showScoringVisit_order() != null) {
                    if (latest == null) {
                        // this must be the first encountered tma_scoring
                        // with a non-null scoring date
                        latest = it
                    } else if (it.showScoringVisit_order() > latest.showScoringVisit_order()) {
                        latest = it;
                    }
                }
            }
        }
        return latest;
    }

    /**
     * show the next (done) tma_scoring
     * return null if this is the latest tma_scoring i.e. the next
     * tma_scoring to show should be one that is never scored before
     * in this session
     * @param curr
     * @return
     */
    public Tma_scorings showNextTma_scoring(Tma_scorings curr) {
        if (curr.showScoringVisit_order() == null) {
            // this is the latest tma_scoring
            return null
        }
        Tma_scorings earliest = null;
        tma_scorings.each {
            if (it.showScoringVisit_order() != null) {
                if (earliest == null) {
                    if (it.showScoringVisit_order() > curr.showScoringVisit_order()) {
                        earliest = it;
                    }
                } else if ((it.showScoringVisit_order() < earliest.showScoringVisit_order()) & (it.showScoringVisit_order() > curr.showScoringVisit_order())) {
                    earliest = it;
                }
            }
        }
        return earliest;
    }

    /**
     * show the previous whole_section_scoring
     * return null if this is the first whole_section_scoring
     * @param curr
     * @return
     */
    public Whole_section_scorings showPrevWhole_section_scoring(Whole_section_scorings curr) {
        Whole_section_scorings latest = null;
        if (curr.showScoringVisit_order() != null) {
            whole_section_scorings.each{
                if (it.showScoringVisit_order() != null) {
                    if (it.showScoringVisit_order() < curr.showScoringVisit_order()) {
                        if (latest == null) {
                            // this must be the first encountered tma_scoring
                            // with a non-null scoring date
                            latest = it
                        } else if (it.showScoringVisit_order() > latest.showScoringVisit_order()) {
                            latest = it;
                        }
                    }
                }
            }
        } else {
            whole_section_scorings.each{
                if (it.showScoringVisit_order() != null) {
                    if (latest == null) {
                        // this must be the first encountered tma_scoring
                        // with a non-null scoring date
                        latest = it
                    } else if (it.showScoringVisit_order() > latest.showScoringVisit_order()) {
                        latest = it;
                    }
                }
            }
        }
        return latest;
    }
    
    /**
     * show the next (done) whole_section_scoring
     * return null if this is the latest tma_scoring i.e. the next
     * whole_section_scoring to show should be one that is never scored before
     * in this session
     * @param curr
     * @return
     */
    public Whole_section_scorings showNextWhole_section_scoring(Whole_section_scorings curr) {
        if (curr.showScoringVisit_order() == null) {
            // this is the latest tma_scoring
            return null
        }
        Whole_section_scorings earliest = null;
        whole_section_scorings.each {
            if (it.showScoringVisit_order() != null) {
                if (earliest == null) {
                    if (it.showScoringVisit_order() > curr.showScoringVisit_order()) {
                        earliest = it;
                    }
                } else if ((it.showScoringVisit_order() < earliest.showScoringVisit_order()) & (it.showScoringVisit_order() > curr.showScoringVisit_order())) {
                    earliest = it;
                }
            }
        }
        return earliest;
    }
    
    /**
     * show the Tma_scoring or Whole_section_scoring instance with the largest visit_order (i.e. latest visited)
     * return null if nothing has been visited yet
     * 
     * NOTE: return ALL Tma_scorings first, then Whole_section_scoring 
     * 
     * @return
     */
    public GenericScorings showMaxVisitOrderScoring() {
        Tma_scorings tma_scoring = null;
        tma_scorings.each {
            if (it.showScoringVisit_order() != null) {
                if (tma_scoring == null) {
                    tma_scoring = it;
                } else if (it.showScoringVisit_order() > tma_scoring.showScoringVisit_order()) {
                    tma_scoring = it;
                }
            }
        }
        Whole_section_scorings whole_section_scoring = null;
        whole_section_scorings.each {
            if (it.showScoringVisit_order() != null) {
                if (whole_section_scoring == null) {
                    whole_section_scoring = it;
                } else if (it.showScoringVisit_order() > whole_section_scoring.showScoringVisit_order()) {
                    whole_section_scoring = it;
                }
            } 
        }
        // return ...
        if (tma_scoring == null && whole_section_scoring == null) {
            return null;
        }
        if (whole_section_scoring != null) { // whole_section_scoring first since we want to display ALL tma_scoring, then whole_section_scoring
            return (GenericScorings)whole_section_scoring;
        } else {
            return (GenericScorings)tma_scoring;
        }
    }
	
    /**
     * return the first scoring_date recorded
     * TODO: potential problem ... a user can update a score at a later time and hence 
     * the scoring_date does not truely reflect the first scoring_date
     * 
     * @return
     */
    public Date showMinScoring_date() {
        Date scoring_date = null;
        tma_scorings.each {
            if (scoring_date != null) {
                if (it.showScoringScoring_date() < scoring_date) {
                    scoring_date = it.showScoringScoring_date();
                }
            } else {
                scoring_date = it.showScoringScoring_date();
            }
        }
        whole_section_scorings.each {
            if (scoring_date != null) {
                if (it.showScoringScoring_date() < scoring_date) {
                    scoring_date = it.showScoringScoring_date();
                }
            } else {
                scoring_date = it.showScoringScoring_date();
            }
        }
        return scoring_date;
    }
	
    /**
     * return the last scoring_date recorded
     * @return
     */
    public Date showMaxScoring_date() {
        Date scoring_date = null;
        tma_scorings.each {
            if (scoring_date != null) {
                if (it.showScoringScoring_date() > scoring_date) {
                    scoring_date = it.showScoringScoring_date();
                }
            } else {
                scoring_date = it.showScoringScoring_date();
            }
        }
        whole_section_scorings.each {
            if (scoring_date != null) {
                if (it.showScoringScoring_date() > scoring_date) {
                    scoring_date = it.showScoringScoring_date();
                }
            } else {
                scoring_date = it.showScoringScoring_date();
            }
        }
        return scoring_date;
    }
    
    /**
     * check to see if there are any tma_scoring_reference(s) available
     **/
    public boolean showTma_scoring_referencesAvailable(){
        Iterator<Tma_scorings> itr = tma_scorings.iterator();
        while(itr.hasNext()) {
            if (itr.next().showTma_scoring_referencesAvailable()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * shows the number of Tma_scoring with reference score available
     **/
    public int showNumTma_scoring_referencesAvailable() {
        int count = 0;
        tma_scorings.each {
            if (it.showTma_scoring_referencesAvailable()) {
                count++;
            }
        }
        return count;
    }
        
    /**
     * statistics from Mei-yin and Lisa
     * sqrt(average([log2(lab%+0.1) - log2(ref%+0.1)]^2))
     * return -1 if reference score is not available for ALL tma_scorings
     **/
    public double showRMSE() {
        int n = tma_scorings.size();
        if (showNumTma_scoring_referencesAvailable() != n) {
            return -1; // not all tma_scorings have reference score
        }
        double temp = 0d;
        tma_scorings.each {
            double labScore = it.showPercentPositive();
            double log2AvgRefScore = it.showAverageLog2PercentTma_scoring_references();
            // log2(percent positive + 0.1%)
            temp = temp + MiscUtil.square(MiscUtil.log2(labScore+0.001d) - log2AvgRefScore);
        }
        return Math.sqrt(temp/n);        
    }
    
    /**
     * statistics from Mei-yin and Lisa
     * sqrt(max([log2(lab%+0.1) - log2(ref%+0.1)]^2))
     * return -1 if reference score is not available for ALL tma_scorings
     **/
    public double showMaxdev() {
        int n = tma_scorings.size();
        if (showNumTma_scoring_referencesAvailable() != n) {
            return -1; // not all tma_scorings have reference score
        }
        double maxValue = 0d;
        tma_scorings.each {
            double labScore = it.showPercentPositive();
            double log2AvgRefScore = it.showAverageLog2PercentTma_scoring_references();
            
            // log2(percent positive + 0.1%)
            double temp = MiscUtil.square(MiscUtil.log2(labScore+0.001d) - log2AvgRefScore);
            if (temp > maxValue) {
                maxValue = temp;
            }
        }
        return Math.sqrt(maxValue);        
    }
    
    /**
     * return true if calibrator is passed
     * false otherwise 
     * 
     * NOTE: if scoring session not submitted, return false
     * NOTE: save status value to domain class!!!
     */
    public boolean showPassedCalibrator() {
        if (!showSubmitted()) {
            return false; // not submitted ... return false
        } else {
            if (status < STATUS_SUBMITTED_KI67_QC_CALIBRATOR_FAILED && status < STATUS_SUBMITTED_KI67_QC_CALIBRATOR_PASSED) {
                // never checked ... need to check
                if (showRMSE() >= KI67_QC_CALIBRATOR_PASSING_RMSE) {
                    status = STATUS_SUBMITTED_KI67_QC_CALIBRATOR_FAILED;
                } else if (showMaxdev() >= KI67_QC_CALIBRATOR_PASSING_MAXDEV) {
                    status = STATUS_SUBMITTED_KI67_QC_CALIBRATOR_FAILED;
                } else {
                    status = STATUS_SUBMITTED_KI67_QC_CALIBRATOR_PASSED;
                }
                save(flush:true);               
            }
            return status == STATUS_SUBMITTED_KI67_QC_CALIBRATOR_PASSED;
        }
    }
    
    /**
     * return true if this is a Ki67 QC Calibrator training session that was AUTOMATICALLY
     * generated i.e. not the ones in the initial ki67 QC phase 2 or 3 study
     */
    public boolean showIsKi67QcCalibratorTraining() {
        return name.startsWith(Scoring_sessionInitializer.TRAINING_SESSION_NAME_PREFIX);
    }
    
    /**
     * return true if this is a Ki67 QC Calibrator test session that was AUTOMATICALLY
     * generated i.e. not the ones in the initial ki67 QC phase 2 or 3 study
     */
    public boolean showIsKi67QcCalibratorTest() {
        return name.startsWith(Scoring_sessionInitializer.TEST_SESSION_NAME_PREFIX);
    }
    
    /**
     * test to see if view_option is VIEW_OPTION_HIDE_FROM_SCORER
     **/ 
    public boolean showIsViewOptionHideFromScorer() {
        if (view_option == null) {
            return false;
        } else {
            return view_option.equals(VIEW_OPTION_HIDE_FROM_SCORER);
        }
    }
    
    /**
     * override default toString()
     */
    public String toString() {
        return name;
    }
}
