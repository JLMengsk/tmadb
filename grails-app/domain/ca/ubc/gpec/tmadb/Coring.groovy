package ca.ubc.gpec.tmadb

class Coring {

	Surgical_blocks surgical_block
	Coring_projects coring_project
	Coring_types coring_type
	Date checked_date
	Date cored_date
	String comment
	
    static constraints = {}
	
	static mapping = {
		surgical_block column:'surgical_block_id'
		coring_project column:'coring_project_id'
		coring_type    column:'coring_type_id'
	}
	
	static hasMany = [ rna_extractions:Rna_extractions ]
	
}
