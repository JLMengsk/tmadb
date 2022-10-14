/*
 * permission relating to Whole_section_slices
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;
import ca.ubc.gpec.tmadb.Whole_section_images;

/**
 *
 * @author samuelc
 */
class Whole_section_slicesSecurityCheck extends SecurityCheck {
    def params
    String currentAction
	
    /**
     * constructor
     */
    public Whole_section_slicesSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Whole_section_slicesSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","ajaxGetAvailableWhole_section_slices"]) {
            // viewer ok for index
            return inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level();
        }
        if (currentAction in ["show"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                // viewer or greater can try to view any whole section slices available to him/her
                Whole_section_images whole_section_image = Whole_section_images.get(params.id);
                if(whole_section_image == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to view any whole section slices available to him/her
                    return whole_section_image.isAvailable(user);
                }
            }
        }
        return false; // default - permission denied
    }	
}

