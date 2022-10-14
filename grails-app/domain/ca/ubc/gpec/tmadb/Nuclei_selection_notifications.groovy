package ca.ubc.gpec.tmadb

/**
 * notify user at certain interval during nuclei selection
 */
class Nuclei_selection_notifications implements Comparable<Nuclei_selection_notifications> {

    Integer nuclei_count; 
    String message;
    
    static constraints = {
    }
    
    /**
     * for Comparable interface
     */
    public int compareTo(Nuclei_selection_notifications other) {
        if (other == null) {
            return 1; // let null be always the smallest
        }
        int compareByMessage = message.compareTo(other.message);
        if (compareByMessage == 0) {
            if (other.nuclei_count == null) {
                return 1; // let null < any integer
            } else if (nuclei_count ==  null) {
                return -1;
            } else {
                return nuclei_count.compareTo(other.nuclei_count);
            }
        } else {
            return compareByMessage;
        }
    }
}
