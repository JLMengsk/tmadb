package ca.ubc.gpec.tmadb

class Tissue_types {

    String name
    String description
	
    static hasMany = [ paraffin_blocks:Paraffin_blocks ]
	
    static constraints = {}
	
    /**
     * override default to string
     */
    public String toString() {
        return name;
    }
}
