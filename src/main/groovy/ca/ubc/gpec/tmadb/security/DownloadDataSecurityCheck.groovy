/*
 * permission relating to ScoreTmaController (there is no domain clasa associated with this controller)
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
class DownloadDataSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public DownloadDataSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public DownloadDataSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","tmaScores","tmaImages","downloadSingleTmaScores","downloadTmaCoreImageSelectionCopyScript"]) {
            return false; // currently only admin is allowed to do index anx list ... for listing available download options.
        }
        int inputPrivilegeLevel = getInputPrivilegeLevel();
        if (currentAction in ["downloadScoringSessionScores","downloadScoringSessionNucleiSelections"]) { 
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // viewer or greater can try to list the TMA projects available to him/her
                if (!params.containsKey("id")) {
                    return false; // need it for all actions (except index, list ... see above)
                }
                Scoring_sessions scoring_session = Scoring_sessions.get(params.id);
                if(scoring_session == null) {
                    return false; // failed to find object
                } else {
                    if (scoring_session.isAvailable(user)) {
                        // viewer or greater can download scoring session data that belongs to him/her
                        return true;
                    } else {
                        // score auditor can download scores from EVERYBODY!!!
                        return (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORE_AUDITOR).getPrivilege_level())
                    }
                }
            }
        }
        return false; // default - permission denied
    }
}

