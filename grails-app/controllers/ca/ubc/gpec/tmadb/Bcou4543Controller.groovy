package ca.ubc.gpec.tmadb

class Bcou4543Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bcou4543InstanceList: Bcou4543.list(params), bcou4543InstanceTotal: Bcou4543.count()]
    }

    def create = {
        def bcou4543Instance = new Bcou4543()
        bcou4543Instance.properties = params
        return [bcou4543Instance: bcou4543Instance]
    }

    def save = {
        def bcou4543Instance = new Bcou4543(params)
        if (bcou4543Instance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), bcou4543Instance.id])}"
            redirect(action: "show", id: bcou4543Instance.id)
        }
        else {
            render(view: "create", model: [bcou4543Instance: bcou4543Instance])
        }
    }

    def show = {
        def bcou4543Instance = Bcou4543.get(params.id)
        if (!bcou4543Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
            redirect(action: "list")
        }
        else {
            [bcou4543Instance: bcou4543Instance]
        }
    }

    def edit = {
        def bcou4543Instance = Bcou4543.get(params.id)
        if (!bcou4543Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [bcou4543Instance: bcou4543Instance]
        }
    }

    def update = {
        def bcou4543Instance = Bcou4543.get(params.id)
        if (bcou4543Instance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bcou4543Instance.version > version) {
                    
                    bcou4543Instance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bcou4543.label', default: 'Bcou4543')] as Object[], "Another user has updated this Bcou4543 while you were editing")
                    render(view: "edit", model: [bcou4543Instance: bcou4543Instance])
                    return
                }
            }
            bcou4543Instance.properties = params
            if (!bcou4543Instance.hasErrors() && bcou4543Instance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), bcou4543Instance.id])}"
                redirect(action: "show", id: bcou4543Instance.id)
            }
            else {
                render(view: "edit", model: [bcou4543Instance: bcou4543Instance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def bcou4543Instance = Bcou4543.get(params.id)
        if (bcou4543Instance) {
            try {
                bcou4543Instance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bcou4543.label', default: 'Bcou4543'), params.id])}"
            redirect(action: "list")
        }
    }
}
