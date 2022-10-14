package ca.ubc.gpec.tmadb

class Clinical_infos {

	Date received_date
	String comment
	Patients patient
	
	static hasMany = [ specs2009:Specs2009, bcou4543:Bcou4543, bcou4620:Bcou4620 ]
	
    static constraints = {
		received_date(blank: false)
	}
	
	static mapping = {
		patient column:'patient_id'
	}
	
	/**
	 * override default toString
	 */
	public String toString(){
		return comment
	}
}
