package ca.ubc.gpec.tmadb

import java.text.DecimalFormat;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.NucleiSelectionParamStringParser;

class Tma_scorings implements GenericScorings, SecuredMethods, Comparable<Tma_scorings> {

    Scoring_sessions scoring_session;
    Tma_core_images tma_core_image;
    Scorings scoring; // must ALWAYS exist!!!
    SortedSet<Tma_scoring_references> tma_scoring_references;
    
    private Integer maxSelect_order=null;
    	
    static mapping = {
        scoring_session column:'scoring_session_id'
        tma_core_image column:'tma_core_image_id'
        scoring column:'scoring_id'
    }

    static mappedBy = [tma_scoring_references:'tma_scoring'] // i.e. this instance is the "tma_scoring" of the record in tma_scoring_references table
    
    static hasMany = [tma_scoring_references:Tma_scoring_references]

    static belongsTo = [scoring_session:Scoring_sessions]
    
    /**
     * return a description of this scoring record
     */
    public String showScoringDescription() {
        return tma_core_image.tma_core.toString();
    }
    
    /**
     * save the Scorings object
     * - for GenericScorings interface
     * @param flush
     * @return 
     */
    public boolean saveScoring(boolean flush) {
        return scoring.save(flush:flush, failOnError:true);
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
     * check to see if this is available to the user
     * for SecureMethods interface
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // if scoring_session is available, tma_scoring is available
        return scoring_session.isAvailable(user);
    }
    
