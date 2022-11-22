package ca.ubc.gpec.tmadb

class Qpcr_results implements Comparable<Qpcr_results>, SecuredMethods {

    public static final int NUM_OF_DIGITS_TO_DISPLAY = 8; // number of digits to display for the double values ... number of digits INCLUDES the decimal point and negative sign if present
    
    Qpcr_experiments qpcr_experiment;
    Double normalized_expression;
    String comment;
    Biomarkers biomarker;
    String status;
    Double crossing_point;
    Double copy_number;
    String precalculated_text;
    Double precalculated_numeric;
    Date date_received;
    
    static constraints = {
    }
    
    /**
     * compare by ...
     * 1. qpcr_experiment
     * 2. biomarker type
     * 3. biomarker
     * 4. comment
     */
    public int compareTo(Qpcr_results other) {
        int compareByQpcr_experiment = qpcr_experiment.compareTo(other.qpcr_experiment);
        if (compareByQpcr_experiment != 0) {
            return compareByQpcr_experiment;
        }
        int compareByBiomarker_type = biomarker.biomarker_type.compareTo(other.biomarker.biomarker_type);
        if (compareByBiomarker_type != 0) {
            return compareByBiomarker_type;
        }
        int compareByBiomarker = biomarker.compareTo(other.biomarker);
        return compareByBiomarker != 0 ? compareByBiomarker : comment.compareTo(other.comment);
    }
    
    static mapping = {
        qpcr_experiment column:'qpcr_experiment_id'
        biomarker column:'biomarker_id'
    }
   
    /**
     * override default toString ... for display
     */
    public String toString() {
        // 1. display biomarker name and precalculated_text/numeric
        if (precalculated_text != null) {
            return biomarker.toString()+": "+precalculated_text 
        }
        if (precalculated_numeric != null) {
            return biomarker.toString()+": "+precalculated_numeric.toString().substring(0,NUM_OF_DIGITS_TO_DISPLAY);
        }
        // 2. display biomarker name and normalized expression
        if (normalized_expression != null) {
            return biomarker.toString()+": "+normalized_expression.toString().substring(0,NUM_OF_DIGITS_TO_DISPLAY);
        }
        
        // NA
        return biomarker.toString()+": NA";
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
        
        return true; // TODO: remember to implement later!!!!
        
    }
    
    /**
     * check to see if precalculated values are available
     */
    public boolean showContainsPrecalculatedValue() {
        return precalculated_text != null | precalculated_numeric != null;
    }
    
}
