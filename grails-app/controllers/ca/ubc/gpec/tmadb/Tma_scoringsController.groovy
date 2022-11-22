package ca.ubc.gpec.tmadb

import java.util.Calendar;

import ca.ubc.gpec.tmadb.util.NucleiSelectionParamStringParser;

class Tma_scoringsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
        
    // 
    // upload with login example ...
    // http://172.16.16.130:8080/tmadb-0.1/users/authenticate?login=dgao&password=scoretma&_HIDDEN_CURRENT_controller=tma_scorings&_HIDDEN_CURRENT_action=uploadNucleiSelection&_HIDDEN_CURRENT_id=239&_HIDDEN_CURRENT_nucleiSelectionParamString=0_30x30yp
    //
    def uploadNucleiSelection = {
        Tma_scorings tma_scoringsInstance = Tma_scorings.get(params.id);
        Scorings scoring = tma_scoringsInstance.getScoring();    
        // parse nuclei selection param string
        NucleiSelectionParamStringParser nspsp = new NucleiSelectionParamStringParser(params.get("nucleiSelectionParamString"));
        
        // find out how many nuclei needs to be removed from the server and remove them
        boolean saveOk = tma_scoringsInstance.removeNucleiSelection(nspsp.showNumNucleiToRemove());
        
        // save inputComments
        tma_scoringsInstance.inputScoringComment(params.get("inputComment"));
        saveOk = saveOk && tma_scoringsInstance.saveScoring(true);
        
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
            nuclei_selection.setSelect_order(tma_scoringsInstance.showNextSelect_order());

            //saveOk = saveOk && nuclei_selection.save(flush: true, failOnError:true);
            saveOk = saveOk && nuclei_selection.save();
            
            scoring.addToNuclei_selections(nuclei_selection);
                        
            //saveOk = saveOk && scoring.save(flush: true, failOnError:true);
            saveOk = saveOk && scoring.save();
        }    

        if (!saveOk) {
            render "ERROR";
        } else {
            render "SAVE SUCCESSFUL";
        }    
    }
}
