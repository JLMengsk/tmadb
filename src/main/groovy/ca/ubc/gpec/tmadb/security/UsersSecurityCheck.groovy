/*
 * permission relating to UsersController
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Users
/**
 *
 * @author samuelc
 */
class UsersSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public UsersSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public UsersSecurityCheck(Users user, def params) {
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
        if (currentAction in ["login_"+ViewConstants.SHOW_BODY_ONLY_CONTROLLER_ACTION_SUFFIX,"logout","authenticate","create_account_showPageBodyOnly","save_create_account"]) {
            return true; // everybody can sign in/out/authenticate
        }
        return false; // default - permission denied
    }
}

