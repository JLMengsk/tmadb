package ca.ubc.gpec.tmadb

class Ihc_score_category_groupsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ihc_score_category_groupsInstanceList: Ihc_score_category_groups.list(params), ihc_score_category_groupsInstanceTotal: Ihc_score_category_groups.count()]
    }

    def create = {
        def ihc_score_category_groupsInstance = new Ihc_score_category_groups()
        ihc_score_category_groupsInstance.properties = params
        return [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance]
    }

    def save = {
        def ihc_score_category_groupsInstance = new Ihc_score_category_groups(params)
        if (ihc_score_category_groupsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), ihc_score_category_groupsInstance.id])}"
            redirect(action: "show", id: ihc_score_category_groupsInstance.id)
        }
        else {
            render(view: "create", model: [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance])
        }
    }

    def show = {
        def ihc_score_category_groupsInstance = Ihc_score_category_groups.get(params.id)
        if (!ihc_score_category_groupsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
            redirect(action: "list")
        }
        else {
            [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance]
        }
    }

    def edit = {
        def ihc_score_category_groupsInstance = Ihc_score_category_groups.get(params.id)
        if (!ihc_score_category_groupsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance]
        }
    }

    def update = {
        def ihc_score_category_groupsInstance = Ihc_score_category_groups.get(params.id)
        if (ihc_score_category_groupsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (ihc_score_category_groupsInstance.version > version) {
                    
                    ihc_score_category_groupsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups')] as Object[], "Another user has updated this Ihc_score_category_groups while you were editing")
                    render(view: "edit", model: [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance])
                    return
                }
            }
            ihc_score_category_groupsInstance.properties = params
            if (!ihc_score_category_groupsInstance.hasErrors() && ihc_score_category_groupsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), ihc_score_category_groupsInstance.id])}"
                redirect(action: "show", id: ihc_score_category_groupsInstance.id)
            }
            else {
                render(view: "edit", model: [ihc_score_category_groupsInstance: ihc_score_category_groupsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ihc_score_category_groupsInstance = Ihc_score_category_groups.get(params.id)
        if (ihc_score_category_groupsInstance) {
            try {
                ihc_score_category_groupsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups'), params.id])}"
            redirect(action: "list")
        }
    }
}
