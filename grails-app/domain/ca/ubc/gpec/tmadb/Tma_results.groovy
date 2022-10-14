package ca.ubc.gpec.tmadb

class Tma_results implements SecuredMethods {

    public static final String DUMMY_NO_COMMENT = "_DUMMY_NO_COMMENT_";
    
    Tma_core_images tma_core_image
    Integer total_nuclei_count
    Integer positive_nuclei_count
    Integer positive_membrane_count
    Integer positive_cytoplasmic_count
    Float visual_percent_positive_estimate
    Float visual_percent_positive_nuclei_estimate
    Float visual_percent_positive_cytoplasmic_estimate
    Float visual_percent_positive_membrane_estimate
    Float visual_percent_positive_cytoplasmic_and_or_membrane_estimate
    Integer positive_itil_count
    Integer positive_stil_count
    Tma_scorers tma_scorer1
    Tma_scorers tma_scorer2
    Tma_scorers tma_scorer3
    Ihc_score_categories ihc_score_category
    Float fish_amplification_ratio
    Float fish_average_signal
    Date scoring_date
    Date received_date
    String comment
    Tma_result_names tma_result_name

    static constraints = {
        total_nuclei_count (nullable: true)
        positive_nuclei_count (nullable: true)
        positive_membrane_count (nullable: true)
        positive_cytoplasmic_count (nullable: true)
        visual_percent_positive_estimate (nullable: true)
        visual_percent_positive_nuclei_estimate (nullable: true)
        visual_percent_positive_cytoplasmic_estimate (nullable: true)
        visual_percent_positive_membrane_estimate (nullable: true)
        visual_percent_positive_cytoplasmic_and_or_membrane_estimate (nullable: true)
        positive_itil_count (nullable: true)
        positive_stil_count (nullable: true)
        tma_scorer1 (nullable: true)
        tma_scorer2 (nullable: true)
        tma_scorer3 (nullable: true)
        ihc_score_category (nullable: true)
        fish_amplification_ratio (nullable: true)
        fish_average_signal (nullable: true)
        scoring_date (nullable: true)
        received_date (nullable: true)
        comment (nullable: true)
    }

    static mapping = {
        tma_core_image column:'tma_core_image_id'
        tma_scorer1 column:'tma_scorer1_id'
        tma_scorer2 column:'tma_scorer2_id'
        tma_scorer3 column:'tma_scorer3_id'
        ihc_score_category column:'ihc_score_category_id'
        tma_result_name column:'tma_result_name_id'
    }

    /**
     * show default score name based on search critera
     * - there should be only one type of scores e.g. categorical IHC, continuous IHC, FISH 
     * return "" (string of length 0) if not found
     * @param tma_scorer1
     * @param tma_scorer2
     * @param tma_scorer3
     * @param scoring_date
     * @param showUnit // whether to show unit e.g. '12%' or '12'
     * @return
     */
    public static String showDefaultScoreName(
	Tma_core_images tma_core_image,
	Tma_scorers tma_scorer1,
	Tma_scorers tma_scorer2,
	Tma_scorers tma_scorer3,
	Date scoring_date,
	def scoreType,
	boolean showUnit) {
        // find tma_result_name
        Tma_result_names tma_result_name = Tma_result_names.get(tma_core_image,tma_scorer1,tma_scorer2,tma_scorer3,scoring_date,scoreType) 
        if (tma_result_name != null) {
            return showDefaultScoreName(tma_core_image,tma_result_name,scoreType,showUnit)
        } else {
            return "" // empty string ... no score name found
        }
    }
      
    /**
     * show default score name based on search critera
     * @param tma_core_image
     * @param tma_result_name
     * @scoreType
     * 
     * showUnit=false
     * 
     */ 
    public static String showDefaultScoreName(
        Tma_core_images tma_core_image,
        Tma_result_names tma_result_name,
        def scoreType
    ) {
        return showDefaultScoreName(tma_core_image,tma_result_name,scoreType,false)
    }
    
