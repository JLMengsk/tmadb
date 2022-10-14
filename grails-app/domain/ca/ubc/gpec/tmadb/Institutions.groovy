package ca.ubc.gpec.tmadb

class Institutions implements Comparable<Institutions> {

    public static final String INTERNET = "Internet";
    
    String name
    String description
    String country
		
    static constraints = {
    }
	
    // override default toString method
    public String toString() {
        return(name);
    }
    
    /**
     * for comparable interface
     */
    public int compareTo(Institutions other) {
        return this.name.compareTo(other.name);
    }
    
    /**
     * return Internet
     */
    public static Institutions getInternet() {
        return Institutions.findByName(INTERNET);
    }
}
