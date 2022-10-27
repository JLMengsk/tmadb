package ca.ubc.gpec.tmadb
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Tissue_typesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tissue_typesInstanceList: Tissue_types.list(params), tissue_typesInstanceTotal: Tissue_types.count()]
    }

    def create = {
        def tissue_typesInstance = new Tissue_types()
        tissue_typesInstance.properties = params
        return [tissue_typesInstance: tissue_typesInstance]
    }

    def save = {
        def tissue_typesInstance = new Tissue_types(params)
        if (tissue_typesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), tissue_typesInstance.id])}"
            redirect(action: "show", id: tissue_typesInstance.id)
        }
        else {
            render(view: "create", model: [tissue_typesInstance: tissue_typesInstance])
        }
    }

    def show = {
        def tissue_typesInstance = Tissue_types.get(params.id)
        if (!tissue_typesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tissue_typesInstance: tissue_typesInstance]
        }
    }

    def edit = {
        def tissue_typesInstance = Tissue_types.get(params.id)
        if (!tissue_typesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tissue_typesInstance: tissue_typesInstance]
        }
    }

    def update = {
        def tissue_typesInstance = Tissue_types.get(params.id)
        if (tissue_typesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tissue_typesInstance.version > version) {
                    
                    tissue_typesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tissue_types.label', default: 'Tissue_types')] as Object[], "Another user has updated this Tissue_types while you were editing")
                    render(view: "edit", model: [tissue_typesInstance: tissue_typesInstance])
                    return
                }
            }
            tissue_typesInstance.properties = params
            if (!tissue_typesInstance.hasErrors() && tissue_typesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), tissue_typesInstance.id])}"
                redirect(action: "show", id: tissue_typesInstance.id)
            }
            else {
                render(view: "edit", model: [tissue_typesInstance: tissue_typesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tissue_typesInstance = Tissue_types.get(params.id)
        if (tissue_typesInstance) {
            try {
                tissue_typesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tissue_types.label', default: 'Tissue_types'), params.id])}"
            redirect(action: "list")
        }
    }
    
    /**
     * list of available tissue types
     */
    def ajaxGetAvailableTissue_types = {
        def tissue_types = Tissue_types.list();
        int unique_id=0; // unique id for dojo table
        render(contentType: "text/json") {
            identifier: "id"
            numRows: tissue_types.size()
            items: array{
                tissue_types.each { y -> 
                    // NOTE: items in array canNOT be null
                    item(
                        "id":unique_id++,
                        "name":y.name+ViewConstants.AJAX_RESPONSE_DELIMITER+y.id
                    )         
                }
            }
        }
    }
}
