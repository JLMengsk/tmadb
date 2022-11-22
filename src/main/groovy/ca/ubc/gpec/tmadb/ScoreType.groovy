package ca.ubc.gpec.tmadb;

/**
 * define constants for identifying IHC score type
 * @author samuelc
 *
 */
public class ScoreType {

    public static String TOTAL_NUCLEI_COUNT = "total nuclei count";
    public static String POSITIVE_NUCLEI_COUNT = "positive nuclei count";
    public static String POSITIVE_MEMBRANE_COUNT = "positive membrane count";
    public static String POSITIVE_CYTOPLASMIC_COUNT = "positive cytoplasmic count";
    public static String VISUAL_PERCENT_POSITIVE_ESTIMATE = "visual percent positive estimate";   
    public static String VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE = "visual percent positive nuclei estimate";
    public static String VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE = "visual percent positive cytoplasmic estimate";
    public static String VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE = "visual percent positive membrane estimate";
    public static String VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE = "visual percent positive cytoplasmic and/or membrane estimate";
    public static String POSITIVE_ITIL_COUNT = "positive intratumoral tumor infiltrating lymphocytes";
    public static String POSITIVE_STIL_COUNT = "positive stromal tumor infiltrating lymphocytes";
    public static String CATEGORICAL_SCORE = "categorical score";
    public static String FISH_AMPLIFICATION_RATIO = "FISH amplification ratio";
    public static String FISH_AVERAGE_SIGNAL = "FISH average signal";
	
    public static String UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME = "uninterpretable scores (IHC)";
    public static String UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME = "uninterpretable scores (FISH)";
	
    /**
     * return the score type of the input tma_result
     * ASSUME Tma_results only have ONE score type !!!! TODO: need to add constraint to database table
     * @return
     */
    public static def getScoreType(Tma_results tma_result) {
        if (tma_result.getTotal_nuclei_count() != null) {
            return TOTAL_NUCLEI_COUNT;
        } else if (tma_result.getPositive_nuclei_count() != null) {
            return POSITIVE_NUCLEI_COUNT;
        } else if (tma_result.getPositive_membrane_count() != null) {
            return POSITIVE_MEMBRANE_COUNT;
        } else if (tma_result.getPositive_cytoplasmic_count() != null) {
            return POSITIVE_CYTOPLASMIC_COUNT;
        } else if (tma_result.getVisual_percent_positive_estimate() != null) {
            return VISUAL_PERCENT_POSITIVE_ESTIMATE;     
        } else if (tma_result.getVisual_percent_positive_nuclei_estimate() != null) {
            return VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE;
        } else if (tma_result.getVisual_percent_positive_cytoplasmic_estimate() != null) {
            return VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE;
        } else if (tma_result.getVisual_percent_positive_membrane_estimate() != null) {
            return VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE;	
        } else if (tma_result.getVisual_percent_positive_cytoplasmic_and_or_membrane_estimate() != null) {
            return VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE;
        } else if (tma_result.getPositive_itil_count() != null) {
            return POSITIVE_ITIL_COUNT;
        } else if (tma_result.getPositive_stil_count() != null) {
            return POSITIVE_STIL_COUNT;	
        } else if (tma_result.getFish_amplification_ratio() != null) {
            return FISH_AMPLIFICATION_RATIO;
        } else if (tma_result.getFish_average_signal() != null) {
            return FISH_AVERAGE_SIGNAL;
        } else {
            // must be categorical score
            // return an Ihc_score_categorical_group object instead
            return tma_result.getIhc_score_category().getIhc_score_category_group();
        }
    }
	
    public static boolean isUninterpretableScoreType(Tma_results tma_result) {
        Ihc_score_categories ihc_score_category = tma_result.getIhc_score_category();
        if (ihc_score_category == null) {
            return false // must not be uninterpretable
        } else {
            if (ihc_score_category.getIhc_score_category_group().getName().equals(UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME) |
                ihc_score_category.getIhc_score_category_group().getName().equals(UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)) {
                return true
            }
        }
        return false
    }
	
    public static boolean isUninterpretableScoreType(Ihc_score_categories ihc_score_category) {
        if (ihc_score_category == null) {
            return false // must not be uninterpretable
        } else {
            if (ihc_score_category.getIhc_score_category_group().getName().equals(UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME) |
                ihc_score_category.getIhc_score_category_group().getName().equals(UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)) {
                return true
            }
        }
        return false
    }
	
}
