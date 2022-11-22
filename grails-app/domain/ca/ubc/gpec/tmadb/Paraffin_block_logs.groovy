package ca.ubc.gpec.tmadb

import java.util.Date;

class Paraffin_block_logs implements Comparable<Paraffin_block_logs> {

    Integer in_gpec
    Date checked_date
    String activity
    Paraffin_blocks paraffin_block
	
    static constraints = {
        in_gpec (nullable: true)
        activity (nullable: true)
    }
	
    static mapping = {
        paraffin_block column:'paraffin_block_id'
    }
    
    /**
     * for comparable interface
     * 1. by paraffin_block
     * 2. by checked_date
     */
    public int compareTo(Paraffin_block_logs other) {
        int compareByParaffin_block = this.paraffin_block.compareTo(other.paraffin_block);
        return compareByParaffin_block == 0 ? this.checked_date.compareTo(other.checked_date) : compareByParaffin_block;
    }
}
