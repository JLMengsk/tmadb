/*
 * permission relating toStaining_details
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Staining_details;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Staining_detailsSecurityCheck extends SecurityCheck  {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Staining_detailsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Staining_detailsSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","show"]) { // currentAction == null i.e. index
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                // viewer or greater can try to list the TMA projects available to him/her
                if (!params.containsKey("id")) {
                    if (currentAction == null || currentAction in ["index","list"]) {
                        return true; // this must be from list action ... ok to proceed
                    } else {
                        return false; // other action(s) require id
                    }
                }
                Staining_details staining_detail = Staining_details.get(params.id);
                if(staining_detail == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to list the Staining_details available to him/her
                    return staining_detail.isAvailable(user);
                }
            }
        }
        return false; // default - permission denied
    }	
}

