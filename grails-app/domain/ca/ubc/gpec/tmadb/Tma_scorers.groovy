package ca.ubc.gpec.tmadb

class Tma_scorers implements Comparable<Tma_scorers> {

    String name
    String description
    int human // this one cannot be null, so its ok to set to int instead of Integer
    Institutions institution
    Date created_date
	
    static constraints = {
        name(blank: false)
        human(blank: false)
        created_date(nullable:true)
    }
	
    static mapping = {
        institution column:'institution_id'
    }
	
    // override default toString method
    public String toString() {
        return(name+" ("+description+")");
    }
        
    /**
     * for Comparable interface
     * 
     */
    public int compareTo(Tma_scorers other) {
        int compareByName = this.name.compareTo(other.name);
        if (compareByName != 0) {
            return compareByName;
        } else {
            int compareByInstitution = this.institution.compareTo(other.institution);
            return compareByInstitution == 0 ? this.id - other.id : compareByInstitution;
        }
    }
    
    public String showShortName() {
        return name;
    }
}
