package ca.ubc.gpec.tmadb

import java.util.Map;

class Patient_sources {

    String name
    String notes
    Institutions institution
	
    static hasMany = [ patients:Patients ]
	
    static constraints = {
        name(blank: false)
    }
	
    static mapping = {
        institution column:'institution_id'
    }
	
    /**
     * list patients available to this user with inputParams
     * @param inputParams
     * @param user
     * @return
     */
    public List<Patients> getPatients(Map inputParams, Users user) {
        // need to reset max and offset
        int max = -1
        int offset = -1
        if (inputParams.containsKey("max")) {
            max = inputParams.get("max")
            inputParams.remove("max")
            inputParams.put("max", patients.size()) // set max to highest possible
        }
        if (inputParams.containsKey("offset")) {
            offset = inputParams.get("offset")
            inputParams.remove("offset")
            inputParams.put("offset", 0) // set offset to lowest possible
        }
						
        def fullList = patients;
        List<Patients> resultList = new ArrayList<Patients>();
        fullList.each {
            if (((Patients)it).isAvailable(user)) {
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
     * override default toString()
     */
    public String toString() {
        return name+": "+notes;
    }
}
