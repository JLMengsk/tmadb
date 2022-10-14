package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.ViewConstants;

class Whole_section_scoringsController {

    /**
     * 1. set visit_order on the scoring object corresponding to the whole_section_REGION_scoring
     * 
     * 2. return ajax whole_section_scoring_region_id and the corresponding nucleiSelectionParamString and comment given the following:
     * - whole_section_scoring_id
     * - x
     * - y
     * - field diameter
     * ASSUME x,y,diameter will uniquely identify the whole_section_scoring region i.e. 
     * current DOES NOT support multiple scoring on the SAME region with in the same
     * whole_section_scoring!!!
     */
    def ajax_start_scoring_whole_section_region_scoring_and_get_id = {
        Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(params.get('id'));
        if (whole_section_scoring == null) {
            render "whole_section_scoring object not found (id:"+params.get('id')+")";
            return;
        }
        int x        = Integer.parseInt(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X)); // anything goes wrong here will though NumberFormatException
        int y        = Integer.parseInt(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y)); // anything goes wrong here will though NumberFormatException
        int diameter = Integer.parseInt(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER)); // anything goes wrong here will though NumberFormatException
        String ki67state = params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE);
        def result = Whole_section_region_scorings.withCriteria {
            eq('whole_section_scoring',whole_section_scoring)
            eq('x',x)
            eq('y',y)
            eq('field_diameter_pixel',diameter)
            eq('ihc_score_category',Ihc_score_categories.findByName(ki67state))
        }
        if (result.isEmpty()) {
            render ViewConstants.AJAX_RESPONSE_NA;
        } else if (result.size() > 1) {
            render ViewConstants.AJAX_RESPONSE_NA;
        } else {         
            Whole_section_region_scorings w = result.first();
            if (w.showScoringVisit_order() == null) { // only set visit_order once
                w.inputScoringVisit_order(whole_section_scoring.showNextWhole_section_region_scoringVisit_order());
                if (!w.save(flush:true)) {
                    render "trying to save visit order in whole_section_region_scoring option but failed.";
                }
            }
            render(contentType: "text/json") {
                identifier = "id"
                numRows = result.size()
                items = array{
                    result.each {
                        // NOTE: items in array canNOT be null
                        wsrs -> item(
                        "id":wsrs.id,
                        "nucleiSelectionParamString":wsrs.showNucleiSelectionParamString(), 
                        "ihc_score_category_name":(wsrs.getIhc_score_category()==null?ViewConstants.AJAX_RESPONSE_NA:wsrs.getIhc_score_category().getName()),
                        "comment":(wsrs.showScoringComment()==null?"":wsrs.showScoringComment()),
                        "nuclei_selection_notification_nuclei_count":(wsrs.getScoring().getScoring_nuclei_selection_notifications()?.isEmpty() ? "" : wsrs.getScoring().getScoring_nuclei_selection_notifications().first().getNuclei_selection_notification().getNuclei_count()), // TODO currently only support ONE notification!!!
                        "nuclei_selection_notification_message":(wsrs.getScoring().getScoring_nuclei_selection_notifications()?.isEmpty() ? "" : wsrs.getScoring().getScoring_nuclei_selection_notifications().first().getNuclei_selection_notification().getMessage()), // TODO currently only support ONE notification!!!
                        )
                    }
                }
            }
        }
    }
    
    /**
     * indicate that the user is finished with this scoring by setting the scoring date
     * @return AJAX_RESPONSE_NA if whole_section_scoring NOT completed e.g. not all linked whole_section_region_scorings are not completed OR visit_order NOT set in parent scorings record
     *         AJAX_RESPONSE_ERROR if something went wrong when trying to save record to database
     *         AJAX_RESPONSE_SAVE_SUCCESSFUL whole_section_scoring is completed and save scoring_date to database is successful
     */
    def set_scoring_date = {
        Whole_section_scorings whole_section_scoringsInstance = Whole_section_scorings.get(params.id);
        Scorings scoring = whole_section_scoringsInstance.getScoring();    
        
        if (scoring.getVisit_order() == null) { // it seems that the user has not even viewed this scoring yet!
            render ViewConstants.AJAX_RESPONSE_NA;   
        } else if (whole_section_scoringsInstance.showNumberOfUnscoredWhole_section_region_scorings()>0) {
            render ViewConstants.AJAX_RESPONSE_NA;  // scoring not completed yet
        } else {
            // set scoring_date to indicate done with this scoring
            scoring.setScoring_date(Calendar.getInstance().getTime());

            if (!scoring.save(flush: true, failOnError:true)) {
                render ViewConstants.AJAX_RESPONSE_ERROR;
            } else {
                render ViewConstants.AJAX_RESPONSE_SAVE_SUCCESSFUL;
            }    
        }
    }
}
