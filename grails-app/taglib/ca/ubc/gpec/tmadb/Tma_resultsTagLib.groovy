package ca.ubc.gpec.tmadb

class Tma_resultsTagLib {
    /**
     * show default scoring system as Html text
     * @attr  tma_slice
     * @attr  tma_scorer1
     * @attr  tma_scorer2
     * @attr  tma_scorer3
     * @attr  scoring_date
     * @return
     */
    def String displayTma_resultsDefaultScoringSystem = { attr, body ->
	SortedSet<Tma_core_images> tma_core_images = attr['tma_slice'].getTma_core_images();
	Tma_scorers tma_scorer1 = attr['tma_scorer1'];
	Tma_scorers tma_scorer2 = attr['tma_scorer2'];
	Tma_scorers tma_scorer3 = attr['tma_scorer3'];
	Date scoring_date = attr['scoring_date'];
	def scoreType = attr['scoreType'];

        String result = ""
        def results = Tma_results.withCriteria {
			'in'("tma_core_image", tma_core_images)
            if (tma_scorer1 !=  null) {eq("tma_scorer1", tma_scorer1)} else {isNull("tma_scorer1")}
            if (tma_scorer2 !=  null) {eq("tma_scorer2", tma_scorer2)} else {isNull("tma_scorer2")}
            if (tma_scorer3 !=  null) {eq("tma_scorer3", tma_scorer3)} else {isNull("tma_scorer3")}
            eq("scoring_date", scoring_date)
            projections {
                groupProperty("positive_nuclei_count")                        // 0
                groupProperty("positive_membrane_count")                      // 1
                groupProperty("positive_cytoplasmic_count")                   // 2
                groupProperty("visual_percent_positive_nuclei_estimate")      // 3
                groupProperty("visual_percent_positive_cytoplasmic_estimate") // 4
                groupProperty("visual_percent_positive_membrane_estimate")    // 5
                groupProperty("positive_itil_count")                          // 6
                groupProperty("positive_stil_count")                          // 7
                groupProperty("ihc_score_category")                           // 8
                groupProperty("fish_amplification_ratio")                     // 9
                groupProperty("fish_average_signal")                          // 10
                groupProperty("visual_percent_positive_estimate")             // 11
                groupProperty("visual_percent_positive_cytoplasmic_and_or_membrane_estimate")//12   
            }
        }
        Ihc_score_category_groups ihc_score_category_group = null
        if (scoreType instanceof Ihc_score_category_groups) {
            ihc_score_category_group = (Ihc_score_category_groups)scoreType
        }
        if (results.size() != 0) {
            TreeSet<Ihc_score_categories> ihc_score_categories = new TreeSet<Ihc_score_categories>()
            results.each {
                if (it.getAt(8) != null) {
                    Ihc_score_categories isc = (Ihc_score_categories)(it.getAt(8))
                    if (ScoreType.isUninterpretableScoreType(isc) |
                        isc.getIhc_score_category_group().id == ihc_score_category_group?.id) {
                        ihc_score_categories.add(isc)
                    }
                }
            }
            result = "<table>"
            // description of scoring system
            if (ihc_score_category_group != null) {
                result = result + "<tr><td colspan=2>"
                result = result + "<i>" + ihc_score_category_group.getDescription() + "</i>";
                result = result + "</td></tr>";
            } 
            // end of description of scoring system
            if (ihc_score_categories.size() != 0) {
                ihc_score_categories.each {
                    result = result + "<tr><td>"+it.getName() + "<td>"+it.getDescription()
                }
            }
            // check to see if there are any non-categorical scores such as visual percent positive nuclei estimate
            for (i in results) {
                if (i.getAt(11) != null & scoreType.toString().equals(ScoreType.VISUAL_PERCENT_POSITIVE_ESTIMATE)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(3) != null & scoreType.toString().equals(ScoreType.VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(4) != null & scoreType.toString().equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(5) != null & scoreType.toString().equals(ScoreType.VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(12) != null & scoreType.toString().equals(ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(6) != null & scoreType.toString().equals(ScoreType.POSITIVE_ITIL_COUNT)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(7) != null & scoreType.toString().equals(ScoreType.POSITIVE_STIL_COUNT)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
                if (i.getAt(9) != null & scoreType.toString().equals(ScoreType.FISH_AMPLIFICATION_RATIO)) {
                    result = result+"<tr><td>##<td>"+scoreType.toString()
                    break; // only need to print once
                }
            }
            result = result+"</table>"
        } else {
            out << ""
        }
        out << result
    }
}
