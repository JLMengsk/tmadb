package ca.ubc.gpec.tmadb

import java.util.TreeSet;
import javax.servlet.http.HttpSession

import ca.ubc.gpec.tmadb.security.BiomarkersSecurityCheck;

class Biomarkers implements SecuredMethods, Comparable<Biomarkers> {

    // some constants
    public static final String BIOMARKER_NAME_HE = "H&E";
    public static final String BIOMARKER_NAME_P63 = "P63";
    
    String name
    String description
    Biomarker_types biomarker_type
    SortedSet<Staining_details> staining_details
    SortedSet<Qpcr_results> qpcr_results
    
    static hasMany = [ staining_details:Staining_details, qpcr_results:Qpcr_results]

    static constraints = {
        name(blank: false) // only name is not null
    }

    static mapping = { biomarker_type column:'biomarker_type_id' }

    public String toString() {
        return(name+" ("+biomarker_type+")")
    }

    /**
     * for Comparable interface
     * @param other
     * @return
     */
    public int compareTo(Biomarkers other){
        // 1. compare by biomarker_type
        int compareByBiomarker_type = biomarker_type.compareTo(other.biomarker_type);
        if (compareByBiomarker_type != 0) {
            return compareByBiomarker_type
        }
        // 2. compare name
        int compareByName = name.compareTo(other.name);
        if (compareByName != 0) {
            return compareByName;
        } else {
            // if same name, use description
            return description.compareTo(other.description);
        }
    }
	
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}

        def results1 = Staining_details.findAllByBiomarker(this)
        if (results1.size() == 0) {return false} // no need to test further

        def results2 = Tma_slices.findAllByStaining_detail(results1)
        if (results2.size() == 0) {return false} // no need to test further

        def results = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",results2)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }

    /**
     * check to see if the user (in session) can edit this biomarker instance
     */
    public boolean showCanEdit(HttpSession inputSession) {
        
        BiomarkersSecurityCheck check = new BiomarkersSecurityCheck(
            inputSession.user, //Users user, 
            null, //def params, 
            "edit" //String currentAction
        );
        
        return check.showAuthorised();
    }
    
    /**
     * check to see if the user (in session) can delete this biomarker instance
     * - allow delete ONLY if this record is not associated with any staining_details record
     */
    public boolean showCanDelete(HttpSession inputSession) {
        
        BiomarkersSecurityCheck check = new BiomarkersSecurityCheck(
            inputSession.user, //Users user, 
            null, //def params, 
            "delete" //String currentAction
        );
        
        return check.showAuthorised() & staining_details.isEmpty() & qpcr_results.isEmpty();
    }
    
    /**
     * check to see if the user (in session) can create new biomarker instance
     */
    public static boolean showCanCreate(HttpSession inputSession) {
        
        BiomarkersSecurityCheck check = new BiomarkersSecurityCheck(
            inputSession.user, //Users user, 
            null, //def params, 
            "create" //String currentAction
        );
        
        return check.showAuthorised();
    }
    
    /**
     * return tma_slices this staining belongs to and is available to the user
     * @param user
     * @return
     */
    public TreeSet<Staining_details> getAvailableStainingDetails(Users user) {
        TreeSet<Staining_details> availableStainingDetails = new TreeSet<Staining_details>()
        staining_details.each {
            if (it.isAvailable(user)) {
                availableStainingDetails.add(it);
            }
        }
        return availableStainingDetails;
    }

    /**
     * get tma_slices available to this user
     * @param user
     * @return list of tma_slices
     */
    public ArrayList<Tma_slices> getAvailableTma_slices(Users user) {
        ArrayList<Tma_slices> resultList = new ArrayList();
        getAvailableStainingDetails(user).each { stainingDetails ->
            stainingDetails.tma_slices.each {
                if (((Tma_slices)it).isAvailable(user)) {
                    resultList.add(it)
                }
            }
        } 
        return(resultList)
    }
    
    /**
     * get biomarkers available to this user
     * @param inputParams
     * @param user
     * @return
     */
    public static List<Biomarkers> list(Map inputParams, Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Biomarkers.list(inputParams)}

        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", Biomarkers.list().size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }

        def fullList = Biomarkers.list(inputParams)
        List<Biomarkers> resultList = new ArrayList<Biomarkers>();
        fullList.each {
            if (((Biomarkers)it).isAvailable(user)) {
                resultList.add(it)
            }
        }

        // now apply max/offset
        if (offset == -1) {offset = 0} // set offset to 0 if not set in param
        if (resultList.size() > offset) {
            if (max == -1) {
                max = resultList.size() // set max to highest possible if not set in param
            } else {
                max = Math.min(offset + max, resultList.size()) // cannot go beyond length of list!!!
            }
            resultList = resultList.subList(offset, max)
        } else {
            // just return empty list
            resultList = new ArrayList()
        }
        
        return resultList;
    }

    /**
     * get number of biomarkers available to user
     * @param user
     * @return
     */
    public static int count(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Biomarkers.list().size()}

        def fullList = Biomarkers.list()

        List<Biomarkers> resultList = new ArrayList<Biomarkers>();
        fullList.each {
            if (((Biomarkers)it).isAvailable(user)) {
                resultList.add(it)
            }
        }
        return resultList.size();
    }

    /**
     * return null if not found or user is not permitted
     * @param inputId
     * @param user
     * @return
     */
    public static Biomarkers get(String inputId, Users user) {
        def result = Biomarkers.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }

}
