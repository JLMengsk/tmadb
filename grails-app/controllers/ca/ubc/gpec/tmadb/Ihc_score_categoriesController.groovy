package ca.ubc.gpec.tmadb

class Ihc_score_categoriesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ihc_score_categoriesInstanceList: Ihc_score_categories.list(params), ihc_score_categoriesInstanceTotal: Ihc_score_categories.count()]
    }

    def create = {
        def ihc_score_categoriesInstance = new Ihc_score_categories()
        ihc_score_categoriesInstance.properties = params
        return [ihc_score_categoriesInstance: ihc_score_categoriesInstance]
    }

    def save = {
        def ihc_score_categoriesInstance = new Ihc_score_categories(params)
        if (ihc_score_categoriesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), ihc_score_categoriesInstance.id])}"
            redirect(action: "show", id: ihc_score_categoriesInstance.id)
        }
        else {
            render(view: "create", model: [ihc_score_categoriesInstance: ihc_score_categoriesInstance])
        }
    }

    def show = {
        def ihc_score_categoriesInstance = Ihc_score_categories.get(params.id)
        if (!ihc_score_categoriesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
            redirect(action: "list")
        }
        else {
            [ihc_score_categoriesInstance: ihc_score_categoriesInstance]
        }
    }

    def edit = {
        def ihc_score_categoriesInstance = Ihc_score_categories.get(params.id)
        if (!ihc_score_categoriesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [ihc_score_categoriesInstance: ihc_score_categoriesInstance]
        }
    }

    def update = {
        def ihc_score_categoriesInstance = Ihc_score_categories.get(params.id)
        if (ihc_score_categoriesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (ihc_score_categoriesInstance.version > version) {
                    
                    ihc_score_categoriesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories')] as Object[], "Another user has updated this Ihc_score_categories while you were editing")
                    render(view: "edit", model: [ihc_score_categoriesInstance: ihc_score_categoriesInstance])
                    return
                }
            }
            ihc_score_categoriesInstance.properties = params
            if (!ihc_score_categoriesInstance.hasErrors() && ihc_score_categoriesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), ihc_score_categoriesInstance.id])}"
                redirect(action: "show", id: ihc_score_categoriesInstance.id)
            }
            else {
                render(view: "edit", model: [ihc_score_categoriesInstance: ihc_score_categoriesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ihc_score_categoriesInstance = Ihc_score_categories.get(params.id)
        if (ihc_score_categoriesInstance) {
            try {
                ihc_score_categoriesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories'), params.id])}"
            redirect(action: "list")
        }
    }
}
