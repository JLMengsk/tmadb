/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.ubc.gpec.tmadb.security
import ca.ubc.gpec.tmadb.Bcou4543;
import ca.ubc.gpec.tmadb.Users;

/**
 *
 * @author samuelc
 */
class Bcou4543SecurityCheck extends SecurityCheck {
    def params
    String currentAction
    Bcou4543 bcou4543
    
    /**
     * constructor 
     */
    public Bcou4543SecurityCheck(Users user, def params, String currentAction, Bcou4543 bcou4543) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
        this.bcou4543 = bcou4543;
    }
    
    /**
     * constructor 
     */
    public Bcou4543SecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
        this.bcou4543 = null;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Bcou4543SecurityCheck(Users user, def params) {
        super(user);
        this.params = params;
        this.currentAction = null;
        this.bcou4543 = null;
    }
    
    /**
     * check to see if permission is granted for this user/action
     */ 
    public boolean showAuthorised() {
        if (this.showAuthorisedBasedOnUser()) {
            return true;
        }
        return false; // default permission denied
    }
}

