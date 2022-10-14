package ca.ubc.gpec.tmadb

class Tma_result_namesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tma_result_namesInstanceList: Tma_result_names.list(params), tma_result_namesInstanceTotal: Tma_result_names.count()]
    }

    def create = {
        def tma_result_namesInstance = new Tma_result_names()
        tma_result_namesInstance.properties = params
        return [tma_result_namesInstance: tma_result_namesInstance]
    }

    def save = {
        def tma_result_namesInstance = new Tma_result_names(params)
        if (tma_result_namesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), tma_result_namesInstance.id])}"
            redirect(action: "show", id: tma_result_namesInstance.id)
        }
        else {
            render(view: "create", model: [tma_result_namesInstance: tma_result_namesInstance])
        }
    }

    def show = {
        def tma_result_namesInstance = Tma_result_names.get(params.id)
        if (!tma_result_namesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tma_result_namesInstance: tma_result_namesInstance]
        }
    }

    def edit = {
        def tma_result_namesInstance = Tma_result_names.get(params.id)
        if (!tma_result_namesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_result_namesInstance: tma_result_namesInstance]
        }
    }

    def update = {
        def tma_result_namesInstance = Tma_result_names.get(params.id)
        if (tma_result_namesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_result_namesInstance.version > version) {
                    
                    tma_result_namesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_result_names.label', default: 'Tma_result_names')] as Object[], "Another user has updated this Tma_result_names while you were editing")
                    render(view: "edit", model: [tma_result_namesInstance: tma_result_namesInstance])
                    return
                }
            }
            tma_result_namesInstance.properties = params
            if (!tma_result_namesInstance.hasErrors() && tma_result_namesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), tma_result_namesInstance.id])}"
                redirect(action: "show", id: tma_result_namesInstance.id)
            }
            else {
                render(view: "edit", model: [tma_result_namesInstance: tma_result_namesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_result_namesInstance = Tma_result_names.get(params.id)
        if (tma_result_namesInstance) {
            try {
                tma_result_namesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_result_names.label', default: 'Tma_result_names'), params.id])}"
            redirect(action: "list")
        }
    }
}
