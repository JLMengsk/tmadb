package ca.ubc.gpec.tmadb

class User_roles implements Comparable<User_roles> {

    public static final String ADMINISTRATOR = "administrator";
    public static final String SCORE_AUDITOR = "score auditor";
    public static final String SCORER = "scorer";
    public static final String PAPER_READER = "paper_reader"; // for people look at images associated with publications, same authority as VIEWER but a bit different behavior on the website
    public static final String VIEWER = "viewer";
    public static final String GUEST = "guest";
	
    String name
    String description
    int privilege_level
	
    static constraints = {
    }

    /**
     * for Comparable interface
     **/
    public int compareTo(User_roles other) {
        int compareByName = this.name.compareTo(other.name);
        return compareByName != 0 ? compareByName : (this.id - other.id);
    }
    
    /**
     * override toString()
     */
    public String toString(){
        return name
    }
    
    /**
     * return ADMINISTRATOR user role
     */
    public static User_roles getAdministrator() {
        return User_roles.findByName(ADMINISTRATOR);
    }
    /**
     * return SCORE_AUDITOR user role
     **/
    public static User_roles getScore_auditor() {
        return User_roles.findByName(SCORE_AUDITOR);
    }
    
    /**
     * return SCORER user role
     */
    public static User_roles getScorer() {
        return User_roles.findByName(SCORER);
    }
    
    /**
     * return VIEWER user role
     */
    public static User_roles getViewer() {
        return User_roles.findByName(VIEWER);
    }
    
    /**
     * return PAPER_READER user role
     */
    public static User_roles getPaper_reader() {
        return User_roles.findByName(PAPER_READER);
    }
    
    /**
     * return GUEST user role
     */
    public static User_roles getGuest() {
        return User_roles.findByName(GUEST);
    }
    
    /**
     * return the preferred home controller name, based on User_role
     */
    public String preferredHomeControllerName() {
        switch (name) {
        case PAPER_READER: 
            return "tma_slices"
            break
        case [SCORER, SCORE_AUDITOR]:
            return "scoreTma"
            break
        default: return null
        }
    }
}
