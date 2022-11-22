package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.CustomCompare

class Ihc_score_category_groups implements Comparable<Ihc_score_category_groups> {
	
    String name
    String description
    SortedSet ihc_score_categories
	
    static constraints = {
        name(blank: false)
    }
	
    static hasMany = [ ihc_score_categories:Ihc_score_categories ]
	
    static def listInterpretableScoreCategoryGroups() {
        def results = Ihc_score_category_groups.withCriteria {
            and {
                ne("name",ScoreType.UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME)
                ne("name",ScoreType.UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)
            }
        }
        return results
    }
	
    // for Comparable interface
    // uninterpretable always smaller
    public int compareTo(Ihc_score_category_groups obj){	
        int compareName = this.name.compareTo(obj.getName());
        if (compareName != 0) {
            if (this.name.equals(ScoreType.UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME) | 
                this.name.equals(ScoreType.UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)) {
                return -1
            }
            if (((Ihc_score_category_groups)obj).getName().equals(this.name.equals(ScoreType.UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME)) | 
                ((Ihc_score_category_groups)obj).getName().equals(this.name.equals(ScoreType.UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME))) {
                return 1
            }
        } 
        return (compareName)
    }
	
    // override default toString method
    public String toString() {
        return(name);
    }
}
