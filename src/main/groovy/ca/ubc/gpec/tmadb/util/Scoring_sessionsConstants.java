/*
 * constants related to scoring sessions
 */
package ca.ubc.gpec.tmadb.util;

/**
 *
 * @author samuelc
 */
public class Scoring_sessionsConstants {

    public static final int SCORING_SESSION_TIMEOUT_IN_SECONDS = 3600 * 3; // 3 hours
    public static final String NUCLEI_COUNT_KEY_POSITIVE = "f";
    public static final String NUCLEI_COUNT_KEY_NEGATIVE = "d";
    public static final String NUCLEI_COUNT_KEY_UNDO = "z";
    public static final String NUCLEI_COUNT_STATUS_NUM_POS_ID = "numPos";
    public static final String NUCLEI_COUNT_STATUS_NUM_NEG_ID = "numNeg";
    public static final String NUCLEI_COUNT_STATUS_NUM_TOTAL_ID = "numTotal";
    public static final String NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID = "numPos_bottom";
    public static final String NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID = "numNeg_bottom";
    public static final String NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID = "numTotal_bottom";
    
    // some constants related to scoring_logs
    public static final String BEGIN_SCORING = "begin scoring";
    public static final String BEGIN_SELECT_HOT_SPOT = "begin select hot-spot";
    public static final String BEGIN_SCORE_HOT_SPOT = "begin score hot-spot";
    public static final String BEGIN_ESTIMATE_PERCENTAGES = "begin estimate percentages";
    public static final String BEGIN_SELECT_FIELDS = "begin select fields";
    public static final String BEGIN_SCORE_FIELDS = "begin score fields";
    
}