    /**
     * show default score name based on search critera
     * @param tma_core_image
     * @param tma_result_name
     * @scoreType
     * @showUnit
     */
    public static String showDefaultScoreName(
        Tma_core_images tma_core_image,
        Tma_result_names tma_result_name,
        def scoreType,
        boolean showUnit
    ){
        if (!showUnit) {
            // use view instead ...
            for (Tma_result_default_score_name_view t:tma_core_image.tma_result_default_score_names) {
                if(t.getTma_result_name().compareTo(tma_result_name)==0) {
                    return t.getDefault_score_name();
                }
            }
        }
        String result = ""
        
        Tma_results tma_result = null; // assume there is ONLY ONE tma_result linke to core image with specified tma_result_name
        for (Tma_results t:tma_core_image.tma_results) { 
            if(t.getTma_result_name().compareTo(tma_result_name)==0) {
                tma_result = t;
                break;
            }
        }

        if (tma_result != null) {
            Ihc_score_category_groups ihc_score_category_group = null
            Ihc_score_categories ihc_score_category = tma_result.getIhc_score_category()
            // NOTE: if the score is uninterpretable, there must be only ONE Tma_results record
            //       associated with this image ... therefore getAt(0) is ok

            // first, check to see if uninterpretable
            if (ScoreType.isUninterpretableScoreType(tma_result)) {
                return (showUnit ? ihc_score_category.getDescription() : ihc_score_category.getName());
            }

            if (scoreType instanceof Ihc_score_category_groups) {
                // categorical score
                ihc_score_category_group = scoreType
                if (tma_result.getIhc_score_category() != null) {
                    if (tma_result.getIhc_score_category().getIhc_score_category_group().id == ihc_score_category_group.id |
                        tma_result.getIhc_score_category().getIhc_score_category_group().getName().equals(ScoreType.UNINTERPRETABLE_IHC_SCORE_CATEGORY_GROUP_NAME) |
                        tma_result.getIhc_score_category().getIhc_score_category_group().getName().equals(ScoreType.UNINTERPRETABLE_FISH_SCORE_CATEGORY_GROUP_NAME)) {
                        ihc_score_category = tma_result.getIhc_score_category();
                        result = (showUnit ? ihc_score_category.getDescription() : ihc_score_category.getName());
                    }
                }
            } else {
                String scoreTypeName = (String)scoreType
                def temp
                // the temp==null check is necessary because there may be
                // multiple tma_results records associated with the same image (same date)
                if (scoreTypeName.equals(ScoreType.TOTAL_NUCLEI_COUNT)) {
                    if (temp==null){temp = tma_result.getTotal_nuclei_count()}
                } else if (scoreTypeName.equals(ScoreType.POSITIVE_NUCLEI_COUNT)) {
                    if (temp==null){temp = tma_result.getPositive_nuclei_count()}
                } else if (scoreTypeName.equals(ScoreType.POSITIVE_MEMBRANE_COUNT)) {
                    if (temp==null){temp = tma_result.getPositive_membrane_count()}
                } else if (scoreTypeName.equals(ScoreType.POSITIVE_CYTOPLASMIC_COUNT)) {
                    if (temp==null){temp = tma_result.getPositive_cytoplasmic_count()}
                } else if (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_ESTIMATE)) {
                    if (temp==null){
                        temp = tma_result.getVisual_percent_positive_estimate()
                        if (showUnit & temp!=null){temp = temp+" %"}
                    }                    
                } else if (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE)) {
                    if (temp==null){
                        temp = tma_result.getVisual_percent_positive_nuclei_estimate()
                        if (showUnit & temp!=null){temp = temp+" %"}
                    }
                } else if (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE)) {
                    if (temp==null){
                        temp = tma_result.getVisual_percent_positive_cytoplasmic_estimate()
                        if (showUnit & temp!=null){temp = temp+" %"}
                    }
                } else if (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE)) {
                    if (temp==null){
                        temp = tma_result.getVisual_percent_positive_membrane_estimate()
                        if (showUnit & temp!=null){temp = temp+" %"}
                    }
                } else if (scoreTypeName.equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE)) {
                    if (temp==null){
                        temp = tma_result.getVisual_percent_positive_cytoplasmic_and_or_membrane_estimate()
                        if (showUnit & temp!=null){temp = temp+" %"}
                    }
                } else if (scoreTypeName.equals(ScoreType.POSITIVE_ITIL_COUNT)) {
                    if (temp==null){
                        temp = tma_result.getPositive_itil_count()
                        if (showUnit & temp!=null){temp = temp+" iTILs"}
                    }
                } else if (scoreTypeName.equals(ScoreType.POSITIVE_STIL_COUNT)) {
                    if (temp==null){
                        temp = tma_result.getPositive_stil_count()
                        if (showUnit & temp!=null){temp = temp+" sTILs"}
                    }
                } else if (scoreTypeName.equals(ScoreType.FISH_AMPLIFICATION_RATIO)) {
                    if (temp==null){
                        temp = tma_result.getFish_amplification_ratio()
                        if (showUnit & temp!=null){temp = temp+" amp. ratio"}
                    }
                } else if (scoreTypeName.equals(ScoreType.FISH_AVERAGE_SIGNAL)) {
                    if (temp==null){
                        temp = tma_result.getFish_average_signal()
                        if (showUnit & temp!=null){temp = temp+" avg. signal"}
                    }
                }
                if (temp != null) {result = ""+temp}
            }
        }
        return result
    }


    /**
     * show default score name based on search critera
     * - there should be only one type of scores e.g. categorical IHC, continuous IHC, FISH
     * - do not show unit i.e. '12' instead of '12%'
     * return "" (string of length 0) if not found
     * @param tma_scorer1
     * @param tma_scorer2
     * @param tma_scorer3
     * @param scoring_date
     * @return
     */
    public static String showDefaultScoreName(
        Tma_core_images tma_core_image,
        Tma_scorers tma_scorer1,
        Tma_scorers tma_scorer2,
        Tma_scorers tma_scorer3,
        Date scoring_date,
        def scoreType) {
        showDefaultScoreName(tma_core_image,tma_scorer1,tma_scorer2,tma_scorer3,scoring_date,scoreType, false);
    }

    /**
     * show score name 
     * 
     * ASSUME there can be only one score type per tma_results.  however, for FISH,
     * there are two (amp ratio and avg. signal) ...
     * 
     * currently, show FISH amp ratio only
     * TODO: see how to show FISH average signal as well
     * 
     * @return
     */
    public String showScoreName(boolean showUnit) {
        def result = null;

        result = getTotal_nuclei_count()
		
        if (result == null) {
            result = getPositive_nuclei_count()
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getPositive_nuclei_count()
        } else {
            return ""+result
        }
		
        if (result == null) {		
            result = getPositive_membrane_count()
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getPositive_cytoplasmic_count()
        } else {
            return ""+result
        }
	
        if (result == null) {
            result = getVisual_percent_positive_estimate()
            if (showUnit){result = result+" %"}
        } else {
            return ""+result
        }
        
        if (result == null) {
            result = getVisual_percent_positive_nuclei_estimate()
            if (showUnit){result = result+" %"}
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getVisual_percent_positive_cytoplasmic_estimate()
            if (showUnit){result = result+" %"}
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getVisual_percent_positive_membrane_estimate()
            if (showUnit){result = result+" %"}
        } else {
            return ""+result
        }

        if (result == null) {
            result = getVisual_percent_positive_cytoplasmic_and_or_membrane_estimate()
            if (showUnit){result = result+" %"}
        } else {
            return ""+result
        }
        
        if (result == null) {
            result = getPositive_itil_count()
            if (showUnit){result = result+" iTILs"}
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getPositive_stil_count()
            if (showUnit){result = result+" sTILs"}
        } else {
            return ""+result
        }
		
        if (result == null) {
            result = getFish_amplification_ratio()
            if (showUnit){result = result+" amp. ratio"}
        } else {
            return ""+result
        } 
		
        if (result == null) {
            result = getFish_average_signal()
            if (showUnit){result = result+" avg. signal"}
        } else {
            return ""+result
        }
		
        // if got here, it must be categorical score ...
        return ihc_score_category.getName();
    }
	
    /**
     * show default score name with scores
     * - for display in Tma_core_images
     * @return
     */
    public String showDefaultScoreNameWithScorers() {
        String result = showDefaultScoreName(
            tma_core_image,
            tma_scorer1,
            tma_scorer2,
            tma_scorer3,
            scoring_date,
            ScoreType.getScoreType(this),
            true)+" ("
        if (tma_scorer1 != null) {result = result + tma_scorer1.getName() }
        if (tma_scorer2 != null) {result = result + "/" + tma_scorer2.getName() }
        if (tma_scorer3 != null) {result = result + "/" + tma_scorer3.getName() }
        result = result+")"
        return result
    }

    /**
     * show default score name with scores
     * - for display in Tma_core_images
     * @return
     */
    public String showDefaultScoreNameWithScorersAndComment() {
        String result = showDefaultScoreName(
            tma_core_image,
            tma_scorer1,
            tma_scorer2,
            tma_scorer3,
            scoring_date,
            ScoreType.getScoreType(this),
            true)+" ("
        if (tma_scorer1 != null) {result = result + tma_scorer1.getName() }
        if (tma_scorer2 != null) {result = result + "/" + tma_scorer2.getName() }
        if (tma_scorer3 != null) {result = result + "/" + tma_scorer3.getName() }
        result = result+")"
        // only show comment if its available
        if (comment != null) {
            String test = comment.trim();
            if (test.length() > 0 && !(test.equals(DUMMY_NO_COMMENT))) {
                result = result + "; "+test;
            }
        }
        return result
    }

    
    /**
     * show a description of the default score
     * e.g. if percent positive, return "percent positive"
     *      if categorica IHC, return a description of the actual score e.g. "<10% positive nuclei"
     * @return
     */
    public String showDefaultScoreDescription() {
        def scoreType = ScoreType.getScoreType(this)
        if (scoreType instanceof Ihc_score_category_groups) {
            return ihc_score_category.toString()
        } else {
            return scoreType.toString();
        }
    }
	
    /**
     * check to see if this is available to the user
     * @param user
     * @return
     */
    public boolean isAvailable(Users user) {
        // admin can access everything
        if (user.login.equals(Users.ADMINISTRATOR_LOGIN)) {return true}

        def results = User_permits.withCriteria {
            and {
                eq("user", user)
				'in'("tma_slice",tma_core_image.getTma_slice())
            }
        }
        if (results.size() > 0) {return true} else {return false}
    }
    
    /**
     * try to find ANY tma_result that are linked to one of the images in tma_slice
     * and has tma_result_name
     * 
     * preference will be given to interpretable tma_result
     * 
     * return null if not found or user is not permitted
     * @param tma_result_name
     * @param tma_slice
     * @param user
     * @return
     *
     */
    public static Tma_results get(Tma_result_names tma_result_name, Tma_slices tma_slice, Users user) {
        if (!tma_slice.isAvailable(user)) {
            return null; // not available to user!!!
        }
        Tma_results tma_result=null;
        for (Tma_core_images tma_core_image:tma_slice.tma_core_images) {
            for (Tma_results t:tma_core_image.tma_results) {
                if(t.tma_result_name.compareTo(tma_result_name)==0) {
                    tma_result = t;
                    if(!ScoreType.isUninterpretableScoreType(t)) {
                        return t; // interpretable scores!!!
                    }
                }
            }
        }
        return tma_result; // if here, means either no interpretable tma_result found or no tma_result found at all.
    }
    
    /**
     * try to find ANY tma_result that are linked to the input image, scored by input
     * scorer(s) on input date
     * 
     * @param tma_core_image
     * @param tma_scorer1
     * @param tma_scorer2
     * @param tma_scorer3
     * @param scoring_Date
     * 
     */
    public static Tma_results get(Tma_core_images tma_core_image,
	Tma_scorers tma_scorer1,
	Tma_scorers tma_scorer2,
	Tma_scorers tma_scorer3,
	Date scoring_date) {
        for (Tma_results tma_result:tma_core_image.getTma_results()) {
            if (tma_scorer1 == tma_result.tma_scorer1 && tma_scorer2 == tma_result.tma_scorer2 && tma_scorer3 == tma_result.tma_scorer3 && scoring_date == tma_result.scoring_date) {              
                return tma_result;
            }
        }
        return null; // nothing found
    }
    
    /**
     * return null if not found or user is not permitted
     * @param inputId
     * @param user
     * @return
     */
    public static Tma_results get(String inputId, Users user) {
        def result = Tma_results.get(inputId)
        if (!result) {return null}
        if (!result.isAvailable(user)) {return null}
        return result
    }

}
