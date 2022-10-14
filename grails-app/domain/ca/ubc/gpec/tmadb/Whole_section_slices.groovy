package ca.ubc.gpec.tmadb

class Whole_section_slices implements SecuredMethods, Comparable<Whole_section_slices> {

    Paraffin_blocks paraffin_block
    String name
    String description
    Date cut_date
    Staining_details staining_detail
    String comment
    Float thickness
    SortedSet<Whole_section_images> whole_section_images
    
    static hasMany = [ whole_section_images:Whole_section_images ]

    static constraints = { 
        name(blank: false)
        description(nullable: true)
        comment(nullable: true)
        cut_date(nullable: true)
        thickness(nullable: true)
    }

    static mapping = {
        paraffin_block column:'paraffin_block_id'
        staining_detail column:'staining_detail_id'
    }

    /**
     * for Comparable interface
     * 
     * 1. compare by paraffin_block
     * 2. name
     * 3. staining_detail's biomarker - DO NOT compare by staining_detail as staining_detail relies on whole_section_slices to compare
     * 4. id
     * 
     * @param other
     * @return
     */
    int compareTo(Whole_section_slices other) {
        int compareByParaffin_block = paraffin_block.compareTo(other.getParaffin_block());
        if (compareByParaffin_block != 0) {
            return compareByParaffin_block;
        }
        int compareByName = name.compareTo(other.getName());
        if (compareByName != 0) {
            return compareByName;
        }
        int compareByBiomarker = staining_detail.getBiomarker().compareTo(other.getStaining_detail().getBiomarker());
        return compareByBiomarker == 0 ? id.compareTo(other.getId()) : compareByBiomarker;
    }

    /**
     * TODO: NEED TO IMPLEMENT THIS!!!
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        return true
    }

    /**
     * just name e.g. "slice b2"
     */
    public String toStringShort() {
        return "slice " + name;
    }
    
    /**
     * override toString()
     */
    public String toString() {
        return "slice " + name+" ("+staining_detail+")";
    }
    
    /**
     * name with paraffin block e.g. "Surgical block (S09-12947) slice b2 (Ki67 (IHC) stained on 2010-12-23 @ GPEC)"
     */
    public String toStringWithParaffin_blockName() {
        return paraffin_block.toString() + " " + this.toString();
    }
}
