package ca.ubc.gpec.tmadb

import java.util.List;
import java.util.Map;

class Tma_projects implements SecuredMethods {
	
    String name
    String description
    String built_by
    String core_id_name // core id name ... for data export e.g. "bseries_id" for 02-008
    SortedSet tma_arrays
	
    static hasMany = [ tma_arrays:Tma_arrays ]
					
    static constraints = {
        name(blank: false) // only name is not null
    }
	
    static mapping = {
        sort "name"
    }
	
    /**
     * overrides equals
     * @param obj
     * @return true/false
     */
    public boolean equals(Object obj) {
        if (obj == this) {return true}
        if (!(obj instanceof Tma_projects)) {return false;}
        return(((Tma_projects)obj).getId() == this.id)
    }
   
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
		
        def results1 = Tma_blocks.findAllByTma_array(this.tma_arrays)
        if (results1.size() == 0) {return false} // no need to test further
		
        def results2 = Tma_slices.findAllByTma_block(results1)
        if (results2.size() == 0) {return false} // no need to test further
		
        def results3 = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",results2)
            }
        }
        if (results3.size() > 0) {return true} else {return false}
    }
   	
    /**
     * get tma_projects available to this user
     * @param inputParams
     * @param user
     * @return
     */
    public static List<Tma_projects> list(Map inputParams, Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_projects.list(inputParams)}

        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", Tma_projects.list().size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }
					   
        def fullList = Tma_projects.list(inputParams)
        List<Tma_projects> resultList = new ArrayList<Tma_projects>();
        fullList.each {
            if (((Tma_projects)it).isAvailable(user)) {
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
     * get number of tma_projects available to user
     * @param user
     * @return
     */
    public static int count(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return Tma_projects.list().size()}
	  
        def fullList = Tma_projects.list()
	  
        List<Tma_projects> resultList = new ArrayList<Tma_projects>();
        fullList.each {
            if (((Tma_projects)it).isAvailable(user)) {
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
    public static Tma_projects get(String inputId, Users user) {
        def result = Tma_projects.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }	
	
    /**
     * get tma_arrays available to this user
     * @param user
     * @return
     */
    public ArrayList<Tma_arrays> getAvailableTma_arrays(Users user) {
        def fullList = tma_arrays.toList();
		
        ArrayList<Tma_arrays> resultList = new ArrayList<Tma_arrays>();
        fullList.each {
            if (((Tma_arrays)it).isAvailable(user)) {
                resultList.add(it)
            }
        }
		
        return resultList
    }
	
    /**
     * get tma_slices available to this user
     * @param user
     * @return list of tma_slices
     */
    public ArrayList<Tma_slices> getAvailableTma_slices(Users user) {
        def availableTma_arrays = getAvailableTma_arrays(user);
        if (availableTma_arrays.size() > 0) {
            def fullList = Tma_slices.withCriteria {
                    'in'('tma_block', Tma_blocks.withCriteria{
				'in'('tma_array', availableTma_arrays)
                    })
            }
            List<Tma_slices> resultList = new ArrayList<Tma_slices>();
            fullList.each {
                if (((Tma_slices)it).isAvailable(user)) {
                    resultList.add(it)
                }
            }
            return(resultList)
        } else {
            return(new ArrayList()); // return empty list
        }
    }
    
    /**
     * get tma_cores available to this user
     * @param user
     * @return
     */
    public ArrayList<Tma_cores> getAvailableTma_cores(Users user) {
        def availableTma_arrays = getAvailableTma_arrays(user);
        if (availableTma_arrays.size() > 0) {
            return Tma_cores.withCriteria {
			'in'('tma_block', Tma_blocks.withCriteria{
				'in'('tma_array', availableTma_arrays)
                    })
            }
        } else {
            return(new ArrayList()); // return empty list
        }
    }

    /**
     * get staining_details on this tm project available to the user
     * @param user
     * @return
     */
    public SortedSet<Staining_details> getAvailableStaining_details(Users user) {
        TreeSet<Staining_details> staining_details = new TreeSet<>();
        tma_arrays.each { tma_array->
            tma_array.getAvailableTma_slices(user).each { tma_slice->
                staining_details.add(tma_slice.getStaining_detail())
            }
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
    
    /**
     * check to see if the input tma_array part of this tma_project
     * @param tma_arrays
     * @return
     */
    public boolean containsTma_array(Tma_arrays tma) {
        for (Tma_arrays tma_array:tma_arrays) {
            if (tma_array.compareTo(tma)==0) {
                return true; // found!!!
            }
        }
        return false; // if there, then not part of this tma project
    }
    
    // override default toString method
    public String toString() {
        return("("+name+") "+description);
    }

}
