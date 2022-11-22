package ca.ubc.gpec.tmadb

class Whole_section_scorings implements GenericScorings, SecuredMethods, Comparable<Whole_section_scorings>{

    public static final String SCORING_STATE_KI67_QC_PHASE3_INIT = "ki67_qc_phase3_init"; // specific states for ki67 qc phase 3
    public static final String SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT = "ki67_qc_phase3_select_hotspot"; // specific states for ki67 qc phase 3
    public static final String SCORING_STATE_KI67_QC_PHASE3_ESTIMATE_PERCENT = "ki67_qc_phase3_estimate_percent"; // specific states for ki67 qc phase 3
    public static final String SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS = "ki67_qc_phase3_select_regions"; // specific states for ki67 qc phase 3
    public static final String SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT = "ki67_qc_phase3_count_hotspot"; // specific states for ki67 qc phase 3
    public static final String SCORING_STATE_KI67_QC_PHASE3_COUNT_NUCLEI = "ki67_qc_phase3_count_nuclei"; // specific states for ki67 qc phase 3
    public static final float DEFAULT_FIELD_DIAMETER_40X_MM = 0.6f; 
    
    Scoring_sessions scoring_session;
    Whole_section_images whole_section_image;
    Scorings scoring; // must ALWAYS exist!!!
    SortedSet<Staining_level_assessments> staining_level_assessments;
    SortedSet<Whole_section_region_scorings> whole_section_region_scorings;
    String state; // scoring state
    // the following fields are applicable for Ki67 phase 3 scoring and not necessarily for other whole section scorings
    Float field_diameter_40x_mm; // 40x field diameter in mm
    Float percent_high;
    Float percent_medium;
    Float percent_low;
    Float percent_negligible;
    
    private Integer maxSelect_order=null;
    	
    static mapping = {
        scoring_session column:'scoring_session_id'
        whole_section_image column:'whole_section_image_id'
        scoring column:'scoring_id'
    }
    
    static hasMany = [staining_level_assessments:Staining_level_assessments, whole_section_region_scorings:Whole_section_region_scorings]

    static belongsTo = [scoring_session:Scoring_sessions]
    
    static constraints = {
        field_diameter_40x_mm (nullable: true)
        percent_high (nullable: true)
        percent_medium (nullable: true)
        percent_low (nullable: true)
        percent_negligible (nullable: true)
    }
    
    /**
     * for comparable inteface
     * 1. scoring_session
     * 2. whole_section_image
     * 3. id
     */
    public int compareTo(Whole_section_scorings other){
        int compareByScoring_session = this.getScoring_session().compareTo(other.getScoring_session());
        if (compareByScoring_session != 0) {
            return compareByScoring_session;
        }
        int compareByWhole_section_image = this.getWhole_section_image().compareTo(other.getWhole_section_image());
        return compareByWhole_section_image == 0 ? (this.getId() - other.getId()) : compareByWhole_section_image;
        
    }
    
    /**
     * add a log entry in Scoring_logs
     * @param description 
     */
    public void log(String description) {
        Scoring_logs.log(scoring, description);
    }
    
    /**
     * @override toString()
     */
    public String toString() {
        return whole_section_image.getWhole_section_slice().toStringWithParaffin_blockName();
    }
    
    /**
     * check to see if this is available to the user
     * - for SecureMethods interface
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // if scoring_session is available, tma_scoring is available
        return scoring_session.isAvailable(user);
    }
    
    /**
     * return a description of this scoring record
     */
    public String showScoringDescription() {
        return toString();
    }
    
    /**
     * save the Scorings object
     * - for GenericScorings interface
     * @param flush
     * @return 
     */
    public boolean saveScoring(boolean flush) {
        return scoring.save(flush:flush);
    }
    
    /**
     * set the score field to Scorings
     * - for GenericScorings interface
     * @param score 
     */
    public void inputScoringScore(String score) {
        scoring.setScore(score);
    }
    
    /**
     * show the "score" field from Scorings
     * - for GenericScorings interface
     * @return 
     */
    public String showScoringScore() {
        return scoring.getScore();
    }
    
    /**
     * set the scoring_date field to Scorings
     * - for GenericScorings interface
     */
    public void inputScoringScoring_date(Date scoring_date) {
        scoring.setScoring_date(scoring_date);
    }
    
    /**
     * show the "scoring_date" field from Scorings
     * - for GenericScorings interface
     * @return 
     */
    public Date showScoringScoring_date() {
        return scoring.getScoring_date();
    }
    
    /**
     * set "visit_order" field on Scorings
     * - for GenericScorings interface
     * @param visit_order
     */
    public void inputScoringVisit_order(Integer visit_order) {
        scoring.setVisit_order(visit_order);
    }
    
    /**
     * show the "visit_order" field from Scorings
     * - for GenericScorings interface
     * @return 
     */
    public Integer showScoringVisit_order() {
        return scoring.getVisit_order();
    }
    
