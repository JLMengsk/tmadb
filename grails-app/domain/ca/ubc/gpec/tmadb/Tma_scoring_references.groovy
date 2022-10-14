package ca.ubc.gpec.tmadb

class Tma_scoring_references implements Comparable<Tma_scoring_references> {

    Tma_scorings tma_scoring;
    Tma_scorings reference_tma_scoring;
    
    static constraints = {
    }
    
    static mapping = {
        tma_scoring column:'tma_scoring_id'
        reference_tma_scoring column:'reference_tma_scoring_id'
    }
    
    public int compareTo(Tma_scoring_references other) {
        int compareByTma_scoring = this.tma_scoring.compareTo(other.tma_scoring);
        if (compareByTma_scoring != 0) {
            return compareByTma_scoring;
        } else {
            return this.reference_tma_scoring.compareTo(other.reference_tma_scoring);
        }
    }
}
