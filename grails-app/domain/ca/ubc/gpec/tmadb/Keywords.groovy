package ca.ubc.gpec.tmadb

/**
 * keywords for various resources
 * - each resource will have their own linking table e.g. keyword_surgical_blocks
 * - do not implement SecureMethod ... allow public access
 */
class Keywords implements Comparable<Keywords>{

    String name
    String description
    SortedSet<Keyword_paraffin_blocks> keyword_paraffin_blocks;
    
    static constraints = {
        name(blank: false) // only name is not null
        description (nullable: true)
    }
    
    static hasMany = [ keyword_paraffin_blocks:Keyword_paraffin_blocks ]
    
    /**
     * override 
     */
    public String toString() {
        return name;
    }
    
    /**
    * for Comparable interface
    */
    public int compareTo(Keywords keyword) {
        int compareByName = this.name.compareTo(keyword.name);
        return compareByName == 0 ? this.id - keyword.id : compareByName;
    }
}
