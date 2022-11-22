/*
 * permission relating to Tma_scorings
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Tma_scorings;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Tma_scoringsSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Tma_scoringsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Tma_scoringsSecurityCheck(Users user, def params) {
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
        if (currentAction == null) {
            return false;
        }
        int inputPrivilegeLevel = getInputPrivilegeLevel();
        if (currentAction in ["uploadNucleiSelection"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // scorer or greater can try to score tma_scorings available to him/her
                Tma_scorings tma_scoring = Tma_scorings.get(params.id);
                if(tma_scoring == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to list the TMA projects available to him/her
                    return tma_scoring.isAvailable(user);
                }                
            }
        }
        return false; // default - permission denied
    }
}

