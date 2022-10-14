package ca.ubc.gpec.tmadb

class Patient_sourcesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [patient_sourcesInstanceList: Patient_sources.list(params), patient_sourcesInstanceTotal: Patient_sources.count()]
    }

    def show = {
        def patient_sourcesInstance = Patient_sources.get(params.id)
        if (!patient_sourcesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'patient_sources.label', default: 'Patient_sources'), params.id])}"
            redirect(action: "list")
        }
        else {
            [patient_sourcesInstance: patient_sourcesInstance]
        }
    }





}
