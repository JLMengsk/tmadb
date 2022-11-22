package ca.ubc.gpec.tmadb

class Clinical_infosController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [clinical_infosInstanceList: Clinical_infos.list(params), clinical_infosInstanceTotal: Clinical_infos.count()]
    }

    def create = {
        def clinical_infosInstance = new Clinical_infos()
        clinical_infosInstance.properties = params
        return [clinical_infosInstance: clinical_infosInstance]
    }

    def save = {
        def clinical_infosInstance = new Clinical_infos(params)
        if (clinical_infosInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), clinical_infosInstance.id])}"
            redirect(action: "show", id: clinical_infosInstance.id)
        }
        else {
            render(view: "create", model: [clinical_infosInstance: clinical_infosInstance])
        }
    }

    def show = {
        def clinical_infosInstance = Clinical_infos.get(params.id)
        if (!clinical_infosInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
            redirect(action: "list")
        }
        else {
            [clinical_infosInstance: clinical_infosInstance]
        }
    }

    def edit = {
        def clinical_infosInstance = Clinical_infos.get(params.id)
        if (!clinical_infosInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [clinical_infosInstance: clinical_infosInstance]
        }
    }

    def update = {
        def clinical_infosInstance = Clinical_infos.get(params.id)
        if (clinical_infosInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (clinical_infosInstance.version > version) {
                    
                    clinical_infosInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'clinical_infos.label', default: 'Clinical_infos')] as Object[], "Another user has updated this Clinical_infos while you were editing")
                    render(view: "edit", model: [clinical_infosInstance: clinical_infosInstance])
                    return
                }
            }
            clinical_infosInstance.properties = params
            if (!clinical_infosInstance.hasErrors() && clinical_infosInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), clinical_infosInstance.id])}"
                redirect(action: "show", id: clinical_infosInstance.id)
            }
            else {
                render(view: "edit", model: [clinical_infosInstance: clinical_infosInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def clinical_infosInstance = Clinical_infos.get(params.id)
        if (clinical_infosInstance) {
            try {
                clinical_infosInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos'), params.id])}"
            redirect(action: "list")
        }
    }
}
