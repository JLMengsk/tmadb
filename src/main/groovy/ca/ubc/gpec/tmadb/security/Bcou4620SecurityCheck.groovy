/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.ubc.gpec.tmadb.security
import ca.ubc.gpec.tmadb.Bcou4620;
import ca.ubc.gpec.tmadb.Users;

/**
 *
 * @author samuelc
 */
class Bcou4620SecurityCheck extends SecurityCheck {
    def params
    String currentAction
    Bcou4620 bcou4620
    
    /**
     * constructor 
     */
    public Bcou4620SecurityCheck(Users user, def params, String currentAction, Bcou4620 bcou4620) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
        this.bcou4543 = bcou4543;
    }
    
    /**
     * constructor 
     */
    public Bcou4620SecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
        this.bcou4620 = null;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Bcou4620SecurityCheck(Users user, def params) {
        super(user);
        this.params = params;
        this.currentAction = null;
        this.bcou4620 = null;
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

