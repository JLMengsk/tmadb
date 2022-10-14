package ca.ubc.gpec.tmadb

class Specs2009Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [specs2009InstanceList: Specs2009.list(params), specs2009InstanceTotal: Specs2009.count()]
    }

    def create = {
        def specs2009Instance = new Specs2009()
        specs2009Instance.properties = params
        return [specs2009Instance: specs2009Instance]
    }

    def save = {
        def specs2009Instance = new Specs2009(params)
        if (specs2009Instance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), specs2009Instance.id])}"
            redirect(action: "show", id: specs2009Instance.id)
        }
        else {
            render(view: "create", model: [specs2009Instance: specs2009Instance])
        }
    }

    def show = {
        def specs2009Instance = Specs2009.get(params.id)
        if (!specs2009Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
            redirect(action: "list")
        }
        else {
            [specs2009Instance: specs2009Instance]
        }
    }

    def edit = {
        def specs2009Instance = Specs2009.get(params.id)
        if (!specs2009Instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [specs2009Instance: specs2009Instance]
        }
    }

    def update = {
        def specs2009Instance = Specs2009.get(params.id)
        if (specs2009Instance) {
            if (params.version) {
                def version = params.version.toLong()
                if (specs2009Instance.version > version) {
                    
                    specs2009Instance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'specs2009.label', default: 'Specs2009')] as Object[], "Another user has updated this Specs2009 while you were editing")
                    render(view: "edit", model: [specs2009Instance: specs2009Instance])
                    return
                }
            }
            specs2009Instance.properties = params
            if (!specs2009Instance.hasErrors() && specs2009Instance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), specs2009Instance.id])}"
                redirect(action: "show", id: specs2009Instance.id)
            }
            else {
                render(view: "edit", model: [specs2009Instance: specs2009Instance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def specs2009Instance = Specs2009.get(params.id)
        if (specs2009Instance) {
            try {
                specs2009Instance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'specs2009.label', default: 'Specs2009'), params.id])}"
            redirect(action: "list")
        }
    }
}
