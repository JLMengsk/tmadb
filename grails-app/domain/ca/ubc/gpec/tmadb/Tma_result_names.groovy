package ca.ubc.gpec.tmadb
/**
 * variable names ...  mainly for data export 
 * - the variable name should follow the guidelines layout in:
 * http://www.gpec.ubc.ca/data_annotation_convention/GPEC_VARIABLE_NAMING_CONVENTION.doc
 * @author samuelc
 *
 */
class Tma_result_names implements Comparable<Tma_result_names> {

    String name;
    String description;
    Date last_modified;
    Set<Tma_results> tma_results;
    Set<Tma_result_default_score_name_view> tma_result_default_score_names;
    
    static hasMany = [ tma_results:Tma_results, tma_result_default_score_names:Tma_result_default_score_name_view ]
	
    static constraints = {
        description (nullable: true)
        last_modified (nullable: true)
    }

    /**
     * for Comparable interface
     */
    public int compareTo(Tma_result_names other) {
        int compareByName = this.name.compareTo(other.name);
        return compareByName == 0 ? (this.id - other.id) : compareByName;
    }
    
    String toString() {
        String projectDesc = "no score assigned yet"
        if (tma_results.size() > 0) {
            projectDesc = tma_results.iterator().next().getTma_core_image().getTma_slice().getTma_block().getTma_array().getTma_project();
        }
        return name+" ("+projectDesc+")";
    }
    
    /**
     * find Tma_result_names based on the input params ...
     * 
     * return null if nothing found OR if its uninterpretable
     * 
     * @param tma_core_image
     * @param tma_scorer1
     * @param tma_scorer2
     * @param tma_scorer3
     * @param scoring_date
     * @scoreType
     * 
     */
    public static Tma_result_names get(Tma_core_images tma_core_image,
	Tma_scorers tma_scorer1,
	Tma_scorers tma_scorer2,
	Tma_scorers tma_scorer3,
	Date scoring_date,
	def scoreType) {
        Tma_result_names tma_result_name = null;
        for (Tma_results tma_result:tma_core_image.getTma_results()) {
            if (tma_scorer1 == tma_result.tma_scorer1 && tma_scorer2 == tma_result.tma_scorer2 && tma_scorer3 == tma_result.tma_scorer3 && scoring_date == tma_result.scoring_date) {
                if (ScoreType.isUninterpretableScoreType(tma_result)) {
                    // cannot reliably get the tma_result ...
                    return tma_result.tma_result_name;
                } else {
                    if (scoreType instanceof Ihc_score_category_groups) {
                        if (tma_result.ihc_score_category?.ihc_score_category_group?.compareTo(scoreType)==0) {
                            return tma_result.tma_result_name;
                        }
                    } else {
                        String scoreTypeName = (String)scoreType 
                        if (
                            (scoreTypeName.equals(ScoreType.TOTAL_NUCLEI_COUNT) && tma_result.getTotal_nuclei_count() != null) || 
                            (scoreTypeName.equals(ScoreType.POSITIVE_NUCLEI_COUNT) && tma_result.getPositive_nuclei_count() != null) ||
                            (scoreTypeName.equals(ScoreType.POSITIVE_MEMBRANE_COUNT) && tma_result.getPositive_membrane_count() != null) ||
                            (scoreTypeName.equals(ScoreType.POSITIVE_CYTOPLASMIC_COUNT) && tma_result.getPositive_cytoplasmic_count() != null) ||
                            (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_ESTIMATE) && tma_result.getVisual_percent_positive_estimate() != null) ||
                            (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE) && tma_result.getVisual_percent_positive_nuclei_estimate() != null) ||
                            (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE)&& tma_result.getVisual_percent_positive_cytoplasmic_estimate() != null) ||
                            (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE) && tma_result.getVisual_percent_positive_membrane_estimate() != null) ||
                            (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE)&& tma_result.getVisual_percent_positive_cytoplasmic_and_or_membrane_estimate() != null) ||
                            (scoreTypeName.equals(ScoreType.POSITIVE_ITIL_COUNT) && tma_result.getPositive_itil_count() != null) ||
                            (scoreTypeName.equals(ScoreType.POSITIVE_STIL_COUNT) && tma_result.getPositive_stil_count() != null) ||
                            (scoreTypeName.equals(ScoreType.FISH_AMPLIFICATION_RATIO)&& tma_result.getFish_amplification_ratio() != null) ||
                            (scoreTypeName.equals(ScoreType.FISH_AVERAGE_SIGNAL)&& tma_result.getFish_average_signal() != null) ) {
                            return tma_result.tma_result_name;
                        }
                    }
                }
            }
        }
        return null; // nothing found
    }
        
    /**
     * check to see if last_modified got updated since last check
     */
    public boolean showIsDirty() {
        if (last_modified == null) {
            return false; // must not be dirty since last_modified was never set
        } else {
            return last_modified.after(Calendar.getInstance().getTime())
        }
    }
}