    /**
     * default 40x field diameter
     * - first try to determine the default field diameter as that of the field
     *   diameter of the other whole_section_scorings in the same scoring_session
     * - if not found, return DEFAULT_FIELD_DIAMETER_40X_MM 
     */
    public float showDefaultField_diameter_40x_mm() {
        for (Whole_section_scorings other_whole_scoring:scoring_session.getWhole_section_scorings()) {
            if (other_whole_scoring.getField_diameter_40x_mm() != null) {
                return other_whole_scoring.getField_diameter_40x_mm();
            }
        }
        return DEFAULT_FIELD_DIAMETER_40X_MM;
    }
    
    /**
     * set the "type" field on Scorings
     * - for GenericScorings interface
     * @param type 
     */
    public void inputScoringType(String type) {
        scoring.setType(type);
    }
    
    /**
     * show the "type" field from Scorings
     * - for GenericScorings interface
     * @return 
     */
    public String showScoringType() {
        return scoring.getType();
    }
        
    /**
     * set the "comment" field on Scorings
     * - for GenericScorings interface 
     * @param comment 
     */
    public void inputScoringComment(String comment) {
        scoring.setComment(comment);
    }
    
    /**
     * show the "comment" field from Scorings
     * - for GenericScorings interface
     * @return 
     */
    public String showScoringComment() {
        return scoring.getComment();
    }
    
    /**
     * show the next visit order of the whole_section_region_scoring objects
     * within this whole_section_scoring object
     * 
     * NOTE: tma_scorings first, then whole_section_scorings
     * @return
     */
    public int showNextWhole_section_region_scoringVisit_order() {
        int visit_order = -1;
        whole_section_region_scorings.each{
            if(it.showScoringVisit_order() != null) {
                if (it.showScoringVisit_order() > visit_order) {
                    visit_order = it.showScoringVisit_order();
                }
            }
        }
        return visit_order+1; // the first visit order will be 0
    }

    /**
     * return the number of unscored whole_section_region_scorings
     * - unscored = scoring_date == null
     */
    public int showNumberOfUnscoredWhole_section_region_scorings() {
        int result = 0;
        whole_section_region_scorings.each { wsrs ->
            if (wsrs.showScoringScoring_date() == null) {
                result++;
            }
        }
        return result;
    }
    
    /**
     * check if all linked ki67 qc phase 3 scorings are completed.
     * 
     * NOTE: if No ki67 qc phase 3 scorings are linked to this scoring_session, return true
     */
    public boolean showIsAllKi67QcPhase3ScoringsCompleted() {
        // ki67 qc phase 3 scores ONLY whole_section_region_scorings
        // i.e. there are no scores needed for whole_section_scorings
        
        // iterate through all whole_section_region_scorings and see if they
        // are completed
        for (Whole_section_region_scorings whole_section_region_scoring:whole_section_region_scorings) {
            if (whole_section_region_scoring.showTotalNucleiCount() == 0) {
                return false; // 0 means scoring must be nuclei count type and 0 nuclei counted.
            } 
        }
        
        return true; // if here, must be all completed.
    }
    
    /**
     * reset score
     * - for GenericScorings interface
     *  
     * return false if something went wrong :(
     **/
    public boolean resetScore() {
        return scoring.resetScore();
    }
    
    /**
     * remove last selected nuclei 
     * - for GenericScorings interface
     *  
     * return true/false if remove successful/fail
     **/
    public boolean removeLastNucleiSelection() {
        return scoring.removeLastNucleiSelection();
    }
    
    /**
     * remove the last n nuclei selection
     * - for GenericScorings interface
     **/
    public boolean removeNucleiSelection(int n) {
        return scoring.removeNucleiSelection(n);
    }
    
    /**
     * remove all nuclei selection
     * - for synchronizing btw applet and server
     * - for GenericScorings interface
     * return false if something went wrong :(
     **/
    public boolean removeAllNucleiSelection() {
        return scoring.removeAllNucleiSelection();
    }
    
    /**
     * return the set of nuclei_selections from the scoring object
     * - for GenericScorings interface
     *  
     * @return 
     */
    public SortedSet<Nuclei_selections> showScoringNuclei_selections() {
        return scoring.getNuclei_selections();
    }    
    
    /**
     * parameter string for telling the applet which nuclei are selected
     * - for GenericScorings interface
     * 
     * format:
     * [x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p]
     * separator = "_"
     * e.g. 12x45yp_98x65yp
     *      12x45y1003000p_98x65y1004000p
     *      12x45y1003000p_98x65y-91000p   (using compressed time format)
     * 
     * NOTE: time format
     *       - time captured in seconds only since MySQL database stores time 
     *         only down to seconds (instead of milliseconds)
     *       - time of first nuclei ALWAYS absolute time i.e. # of seconds from epic (1970-01-01)
     *       - time of all nuclei after the first one would be # of seconds from previous nuclei
     * 
     * there are ONLY THREE types of nuclei selection
     * 
     * 1. coordinates without time
     * e.g. 12x34yp
     * 
     * 2. coordinates with time
     * e.g. 12x34y56p
     * 
     * 3. time only (i.e. no coordinates)
     * e.g. 123p
     * 
     * 
     * more than one set of nuclei selection ...
     * COMPARE_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p] ... _BEGIN_[x-coordinate]x[y-coordinate]y[optional time in milliseconds][n/p] ...
     * e.g. COMPARE_12x45yp_98x65yp_BEGIN_13x42yp_102x69yp
     *      COMPARE_12x45y1003000p_98x65y1004000p_BEGIN_13x42y1003000p_102x69y1004000p
     * 
     * 
     */
    public String showNucleiSelectionParamString() {
        return scoring.showNucleiSelectionParamString();
    }
    
