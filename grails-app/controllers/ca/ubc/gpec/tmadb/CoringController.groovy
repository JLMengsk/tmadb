package ca.ubc.gpec.tmadb

class CoringController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [coringInstanceList: Coring.list(params), coringInstanceTotal: Coring.count()]
    }

    def create = {
        def coringInstance = new Coring()
        coringInstance.properties = params
        return [coringInstance: coringInstance]
    }

    def save = {
        def coringInstance = new Coring(params)
        if (coringInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'coring.label', default: 'Coring'), coringInstance.id])}"
            redirect(action: "show", id: coringInstance.id)
        }
        else {
            render(view: "create", model: [coringInstance: coringInstance])
        }
    }

    def show = {
        def coringInstance = Coring.get(params.id)
        if (!coringInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
            redirect(action: "list")
        }
        else {
            [coringInstance: coringInstance]
        }
    }

    def edit = {
        def coringInstance = Coring.get(params.id)
        if (!coringInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [coringInstance: coringInstance]
        }
    }

    def update = {
        def coringInstance = Coring.get(params.id)
        if (coringInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (coringInstance.version > version) {
                    
                    coringInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'coring.label', default: 'Coring')] as Object[], "Another user has updated this Coring while you were editing")
                    render(view: "edit", model: [coringInstance: coringInstance])
                    return
                }
            }
            coringInstance.properties = params
            if (!coringInstance.hasErrors() && coringInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'coring.label', default: 'Coring'), coringInstance.id])}"
                redirect(action: "show", id: coringInstance.id)
            }
            else {
                render(view: "edit", model: [coringInstance: coringInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def coringInstance = Coring.get(params.id)
        if (coringInstance) {
            try {
                coringInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'coring.label', default: 'Coring'), params.id])}"
            redirect(action: "list")
        }
    }
}
