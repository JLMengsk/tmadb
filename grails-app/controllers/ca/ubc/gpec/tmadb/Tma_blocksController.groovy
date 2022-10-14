package ca.ubc.gpec.tmadb

import grails.converters.*;

class Tma_blocksController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : DisplayConstant.NUMBER_OF_ITEM_TO_SHOW, 100)
		// assume user is found!!!
		Users user = Users.findByLogin(session.user.login)
        [tma_blocksInstanceList: Tma_blocks.list(params, user), tma_blocksInstanceTotal: Tma_blocks.count(user)]
    }

    def create = {
        def tma_blocksInstance = new Tma_blocks()
        tma_blocksInstance.properties = params
        return [tma_blocksInstance: tma_blocksInstance]
    }

    def save = {
        def tma_blocksInstance = new Tma_blocks(params)
        if (tma_blocksInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), tma_blocksInstance.id])}"
            redirect(action: "show", id: tma_blocksInstance.id)
        }
        else {
            render(view: "create", model: [tma_blocksInstance: tma_blocksInstance])
        }
    }

    def show = {
        def tma_blocksInstance = Tma_blocks.get(params.id, Users.findByLogin(session.user.login))
        if (!tma_blocksInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
			flash.message = "Tma_blocks "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(action: "list")
        }
        else {
            [tma_blocksInstance: tma_blocksInstance]
        }
    }

    def edit = {
        def tma_blocksInstance = Tma_blocks.get(params.id)
        if (!tma_blocksInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_blocksInstance: tma_blocksInstance]
        }
    }

    def update = {
        def tma_blocksInstance = Tma_blocks.get(params.id)
        if (tma_blocksInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_blocksInstance.version > version) {
                    
                    tma_blocksInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_blocks.label', default: 'Tma_blocks')] as Object[], "Another user has updated this Tma_blocks while you were editing")
                    render(view: "edit", model: [tma_blocksInstance: tma_blocksInstance])
                    return
                }
            }
            tma_blocksInstance.properties = params
            if (!tma_blocksInstance.hasErrors() && tma_blocksInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), tma_blocksInstance.id])}"
                redirect(action: "show", id: tma_blocksInstance.id)
            }
            else {
                render(view: "edit", model: [tma_blocksInstance: tma_blocksInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_blocksInstance = Tma_blocks.get(params.id)
        if (tma_blocksInstance) {
            try {
                tma_blocksInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_blocks.label', default: 'Tma_blocks'), params.id])}"
            redirect(action: "list")
        }
    }
	
	// reference: http://www.grails.org/AJAX-Driven+SELECTs+in+GSP
	// for selecting tma_arrays corresponding to selected tma_projects
	def ajaxGetSlices = {
		def tma_block = Tma_blocks.get(params.id)
		render tma_block?.tma_slices as JSON
	}
}
