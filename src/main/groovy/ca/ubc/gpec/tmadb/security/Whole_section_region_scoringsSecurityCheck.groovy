/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;
import ca.ubc.gpec.tmadb.Whole_section_region_scorings
/**
 *
 * @author samuelc
 */
class Whole_section_region_scoringsSecurityCheck extends SecurityCheck {
    def params
    String currentAction
	
    /**
     * constructor
     */
    public Whole_section_region_scoringsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Whole_section_region_scoringsSecurityCheck(Users user, def params) {
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
        if (currentAction in ["uploadNucleiSelection","set_scoring_date"]) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                // scorer or greater can try to score tma_scorings available to him/her
                Whole_section_region_scorings whole_section_region_scoring = Whole_section_region_scorings.get(params.id);
                if(whole_section_region_scoring == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to list the TMA projects available to him/her
                    return whole_section_region_scoring.isAvailable(user);
                }                
            }
        }
        return false; // default - permission denied
    }	
}

