package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.security.Staining_detailsSecurityCheck
import ca.ubc.gpec.tmadb.util.MiscUtil;
import javax.servlet.http.HttpSession

class Staining_details implements SecuredMethods, Comparable<Staining_details> {

    String gpec_protocol_number
    String antibody_concentration
    String dilution
    String incubation
    String antigen_retrieval_method
    String detection_method
    Date staining_date
    Biomarkers biomarker
    Institutions staining_institution
    SortedSet<Tma_slices> tma_slices
    SortedSet<Whole_section_slices> whole_section_slices

    static hasMany = [ tma_slices:Tma_slices, whole_section_slices:Whole_section_slices ]

    static constraints = {
        gpec_protocol_number(nullable: true)
        antibody_concentration(nullable: true)
        dilution(nullable: true)
        incubation(nullable: true)
        antigen_retrieval_method(nullable: true)
        detection_method(nullable: true)
        staining_date(nullable: true)
        staining_institution(nullable: true)
    }

    static mapping = {
        biomarker column:'biomarker_id'
        staining_institution column: 'staining_institution_id'
    }

    /**
     * override toString
     */
    public String toString() {
        return biomarker.getName()+" ("+biomarker.getBiomarker_type().getName()+") stained"+ (staining_date!=null ? (" on "+MiscUtil.formatDate(staining_date)):"") +" @ "+staining_institution.getName();
    }

    /**
     * for Comparable interface
     * @param other
     * @return
     */
    public int compareTo(Staining_details other){
        // first compare by biomarker
        int compareByBiomarker = biomarker.compareTo(other.biomarker);
        if (compareByBiomarker != 0) {
            return compareByBiomarker;
        } else {
            // compare by staining_date
            int compareByDate = (staining_date != null && other.getStaining_date() != null) ? staining_date.compareTo(other.staining_date) : 0;
            if (compareByDate != 0) {
                return compareByDate;
            } else {
                boolean tma_slicesEmpty = tma_slices.isEmpty();
                boolean otherTma_slicesEmpty = other.getTma_slices().isEmpty();
                boolean whole_section_slicesEmpty = whole_section_slices.isEmpty();
                boolean otherWhole_section_slicesEmpty = other.getWhole_section_slices().isEmpty();
                if (!tma_slicesEmpty && !otherTma_slicesEmpty) {
                    return tma_slices.first().compareTo(other.getTma_slices().first());
                } else if (!whole_section_slicesEmpty && !otherWhole_section_slicesEmpty) {
                    return whole_section_slices.first().compareTo(other.getWhole_section_slices().first());
                } else {
                    // just compare by slice name regardless of tma or whole section
                    String thisSliceName = tma_slicesEmpty ? (whole_section_slicesEmpty? null : whole_section_slices.first().getName()) : tma_slices.first().getName();
                    String otherSliceName = otherTma_slicesEmpty ? (otherWhole_section_slicesEmpty ? null :  other.getWhole_section_slices().first().getName()) : other.getTma_slices().first().getName();
                    if (thisSliceName == null && otherSliceName == null) {
                        // can't even compare by name!!! just compare by id
                        return id - other.getId();
                    } else {
                        return thisSliceName.compareTo(otherSliceName);
                    }
                }
            }
        }
    }

    /**
     * for selecting biomarkers for download
     * @return
     */
    public String toStringForDownloadSelection() {

        // this arrays stained on
        TreeSet<Tma_arrays> arrays = new TreeSet<Tma_arrays>();
        Iterator<Tma_slices> itr = tma_slices.iterator();
        while (itr.hasNext()) {
            arrays.add(itr.next().tma_block.tma_array);
        }

        Iterator<Tma_arrays> itr2 = arrays.iterator();
        String array_desc = ""+itr2.next().array_version;
        while (itr2.hasNext()) {
            array_desc = array_desc+"/"+itr2.next().array_version;
        }

        return biomarker.getName()+
		" ("+biomarker.getBiomarker_type().getName()+
		"), array v"+
        array_desc+
		", "+
        MiscUtil.formatDate(staining_date)+
		", "+
        staining_institution.getName()
    }

    /**
     * return associated tma_arrays
     */
    public TreeSet<Tma_arrays> showTma_arrays() {
        TreeSet<Tma_arrays> arrays = new TreeSet<Tma_arrays>();
        if (tma_slices != null) {
            Iterator<Tma_slices> itr = tma_slices.iterator();
            while (itr.hasNext()) {
                arrays.add(itr.next().tma_block.tma_array);
            }
        }
        return arrays;
    }
    
