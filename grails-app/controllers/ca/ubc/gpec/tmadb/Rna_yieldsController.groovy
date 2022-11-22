package ca.ubc.gpec.tmadb

class Rna_yieldsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [rna_yieldsInstanceList: Rna_yields.list(params), rna_yieldsInstanceTotal: Rna_yields.count()]
    }

    def create = {
        def rna_yieldsInstance = new Rna_yields()
        rna_yieldsInstance.properties = params
        return [rna_yieldsInstance: rna_yieldsInstance]
    }

    def save = {
        def rna_yieldsInstance = new Rna_yields(params)
        if (rna_yieldsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), rna_yieldsInstance.id])}"
            redirect(action: "show", id: rna_yieldsInstance.id)
        }
        else {
            render(view: "create", model: [rna_yieldsInstance: rna_yieldsInstance])
        }
    }

    def show = {
        def rna_yieldsInstance = Rna_yields.get(params.id)
        if (!rna_yieldsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
            redirect(action: "list")
        }
        else {
            [rna_yieldsInstance: rna_yieldsInstance]
        }
    }

    def edit = {
        def rna_yieldsInstance = Rna_yields.get(params.id)
        if (!rna_yieldsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [rna_yieldsInstance: rna_yieldsInstance]
        }
    }

    def update = {
        def rna_yieldsInstance = Rna_yields.get(params.id)
        if (rna_yieldsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (rna_yieldsInstance.version > version) {
                    
                    rna_yieldsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rna_yields.label', default: 'Rna_yields')] as Object[], "Another user has updated this Rna_yields while you were editing")
                    render(view: "edit", model: [rna_yieldsInstance: rna_yieldsInstance])
                    return
                }
            }
            rna_yieldsInstance.properties = params
            if (!rna_yieldsInstance.hasErrors() && rna_yieldsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), rna_yieldsInstance.id])}"
                redirect(action: "show", id: rna_yieldsInstance.id)
            }
            else {
                render(view: "edit", model: [rna_yieldsInstance: rna_yieldsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def rna_yieldsInstance = Rna_yields.get(params.id)
        if (rna_yieldsInstance) {
            try {
                rna_yieldsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rna_yields.label', default: 'Rna_yields'), params.id])}"
            redirect(action: "list")
        }
    }
}