    /**
     * for Comparable interface
     * @param other
     * @return
     * 
     * NOTE 1.: view_order_type of the tma_scorings MUST be the same, otherwise, default
     *          ordering will be used
     * NOTE 2.: if cannot order by specified order, will fall back to order by default order
     *          i.e. will NOT necessarily return "equal" even if by the specified order
     *          this and other objects are considered equal. 
     */
    public int compareTo(Tma_scorings other) {
        boolean useDefaultOrder = false;
        String thisView_order_type = this.getScoring_session().getView_order_type();
        String otherView_order_type = other.getScoring_session().getView_order_type();
        if (thisView_order_type != null & otherView_order_type != null) {
            if (!thisView_order_type.equals(otherView_order_type)) {
                useDefaultOrder = true; // use default order since the order type differs between this, other
            } else {
                if (thisView_order_type.equals(Scoring_sessions.VIEW_ORDER_TYPE_SPECIMEN_NUMBER)) {
                    // order by LAST 4 digits of specimen number
                    Integer thisNum = null;
                    Integer otherNum = null;
                    try {
                        String thisSpecimenNumber  =  this.getTma_core_image().getTma_core().getSurgical_block().showSpecimen_number();
                        String otherSpecimenNumber = other.getTma_core_image().getTma_core().getSurgical_block().showSpecimen_number();
                        int thisSpecimenNumberLength   = thisSpecimenNumber.length();
                        int otherSpecimenNumberLength = otherSpecimenNumber.length();
                        thisNum  = new Integer( thisSpecimenNumber.substring( thisSpecimenNumberLength > 4 ? (thisSpecimenNumberLength -  4) : 0));
                        otherNum = new Integer(otherSpecimenNumber.substring(otherSpecimenNumberLength > 4 ? (otherSpecimenNumberLength - 4) : 0));
                    } catch (NumberFormatException nfe) {
                        // fall back to default order SILENTLY!!!!
                        useDefaultOrder = true;
                    }
                    int compareByThisViewOrder = thisNum.compareTo(otherNum);
                    if (compareByThisViewOrder == 0) {
                        useDefaultOrder = true; // cannot order based on the specified order, fall back to default
                    } else {
                        return compareByThisViewOrder; // RETURN!!!
                    }
                } else if (thisView_order_type.equals(Scoring_sessions.VIEW_ORDER_TYPE_NUMERIC_CORE_ID)) {                    
                    // order by core id
                    // assume core id is numeric
                    Integer thisNum = null;
                    Integer otherNum = null;
                    try {
                        thisNum  = new Integer(this.getTma_core_image().getTma_core().getCore_id());
                        otherNum = new Integer(other.getTma_core_image().getTma_core().getCore_id());
                    } catch (NumberFormatException nfe) {
                        // fall back to default order SILENTLY!!!!
                        useDefaultOrder = true;
                    }
                    int compareByThisViewOrder = thisNum.compareTo(otherNum);
                    if (compareByThisViewOrder == 0) {
                        useDefaultOrder = true; // cannot order based on the specified order, fall back to default
                    } else {
                        return compareByThisViewOrder; // RETURN!!!
                    }
                } else {
                    // unknown order type ... use default order type
                    useDefaultOrder = true;
                }
            }
        } else {
            useDefaultOrder = true;
        }
        
        // default order
        if (useDefaultOrder) {
            // no view order specified ... the following rule:
            // 1. by tma_core_image
            // 2. by scoring_session
            // 3. by scoring_date
            int compareByTma_core_image = tma_core_image.compareTo(other.tma_core_image);
            if (compareByTma_core_image != 0) {
                return compareByTma_core_image;
            } else {
                int compareByScoring_session = this.scoring_session.compareTo(other.scoring_session);
                if (compareByScoring_session != 0) {
                    return compareByScoring_session;
                } else {
                    if (scoring.getScoring_date() == null & other.scoring.getScoring_date() == null) {
                        return 0;
                    } else if  (scoring.getScoring_date() != null & other.scoring.getScoring_date() != null) {
                        return this.scoring.getScoring_date().compareTo(other.scoring.getScoring_date());
                    } else {
                        // must be either one is null the other not null,
                        // let null < integer
                        if  (scoring.getScoring_date() == null) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                   
                }
            }
        } 
    }
        
    /**
     * reset score
     * return false if something went wrong :(
     **/
    public boolean resetScore() {
        return scoring.resetScore();
    }
    
    /**
     * remove last selected nuclei 
     * return true/false if remove successful/fail
     **/
    public boolean removeLastNucleiSelection() {
        return scoring.removeLastNucleiSelection();
    }
    
    /**
     * remove the last n nuclei selection
     **/
    public boolean removeNucleiSelection(int n) {
        return scoring.removeNucleiSelection(n);
    }
    
    /**
     * remove all nuclei selection
     * - for synchronizing btw applet and server
     * return false if something went wrong :(
     **/
    public boolean removeAllNucleiSelection() {
        return scoring.removeAllNucleiSelection();
    }
    
    /**
     * return the set of nuclei_selections from the scoring object
     * @return 
     */
    public SortedSet<Nuclei_selections> showScoringNuclei_selections() {
        return scoring.getNuclei_selections();
    }    
    
    /**
     * parameter string for telling the applet which nuclei are selected
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
     **/
    public boolean showIsScoringTypeFreeText() {
        return scoring.showIsScoringTypeFreeText();
    }
    
    public boolean showIsScoringTypeNucleiCount() {
        return scoring.showIsScoringTypeNucleiCount();
    }
    
    public boolean showIsScoringTypeNucleiCountNoCoord() {
        return scoring.showIsScoringTypeNucleiCountNoCoord();
    }
    
    public boolean showIsScoringTypeKi67Phase3() {
        return scoring.showIsScoringTypeKi67Phase3();
    }
   
    /**
     * check to see if it is still allowed to score this image
     * e.g. if submitted - do not allow
     **/
    public boolean showIsAllowedToUpdateScore() {
        return !scoring_session.showSubmitted();
    }
    
    /**
     * return true if there are any nuclei selection notification linked
     * to this tma_scoring
     */
    public boolean showHasNucleiSelectionNotifications() {
        return scoring.showHasNucleiSelectionNotifications();
    }
    
    public SortedSet<Scoring_nuclei_selection_notifications> showScoring_nuclei_selection_notifications() {
        return scoring.scoring_nuclei_selection_notifications;
    }
    
    /**
     * return the next select order
     * ALSO INCREMENT maxSelect_order
     **/
    public int showNextSelect_order() {
        return scoring.showNextSelect_order();
    }
       
    /**
     * print out nice format of score type
     */ 
    public String showScoreType() {
        return scoring.showScoreType();
    }
    
    /**
     * print out nice format of score
     **/
    public String showScore() {
        return scoring.showScore();
    }
    
    /**
     * print out nice format of score in short format
     */
    public String showScoreShort() {
        return scoring.showScoreShort();
    }
    
    /**
     * show percent positive (0-1)
     * return -1 if not applicable e.g. not nuclei count
     **/
    public double showPercentPositive() {
        return scoring.showPercentPositive();
    }
    
    /**
     * show scoring date
     */
    public Date showScoring_date() {
        return scoring.getScoring_date(); // just show date of first visit for now
    }
    
    /**
     * shows the corresponding reference scoring session.
     * - return itself, if it is the reference scoring session
     * - returns null if reference scoring not available
     **/
    public SortedSet<Tma_scoring_references> showTma_scoring_references() {
        return tma_scoring_references;
    }
    
    /**
     * check to see if there are any tma_scoring_reference(s) available
     **/
    public boolean showTma_scoring_referencesAvailable(){
        return tma_scoring_references.size() != 0;
    }

    /**
     * formula to use .... (log2(Ref1) + log2(Ref2) + log2(Ref2) ... + log2(Refn))/n
     * Ref1 ... Refn the range of values is 0-1 NOT 0-100
     * 
     * returns Double.Nan if no reference score is available
     */
    public double showAverageLog2PercentTma_scoring_references() {
        if (tma_scoring_references.size()==0) {
            return Double.NaN;
        }
        // calculate reference score ...
        // iterate through the rest ...
        double referenceScore = 0d;
        int totalNumRef = tma_scoring_references.size();
        Iterator<Tma_scoring_references> itr = tma_scoring_references.iterator();
        for (int i=0; i<totalNumRef; i++) {
            // log2 (percent positive + 0.1%)
            referenceScore = referenceScore + MiscUtil.log2(itr.next().reference_tma_scoring.showPercentPositive()+0.001d);
        }
        // take average
        return referenceScore/((double)totalNumRef);
    }
    
    /**
     * shows average of the percent positive of the reference scores
     * 
     * returns -1 if no reference score is available
     **/
    public double showAveragePercentPositiveTma_scoring_references() {
        if (tma_scoring_references.size()==0) {
            return -1;
        } else {
            return Math.pow(2,showAverageLog2PercentTma_scoring_references());
        }
    }
    
    /**
     * shows average of the percent positive of the reference scores
     * - as String in 0-100%
     **/
    public String showAveragePercentPositiveTma_scoring_referencesText() {
        if (showTma_scoring_referencesAvailable()) {
            return ""+Math.round(showAveragePercentPositiveTma_scoring_references()*100);
        } else {
            return "NA";
        }
    }
}
