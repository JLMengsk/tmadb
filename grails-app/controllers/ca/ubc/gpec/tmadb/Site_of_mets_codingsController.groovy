package ca.ubc.gpec.tmadb

class Site_of_mets_codingsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [site_of_mets_codingsInstanceList: Site_of_mets_codings.list(params), site_of_mets_codingsInstanceTotal: Site_of_mets_codings.count()]
    }

    def create = {
        def site_of_mets_codingsInstance = new Site_of_mets_codings()
        site_of_mets_codingsInstance.properties = params
        return [site_of_mets_codingsInstance: site_of_mets_codingsInstance]
    }

    def save = {
        def site_of_mets_codingsInstance = new Site_of_mets_codings(params)
        if (site_of_mets_codingsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), site_of_mets_codingsInstance.id])}"
            redirect(action: "show", id: site_of_mets_codingsInstance.id)
        }
        else {
            render(view: "create", model: [site_of_mets_codingsInstance: site_of_mets_codingsInstance])
        }
    }

    def show = {
        def site_of_mets_codingsInstance = Site_of_mets_codings.get(params.id)
        if (!site_of_mets_codingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
            redirect(action: "list")
        }
        else {
            [site_of_mets_codingsInstance: site_of_mets_codingsInstance]
        }
    }

    def edit = {
        def site_of_mets_codingsInstance = Site_of_mets_codings.get(params.id)
        if (!site_of_mets_codingsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [site_of_mets_codingsInstance: site_of_mets_codingsInstance]
        }
    }

    def update = {
        def site_of_mets_codingsInstance = Site_of_mets_codings.get(params.id)
        if (site_of_mets_codingsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (site_of_mets_codingsInstance.version > version) {
                    
                    site_of_mets_codingsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings')] as Object[], "Another user has updated this Site_of_mets_codings while you were editing")
                    render(view: "edit", model: [site_of_mets_codingsInstance: site_of_mets_codingsInstance])
                    return
                }
            }
            site_of_mets_codingsInstance.properties = params
            if (!site_of_mets_codingsInstance.hasErrors() && site_of_mets_codingsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), site_of_mets_codingsInstance.id])}"
                redirect(action: "show", id: site_of_mets_codingsInstance.id)
            }
            else {
                render(view: "edit", model: [site_of_mets_codingsInstance: site_of_mets_codingsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def site_of_mets_codingsInstance = Site_of_mets_codings.get(params.id)
        if (site_of_mets_codingsInstance) {
            try {
                site_of_mets_codingsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings'), params.id])}"
            redirect(action: "list")
        }
    }
}
