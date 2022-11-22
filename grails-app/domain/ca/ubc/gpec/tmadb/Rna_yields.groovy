package ca.ubc.gpec.tmadb

class Rna_yields {

	Float concentration_ug_per_ul
	Float yield_ug
	Date record_date
	Rna_extractions rna_extraction
	String comment
	String source_description
	
    static constraints = {
		concentration_ug_per_ul (nullable: true)
		yield_ug (nullable: true)
	}
	
	static mapping = {
		rna_extraction column:'rna_extraction_id'
	}
}
