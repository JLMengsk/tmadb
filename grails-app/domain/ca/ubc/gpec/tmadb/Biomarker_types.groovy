package ca.ubc.gpec.tmadb

class Biomarker_types implements Comparable<Biomarker_types>{

    String name
    String description
	
    static hasMany = [ biomarkers:Biomarkers ]
	
    static constraints = {
        name(blank: false) // only name is not null
    }
	
    // override default toString method
    public String toString() {
        return(name);
    }
    
    // for Comparable interface
    public int compareTo(Biomarker_types other) {
        return name.compareTo(other.name);
    }
}
