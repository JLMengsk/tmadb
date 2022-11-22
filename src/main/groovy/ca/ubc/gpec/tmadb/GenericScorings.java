/*
 * interface for generic scoring object
 * - specific scoring object ... e.g. Tma_scorings
 */
package ca.ubc.gpec.tmadb;

import java.util.Date;
import java.util.SortedSet;

/**
 *
 * @author samuelc
 */
public interface GenericScorings {

    public static final String SCORING_TYPE_NUCLEI_COUNT = "nc";
    public static final String SCORING_TYPE_NUCLEI_COUNT_NO_COORD = "nc_no_coord"; // nuclei count only, no x/y coord tracking
    public static final String SCORING_TYPE_KI67_QC_PHASE3 = "ki67_qc_phase3"; // specific scoring type for KI67-QC study phase III
    // null scoring type implies free-text
    
    /**
     * return a text description of the scoring record
     * 
     * @return 
     */
    public String showScoringDescription();
    
    /**
     * save the Scorings object
     *
     * @param flush
     * @return
     */
    public boolean saveScoring(boolean flush);

    /**
     * set the score field to Scorings
     *
     * @param score
     */
    public void inputScoringScore(String score);

    /**
     * show the "score" field from Scorings
     *
     * @return
     */
    public String showScoringScore();

    /**
     * set the scoring_date field to Scorings
     *
     * @param scoring_date
     */
    public void inputScoringScoring_date(Date scoring_date);

    /**
     * show the "scoring_date" field from Scorings
     *
     * @return
     */
    public Date showScoringScoring_date();

    /**
     * set "visit_order" field on Scorings
     *
     * @param visit_order
     */
    public void inputScoringVisit_order(Integer visit_order);

    /**
     * show the "visit_order" field from Scorings
     *
     * @return
     */
    public Integer showScoringVisit_order();

    /**
     * set the "type" field on Scorings
     * 
     * @param type 
     */
    public void inputScoringType(String type);
    
    /**
     * show the "type" field from Scorings
     *
     * @return
     */
    public String showScoringType();

    /**
     * set the "comment" field on Scorings
     * 
     * @param comment 
     */
    public void inputScoringComment(String comment);
    
    /**
     * show the "comment" field from Scorings
     *
     * @return
     */
    public String showScoringComment();

    /**
     * reset score return false if something went wrong :(
     *
     */
    public boolean resetScore();

    /**
     * remove last selected nuclei return true/false if remove successful/fail
     *
     */
    public boolean removeLastNucleiSelection();

    /**
     * remove the last n nuclei selection
     *
     */
    public boolean removeNucleiSelection(int n);

    /**
     * remove all nuclei selection - for synchronizing btw applet and server
     * return false if something went wrong :(
     *
     */
    public boolean removeAllNucleiSelection();

    /**
     * return the set of nuclei_selections from the scoring object
     * @return 
     */
    public SortedSet<Nuclei_selections> showScoringNuclei_selections();
    
    /**
     * parameter string for telling the applet which nuclei are selected
     *
     * format: [x-coordinate]x[y-coordinate]y[optional time in
     * milliseconds][n/p] separator = "_" e.g. 12x45yp_98x65yp
     * 12x45y1003000p_98x65y1004000p 12x45y1003000p_98x65y-91000p (using
     * compressed time format)
     *
     * NOTE: time format - time captured in seconds only since MySQL database
     * stores time only down to seconds (instead of milliseconds) - time of
     * first nuclei ALWAYS absolute time i.e. # of seconds from epic
     * (1970-01-01) - time of all nuclei after the first one would be # of
     * seconds from previous nuclei
     *
     * there are ONLY THREE types of nuclei selection
     *
     * 1. coordinates without time e.g. 12x34yp
     *
     * 2. coordinates with time e.g. 12x34y56p
     *
     * 3. time only (i.e. no coordinates) e.g. 123p
     *
     *
     * more than one set of nuclei selection ...
     * COMPARE_[x-coordinate]x[y-coordinate]y[optional time in
     * milliseconds][n/p] ... _BEGIN_[x-coordinate]x[y-coordinate]y[optional
     * time in milliseconds][n/p] ... e.g.
     * COMPARE_12x45yp_98x65yp_BEGIN_13x42yp_102x69yp
     * COMPARE_12x45y1003000p_98x65y1004000p_BEGIN_13x42y1003000p_102x69y1004000p
     *
     *
     */
    public String showNucleiSelectionParamString();

    /**
     * check to see if scoring type is free text type==null => free text
     *
     */
    public boolean showIsScoringTypeFreeText();

    public boolean showIsScoringTypeNucleiCount();

    public boolean showIsScoringTypeNucleiCountNoCoord();
    
    public boolean showIsScoringTypeKi67Phase3();

    /**
     * return true if there are any nuclei selection notification linked to this
     * tma_scoring
     */
    public boolean showHasNucleiSelectionNotifications();
    
    /**
     * return all scoring_nuclei_selection_notifications
     * @return 
     */
    public SortedSet<Scoring_nuclei_selection_notifications> showScoring_nuclei_selection_notifications();

    /**
     * return the next select order ALSO INCREMENT maxSelect_order
     *
     */
    public int showNextSelect_order();

    /**
     * print out nice format of score type
     */
    public String showScoreType();

    /**
     * print out nice format of score
     *
     */
    public String showScore();
    
    /**
     * print out nice format of score in short form
     * 
     * @return 
     */
    public String showScoreShort();

    /**
     * show percent positive (0-1) return -1 if not applicable e.g. not nuclei
     * count
     *
     */
    public double showPercentPositive();
}
