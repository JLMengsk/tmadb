package ca.ubc.gpec.tmadb

class Tma_scorersController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tma_scorersInstanceList: Tma_scorers.list(params), tma_scorersInstanceTotal: Tma_scorers.count()]
    }

    def create = {
        def tma_scorersInstance = new Tma_scorers()
        tma_scorersInstance.properties = params
        return [tma_scorersInstance: tma_scorersInstance]
    }

    def save = {
        def tma_scorersInstance = new Tma_scorers(params)
        if (tma_scorersInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), tma_scorersInstance.id])}"
            redirect(action: "show", id: tma_scorersInstance.id)
        }
        else {
            render(view: "create", model: [tma_scorersInstance: tma_scorersInstance])
        }
    }

    def show = {
        def tma_scorersInstance = Tma_scorers.get(params.id)
        if (!tma_scorersInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tma_scorersInstance: tma_scorersInstance]
        }
    }

    def edit = {
        def tma_scorersInstance = Tma_scorers.get(params.id)
        if (!tma_scorersInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_scorersInstance: tma_scorersInstance]
        }
    }

    def update = {
        def tma_scorersInstance = Tma_scorers.get(params.id)
        if (tma_scorersInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_scorersInstance.version > version) {
                    
                    tma_scorersInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_scorers.label', default: 'Tma_scorers')] as Object[], "Another user has updated this Tma_scorers while you were editing")
                    render(view: "edit", model: [tma_scorersInstance: tma_scorersInstance])
                    return
                }
            }
            tma_scorersInstance.properties = params
            if (!tma_scorersInstance.hasErrors() && tma_scorersInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), tma_scorersInstance.id])}"
                redirect(action: "show", id: tma_scorersInstance.id)
            }
            else {
                render(view: "edit", model: [tma_scorersInstance: tma_scorersInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_scorersInstance = Tma_scorers.get(params.id)
        if (tma_scorersInstance) {
            try {
                tma_scorersInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_scorers.label', default: 'Tma_scorers'), params.id])}"
            redirect(action: "list")
        }
    }
}
