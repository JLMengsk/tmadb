package ca.ubc.gpec.tmadb

class Coring_typesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [coring_typesInstanceList: Coring_types.list(params), coring_typesInstanceTotal: Coring_types.count()]
    }

    def create = {
        def coring_typesInstance = new Coring_types()
        coring_typesInstance.properties = params
        return [coring_typesInstance: coring_typesInstance]
    }

    def save = {
        def coring_typesInstance = new Coring_types(params)
        if (coring_typesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), coring_typesInstance.id])}"
            redirect(action: "show", id: coring_typesInstance.id)
        }
        else {
            render(view: "create", model: [coring_typesInstance: coring_typesInstance])
        }
    }

    def show = {
        def coring_typesInstance = Coring_types.get(params.id)
        if (!coring_typesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
            redirect(action: "list")
        }
        else {
            [coring_typesInstance: coring_typesInstance]
        }
    }

    def edit = {
        def coring_typesInstance = Coring_types.get(params.id)
        if (!coring_typesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [coring_typesInstance: coring_typesInstance]
        }
    }

    def update = {
        def coring_typesInstance = Coring_types.get(params.id)
        if (coring_typesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (coring_typesInstance.version > version) {
                    
                    coring_typesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'coring_types.label', default: 'Coring_types')] as Object[], "Another user has updated this Coring_types while you were editing")
                    render(view: "edit", model: [coring_typesInstance: coring_typesInstance])
                    return
                }
            }
            coring_typesInstance.properties = params
            if (!coring_typesInstance.hasErrors() && coring_typesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), coring_typesInstance.id])}"
                redirect(action: "show", id: coring_typesInstance.id)
            }
            else {
                render(view: "edit", model: [coring_typesInstance: coring_typesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def coring_typesInstance = Coring_types.get(params.id)
        if (coring_typesInstance) {
            try {
                coring_typesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_types.label', default: 'Coring_types'), params.id])}"
            redirect(action: "list")
        }
    }
}
