package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class PatientsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [patientsInstanceList: Patients.list(params), patientsInstanceTotal: Patients.count()]
    }

    def show = {
        def patientsInstance = Patients.get(params.id)
        if (!patientsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'patients.label', default: 'Patients'), params.id])}"
            redirect(action: "list")
        }
        else {
            [
                patientsInstance: patientsInstance,
                oddEvenRowFlag: new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            ]
        }
    }

}
