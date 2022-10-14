package ca.ubc.gpec.tmadb

class Whole_section_resultsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [whole_section_resultsInstanceList: Whole_section_results.list(params), whole_section_resultsInstanceTotal: Whole_section_results.count()]
    }

    def create = {
        def whole_section_resultsInstance = new Whole_section_results()
        whole_section_resultsInstance.properties = params
        return [whole_section_resultsInstance: whole_section_resultsInstance]
    }

    def save = {
        def whole_section_resultsInstance = new Whole_section_results(params)
        if (whole_section_resultsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), whole_section_resultsInstance.id])}"
            redirect(action: "show", id: whole_section_resultsInstance.id)
        }
        else {
            render(view: "create", model: [whole_section_resultsInstance: whole_section_resultsInstance])
        }
    }

    def show = {
        def whole_section_resultsInstance = Whole_section_results.get(params.id)
        if (!whole_section_resultsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
            redirect(action: "list")
        }
        else {
            [whole_section_resultsInstance: whole_section_resultsInstance]
        }
    }

    def edit = {
        def whole_section_resultsInstance = Whole_section_results.get(params.id)
        if (!whole_section_resultsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [whole_section_resultsInstance: whole_section_resultsInstance]
        }
    }

    def update = {
        def whole_section_resultsInstance = Whole_section_results.get(params.id)
        if (whole_section_resultsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (whole_section_resultsInstance.version > version) {
                    
                    whole_section_resultsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'whole_section_results.label', default: 'Whole_section_results')] as Object[], "Another user has updated this Whole_section_results while you were editing")
                    render(view: "edit", model: [whole_section_resultsInstance: whole_section_resultsInstance])
                    return
                }
            }
            whole_section_resultsInstance.properties = params
            if (!whole_section_resultsInstance.hasErrors() && whole_section_resultsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), whole_section_resultsInstance.id])}"
                redirect(action: "show", id: whole_section_resultsInstance.id)
            }
            else {
                render(view: "edit", model: [whole_section_resultsInstance: whole_section_resultsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def whole_section_resultsInstance = Whole_section_results.get(params.id)
        if (whole_section_resultsInstance) {
            try {
                whole_section_resultsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'whole_section_results.label', default: 'Whole_section_results'), params.id])}"
            redirect(action: "list")
        }
    }
}
