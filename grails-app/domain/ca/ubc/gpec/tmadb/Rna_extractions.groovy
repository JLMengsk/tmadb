package ca.ubc.gpec.tmadb

class Rna_extractions {

	Coring coring
	Date record_date
	String comment
	
	static hasMany = [ rna_yields:Rna_yields ]
	
    static constraints = {
    }
	
	static mapping = {
		coring column:'coring_id'
	}
}
