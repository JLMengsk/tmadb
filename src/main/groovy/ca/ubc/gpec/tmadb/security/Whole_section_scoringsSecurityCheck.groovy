/*
 * permission relating to Whole_section_scorings
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;
import ca.ubc.gpec.tmadb.Whole_section_scorings

/**
 *
 * @author samuelc
 */
class Whole_section_scoringsSecurityCheck extends SecurityCheck {
    def params
    String currentAction
	
    /**
     * constructor
     */
    public Whole_section_scoringsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Whole_section_scoringsSecurityCheck(Users user, def params) {
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
        if (currentAction in ["ajax_start_scoring_whole_section_region_scoring_and_get_id","set_scoring_date"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // scorer or greater can try to score tma_scorings available to him/her
                Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(params.id);
                if(whole_section_scoring == null) {
                    return false; // failed to find object
                } else {
                    // scorer or greater can access only if this record belongs to him/her
                    return whole_section_scoring.isAvailable(user);
                }                
            }
        }
        return false; // default - permission denied
    }	
}

