package ca.ubc.gpec.tmadb

class Qpcr_experimentsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [qpcr_experimentsInstanceList: Qpcr_experiments.list(params), qpcr_experimentsInstanceTotal: Qpcr_experiments.count()]
    }

    def create = {
        def qpcr_experimentsInstance = new Qpcr_experiments()
        qpcr_experimentsInstance.properties = params
        return [qpcr_experimentsInstance: qpcr_experimentsInstance]
    }

    def save = {
        def qpcr_experimentsInstance = new Qpcr_experiments(params)
        if (qpcr_experimentsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), qpcr_experimentsInstance.id])}"
            redirect(action: "show", id: qpcr_experimentsInstance.id)
        }
        else {
            render(view: "create", model: [qpcr_experimentsInstance: qpcr_experimentsInstance])
        }
    }

    def show = {
        def qpcr_experimentsInstance = Qpcr_experiments.get(params.id)
        if (!qpcr_experimentsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
            redirect(action: "list")
        }
        else {
            [qpcr_experimentsInstance: qpcr_experimentsInstance]
        }
    }

    def edit = {
        def qpcr_experimentsInstance = Qpcr_experiments.get(params.id)
        if (!qpcr_experimentsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [qpcr_experimentsInstance: qpcr_experimentsInstance]
        }
    }

    def update = {
        def qpcr_experimentsInstance = Qpcr_experiments.get(params.id)
        if (qpcr_experimentsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (qpcr_experimentsInstance.version > version) {
                    
                    qpcr_experimentsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments')] as Object[], "Another user has updated this Qpcr_experiments while you were editing")
                    render(view: "edit", model: [qpcr_experimentsInstance: qpcr_experimentsInstance])
                    return
                }
            }
            qpcr_experimentsInstance.properties = params
            if (!qpcr_experimentsInstance.hasErrors() && qpcr_experimentsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), qpcr_experimentsInstance.id])}"
                redirect(action: "show", id: qpcr_experimentsInstance.id)
            }
            else {
                render(view: "edit", model: [qpcr_experimentsInstance: qpcr_experimentsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def qpcr_experimentsInstance = Qpcr_experiments.get(params.id)
        if (qpcr_experimentsInstance) {
            try {
                qpcr_experimentsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_experiments.label', default: 'Qpcr_experiments'), params.id])}"
            redirect(action: "list")
        }
    }
}
