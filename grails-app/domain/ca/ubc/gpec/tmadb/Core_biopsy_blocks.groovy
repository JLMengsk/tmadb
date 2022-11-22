package ca.ubc.gpec.tmadb
/**
 * a block containing core biopsy
 */
class Core_biopsy_blocks implements Comparable<Core_biopsy_blocks>, GenericParaffin_blocks, SecuredMethods {
    Paraffin_blocks paraffin_block
    Integer num_cores
    
    static constraints = {}
    
    static mapping = {
        paraffin_block column:'paraffin_block_id'
    }
    
    static belongsTo = [ paraffin_block:Paraffin_blocks ]
        
    /**
     * for Comparable interface
     * 1. by paraffin block
     * 2. by id
     **/
    public int compareTo(Core_biopsy_blocks other) {
        int compareByParaffin_block = this.paraffin_block.compareTo(other.paraffin_block);
        return compareByParaffin_block == 0 ? this.id - other.id : compareByParaffin_block;
    }
    
    // override default toString method
    public String toString() {
        return("specimen #: "+paraffin_block.getSpecimen_number()+" (core biopsy block)");
    }
    
    /**
     * check to see if this is available to the user
     * TODO: since its not likely this will link to TMA, need to figure out
     *       permission scheme for resources linking to this object
     *       
     *       CURRENTLY ADMIN only
     *       
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {
            return true;
        }
        boolean result = false;
        return result;
    }
    
    /**
     * set specimen number; for GenericParaffin_blocks interface
     * @param specimen_number 
     */
    public void inputSpecimen_number(String specimen_number) {
        paraffin_block.setSpecimen_number(specimen_number);
    }
    
    /**
     * show specimen number; for GenericParaffin_blocks interface
     * @return 
     */
    public String showSpecimen_number() {
        return paraffin_block.getSpecimen_number();
    }
    
    /**
     * set additional info; for GenericParaffin_blocks interface
     * @param additional_info 
     */
    public void inputAdditional_info(String additional_info) {
        paraffin_block.setAdditional_info(additional_info);
    }
    
    /**
     * show additional info; for GenericParaffin_blocks interface
     * @return 
     */
    public String showAdditional_info() {
        return paraffin_block.getAdditional_info();
    }
    
    /**
     * set comment; for GenericParaffin_blocks interface
     * @param comment 
     */
    public void inputComment(String comment) {
        paraffin_block.setComment(comment);
    }
    
    /**
     * show comment; for GenericParaffin_blocks interface
     * @return 
     */
    public String showComment() {
        return paraffin_block.getComment();
    }
    
    /**
     * set tissue type; for GenericParaffin_blocks interface
     * @param tissue_type 
     */
    public void inputTissue_type(Tissue_types tissue_type) {
        paraffin_block.setTissue_type(tissue_type);
    }
    
    /**
     * show tissue type; for GenericParaffin_blocks interface
     * @return 
     */
    public Tissue_types showTissue_type() {
        return paraffin_block.getTissue_type();
    }
    
    /**
     * set patient; for GenericParaffin_blocks interface
     * @param patient 
     */
    public void inputPatient(Patients patient) {
        paraffin_block.setPatient(patient);
    }
    
    /**
     * show patient; for GenericParaffin_blocks interface
     * @return 
     */
    public Patients showPatient() {
        return paraffin_block.getPatient();
    }
    
    /**
     * set paraffin block package; for GenericParaffin_blocks interface
     * @param paraffin_block_package 
     */
    public void inputParaffin_block_package(Paraffin_block_packages paraffin_block_package) {
        paraffin_block.setParaffin_block_package(paraffin_block_package);
    }
    
    /**
     * show paraffin block package; for GenericParaffin_blocks interface
     * @return 
     */
    public Paraffin_block_packages showParaffin_block_package() {
        return paraffin_block.getParaffin_block_package();
    }
    
    /**
     * set whole section slice
     * @param whole_section_slices 
     */
    public void inputWhole_section_slice(Whole_section_slices whole_section_slices) {
        paraffin_block.getWhole_section_slices().add(whole_section_slices);
    }
    
    /**
     * show whole section slice
     * @return 
     */
    public SortedSet<Whole_section_slices> showWhole_section_slices() {
        return paraffin_block.getWhole_section_slices();
    }
    
    /**
     * show whole section slices available to the user
     * @param user
     * @return
     */
    public SortedSet<Whole_section_slices> getAvailableWhole_section_slices(Users user) {
        TreeSet<Whole_section_slices> whole_section_slices = new TreeSet<>();
        showWhole_section_slices().each { whole_section_slice ->
            if (whole_section_slice.isAvailable(user)) {
                whole_section_slices.add(whole_section_slice)
            }
        }
        return whole_section_slices;
    }
    
    /**
     * show whole section slices available to the user restricted by staining_detail
     * @param user
     * @param staining_detail
     * @return
     */
    public SortedSet<Whole_section_slices> getAvailableWhole_section_slices(Users user, Staining_details staining_detail) {
        TreeSet<Whole_section_slices> whole_section_slices = new TreeSet<>();
        getAvailableWhole_section_slices(user).each { whole_section_slice ->
            if (whole_section_slice.getStaining_detail().compareTo(staining_detail)==0) {
                whole_section_slices.add(whole_section_slice)
            }
        }
        return whole_section_slices;
    }
}
