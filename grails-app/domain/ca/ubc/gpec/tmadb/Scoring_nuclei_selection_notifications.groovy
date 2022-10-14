package ca.ubc.gpec.tmadb

class Scoring_nuclei_selection_notifications implements Comparable<Scoring_nuclei_selection_notifications> {

    Scorings scoring;
    Nuclei_selection_notifications nuclei_selection_notification;
        
    static mapping = {
        scoring column:'scoring_id'
        nuclei_selection_notification column:'nuclei_selection_notification_id'
    }
    
    static belongsTo = [scoring: Scorings]
    
    /**
     * for Comparable interface
     * 1. compare by nuclei_selection_notification
     * 2. compare by id
     */
    public int compareTo(Scoring_nuclei_selection_notifications other) {
        if (other == null) {
            return 1; // let null be always the smallest
        }
        int compareByNotification = nuclei_selection_notification.compareTo(other.nuclei_selection_notification);
        return compareByNotification == 0 ?  this.id - other.id : compareByNotification;
    }
}
