package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Surgical_blocksController {

    def show = {
        def surgical_blocksInstance = Surgical_blocks.get(params.id)
        if (!surgical_blocksInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surgical_blocks.label', default: 'Surgical block'), params.id])}";
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
        }
        else {
            [
                surgical_blocksInstance: surgical_blocksInstance,
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }
    
    def create = {
        [patient: Patients.get(params.patient_id)]
    }

    /**
     * save changes or create surgical blocks
     */
    def save = {
        
    }
}
