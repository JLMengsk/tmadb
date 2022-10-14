package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.CustomCompare

class Ihc_score_categories implements Comparable<Ihc_score_categories> {
	
    String name
    Integer numeric_code
    String description
    Ihc_score_category_groups ihc_score_category_group
	
	
    static mapping = {
        ihc_score_category_group column:'ihc_score_category_group_id'
    }
		
    static constraints = {}
	
    static def listUninterpretableScoreCategories() {
        def results = Ihc_score_category_groups.withCriteria {
            or {
                eq("name",ScoreType.UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME)
                eq("name",ScoreType.UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)
            }
        }
        def results2 = Ihc_score_categories.withCriteria  {
			'in'("ihc_score_category_group", results)
        }
        return results2
    }
		
    // for Comparable interface
    public int compareTo(Ihc_score_categories obj){
		
        int compareNumeric_code = numeric_code.compareTo(obj.numeric_code)
		
        if (compareNumeric_code == 0) {
            return this.id - ((Ihc_score_categories)obj).id	// compare id - must be unique
        } else {
            return compareNumeric_code
        }
    }
	
    // override default toString method
    public String toString() {
        return(name+" : "+description);
    }
}
