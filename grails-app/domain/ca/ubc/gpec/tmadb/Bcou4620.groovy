package ca.ubc.gpec.tmadb

class Bcou4620 {

	Clinical_infos clinical_info
	int studynum // this cannot be null therefore its ok to use int instead of Integer
	int gpec_id  // this cannot be null therefore its ok to use int instead of Integer
	Integer er1
	Integer er2
	Integer er3
	String registry_group
	Integer age_at_diagnosis
	String sex
	Date diagnosi
	String laterality
	String site
	String site_desc
	String tumloc
	Date site_admit_date
	String status_at_referral
	String hx_bilatca_fst_deg_rel
	String meno_status
	String behavior
	String hist1
	String hist1_desc
	String hist2
	String hist2_desc
	String hist3
	String hist3_desc
	String histcat
	String grade
	Float size_lesion
	String marg_at_init_dx
	Integer erresult
	String missing_erresult
	String immuno
	String er
	String erposneg
	String lvnnew
	Integer posnodes
	String missing_posnodes
	Integer negnodes
	String missing_negnodes
	Integer totnodes
	String tnm_clin_yr
	String tnm_clin_t
	String tnm_clin_n
	String tnm_clin_m
	String tnm_surg_yr
	String tnm_surg_t
	String tnm_surg_n
	String tnm_surg_m
	String m1
	String nodestat
	String chemflag
	String chemtype
	String hormflag
	String hormtype
	String systemic
	String sys
	String ad
	String partial
	String complete
	String finsurg
	String rtintent
	String brchwrt
	String nodalrt
	String boost
	String finrt
	String localtx
	String locind
	Date locdate
	String locsite
	String locnarr
	String regind
	Date regdate
	String regsite
	String regnarr
	String distind
	Date distdate
	String distsite
	String distnarr
	String subbrca
	Date subbrdat
	String pat_stat
	Date death_da
	String bccacod
	String bcca_cod_desc
	String dvsprim
	String death_cause_desc
	String dvssec
	String death_sec_cause_desc
	Float survyrs
	String statjn04
	String brdeath
	Date brdthdat
	Date disenddt
	Float distsurv
	String diststat
	Date regenddt
	Float regsurv
	String regstat
	Date locenddt
	Float locsurv
	String locstat
	Date locregdt
	Date lrgenddt
	Float lrgsurv
	String lrgstat
	Date anyreldt
	Date anyenddt
	Float anysurv
	String anystat
	Date anyevndt
	Date evnenddt
	Float evntsurv
	String evntstat
	String subgroup
	String testvsvalid 
	
    static constraints = {
		studynum(blank:false)
		gpec_id(blank:false)
    }
	
	static mapping = {
		clinical_info column:'clinical_info_id'
	}
}
