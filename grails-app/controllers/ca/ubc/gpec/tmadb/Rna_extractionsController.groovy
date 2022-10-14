package ca.ubc.gpec.tmadb

class Rna_extractionsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [rna_extractionsInstanceList: Rna_extractions.list(params), rna_extractionsInstanceTotal: Rna_extractions.count()]
    }

    def create = {
        def rna_extractionsInstance = new Rna_extractions()
        rna_extractionsInstance.properties = params
        return [rna_extractionsInstance: rna_extractionsInstance]
    }

    def save = {
        def rna_extractionsInstance = new Rna_extractions(params)
        if (rna_extractionsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), rna_extractionsInstance.id])}"
            redirect(action: "show", id: rna_extractionsInstance.id)
        }
        else {
            render(view: "create", model: [rna_extractionsInstance: rna_extractionsInstance])
        }
    }

    def show = {
        def rna_extractionsInstance = Rna_extractions.get(params.id)
        if (!rna_extractionsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
            redirect(action: "list")
        }
        else {
            [rna_extractionsInstance: rna_extractionsInstance]
        }
    }

    def edit = {
        def rna_extractionsInstance = Rna_extractions.get(params.id)
        if (!rna_extractionsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [rna_extractionsInstance: rna_extractionsInstance]
        }
    }

    def update = {
        def rna_extractionsInstance = Rna_extractions.get(params.id)
        if (rna_extractionsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (rna_extractionsInstance.version > version) {
                    
                    rna_extractionsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rna_extractions.label', default: 'Rna_extractions')] as Object[], "Another user has updated this Rna_extractions while you were editing")
                    render(view: "edit", model: [rna_extractionsInstance: rna_extractionsInstance])
                    return
                }
            }
            rna_extractionsInstance.properties = params
            if (!rna_extractionsInstance.hasErrors() && rna_extractionsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), rna_extractionsInstance.id])}"
                redirect(action: "show", id: rna_extractionsInstance.id)
            }
            else {
                render(view: "edit", model: [rna_extractionsInstance: rna_extractionsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def rna_extractionsInstance = Rna_extractions.get(params.id)
        if (rna_extractionsInstance) {
            try {
                rna_extractionsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_extractions.label', default: 'Rna_extractions'), params.id])}"
            redirect(action: "list")
        }
    }
}
