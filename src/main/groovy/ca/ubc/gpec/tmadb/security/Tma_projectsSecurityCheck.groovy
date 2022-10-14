/*
 * permission relating to Tma_projects
 */

package ca.ubc.gpec.tmadb.security

import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.Tma_projects;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.User_roles;

/**
 *
 * @author samuelc
 */
class Tma_projectsSecurityCheck extends SecurityCheck {
    def params
    String currentAction
    
    /**
     * constructor
     */
    public Tma_projectsSecurityCheck(Users user, def params, String currentAction) {
        super(user);
        this.params = params;
        this.currentAction = currentAction;
    }
	
    /**
     * constructor (no currentAction defined yet)
     */
    public Tma_projectsSecurityCheck(Users user, def params) {
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
        if (currentAction == null || currentAction in ["index","list","show","ajaxGetTma_projects"]) { // currentAction == null i.e. index
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                // viewer or greater can try to list the TMA projects available to him/her
                if (!params.containsKey("id")) {
                    if (currentAction == null || currentAction in ["index","list","ajaxGetTma_projects"]) {
                        return true; // this must be from list action ... ok to proceed
                    } else {
                        return false; // other action(s) require id
                    }
                }
                Tma_projects tma_project = Tma_projects.get(params.id);
                if(tma_project == null) {
                    return false; // failed to find object
                } else {
                    // viewer or greater can try to list the TMA projects available to him/her
                    return tma_project.isAvailable(user);
                }
            }
        }
        return false; // default - permission denied
    }
}

