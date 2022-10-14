package ca.ubc.gpec.tmadb

class Coring_projectsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [coring_projectsInstanceList: Coring_projects.list(params), coring_projectsInstanceTotal: Coring_projects.count()]
    }

    def create = {
        def coring_projectsInstance = new Coring_projects()
        coring_projectsInstance.properties = params
        return [coring_projectsInstance: coring_projectsInstance]
    }

    def save = {
        def coring_projectsInstance = new Coring_projects(params)
        if (coring_projectsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), coring_projectsInstance.id])}"
            redirect(action: "show", id: coring_projectsInstance.id)
        }
        else {
            render(view: "create", model: [coring_projectsInstance: coring_projectsInstance])
        }
    }

    def show = {
        def coring_projectsInstance = Coring_projects.get(params.id)
        if (!coring_projectsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
            redirect(action: "list")
        }
        else {
            [coring_projectsInstance: coring_projectsInstance]
        }
    }

    def edit = {
        def coring_projectsInstance = Coring_projects.get(params.id)
        if (!coring_projectsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [coring_projectsInstance: coring_projectsInstance]
        }
    }

    def update = {
        def coring_projectsInstance = Coring_projects.get(params.id)
        if (coring_projectsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (coring_projectsInstance.version > version) {
                    
                    coring_projectsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'coring_projects.label', default: 'Coring_projects')] as Object[], "Another user has updated this Coring_projects while you were editing")
                    render(view: "edit", model: [coring_projectsInstance: coring_projectsInstance])
                    return
                }
            }
            coring_projectsInstance.properties = params
            if (!coring_projectsInstance.hasErrors() && coring_projectsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), coring_projectsInstance.id])}"
                redirect(action: "show", id: coring_projectsInstance.id)
            }
            else {
                render(view: "edit", model: [coring_projectsInstance: coring_projectsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def coring_projectsInstance = Coring_projects.get(params.id)
        if (coring_projectsInstance) {
            try {
                coring_projectsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring_projects.label', default: 'Coring_projects'), params.id])}"
            redirect(action: "list")
        }
    }
}
