package ca.ubc.gpec.tmadb

class Paraffin_blocks implements Comparable<Paraffin_blocks>, SecuredMethods {
    String specimen_number
    String additional_info
    String comment
    Tissue_types tissue_type
    Patients patient
    Paraffin_block_packages paraffin_block_package
    Surgical_blocks surgical_block
    Core_biopsy_blocks core_biopsy_block
    // NOTE: probably not a good idea to link to Tma_blocks even though
    //       a TMA block is a paraffin block, however, it does not associate
    //       with a single patient but can be > 1 patient
    SortedSet<Paraffin_block_logs> paraffin_block_logs
    SortedSet<Whole_section_slices> whole_section_slices
    SortedSet<Keyword_paraffin_blocks> keyword_paraffin_blocks;
    
    static constraints = {
        specimen_number(nullable:true)
        additional_info(nullable:true)
        comment(nullable:true)
        tissue_type(nullable:true)
        paraffin_block_package(nullable:true)
    }
    
    static hasOne = [ surgical_block:Surgical_blocks, core_biopsy_block:Core_biopsy_blocks ]
    
    static hasMany = [ whole_section_slices:Whole_section_slices, paraffin_block_logs:Paraffin_block_logs, keyword_paraffin_blocks:Keyword_paraffin_blocks ]
    
    static mapping = {
        tissue_type column:'tissue_type_id'
        patient column:'patient_id'
        paraffin_block_package column:'paraffin_block_package_id'
    }
    
    /**
     * for Comparable interface
     * 1. by specimen number 
     * 2. by id
     **/
    public int compareTo(Paraffin_blocks other) {
        int compareBySpecimen_number = 0;
        if (specimen_number != null && other.specimen_number != null) {
            this.specimen_number.compareTo(other.specimen_number);
        }
        return compareBySpecimen_number == 0 ? this.id - other.id : compareBySpecimen_number;
    }
    
    /**
     * override default to string
     */    
    public String toString() {
        String result = "";
        if (showIsSurgical_block()) {
            result = result + "Surgical block ";
        } else if (showIsCore_biopsy_block()) {
            result = result + "Core biopsy block ";
        } else {
            result = result + "Paraffin block ";
        }
        if (specimen_number != null) {
            if (specimen_number.length() != 0) {
                result = result + "("+specimen_number+")";
            }
        }
    }
    
    /**
     * show keyword with display_order 0
     * - return null if keyword_paraffin_blocks is empty
     */
    public Keywords showFirstKeyword() {
        if (keyword_paraffin_blocks==null) {
            return null;
        }
        if (keyword_paraffin_blocks.isEmpty()) {
            return null;
        }
        return keyword_paraffin_blocks?.first()?.keyword;
    }
    
    /**
     * check to see if this is available to the user
     * 
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {
            return true;
        }
        return surgical_block?.isAvailable(user) || core_biopsy_block?.isAvailable(user);
    }
    
    /**
     * check to see if this paraffin block is a surgical block
     */
    public boolean showIsSurgical_block() {
        return surgical_block != null;
    }
    
    /**
     * check to see if this paraffin block is a core biopsy block
     */
    public boolean showIsCore_biopsy_block() {
        return core_biopsy_block != null;
    }
}
