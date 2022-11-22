package ca.ubc.gpec.tmadb
import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.util.MiscUtil;

class Biomarker_typesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(base:(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [biomarker_typesInstanceList: Biomarker_types.list(params), biomarker_typesInstanceTotal: Biomarker_types.count()]
    }

    def show = {
        def biomarker_typesInstance = Biomarker_types.get(params.id)
        if (!biomarker_typesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'biomarker_types.label', default: 'Biomarker_types'), params.id])}"
            redirect(action: "list")
        }
        else {
            [
                biomarker_typesInstance: biomarker_typesInstance,
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }
}
