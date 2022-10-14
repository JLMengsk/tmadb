package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.CustomCompare

class Tma_cores implements Comparable<Tma_cores>, SecuredMethods {
	
    String core_id
    String description
    Tma_blocks tma_block
    Surgical_blocks surgical_block
    Integer row
    Integer col
    Float diameter
    SortedSet<Tma_core_images> tma_core_images
	
    static hasMany = [ tma_core_images:Tma_core_images ]
	
    static constraints = {
        row(blank: false)
        col(blank: false)
    }
	
    static mapping = {
        tma_block column:'tma_block_id'
        surgical_block column:'surgical_block_id'
    }

    /**
     * return allowable diameters for TMA core
     * TODO: change this to get values from some config files
     * @return
     */
    public static float[] allowableDiameters() {
        float[] allowableDiameters = new float[4];
        allowableDiameters[0] = 0.6f;
        allowableDiameters[1] = 1.0f; 
        allowableDiameters[2] = 1.5f; 
        allowableDiameters[3] = 2.0f;
        return allowableDiameters;
    }
		
    // for Comparable interface
    public int compareTo(Tma_cores obj){
        int compareBlock = CustomCompare.compareNumBeforeNonNum(tma_block.name, obj.tma_block.name)
        if (compareBlock == 0) {
            int compareRow = row.intValue() - obj.row.intValue()
            if (compareRow == 0) {
                int compareByCol = col.intValue() - obj.col.intValue();
                return compareByCol == 0 ? this.id - obj.id : compareByCol;
            } else {
                return(compareRow)
            } 
        } else {
            return(compareBlock)
        }
    }
	
    // override default toString method
    public String toString() {
        return(tma_block.tma_array.getDescription()+"; blk: "+tma_block.getName()+"; row: "+row+"; col: "+col+"; "+surgical_block);
    }

    /**
     * show keyword with display_order 0
     * - return null if no keyword is associated with this object
     */
    public Keywords showFirstKeyword() {
        return surgical_block.showFirstKeyword();
    }
    
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}
				
        def results1 = Tma_slices.findAllByTma_block(this.tma_block)
        if (results1.size() == 0) {return false} // no need to test further
		
        def results2 = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",results1)
            }
        }
        if (results2.size() > 0) {return true} else {return false}
    }
	
    /**
     * return null if not found or user is not permitted
     * @param inputId
     * @param user
     * @return
     */
    public static Tma_cores get(String inputId, Users user) {
        Tma_cores result = Tma_cores.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }
        
    /**
     * return a sector map string for drawing a sector map with this core as the selected core
     * gr[row#]gc[col#]_sr[row#]sc[col#]_r[row#]c[col#]_r[row#]c[col#]_ ...
     *
     * gr = gap row # i.e. after this row, there should be a row gap e.g.
     * between sector 1 and 2 gc = gap col # i.e. after this col, there should
     * be a col gap e.g. between sector 1 and 3 sr = selected core row# sc =
     * selected core col#
     *
     * NOTE: there can be at most ONE selected core gr/gc/sr/sc ... -1 indicates
     * NA
     */
    public String showSectorMapString(){
        String mapString = "gr"+tma_block.showRow_gap()+"gc"+tma_block.showCol_gap()+"_sr"+row+"sc"+col;
        tma_block.getTma_cores().each {
            mapString = mapString + "_r" + it.getRow() + "c" + it.getCol();
        }
        return mapString;
    }
   
}
