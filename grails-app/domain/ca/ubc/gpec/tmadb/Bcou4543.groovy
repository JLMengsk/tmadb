package ca.ubc.gpec.tmadb

class Bcou4543 {

	Clinical_infos clinical_info
	int gpec_id
	String registry_group
	String sex
	Date diagnosis_date
	Date site_admit_date
	String status_at_referral
	Integer age_at_diagnosis
	String meno_status
	String behavior
	String hist1
	String hist1_desc
	String a_o_2
	String hist2
	String hist2_desc
	String a_o_3
	String hist3
	String hist3_desc
	String site
	String site_desc
	String tnm_clin_yr
	String tnm_clin_t
	String tnm_clin_n
	String tnm_clin_m
	String tnm_surg_yr
	String tnm_surg_t
	String tnm_surg_n
	String tnm_surg_m
	Integer num_pos_nod_init_dx
	String missing_num_pos_nod_init_dx
	Integer num_neg_nod_init_dx
	String missing_num_neg_nod_init_dx
	Float size_lesion
	Integer er_result
	String missing_er_result
	String immuno_stains
	String er
	String erposneg
	String marg_at_init_dx
	String grade
	String lvn
	String lymph
	String veins
	String lvnnew
	String bcca_chemo
	String type_init_chemo
	String bcca_horm
	String type_init_horm
	String locind
	Date locdate
	String locsite
	String locsitedesc
	String regind
	Date regdate
	String regsite
	String regsitedesc
	Date distdate1
	Site_of_mets_codings distcat1
	String distsite1
	String distsitedesc1
	Date distdate2
	Site_of_mets_codings distcat2
	String distsite2
	String distsitedesc2
	Date distdate3
	Site_of_mets_codings distcat3
	String distsite3
	String distsitedesc3
	Date distdate4
	Site_of_mets_codings distcat4
	String distsite4
	String distsitedesc4
	Date distdate5
	Site_of_mets_codings distcat5
	String distsite5
	String distsitedesc5
	String pat_status
	Date death_date
	String bcca_cod
	String bcca_cod_desc
	String death_cause_original
	String death_cause_orig_desc
	String death_sec_cause
	String death_sec_cause_desc
	String distant_completeness
	
    static constraints = {
		gpec_id(blank:false)
	}
	
	static mapping = {
		clinical_info column:'clinical_info_id'
		distcat1 column:'distcat1'
		distcat2 column:'distcat2'
		distcat3 column:'distcat3'
		distcat4 column:'distcat4'
		distcat5 column:'distcat5'
	}
}