    /**
     * return associated surgical_blocks
     */
    public TreeSet<Surgical_blocks> showSurgical_blocks() {
        TreeSet<Paraffin_blocks> paraffin_blocks = new TreeSet<Paraffin_blocks>();
        if (whole_section_slices != null) {
            Iterator<Whole_section_slices> itr = whole_section_slices.iterator();
            while (itr.hasNext()) {
                paraffin_blocks.add(itr.next().getParaffin_block());
            }
        }
        TreeSet<Surgical_blocks> surgical_blocks = new TreeSet<Surgical_blocks>();
        TreeSet<Core_biopsy_blocks> core_biopsy_blocks = new TreeSet<Core_biopsy_blocks>();
        for (Paraffin_blocks paraffin_block:paraffin_blocks) {
            Surgical_blocks s = paraffin_block.getSurgical_block();
            Core_biopsy_blocks c = paraffin_block.getCore_biopsy_block();
            if (s != null) {
                surgical_blocks.add(s);
            }
            if (c != null) {
                core_biopsy_blocks.add(c);
            }
        }
        return surgical_blocks;
    }
    
    /**
     * return associated core_biopsy_blocks
     */
    public TreeSet<Core_biopsy_blocks> showCore_biopsy_blocks() {
        TreeSet<Paraffin_blocks> paraffin_blocks = new TreeSet<Paraffin_blocks>();
        if (whole_section_slices != null) {
            Iterator<Whole_section_slices> itr = whole_section_slices.iterator();
            while (itr.hasNext()) {
                paraffin_blocks.add(itr.next().getParaffin_block());
            }
        }
        TreeSet<Surgical_blocks> surgical_blocks = new TreeSet<Surgical_blocks>();
        TreeSet<Core_biopsy_blocks> core_biopsy_blocks = new TreeSet<Core_biopsy_blocks>();
        for (Paraffin_blocks paraffin_block:paraffin_blocks) {
            Surgical_blocks s = paraffin_block.getSurgical_block();
            Core_biopsy_blocks c = paraffin_block.getCore_biopsy_block();
            if (s != null) {
                surgical_blocks.add(s);
            }
            if (c != null) {
                core_biopsy_blocks.add(c);
            }
        }
        return core_biopsy_blocks;
    }
    
    
    /**
     * for showing "tips" when displaying in showing biomarkers
     * - show the names of the TMA array / whole section this staining is done on
     * @return
     */
    public String showStainingNames() {
        // this arrays stained on
        TreeSet<Tma_arrays> arrays = new TreeSet<Tma_arrays>();
        if (tma_slices != null) {
            Iterator<Tma_slices> itr = tma_slices.iterator();
            while (itr.hasNext()) {
                arrays.add(itr.next().tma_block.tma_array);
            }
        }
        TreeSet<Paraffin_blocks> paraffin_blocks = new TreeSet<Paraffin_blocks>();
        if (whole_section_slices != null) {
            Iterator<Whole_section_slices> itr = whole_section_slices.iterator();
            while (itr.hasNext()) {
                paraffin_blocks.add(itr.next().getParaffin_block());
            }
        }
        TreeSet<Surgical_blocks> surgical_blocks = new TreeSet<Surgical_blocks>();
        TreeSet<Core_biopsy_blocks> core_biopsy_blocks = new TreeSet<Core_biopsy_blocks>();
        for (Paraffin_blocks paraffin_block:paraffin_blocks) {
            Surgical_blocks s = paraffin_block.getSurgical_block();
            Core_biopsy_blocks c = paraffin_block.getCore_biopsy_block();
            if (s != null) {
                surgical_blocks.add(s);
            }
            if (c != null) {
                core_biopsy_blocks.add(c);
            }
        }
        
        Iterator<Tma_arrays> itr2 = arrays.iterator();
        String array_names = "";
        if (itr2.hasNext()) {
            // different format needed for first staining
            array_names = "TMA: "+itr2.next().toString();
        }
        while (itr2.hasNext()) {
            array_names = array_names+", "+itr2.next().toString();
        }
        Iterator<Surgical_blocks> itr3 = surgical_blocks.iterator();
        // different format needed for first staining
        if (itr3.hasNext()) {
            array_names = (array_names.length()==0 ? "" : array_names+"; ")+"Whole section: "+itr3.next().toString();
        }
        while (itr3.hasNext()) {
            array_names = array_names+", "+itr3.next().toString();
        }
        Iterator<Core_biopsy_blocks> itr4 = core_biopsy_blocks.iterator();
        // different format needed for first staining
        if (itr4.hasNext()) {
            array_names = (array_names.length()==0 ? "" : array_names+"; ")+"Core biopsy section: "+itr4.next().toString();
        }
        while (itr4.hasNext()) {
            array_names = array_names+", "+itr4.next().toString();
        }
        return array_names
    }

