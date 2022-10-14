package ca.ubc.gpec.tmadb

class Whole_section_region_scorings implements GenericScorings, SecuredMethods, Comparable<Whole_section_region_scorings>{

    Whole_section_scorings whole_section_scoring;
    Scorings scoring; // must ALWAYS exist!!!
    Ihc_score_categories ihc_score_category;
    Integer x; // in pixel, in coordinate system of ORIGINAL image (not preview image)
    Integer y; // in pixel, in coordinate system of ORIGINAL image (not preview image)
    Integer field_diameter_pixel; // in pixel
    
    static mapping = {
        whole_section_scoring column:'whole_section_scoring_id'  
        ihc_score_category column:'ihc_score_category_id'
        scoring column:'scoring_id'
    }
    
    static belongsTo = [whole_section_scoring:Whole_section_scorings]
    
    static constraints = {
        ihc_score_category(nullable:true)
    }
    
    /**
     * for Comparable interface
     * 0. if ihc_score_category is available, sort by this (descending order) for display purposes.
     * 1. by Whole_section_scorings
     * 2. by scoring_date from scoring
     * 3. id
     */
    public int compareTo(Whole_section_region_scorings other) {
        if (ihc_score_category != null && other.getIhc_score_category() != null) {
            int compareByIhc_score_category = ihc_score_category.getNumeric_code().compareTo(other.getIhc_score_category().getNumeric_code()); 
            if (compareByIhc_score_category != 0) {
                return -1*compareByIhc_score_category; // want descending order so that the highest score group is displayed first
            }
        }
        int compareByWhole_section_scoring = this.getWhole_section_scoring().compareTo(other.getWhole_section_scoring());
        if (compareByWhole_section_scoring != 0) {
            return compareByWhole_section_scoring;
        }
        int compareByScoring_date = 0;
        if (this.getScoring().getScoring_date() != null && other.getScoring().getScoring_date() != null) {
            compareByScoring_date = this.getScoring().getScoring_date().compareTo(other.getScoring().getScoring_date());
        }
        return compareByScoring_date == 0 ? (this.getId() - other.getId()) : compareByScoring_date;
    }
        
    /**
     * @override toString()
     */
    public String toString() {
        //return whole_section_scoring.showScoringDescription()+" region (x,y,diameter): "+x+", "+y+", "+field_diameter_pixel;
        return whole_section_scoring.showScoringDescription()+" region (x,y,diameter): "+x+", "+y+", "+field_diameter_pixel + "###" + scoring.comment + "###";
    }
    
    /**
     * check to see if this is available to the user
     * - for SecureMethods interface
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // if scoring_session is available, tma_scoring is available
        return whole_section_scoring.isAvailable(user);
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
    
    /**
     * show total nuclei count
     * 
     * return -1 if not applicable e.g. not nuclei count
     **/
    public int showTotalNucleiCount() {
        if (scoring.getType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT) | scoring.getType().equals(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD)) {
            return scoring.countTotalNucleiSelected();
        } else {
            return -1; // not nuclei count
        }
    }
    
    
}
