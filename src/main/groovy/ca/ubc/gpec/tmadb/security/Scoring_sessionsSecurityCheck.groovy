/*
 * permission relating to Scoring_sessions
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Scoring_sessionsSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Scoring_sessionsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Scoring_sessionsSecurityCheck(Users user, def params) {
        super(user);
        this.params = params;
        this.currentAction = null;
    }
    
    /**
     * check to see if permission is granted for this user/action
     */ 
    public boolean showAuthorised() {
        if (this.showAuthorisedBasedOnUser()) {
            return true;
        }
        int inputPrivilegeLevel = getInputPrivilegeLevel();
        if (currentAction == null) {
            // list and index actions are NOT available for scoring_session ... use ScoreTma controller
            return false; 
        } else if (currentAction in ["submitScores",
                                        "resetScores",
                                        "saveScore",
                                        "save_scoring_session_questions_answers",
                                        "score",
                                        "report",
                                        "ajax_get_report_table",
                                        "save_ki67_qc_phase3_init",
                                        "save_ki67_qc_phase3_estimate_percent",
                                        "save_ki67_qc_phase3_select_region",
                                        "ajax_ki67_qc_phase3_go_back"
            ]) { // currentAction == null i.e. index
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // viewer or greater can try to list the Scoring_session(s) available to him/her
                if (!params.containsKey("id")) {
                    // list and index actions are NOT available for scoring_session ... use ScoreTma controller
                    return false; // action requires an id
                }
                Scoring_sessions scoring_session = Scoring_sessions.get(params.id);
                if(scoring_session == null) {
                    return false; // failed to find object
                } else {
                    if (scoring_session.isAvailable(user)) {
                        // viewer or greater can try to list the TMA projects available to him/her
                        return true;
                    } else {
                        // score auditor does not own any scoring_session ... however,
                        // score auditor should be allowed to view the scores i.e. action:"score","report"
                        if (currentAction in ["score","report","ajax_get_report_table"]) {
                            return (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORE_AUDITOR).getPrivilege_level())
                        }
                    }
                }
            }
        } else if (currentAction in ["setup","uploadCoreImagesForScoring"]) {
            return false; // currently these actions are only available for admin ... which would be authorized by this.showAuthorisedBasedOnUser()
        }
        return false; // default - permission denied
    }	
}

