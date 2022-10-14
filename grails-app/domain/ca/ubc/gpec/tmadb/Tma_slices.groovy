package ca.ubc.gpec.tmadb

import java.util.List;
import java.util.Map;

import ca.ubc.gpec.tmadb.util.*;
import ca.ubc.gpec.tmadb.Tma_scorers;

class Tma_slices implements Comparable<Tma_slices>, SecuredMethods {
	
    String name
    String description
    String comment
    Tma_blocks tma_block
    Date cut_date
    Staining_details staining_detail
    SortedSet<Tma_core_images> tma_core_images
    Float thickness
    Set<User_permits> user_permits;
            
    static hasMany = [ tma_core_images:Tma_core_images, user_permits:User_permits ]
	
    static constraint = {
        name(blank: false)
        description(nullable: true)
        comment(nullable: true)
        cut_date(nullable: true)
        thickness(nullable: true)
    }
	
    static mapping = {
        tma_block column:'tma_block_id'
        staining_detail column:'staining_detail_id'
        sort "name"
    }

    /**
     * return allowable thickness
     * TODO: get these values from some config file	
     * @return
     */
    public static float[] allowableThickness() {
        float[] allowableThickness = new float[4];
        allowableThickness[0] = 4f;
        allowableThickness[1] = 5f;
        allowableThickness[2] = 6f;
        allowableThickness[3] = 8f;
        return allowableThickness;
    }
	
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
		
        def results = User_permits.withCriteria {
            and {
                eq("user", user)
                eq("tma_slice", this)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }
   
	
    /**
     * get tma_slices available to this user
     * @param inputParams
     * @param user
     * @return
     */
    public static List<Tma_slices> list(Map inputParams, Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_slices.list(inputParams)}

        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", Tma_slices.list().size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }
						
        def fullList = Tma_slices.list(inputParams)
        List<Tma_slices> resultList = new ArrayList<Tma_slices>();
        fullList.each {
            if (((Tma_slices)it).isAvailable(user)) {
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
     * get number of tma_slices available to user
     * @param user
     * @return
     */
    public static int count(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_slices.list().size()}
	   
        def fullList = Tma_slices.list()
	   
        List<Tma_slices> resultList = new ArrayList<Tma_slices>();
        fullList.each {
            if (((Tma_slices)it).isAvailable(user)) {
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
    public static Tma_slices get(String inputId, Users user) {
        def result = Tma_slices.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }
	

    /**
     * for Comparable interface
     * 1. by tma block
     * 2. by name
     * 3. by id 
     */
    public int compareTo(Tma_slices obj){
        int compareByTma_block = this.tma_block.compareTo(obj.getTma_block());
        if (compareByTma_block != 0) {
            return compareByTma_block;
        } else {
            int compareByName = CustomCompare.compareNumBeforeNonNum(this.name, obj.name);
            return compareByName == 0 ? this.id - obj.id : compareByName;
        }
    }
    
    /**
     * override default toString method
     */
    public String toString() {
        return("("+tma_block.getTma_array().getTma_project().getName()+"v"+tma_block.getTma_array().getArray_version()+" block: "+tma_block.getName()+") slice: "+name);
    }
    
    /**
     * return true if a core in this tma_slices is from the input surgical_block
     */
    public boolean showContainsSurgical_block(Surgical_blocks surgical_block) {
        for(Tma_core_images tma_core_image : tma_core_images) {
            if(tma_core_image.getTma_core().getSurgical_block().compareTo(surgical_block)==0) {
                return true;
            }
        }
        // no matching surgical block found
        return false;
    }
	
}
