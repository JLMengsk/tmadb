/*
 * permission relating to Tma_arrays
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Tma_arrays;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Tma_arraysSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Tma_arraysSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Tma_arraysSecurityCheck(Users user, def params) {
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
        if (currentAction in ["show"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                // viewer or greater can try to list the TMA projects available to him/her
                Tma_arrays tma_array = Tma_arrays.get(params.id);
                if(tma_array == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to list the TMA projects available to him/her
                    return tma_array.isAvailable(user);
                }
            }
        }
        return false; // default - permission denied
    }
}

