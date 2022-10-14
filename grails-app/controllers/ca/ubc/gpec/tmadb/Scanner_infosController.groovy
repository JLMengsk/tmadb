package ca.ubc.gpec.tmadb

class Scanner_infosController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [scanner_infosInstanceList: Scanner_infos.list(params), scanner_infosInstanceTotal: Scanner_infos.count()]
    }

    def create = {
        def scanner_infosInstance = new Scanner_infos()
        scanner_infosInstance.properties = params
        return [scanner_infosInstance: scanner_infosInstance]
    }

    def save = {
        def scanner_infosInstance = new Scanner_infos(params)
        if (scanner_infosInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), scanner_infosInstance.id])}"
            redirect(action: "show", id: scanner_infosInstance.id)
        }
        else {
            render(view: "create", model: [scanner_infosInstance: scanner_infosInstance])
        }
    }

    def show = {
        def scanner_infosInstance = Scanner_infos.get(params.id)
        if (!scanner_infosInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
            redirect(action: "list")
        }
        else {
            [scanner_infosInstance: scanner_infosInstance]
        }
    }

    def edit = {
        def scanner_infosInstance = Scanner_infos.get(params.id)
        if (!scanner_infosInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [scanner_infosInstance: scanner_infosInstance]
        }
    }

    def update = {
        def scanner_infosInstance = Scanner_infos.get(params.id)
        if (scanner_infosInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (scanner_infosInstance.version > version) {
                    
                    scanner_infosInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'scanner_infos.label', default: 'Scanner_infos')] as Object[], "Another user has updated this Scanner_infos while you were editing")
                    render(view: "edit", model: [scanner_infosInstance: scanner_infosInstance])
                    return
                }
            }
            scanner_infosInstance.properties = params
            if (!scanner_infosInstance.hasErrors() && scanner_infosInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), scanner_infosInstance.id])}"
                redirect(action: "show", id: scanner_infosInstance.id)
            }
            else {
                render(view: "edit", model: [scanner_infosInstance: scanner_infosInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def scanner_infosInstance = Scanner_infos.get(params.id)
        if (scanner_infosInstance) {
            try {
                scanner_infosInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scanner_infos.label', default: 'Scanner_infos'), params.id])}"
            redirect(action: "list")
        }
    }
}
