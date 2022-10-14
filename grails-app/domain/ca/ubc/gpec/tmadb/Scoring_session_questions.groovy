package ca.ubc.gpec.tmadb

/**
 * logistic/administrative questions to ask users before they begin the scoring session
 *
 **/

class Scoring_session_questions implements Comparable<Scoring_session_questions> {

    public static final String ANSWER_HTML_INPUT_PARAM_NAME = "answer";
    
    Integer display_order;
    String question;
    String answer;
    Scoring_sessions scoring_session;
    
    static constraints = {
        answer (nullable: true);
    }
    
    static mapping = { scoring_session column:'scoring_session_id' }
    
    /**
     * for Comparable interface
     * compare by scoring_session (1st) then display_order (2nd)
     **/
    public int compareTo(Scoring_session_questions otherQ) {
        int compareByScoring_session = scoring_session.compareTo(otherQ.scoring_session);
        return compareByScoring_session == 0 ? display_order.compareTo(otherQ.display_order) : compareByScoring_session;
    }
    
    /**
     * check to see if this question has been answered 
     * - currently just assumed, if not null and length > 0, means question
     *   has been answered. 
     **/
    public boolean showIsAnswered() {
        if (answer != null) {
            return answer.length() > 0;
        }
        return false; // answer == null, therefore, has not been answered
    }
}
