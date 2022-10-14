package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.OddEvenRowFlag
import ca.ubc.gpec.tmadb.util.ViewConstants

class Tma_coresController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tma_coresInstanceList: Tma_cores.list(params), tma_coresInstanceTotal: Tma_cores.count()]
    }

    def create = {
        def tma_coresInstance = new Tma_cores()
        tma_coresInstance.properties = params
        return [tma_coresInstance: tma_coresInstance]
    }

    def save = {
        def tma_coresInstance = new Tma_cores(params)
        if (tma_coresInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), tma_coresInstance.id])}"
            redirect(action: "show", id: tma_coresInstance.id)
        }
        else {
            render(view: "create", model: [tma_coresInstance: tma_coresInstance])
        }
    }

    def show = {
		Users user = Users.findByLogin(session.user.login)
        Tma_cores tma_coresInstance = Tma_cores.get(params.id, user)
        if (!tma_coresInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
			flash.message = "Tma_cores "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
			redirect(action: "list")
        }
        else {
			ArrayList<Tma_core_images> availableTmaCoreImages = new ArrayList<Tma_core_images>()
			tma_coresInstance.tma_core_images.each {
				if (it.isAvailable(user)) {
					availableTmaCoreImages.add(it)
				}
			}
            OddEvenRowFlag oddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN);
            oddEvenRowFlag.showFlag(); // want to start even row
            [
                tma_coresInstance: tma_coresInstance, 
                availableTmaCoreImages: availableTmaCoreImages, 
                oddEvenRowFlag: oddEvenRowFlag
            ]
        }
    }
    
    def edit = {
        def tma_coresInstance = Tma_cores.get(params.id)
        if (!tma_coresInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_coresInstance: tma_coresInstance]
        }
    }

    def update = {
        def tma_coresInstance = Tma_cores.get(params.id)
        if (tma_coresInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_coresInstance.version > version) {
                    
                    tma_coresInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_cores.label', default: 'Tma_cores')] as Object[], "Another user has updated this Tma_cores while you were editing")
                    render(view: "edit", model: [tma_coresInstance: tma_coresInstance])
                    return
                }
            }
            tma_coresInstance.properties = params
            if (!tma_coresInstance.hasErrors() && tma_coresInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), tma_coresInstance.id])}"
                redirect(action: "show", id: tma_coresInstance.id)
            }
            else {
                render(view: "edit", model: [tma_coresInstance: tma_coresInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_coresInstance = Tma_cores.get(params.id)
        if (tma_coresInstance) {
            try {
                tma_coresInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_cores.label', default: 'Tma_cores'), params.id])}"
            redirect(action: "list")
        }
    }
}
