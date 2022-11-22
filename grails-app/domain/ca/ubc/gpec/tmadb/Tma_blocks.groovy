package ca.ubc.gpec.tmadb

import java.util.List;
import java.util.Map;

import ca.ubc.gpec.tmadb.util.*;

class Tma_blocks implements Comparable<Tma_blocks>, SecuredMethods {
    String name
    String description
    Tma_arrays tma_array
    SortedSet<Tma_slices> tma_slices
    SortedSet<Tma_cores> tma_cores
    Integer col_gap; // +ve col_gap means repeat gap every col_gap, -ve means do it once only
    Integer row_gap; // +ve row_gap means repeat gap every row_gap, -ve means do it once only
    
    static hasMany = [ tma_slices:Tma_slices, tma_cores:Tma_cores ]
	
    static mapping = {
        tma_array column:'tma_array_id'
        sort "name"
    }
        
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
		
        if (this.tma_slices == null) {return false}
        if (this.tma_slices.size() == 0) {return false}
		
        def results = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",this.tma_slices)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }
	
    /**
     * get tma_blocks available to this user
     * @param inputParams
     * @param user
     * @return
     */
    public static List<Tma_blocks> list(Map inputParams, Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_blocks.list(inputParams)}

        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", Tma_blocks.list().size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }
						
        def fullList = Tma_blocks.list(inputParams)
        List<Tma_blocks> resultList = new ArrayList<Tma_blocks>();
        fullList.each {
            if (((Tma_blocks)it).isAvailable(user)) {
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
     * get number of tma_blocks available to user
     * @param user
     * @return
     */
    public static int count(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_blocks.list().size()}
	   
        def fullList = Tma_blocks.list()
	   
        List<Tma_blocks> resultList = new ArrayList<Tma_blocks>();
        fullList.each {
            if (((Tma_blocks)it).isAvailable(user)) {
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
    public static Tma_blocks get(String inputId, Users user) {
        def result = Tma_blocks.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }
	
    /**
     * for Comparable interface
     * 1. compare by tma_array
     * 2. compare by name
     * 3. compare by id
     */
    public int compareTo(Tma_blocks other) {
        int compareByTma_array = this.tma_array.compareTo(other.tma_array);
        if (compareByTma_array != 0) {
            return compareByTma_array;
        } else {
            int compareByName = CustomCompare.compareNumBeforeNonNum(this.name, other.name);
            return compareByName == 0 ? this.id - other.id : compareByName;
        }
    }
    
    /**
     * override default toString method
     */
    public String toString() {
        return("("+tma_array.getTma_project().getName()+"v"+tma_array.getArray_version()+") block: "+name);
    }
	
    // get the number of columns (i.e. maximum column number) in this blocks
    public int maxCol() {
        int maximumColumn = 0;	
        tma_cores.each { 
            int tempColumn = ((Tma_cores)it).getCol()
            if (tempColumn > maximumColumn) {
                maximumColumn = tempColumn;
            }
        }
        return maximumColumn;
    }
	
    /**
     * get the number of columns (i.e. maximum column number) in this blocks
     */
    public int showMaxCol() {
        return maxCol();
    }
    
    /**
     *
     */
    public int showMaxRow() {
        int maximumRow = 0;	
        tma_cores.each { 
            int tempRow = ((Tma_cores)it).getRow()
            if (tempRow > maximumRow) {
                maximumRow = tempRow;
            }
        }
        return maximumRow;
    }
    
    /**
     * get tma_slices available to this user
     * @param user
     * @return
     */
    public List<Tma_slices> getAvailableTma_slices(Users user) {
        def fullList = tma_slices.toList();
	   
        List<Tma_slices> resultList = new ArrayList<Tma_slices>();
        fullList.each {
            if (((Tma_slices)it).isAvailable(user)) {
                resultList.add(it)
            }
        }
	   
        return resultList
    }
    
    /**
     * return col_gap; Integer.MIN_VALUE if col_gap == null
     */
    public int showCol_gap() {
        return col_gap==null ? Integer.MIN_VALUE : col_gap.intValue();
    }
    
    /**
     * return row_gap; Integer.MIN_VALUE if row_gap == null
     */
    public int showRow_gap() {
        return row_gap==null ? Integer.MIN_VALUE : row_gap.intValue();
    }
    
}
