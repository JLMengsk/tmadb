package ca.ubc.gpec.tmadb

class Whole_section_results {

	Whole_section_images whole_section_image
	Date scoring_date
	Date received
	Tma_scorers tma_scorer1
	Integer total_nuclei_count
	Integer positive_nuclei_count
	Integer positive_membrane_count
	Integer positive_cytoplasmic_count
	Float visual_percent_positive_nuclei_estimate
	
    static constraints = {}
	
	static mapping = {
		whole_section_image column:'whole_section_image_id'
		tma_scorer1 column:'tma_scorer1_id'
	}
}
