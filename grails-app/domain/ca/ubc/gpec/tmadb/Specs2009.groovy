package ca.ubc.gpec.tmadb

class Specs2009 {

	Clinical_infos clinical_info
	Integer rna_number
	Integer tissue_number
	Integer percent_tumor
	Integer age_at_dx
	String race
	String gender
	String histology
	String t_stage
	String n_stage
	String m_stage
	String stage
	String er
	String pr
	String her2
	String paraffin_grade
	String ls_paraffin_grade
	String frozen_grade
	String nuclear_grade
	String architectual_grade
	String mitotic_grade
	Integer months_at_last_contact
	String vital_status
	String cancer_status
	Integer months_to_recurrance
	String recurrence_site_1
	String recurrence_site_2
	String adjuvant_chemo
	String adjuvant_hormone
	
    static constraints = {}
	
	static mapping = {
		clinical_info column:'clinical_info_id'
	}
	
}
