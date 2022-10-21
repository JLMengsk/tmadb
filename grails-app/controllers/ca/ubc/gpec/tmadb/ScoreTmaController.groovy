package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class ScoreTmaController {

    def index = { 
        Users user = Users.findByLogin(session.user.login)
        if (user.showIsScoreAuditor()) {
            redirect(base: grailsApplication.config.grails.serverSecureURL, action: "auditScores")
        } else {
            redirect(base: grailsApplication.config.grails.serverSecureURL, action: "list") 
        }
    }
	
    def list = {
        Users user = Users.findByLogin(session.user.login)
        // generate a message to reminder user to finish their scoring session!!!
        ArrayList<String> msgs = new ArrayList<>();

        int numNotStarted = 0; // number of scoring sessions not started
        int numStarted = 0; // number of scoring sessions that are started by not completed
        int numCompleted = 0; // number of scoring sessions that are completed but not submitted

        user.showAvailableScoringSessions().each { scoring_session->
            if (!scoring_session.showSubmitted()) {
                // either completed, started or not started
                if (!scoring_session.showCompleted()) {
                    // either started or not started
                    if (!scoring_session.showStarted()) {
                        // definitely not started
                        numNotStarted++;
                    } else {
                        // must be started but not completed
                        numStarted++;
                    }
                } else {
                    // mnust be completed but not submitted\
                    numCompleted++;
                }
            }
        }        
        
        if (numNotStarted > 0) {
            msgs.add("you have "+numNotStarted+" scoring session"+(numNotStarted==1?"":"s")+" not started.");
        } 
        if (numStarted    > 0) {
            msgs.add("you have "+numStarted   +" scoring session"+(numStarted==1   ?"":"s")+" not completed.");
        }
        if (numCompleted  > 0) {
            msgs.add("you have "+numCompleted +" scoring session"+(numCompleted==1 ?"":"s")+" completed but need submission of results.");
        }
        
        render(view: "list", model: [user: user, msgs: msgs])
    }
	
    def auditScores = {
        Users user = Users.findByLogin(session.user.login)
        render(view: "auditScores", model: [user: user])
    }
	









    /**
     * return a list of available scoring session
     * - assume user is logged in!!!
     */
    def ajax_get_available_scoring_session = {
        Users user = Users.findByLogin(session.user.login);
        SortedSet<Scoring_sessions> scoring_sessions = user.showAvailableScoringSessions();
        
        int unique_id=0; // unique id for dojo table
                
        render(contentType: "text/json") {
            identifier: "id"
            numRows: scoring_sessions.size()
            items: array{
                scoring_sessions.each { scoring_session -> 
                    // NOTE: items in array canNOT be null
                    item(
                        "id":unique_id++,
                        "start_date":MiscUtil.formatDate(scoring_session.getStart_date()),
                        "name":scoring_session.getName()+ViewConstants.AJAX_RESPONSE_DELIMITER_2+scoring_session.getDescription()+ViewConstants.AJAX_RESPONSE_DELIMITER+scoring_session.getId(),
                        "status":scoring_session.showStatusNotice()
                    )         
                }
            }
        }
    }





    /**
     * return a list of submitted scoring sessions for score audit
     */
    def ajax_get_submitted_scoring_sessions_for_score_audit = {
        TreeSet<Scoring_sessions> submitted_scoring_sessions = new TreeSet<Scoring_sessions>();
        Scoring_sessions.list().each { 
            if(it.showSubmitted()) {
                submitted_scoring_sessions.add(it);   
            }
        }
        int unique_id=0; // unique id for dojo table
        render(contentType: "text/json") {
            identifier: "id"
            numRows: submitted_scoring_sessions.size()
            items: array{    
                submitted_scoring_sessions.each {scoring_session -> 
                    // NOTE: items in array canNOT be null
                    item(
                        "id":unique_id++,
                        "scorer":scoring_session.getTma_scorer().getName(),
                        "scoring_date":MiscUtil.formatDate(scoring_session.getSubmitted_date()), // assume submitted date must NOT BE NULL if submitted!!!
                        "name":scoring_session.getName()+ViewConstants.AJAX_RESPONSE_DELIMITER_2+scoring_session.getDescription()+ViewConstants.AJAX_RESPONSE_DELIMITER+scoring_session.getId()
                    )         
                }
            }
        }
    }
}
