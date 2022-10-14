package ca.ubc.gpec.tmadb

class Patients {

    String patient_id_txt
    String patient_id_txt2
    Patient_sources patient_source
    String comment
    SortedSet<Paraffin_blocks> paraffin_blocks	
    
    static hasMany = [ paraffin_blocks:Paraffin_blocks, clinical_infos:Clinical_infos ]
	
    static constraints = {}
	
    static mapping = {
        patient_source column:'patient_source_id'
    }
	
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
				
        def results1 = Tma_slices.withCriteria {
			'in'("tma_block", Tma_cores.withCriteria {
				'in'("surgical_block",surgical_blocks)
                    projections {property "surgical_block"}
                })
        }
        if (results1.size() == 0){return false} // no need to test further
		
        def results2 = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",results1)
            }
        }
        if (results2.size() > 0) {return true} else {return false}
    }
	
    /**
     * override default toString
     */
    public String toString() {
        String result = "Patient " + patient_id_txt;
        if (patient_id_txt2 != null) {
            result = result + " / "+patient_id_txt2;
        }
        return result;
    }
    
    /**
     * more detail name
     */
    public String toStringWithPatient_source() {
        return this.toString() + " from "+patient_source.toString();
    }
}
