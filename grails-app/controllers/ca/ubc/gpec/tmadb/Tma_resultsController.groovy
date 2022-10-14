package ca.ubc.gpec.tmadb

class Tma_resultsController {

	def scaffold = Tma_results // def index = { }
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tma_resultsInstanceList: Tma_results.list(params), tma_resultsInstanceTotal: Tma_results.count()]
    }

    def create = {
        def tma_resultsInstance = new Tma_results()
        tma_resultsInstance.properties = params
        return [tma_resultsInstance: tma_resultsInstance]
    }

    def save = {
        def tma_resultsInstance = new Tma_results(params)
        if (tma_resultsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), tma_resultsInstance.id])}"
            redirect(action: "show", id: tma_resultsInstance.id)
        }
        else {
            render(view: "create", model: [tma_resultsInstance: tma_resultsInstance])
        }
    }

    def show = {
        def tma_resultsInstance = Tma_results.get(params.id, Users.findByLogin(session.user.login))
        if (!tma_resultsInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
			flash.message = "Tma_results "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
			redirect(action: "list")
        }
        else {
            [tma_resultsInstance: tma_resultsInstance]
        }
    }

    def edit = {
        def tma_resultsInstance = Tma_results.get(params.id)
        if (!tma_resultsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_resultsInstance: tma_resultsInstance]
        }
    }

    def update = {
        def tma_resultsInstance = Tma_results.get(params.id)
        if (tma_resultsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_resultsInstance.version > version) {
                    
                    tma_resultsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_results.label', default: 'Tma_results')] as Object[], "Another user has updated this Tma_results while you were editing")
                    render(view: "edit", model: [tma_resultsInstance: tma_resultsInstance])
                    return
                }
            }
            tma_resultsInstance.properties = params
            if (!tma_resultsInstance.hasErrors() && tma_resultsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), tma_resultsInstance.id])}"
                redirect(action: "show", id: tma_resultsInstance.id)
            }
            else {
                render(view: "edit", model: [tma_resultsInstance: tma_resultsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_resultsInstance = Tma_results.get(params.id)
        if (tma_resultsInstance) {
            try {
                tma_resultsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_results.label', default: 'Tma_results'), params.id])}"
            redirect(action: "list")
        }
    }
}
