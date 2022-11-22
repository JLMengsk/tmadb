package ca.ubc.gpec.tmadb

import java.util.Calendar;

import ca.ubc.gpec.tmadb.util.NucleiSelectionParamStringParser;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Whole_section_region_scoringsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
        
    // 
    // upload with login example ...
    // http://172.16.16.130:8080/tmadb-0.1/users/authenticate?login=dgao&password=scoretma&_HIDDEN_CURRENT_controller=tma_scorings&_HIDDEN_CURRENT_action=uploadNucleiSelection&_HIDDEN_CURRENT_id=239&_HIDDEN_CURRENT_nucleiSelectionParamString=0_30x30yp
    //
    def uploadNucleiSelection = {
        Whole_section_region_scorings whole_section_region_scoringsInstance = Whole_section_region_scorings.get(params.id);
        Scorings scoring = whole_section_region_scoringsInstance.getScoring();    
        // parse nuclei selection param string
        NucleiSelectionParamStringParser nspsp = new NucleiSelectionParamStringParser(params.get("nucleiSelectionParamString"));
        
        // find out how many nuclei needs to be removed from the server and remove them
        boolean saveOk = whole_section_region_scoringsInstance.removeNucleiSelection(nspsp.showNumNucleiToRemove());
        
        // save inputComments
        whole_section_region_scoringsInstance.inputScoringComment(params.get("inputComment"));
        
        // check to see if need to save scoring_date
        if (params.get("done")) {
            scoring.setScoring_date(Calendar.getInstance().getTime());
            saveOk = saveOk && scoring.save(flush: true, failOnError:true);
        }
        
        saveOk = saveOk && whole_section_region_scoringsInstance.saveScoring(true);
        
        // nuclei selection param string contains only newly added nuclei ...
        // add them to the end of the nuclei selection list
        for (int i=0; i<nspsp.showSize(); i++) {
            // new Nuclei_selection object
            Nuclei_selections nuclei_selection = new Nuclei_selections();
            nuclei_selection.setScoring(scoring);
            nuclei_selection.setX(nspsp.showX(i));
            nuclei_selection.setY(nspsp.showY(i));
            nuclei_selection.setState(nspsp.showState(i));
            nuclei_selection.setScoring_date(nspsp.showTimeInDate(i));
            nuclei_selection.setSelect_order(whole_section_region_scoringsInstance.showNextSelect_order());

            saveOk = saveOk && nuclei_selection.save(flush: true, failOnError:true);
            
            scoring.addToNuclei_selections(nuclei_selection);
                        
            saveOk = saveOk && scoring.save(flush: true, failOnError:true);
        }    

        if (!saveOk) {
            render ViewConstants.AJAX_RESPONSE_ERROR;
        } else {
            render ViewConstants.AJAX_RESPONSE_SAVE_SUCCESSFUL;
        }    
    }
    
    /**
     * indicate that the user is finished with this scoring by setting the scoring date
     * - returns the number of remaining UNSCORED whole_section_scoring_region_scoring records
     *   from the parent whole_section_scoring object
     * - when scoring ki67-qc phase 3 hotspot, change the scoring state when setting the scoring date!!!
     */
    def set_scoring_date = {
        Whole_section_region_scorings whole_section_region_scoringsInstance = Whole_section_region_scorings.get(params.id);
        Scorings scoring = whole_section_region_scoringsInstance.getScoring();    
        
        // set scoring_date to indicate done with this scoring
        scoring.setScoring_date(Calendar.getInstance().getTime());

        if (!scoring.save(flush: true, failOnError:true)) {
            render ViewConstants.AJAX_RESPONSE_ERROR;
        } else {
            Whole_section_scorings whole_section_scoring = whole_section_region_scoringsInstance.getWhole_section_scoring();
            if(whole_section_scoring.getState() == Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT) {
                // when scoring hotspot, change the scoring state when setting the scoring date!!!
                whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_ESTIMATE_PERCENT);
                if (!whole_section_scoring.save(flush: true)) {
                    render ViewConstants.AJAX_RESPONSE_ERROR;
                    return;
                }
            } // for non-hotspot (i.e. Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_NUCLEI) advance state in Whole_section_scoringsController
            render whole_section_region_scoringsInstance.getWhole_section_scoring().showNumberOfUnscoredWhole_section_region_scorings();
        }    
    }
}
