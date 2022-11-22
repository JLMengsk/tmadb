package ca.ubc.gpec.tmadb

import java.util.Date;

/**
 * keep tracks of any events related to a scoring record
 * 
 */
class Scoring_logs implements Comparable<Scoring_logs> {

    Scorings scoring;
    String description;
    Date scoring_date;
    
    static constraints = {
        description (nullable: true)
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
    public int compareTo(Scoring_logs other) {
        int compareByScoring = this.scoring.id - other.scoring.id;
        if (compareByScoring != 0) {
            return compareByScoring;
        } else {
            int compareByScoring_date = this.scoring_date.CompareTo(other.scoring_date);
            return compareByScoring_date == 0 ? (this.id - other.id) : compareByScoring_date;
        }
    }
    
    /**
     * add a log by creating a new Scoring_logs object and set the time to now
     * @param scoring
     * @param description
     */
    public static void log(Scorings scoring, String description) {
        Scoring_logs log = new Scoring_logs();
        log.setScoring(scoring);
        log.setDescription(description);
        log.setScoring_date(new Date());
        log.save(flush:true); // best effort saving ... just ignore if failed to save
    }
}
