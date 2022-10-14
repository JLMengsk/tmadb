package ca.ubc.gpec.tmadb

/**
 * MySQL table to keep track of nuclei being selected
 */
class Nuclei_selections implements Comparable<Nuclei_selections> {

    public static final String X_LABEL = "x";
    public static final String Y_LABEL = "y";
    public static final String DELIMITER = "_";
    public static final String POSITIVE_LABEL = "p";
    public static final String NEGATIVE_LABEL = "n";
    
    Scorings scoring;
    Integer x;
    Integer y;
    String state;
    Date scoring_date;
    Integer select_order;
    
    static constraints = {
        scoring_date (nullable: true)
        x (nullable: true)
        y (nullable: true)
        scoring (nullable: false)
    }
    
    static mapping = {
        scoring column:'scoring_id'
    }

    static belongsTo = [scoring: Scorings]
    
    /**
     * for Comparable interface
     * @param other
     * @return
     */
    public int compareTo(Nuclei_selections other) {
        return select_order.compareTo(other.select_order);
    }
    
    /**
     * show if nuclei selected is labelled as positive
     */
    public boolean showIsPositive() {
        return state.equals(POSITIVE_LABEL);
    }
    
    /**
     * show time in milliseconds
     */
    public Long showTimeInMilliseconds() {
        return (scoring_date == null) ? null : scoring_date.getTime();
        // number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this scoring_date object..

    }
}