    /**
     * check to see if scoring type is free text
     * type==null => free text
     * - for GenericScorings interface
     *  
     **/
    public boolean showIsScoringTypeFreeText() {
        return scoring.showIsScoringTypeFreeText();
    }
    
    /**
     * - for GenericScorings interface
     */
    public boolean showIsScoringTypeNucleiCount() {
        return scoring.showIsScoringTypeNucleiCount();
    }
    
    /**
     * - for GenericScorings interface
     */
    public boolean showIsScoringTypeNucleiCountNoCoord() {
        return scoring.showIsScoringTypeNucleiCountNoCoord();
    }
   
    /**
     * - for GenericScorings interface
     */
    public boolean showIsScoringTypeKi67Phase3() {
        return scoring.showIsScoringTypeKi67Phase3();
    }
    
    /**
     * check to see if it is still allowed to score this image
     * - for GenericScorings interface
     *  
     * e.g. if submitted - do not allow
     **/
    public boolean showIsAllowedToUpdateScore() {
        return !scoring_session.showSubmitted();
    }
    
    /**
     * return true if there are any nuclei selection notification linked
     * to this tma_scoring
     * - for GenericScorings interface
     *  
     */
    public boolean showHasNucleiSelectionNotifications() {
        return scoring.showHasNucleiSelectionNotifications();
    }
    
    /**
     * - for GenericScorings interface
     */
    public SortedSet<Scoring_nuclei_selection_notifications> showScoring_nuclei_selection_notifications() {
        return scoring.scoring_nuclei_selection_notifications;
    }
    
    /**
     * return the next select order
     * ALSO INCREMENT maxSelect_order
     * - for GenericScorings interface
     *  
     **/
    public int showNextSelect_order() {
        return scoring.showNextSelect_order();
    }
       
    /**
     * print out nice format of score type
     * - for GenericScorings interface
     *  
     */ 
    public String showScoreType() {
        return scoring.showScoreType();
    }
    
    /**
     * print out nice format of score
     * - for GenericScorings interface
     *  
     **/
    public String showScore() {
        return scoring.showScore();
    }
    
    /**
     * print out nice format of score in short format
     * - for GenericScorings interface
     */
    public String showScoreShort() {
        return scoring.showScoreShort();
    }
    
    /**
     * show percent positive (0-1)
     * - for GenericScorings interface
     *  
     * return -1 if not applicable e.g. not nuclei count
     **/
    public double showPercentPositive() {
        return scoring.showPercentPositive();
    }
    
    /*
     * for communication between server and fieldSelector applet 
     * 
     * parameter string format: (all numbers are in pixels, in coordinate system of ORIGINAL image i.e. not preview/lowres image)
     * [x-coordinate]x[y-coordinate]y[field diameter]pp[categorical staining level][viewing state flag][scoring state flag]_
     * 
     * viewing state flag
     * CURRENT = c
     * PREVIEW = p
     * NOT_CURRENT = n
     * 
     * scoring state flag
     * NOT_SCORED = o
     * SCORING = i
     * SCORED = s
     * 
     * e.g.
     * 3822x4856y4000pp0no_13474x4347y4000pp1no_34563x5981y4000pp2ns_46164x3418y4000pp4no_7299x1828y4000pp3cs
     * first selection @ x=3822, y=4856, negligible Ki67, field diameter=4000, not current viewing, not scored
     * second selection @ x=13474, y=4347, low Ki67, field diameter=4000, not current scoring, not scored
     * third selection @ x=34563, y=5981, medium Ki67, field diameter=4000, not current scoring, scored
     * 
     * the second selection is the current "scoring" field of view
     * 
     */
    public String showFieldSelectionParamString() {
        String result = "";
        for (Whole_section_region_scorings whole_section_region_scoring:whole_section_region_scorings) {
            String scoringState = "o"; // not scored 
            if (whole_section_region_scoring.getScoring().getVisit_order() != null) {
                scoringState = "i"; // scoring
            }
            if (whole_section_region_scoring.getScoring().getScoring_date() != null) {
                scoringState = "s"; // scored
            }
            
            result = result + 
            whole_section_region_scoring.getX()+"x"+
            whole_section_region_scoring.getY()+"y"+
            whole_section_region_scoring.getField_diameter_pixel()+
                "pp"+whole_section_region_scoring.getIhc_score_category().getNumeric_code()+
                "n"+ // NOTE: param string from server is ALWAYS NOT_CURRENT
            scoringState+
                "_"; // separator
        }
        if (!whole_section_region_scorings.isEmpty()) {
            result = result.substring(0, result.length()-1); // get rid of the last separator
        }
        return result;
    }
}
