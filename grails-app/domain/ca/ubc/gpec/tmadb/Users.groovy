package ca.ubc.gpec.tmadb

import javax.servlet.http.HttpSession
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer;

/**
 * need to impment Serializable since wanted to put this object in session 
 */
class Users implements Serializable, Comparable<Users>{

    public static final String ADMINISTRATOR_LOGIN = "admin"
	
    String login
    String password
    String name
    String email
    String description
    User_roles user_role
    Tma_scorers tma_scorer
    Date created_date
    Set<Notice_message_users> notice_message_users;
    SortedSet<User_permits> user_permits;
    
    static constraints = {
        login(unique:true)
        email(unique:true)
        password(password:true)
        tma_scorer(nullable:true)
        created_date(nullable:true)
    }
	
    static mapping = {
        user_role column:'user_role_id'
        tma_scorer column:'tma_scorer_id'
        
        user_role lazy: false // needed for showIsPaper_reader() ... to avoid "could not initialize proxy - no Session" error
    }
	
    static hasMany = [ notice_message_users:Notice_message_users, user_permits:User_permits ]
    
    /**
     * check to see if user is authorised to do something
     * @param session
     * @param Class
     * @param currentAction
     * @return
     */
    public static boolean isAuthorised(HttpSession inputSession, Class inputClass, String currentAction) {
        if (!inputSession.user) {
            // user not even set, failed for sure !!!
            return false
        }
		
        Users inputUser = Users.findByLogin(inputSession.user.login)
        int inputPrivilegeLevel = inputUser.getUser_role().getPrivilege_level()

        // SUPER USER Users.ADMINISTRATOR_LOGIN
        // allow this user to do anything !!!!
        if (inputUser.getLogin().equals(Users.ADMINISTRATOR_LOGIN)) {return true}
	
        // allow score_auditor or higher to audit TMA scores
        if (
            (inputClass in [ScoreTmaController.class] &
                currentAction in ["auditScores"])
        ) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORE_AUDITOR).getPrivilege_level()) {
                return true // allow anyone with scorer role or greater (smaller privilege_level, higher privilege)
            }
        }
        
        // allow scorer or higher to score TMA
        // TODO: need to fix permission ... currently tester can read/write each other's tests!!!!
        if (
            (inputClass in [
                    Scoring_sessionsController.class,
                    Tma_scoringsController.class,
                    ScoreTmaController.class,
                    DownloadDataController.class
                ] &
                currentAction in ["index","list","show","score","saveScore","report","downloadScoringSessionScores","downloadScoringSessionNucleiSelections","uploadNucleiSelection","removeLastNucleiSelection","submitScores","save_scoring_session_questions_answers"])
        ) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.SCORER).getPrivilege_level()) {
                return true // allow anyone with scorer role or greater (smaller privilege_level, higher privilege)
            }
        }
		
        // allow viewer or higher to view the following controllers to do index/list/show...
        if (
            (inputClass in [
                    BiomarkersController.class,
                    Tma_projectsController.class,
                    Tma_arraysController.class,
                    Tma_blocksController.class
                ] &
                currentAction in ["index","list","show"]) 
            |
            (inputClass in [
                    Tma_slicesController.class
                ] &
                currentAction in ["index","list","show","ajaxGetAvailableScorers","ajaxGetScannerInfos"])
            |
            (inputClass in [
                    Surgical_blocksController.class,
                    Staining_detailsController.class,
                    Tma_coresController.class,
                    Tma_core_imagesController.class,
                    Tma_resultsController.class,
                    Qpcr_experimentsController.class,
                    Qpcr_resultsController.class
                ] &
                currentAction in ["show"]) 
        ) {
            if (inputPrivilegeLevel <= User_roles.findByName(User_roles.VIEWER).getPrivilege_level()) {
                return true // allow anyone with viewer role or greater (smaller privilege_level, higher privilege)
            }
        }
		
        return false // authorisation failed
    }

	
    /**
     * override toString
     */
    public String toString() {
        return name
    }

    /**
     * for Comparable interface
     * NOTE: need this 2-steps (name,login) compareTo so that the sorting will be by
     *       name and login
     * 1. compare by name
     * 2. compare by login
     */ 
    int compareTo(Users other) {
        int compareByName = 0;
        if (name != null && other.name != null) {
            compareByName = name.compareTo(other.name);
        }
        return compareByName == 0 ? login.compareTo(other.login) : compareByName;
    }
    
    /**
     * check to see if this user is the administrator
     */
    public boolean showIsAdministrator() {
        return user_role.compareTo(User_roles.getAdministrator())==0;
    }  
    
    /**
     * show available slices to the users
     * @return
     */
    public SortedSet<Tma_slices> showAvailableTmaSlices() {
        ArrayList<User_permits> user_permits = User_permits.findAllByUser(this);
        TreeSet<Tma_slices> availableTmaSlices = new TreeSet<Tma_slices>();
        user_permits.each {
            availableTmaSlices.add(it.getTma_slice());
        }
        return availableTmaSlices;
    }
	
    /**
     * list available scoring sessions for a user who is a tma_scorer
     **/
    public SortedSet<Scoring_sessions> showAvailableScoringSessions() {
        TreeSet<Scoring_sessions> scoring_sessions = new TreeSet<Scoring_sessions>();
        if (tma_scorer != null) {
            Scoring_sessions.findAllByTma_scorer(tma_scorer).each {
                if (!it.showIsViewOptionHideFromScorer()) {
                    scoring_sessions.add(it);
                }
            }
        } 
        return scoring_sessions;
    }
    
    /**
     * check to see if this user is a score auditor
     */
    public boolean showIsScoreAuditor() {
        return user_role.name.equals(User_roles.SCORE_AUDITOR);
    }
    
    /**
     * check to see if this user is a paper reader
     */
    public boolean showIsPaper_reader() {
        return user_role.name.equals(User_roles.PAPER_READER);
    }

    /**
     * check to see if this user is a scorer
     */
    public boolean showIsScorer() {
        return user_role.name.equals(User_roles.SCORER);
    }
    
    /**
     * show any notice message for this user
     */ 
    public ArrayList<String> showNotice_messages() {
        ArrayList<String> messageList = new ArrayList<String>();
        notice_message_users.each {
            String message = it.notice_message.showMessage();
            if (message != null) {
                messageList.add(message);
            }
        }
       
        // remove last delimiter
        return messageList;
    }
    
    /**
     * return the preferred home controller name, based on User_role
     */
    public String preferredHomeControllerName() {
        return user_role.preferredHomeControllerName();        
    }
    
    /**
    * return true if this user passed the calibrator test session already.
    * otherwise return false (e.g. if not 
    */
    public boolean showKi67QcCalibratorTestPassed() {
        
    }
}
