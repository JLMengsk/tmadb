package ca.ubc.gpec.tmadb

class Tma_core_imagesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tma_core_imagesInstanceList: Tma_core_images.list(params), tma_core_imagesInstanceTotal: Tma_core_images.count()]
    }

    def create = {
        def tma_core_imagesInstance = new Tma_core_images()
        tma_core_imagesInstance.properties = params
        return [tma_core_imagesInstance: tma_core_imagesInstance]
    }

    def save = {
        def tma_core_imagesInstance = new Tma_core_images(params)
        if (tma_core_imagesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), tma_core_imagesInstance.id])}"
            redirect(action: "show", id: tma_core_imagesInstance.id)
        }
        else {
            render(view: "create", model: [tma_core_imagesInstance: tma_core_imagesInstance])
        }
    }

    def show = {
        def tma_core_imagesInstance = Tma_core_images.get(params.id, Users.findByLogin(session.user.login))
        if (!tma_core_imagesInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
			flash.message = "Tma_core_images "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
			redirect(action: "list")
        }
        else {
            Tma_results tma_result_rep = null;
            if (params.containsKey("tma_results_id")) {
                tma_result_rep = Tma_results.get(params.tma_results_id)
            }
            [tma_core_imagesInstance: tma_core_imagesInstance, tma_result_rep:tma_result_rep]
        }
    }

    def edit = {
        def tma_core_imagesInstance = Tma_core_images.get(params.id)
        if (!tma_core_imagesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_core_imagesInstance: tma_core_imagesInstance]
        }
    }

    def update = {
        def tma_core_imagesInstance = Tma_core_images.get(params.id)
        if (tma_core_imagesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_core_imagesInstance.version > version) {
                    
                    tma_core_imagesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_core_images.label', default: 'Tma_core_images')] as Object[], "Another user has updated this Tma_core_images while you were editing")
                    render(view: "edit", model: [tma_core_imagesInstance: tma_core_imagesInstance])
                    return
                }
            }
            tma_core_imagesInstance.properties = params
            if (!tma_core_imagesInstance.hasErrors() && tma_core_imagesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), tma_core_imagesInstance.id])}"
                redirect(action: "show", id: tma_core_imagesInstance.id)
            }
            else {
                render(view: "edit", model: [tma_core_imagesInstance: tma_core_imagesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_core_imagesInstance = Tma_core_images.get(params.id)
        if (tma_core_imagesInstance) {
            try {
                tma_core_imagesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_core_images.label', default: 'Tma_core_images'), params.id])}"
            redirect(action: "list")
        }
    }
}
