/*
 * permission relating to ScoreTmaController (there is no domain clasa associated with this controller)
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class ScoreTmaSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public ScoreTmaSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public ScoreTmaSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","ajax_get_available_scoring_session"]) { // currentAction == null i.e. index
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // scorer or greater can try to list the scoring session(s) available to him/her
                return true
            }
        } else if (currentAction in ["auditScores","ajax_get_submitted_scoring_sessions_for_score_audit"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORE_AUDITOR).getPrivilege_level()) {
                // scorer auditor or greater can audit scores of other scorer
                return true
            }
        }
        return false; // default - permission denied
    }	
}

