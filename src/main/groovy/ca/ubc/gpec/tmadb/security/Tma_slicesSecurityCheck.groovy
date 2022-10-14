/*
 * permission relating to Tma_slices
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Tma_slices;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Tma_slicesSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Tma_slicesSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Tma_slicesSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","ajaxGetAvailableTma_slices"]) {
            // viewer ok for index
            return inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level();
        }
        if (currentAction in ["show","ajaxGetAvailableScorers"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                // viewer or greater can try to list the TMA projects available to him/her
                Tma_slices tma_slice = Tma_slices.get(params.id);
                if(tma_slice == null) {
                    return false; // failed to find object, ok only for list and ajaxGetAvailableTma_slices
                } else {
                    // viewer or greater can try to list the TMA projects available to him/her
                    return tma_slice.isAvailable(user);
                }                
            }
        }
        return false; // default - permission denied
    }
}

