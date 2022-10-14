package ca.ubc.gpec.tmadb

class InstitutionsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [institutionsInstanceList: Institutions.list(params), institutionsInstanceTotal: Institutions.count()]
    }

    def create = {
        def institutionsInstance = new Institutions()
        institutionsInstance.properties = params
        return [institutionsInstance: institutionsInstance]
    }

    def save = {
        def institutionsInstance = new Institutions(params)
        if (institutionsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'institutions.label', default: 'Institutions'), institutionsInstance.id])}"
            redirect(action: "show", id: institutionsInstance.id)
        }
        else {
            render(view: "create", model: [institutionsInstance: institutionsInstance])
        }
    }

    def show = {
        def institutionsInstance = Institutions.get(params.id)
        if (!institutionsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
            redirect(action: "list")
        }
        else {
            [institutionsInstance: institutionsInstance]
        }
    }

    def edit = {
        def institutionsInstance = Institutions.get(params.id)
        if (!institutionsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [institutionsInstance: institutionsInstance]
        }
    }

    def update = {
        def institutionsInstance = Institutions.get(params.id)
        if (institutionsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (institutionsInstance.version > version) {
                    
                    institutionsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'institutions.label', default: 'Institutions')] as Object[], "Another user has updated this Institutions while you were editing")
                    render(view: "edit", model: [institutionsInstance: institutionsInstance])
                    return
                }
            }
            institutionsInstance.properties = params
            if (!institutionsInstance.hasErrors() && institutionsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'institutions.label', default: 'Institutions'), institutionsInstance.id])}"
                redirect(action: "show", id: institutionsInstance.id)
            }
            else {
                render(view: "edit", model: [institutionsInstance: institutionsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def institutionsInstance = Institutions.get(params.id)
        if (institutionsInstance) {
            try {
                institutionsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'institutions.label', default: 'Institutions'), params.id])}"
            redirect(action: "list")
        }
    }
}
