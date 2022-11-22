package ca.ubc.gpec.tmadb

class User_permits implements Comparable<User_permits>{

    Users user
    Tma_slices tma_slice
    Whole_section_slices whole_section_slice
	
    static mapping = {
        user column:'user_id'
        tma_slice column:'tma_slice_id'
        whole_section_slice column:'whole_section_slice_id'
    }
	
    static constraints = {
        tma_slice (nullable: true)
        whole_section_slice (nullable: true)
    }
    
    /**
     * for comparable inteface
     * 1. compare by user
     * 2. compare by id
     */
    public int compareTo(User_permits other) {
        int compareByUser = this.user.compareTo(other.user);
        return compareByUser == 0 ?  this.getId().compareTo(other.getId()) : compareByUser;
    }
}
