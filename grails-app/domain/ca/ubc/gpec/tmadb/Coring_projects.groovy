package ca.ubc.gpec.tmadb

class Coring_projects {

	String name
	String description
	Date start_date
	Date end_date
	
	static hasMany = [ coring:Coring ]
	
    static constraints = {}
	
	/**
	* override default toString()
	*/
   public String toString() {
	   return name+"("+description+")"
   }
}
