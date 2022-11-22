package ca.ubc.gpec.tmadb

class Qpcr_resultsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [qpcr_resultsInstanceList: Qpcr_results.list(params), qpcr_resultsInstanceTotal: Qpcr_results.count()]
    }

    def create = {
        def qpcr_resultsInstance = new Qpcr_results()
        qpcr_resultsInstance.properties = params
        return [qpcr_resultsInstance: qpcr_resultsInstance]
    }

    def save = {
        def qpcr_resultsInstance = new Qpcr_results(params)
        if (qpcr_resultsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), qpcr_resultsInstance.id])}"
            redirect(action: "show", id: qpcr_resultsInstance.id)
        }
        else {
            render(view: "create", model: [qpcr_resultsInstance: qpcr_resultsInstance])
        }
    }

    def show = {
        def qpcr_resultsInstance = Qpcr_results.get(params.id)
        if (!qpcr_resultsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
            redirect(action: "list")
        }
        else {
            [qpcr_resultsInstance: qpcr_resultsInstance]
        }
    }

    def edit = {
        def qpcr_resultsInstance = Qpcr_results.get(params.id)
        if (!qpcr_resultsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [qpcr_resultsInstance: qpcr_resultsInstance]
        }
    }

    def update = {
        def qpcr_resultsInstance = Qpcr_results.get(params.id)
        if (qpcr_resultsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (qpcr_resultsInstance.version > version) {
                    
                    qpcr_resultsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'qpcr_results.label', default: 'Qpcr_results')] as Object[], "Another user has updated this Qpcr_results while you were editing")
                    render(view: "edit", model: [qpcr_resultsInstance: qpcr_resultsInstance])
                    return
                }
            }
            qpcr_resultsInstance.properties = params
            if (!qpcr_resultsInstance.hasErrors() && qpcr_resultsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), qpcr_resultsInstance.id])}"
                redirect(action: "show", id: qpcr_resultsInstance.id)
            }
            else {
                render(view: "edit", model: [qpcr_resultsInstance: qpcr_resultsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def qpcr_resultsInstance = Qpcr_results.get(params.id)
        if (qpcr_resultsInstance) {
            try {
                qpcr_resultsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'qpcr_results.label', default: 'Qpcr_results'), params.id])}"
            redirect(action: "list")
        }
    }
}
