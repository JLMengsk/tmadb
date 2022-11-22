package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Core_biopsy_blocksController {

    def show = {
        def core_biopsy_blocksInstance = Core_biopsy_blocks.get(params.id)
        if (!core_biopsy_blocksInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'core_biopsy_blocks.label', default: 'Core biopsy block'), params.id])}";
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
        }
        else {
            [
                core_biopsy_blocksInstance: core_biopsy_blocksInstance,
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }
}
