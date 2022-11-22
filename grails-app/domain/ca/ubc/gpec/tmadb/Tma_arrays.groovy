package ca.ubc.gpec.tmadb

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Tma_arrays implements Comparable, SecuredMethods {
	
    String description
    Integer array_version
    Tma_projects tma_project
    SortedSet tma_blocks
	
    static hasMany = [ tma_blocks:Tma_blocks ]
	
    static mapping = {
        tma_project column:'tma_project_id'
        sort "array_version"
    }
	
    /**
     * overrides equals
     * @param obj
     * @return true/false
     */
    public boolean equals(Object obj) {
        if (obj == this) {return true}
        if (!(obj instanceof Tma_arrays)) {return false;}
        return(((Tma_arrays)obj).getId() == this.id)
    }
	
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
		
        def results1 = Tma_slices.withCriteria {
			'in'("tma_block", this.tma_blocks)
        }
        if (results1.size() == 0){return false} // no need to test further
		
        def results2 = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",results1)
            }
        }
        if (results2.size() > 0) {return true} else {return false}
    }
	
    /**
     * get tma_arrays available to this user
     * @param inputParams
     * @param user
     * @return
     */
    public static List<Tma_arrays> list(Map inputParams, Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_arrays.list(inputParams)}

        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", Tma_arrays.list().size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }
					   
        def fullList = Tma_arrays.list(inputParams)
        List<Tma_arrays> resultList = new ArrayList<Tma_arrays>();
        fullList.each {
            if (((Tma_arrays)it).isAvailable(user)) {
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
     * get number of tma_arrays available to user
     * @param user
     * @return
     */
    public static int count(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_arrays.list().size()}
	  
        def fullList = Tma_arrays.list()
	  
        List<Tma_arrays> resultList = new ArrayList<Tma_arrays>();
        fullList.each {
            if (((Tma_arrays)it).isAvailable(user)) {
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
    public static Tma_arrays get(String inputId, Users user) {
        def result = Tma_arrays.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }
		    
    /**
     * for Comparable interface
     * 1. compare by tma_project name
     * 2. compare by version number
     */
    public int compareTo(Object obj){
        int result = this.tma_project.getName().compareTo(((Tma_arrays)obj).tma_project.getName());
        if (result != 0) {
            return result;
        }
        result = this.array_version.compareTo( ((Tma_arrays)obj).getArray_version());
        // TODO: HARD CODE HERE!!!
        if (tma_project.getName()=="02-008") {
            return (-1*result); // for better display since its alwasy version x block A-J, version x-1 block K-Q
        } else {
            return result;
        }
    }
	
    // override default toString method
    public String toString() {
        return(description);
    }

	
    /**
     * get tma_blocks available to this user
     * @param user
     * @return
     */
    public List<Tma_blocks> getAvailableTma_blocks(Users user) {
        def fullList = tma_blocks.toList();
	   
        List<Tma_blocks> resultList = new ArrayList<Tma_blocks>();
        fullList.each {
            if (((Tma_blocks)it).isAvailable(user)) {
                resultList.add(it)
            }
        }
	   
        return resultList
    }
   
    /**
     * get tma_slices available to this user
     * @param user
     * @return tma_slices
     */ 
    public ArrayList<Tma_slices> getAvailableTma_slices(Users user) {
        TreeSet<Tma_slices> tma_slices = new TreeSet<>();
        getAvailableTma_blocks(user).each { tma_block->
            tma_block.getAvailableTma_slices(user).each {tma_slice->
                tma_slices.add(tma_slice);
            }
        }
        return tma_slices;
    }
    
    /**
     * get tma_slices available to this user
     * @param user
     * @param staining_detail - restrict list of slices to a single staining
     * @return tma_slices
     */
    public ArrayList<Tma_slices> getAvailableTma_slices(Users user, Staining_details staining_detail) {
        TreeSet<Tma_slices> tma_slices = new TreeSet<>();
        getAvailableTma_slices(user).each { tma_slice ->
            if(staining_detail.compareTo(tma_slice.getStaining_detail())==0) {
                tma_slices.add(tma_slice);
            }
        }
        ArrayList<Tma_slices> tma_slicesArr = new ArrayList<>();
        tma_slices.each {tma_slicesArr.add(it)}
        return tma_slicesArr;
    }
    
    /**
     * get tma_cores available to this user
     * @param user
     * @return
     */
    public ArrayList<Tma_cores> getAvailableTma_cores(Users user) {
        if (!isAvailable(user)) {
            return new ArrayList<Tma_cores>(); // user not allowed see any tma cores!
        }
        return Tma_cores.withCriteria {
		  'in'('tma_block', getAvailableTma_blocks(user))
        }
    }
    
    /**
     * get staining_details on this tm project available to the user
     * @param user
     * @return
     */
    public SortedSet<Staining_details> getAvailableStaining_details(Users user) {
        TreeSet<Staining_details> staining_details = new TreeSet<>();
        getAvailableTma_slices(user).each { tma_slice->
            staining_details.add(tma_slice.getStaining_detail())
        }
        return staining_details;
    }
    
    /**
     * get biomarkers on this tma project available to the user
     * @param user
     * @return
     */
    public SortedSet<Biomarkers> getAvailableBiomarkers(Users user) {
        TreeSet<Biomarkers> biomarkers = new TreeSet<>();
        getAvailableStaining_details(user).each { staining_detail->
            biomarkers.add(staining_detail.getBiomarker());
        }
        return biomarkers;
    }
}
