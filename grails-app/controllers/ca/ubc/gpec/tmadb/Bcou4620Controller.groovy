package ca.ubc.gpec.tmadb

class Bcou4620Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bcou4620InstanceList: Bcou4620.list(params), bcou4620InstanceTotal: Bcou4620.count()]
    }

    def create = {
        def bcou4620Instance = new Bcou4620()
        bcou4620Instance.properties = params
        return [bcou4620Instance: bcou4620Instance]
    }

    def save = {
        def bcou4620Instance = new Bcou4620(params)
        if (bcou4620Instance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), bcou4620Instance.id])}"
            redirect(action: "show", id: bcou4620Instance.id)
        }
        else {
            render(view: "create", model: [bcou4620Instance: bcou4620Instance])
        }
    }

    def show = {
        def bcou4620Instance = Bcou4620.get(params.id)
        if (!bcou4620Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
            redirect(action: "list")
        }
        else {
            [bcou4620Instance: bcou4620Instance]
        }
    }

    def edit = {
        def bcou4620Instance = Bcou4620.get(params.id)
        if (!bcou4620Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [bcou4620Instance: bcou4620Instance]
        }
    }

    def update = {
        def bcou4620Instance = Bcou4620.get(params.id)
        if (bcou4620Instance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bcou4620Instance.version > version) {
                    
                    bcou4620Instance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bcou4620.label', default: 'Bcou4620')] as Object[], "Another user has updated this Bcou4620 while you were editing")
                    render(view: "edit", model: [bcou4620Instance: bcou4620Instance])
                    return
                }
            }
            bcou4620Instance.properties = params
            if (!bcou4620Instance.hasErrors() && bcou4620Instance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), bcou4620Instance.id])}"
                redirect(action: "show", id: bcou4620Instance.id)
            }
            else {
                render(view: "edit", model: [bcou4620Instance: bcou4620Instance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def bcou4620Instance = Bcou4620.get(params.id)
        if (bcou4620Instance) {
            try {
                bcou4620Instance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4620.label', default: 'Bcou4620'), params.id])}"
            redirect(action: "list")
        }
    }
}
