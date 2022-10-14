package ca.ubc.gpec.tmadb

class Staining_level_assessments implements Comparable<Staining_level_assessments> {

    Whole_section_scorings whole_section_scoring;
    Ihc_score_categories ihc_score_category;
    Float percentage;
    
    static mapping = {
        whole_section_scoring column:'whole_section_scoring_id'  
        ihc_score_category column:'ihc_score_category_id'
    }
    
    static constraints = {
    }
    
    static belongsTo = [whole_section_scoring:Whole_section_scorings]
    
    /**
     * for comparable interface
     * 1. by whole section scoring
     * 2. by ihc_score_category
     * 3. by percentage
     * 4. by id
     */
    public int compareTo(Staining_level_assessments other) {
        int compareByWhole_section_scoring = this.getWhole_section_scoring().compareTo(other.getWhole_section_scoring());
        if (compareByWhole_section_scoring != 0) {
            return compareByWhole_section_scoring;
        }
        int compareByIhc_score_category = this.getIhc_score_category().compareTo(other.getIhc_score_category());
        if (compareByIhc_score_category != 0) {
            return compareByIhc_score_category;
        }
        int compareByPercentage = this.percentage.compareTo(other.percentage);
        return compareByPercentage == 0 ? (this.getId() - other.getId()) : compareByPercentage;
        
    }
}
