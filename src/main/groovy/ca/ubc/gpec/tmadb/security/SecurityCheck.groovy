/*
 * responsible for security check ... whether or not a user is allowed to
 * view/do certain things
 */

package ca.ubc.gpec.tmadb.security
import ca.ubc.gpec.tmadb.Users;

/**
 * base case for doing security check
 * @author samuelc
 */
abstract class SecurityCheck {
    Users user; // the user wanting to do something
     
    /**
     * constructor
     */
    public SecurityCheck(Users inputUser) {
        this.user = inputUser;
    }
    
    /**
     * some privileges that can be given to the user regardless of controller/action
     */
    public boolean showAuthorisedBasedOnUser() {
        if (user==null) {
            return false; // not signed in, denied permission
        } else {
            return user.showIsAdministrator();
        }
    }
    
    /**
     * some privileges based on specific controller/action
     */
    public abstract boolean showAuthorised();
        
    /**
     * figure out input privilege level (lower, the more privilege)
     */
    protected int getInputPrivilegeLevel() {
        int inputPrivilegeLevel = Integer.MAX_VALUE;
        if (user != null) {
            inputPrivilegeLevel = user.getUser_role().getPrivilege_level();
        }
        return inputPrivilegeLevel;
    }
    
}