    /**
     * return the tma_project this staining belongs to
     * ASSUME ONLY ONE PROJECT POSSIBLE
     * @return null if this is not available to the user
     */
    public Tma_projects getAvailableTma_project(Users user) {
        Tma_projects tma_project = tma_slices.first().getTma_block().getTma_array().getTma_project();
        if (tma_project.isAvailable(user)) {
            return tma_project
        } else {
            return null
        }
    }

    /**
     * return tma_arrays this staining belongs to and is available to the user
     * @param user
     * @return empty TreeSet if nothing is available to the user
     */
    public TreeSet<Tma_arrays> getAvailableTma_arrays(Users user) {
        TreeSet<Tma_arrays> tma_arrays = new TreeSet<Tma_arrays>();
        for (Tma_slices tma_slice : tma_slices) {
            Tma_arrays tma_array = tma_slice.getTma_block().getTma_array();
            if (tma_array.isAvailable(user)) {
                tma_arrays.add(tma_array);
            }
        }
        return tma_arrays
    }

    /**
     * return tma_slices this staining belongs to and is available to the user
     * @param user
     * @return
     */
    public TreeSet<Tma_slices> getAvailableTma_slices(Users user) {
        TreeSet<Tma_slices> availableTma_slices = new TreeSet<Tma_slices>();
        tma_slices.each {
            if (it.isAvailable(user)) {
                availableTma_slices.add(it);
            }
        }
        return availableTma_slices;
    }

    /**
     * return a list of surgical blocks available to the user
     * @param user
     * @return 
     */
    public TreeSet<Surgical_blocks> getAvailableSurgical_blocks(Users user) {
        TreeSet<Surgical_blocks> surgical_blocks = new TreeSet<>();
        showSurgical_blocks().each { surgical_block ->
            if (surgical_block.isAvailable(user)) {
                surgical_blocks.add(surgical_block);
            }
        }
        return surgical_blocks;
    }
        
    /**
     * return a list of core biopsy blocks available to the user
     * @param  user
     * @return
     */
    public TreeSet<Core_biopsy_blocks> getAvailableCore_biopsy_blocks(Users user) {
        TreeSet<Core_biopsy_blocks> core_biopsy_blocks = new TreeSet<>();
        showCore_biopsy_blocks().each { core_biopsy_block ->
            if (core_biopsy_block.isAvailable(user)) {
                core_biopsy_blocks.add(core_biopsy_block);
            }
        }
        return core_biopsy_blocks;
    }
    
    /**
     * return tma_slices this staining belongs to and is available to the user
     * @param user
     * @return
     */
    public TreeSet<Whole_section_slices> getAvailableWhole_section_slices(Users user) {
        TreeSet<Whole_section_slices> availableWhole_section_slices = new TreeSet<Whole_section_slices>();
        whole_section_slices.each {
            if (it.isAvailable(user)) {
                availableWhole_section_slices.add(it);
            }
        }
        return availableWhole_section_slices;
    }

    /**
     * return tma_cores available to the user
     * @param user
     * @return empty TreeSet if nothing is available to the user
     */
    public TreeSet<Tma_cores> getAvailableTma_cores(Users user) {
        TreeSet<Tma_cores> tma_cores = new TreeSet<Tma_cores>();
        for (Tma_slices tma_slice : tma_slices) {
            Tma_arrays tma_array = tma_slice.getTma_block().getTma_array();
            if (tma_array.isAvailable(user)) {
                tma_cores.addAll(tma_array.getAvailableTma_cores(user));
            }
        }
        return tma_cores;
    }


    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}

        if (this.tma_slices == null) {return false}
        if (this.tma_slices.size() == 0) {return false}

        def results = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",this.tma_slices)
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }

    /**
     * check to see if the user (in session) can create new biomarker instance
     */
    public static boolean showCanCreate(HttpSession inputSession) {
        
        Staining_detailsSecurityCheck check = new Staining_detailsSecurityCheck(
            inputSession.user, //Users user, 
            null, //def params, 
            "create" //String currentAction
        );
        
        return check.showAuthorised();
    }
    
    /**
     * return null if not found or user is not permitted
     * @param inputId
     * @param user
     * @return
     */
    public static Staining_details get(String inputId, Users user) {
        def result = Staining_details.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }

}
