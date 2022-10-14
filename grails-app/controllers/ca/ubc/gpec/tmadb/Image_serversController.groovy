package ca.ubc.gpec.tmadb

class Image_serversController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [image_serversInstanceList: Image_servers.list(params), image_serversInstanceTotal: Image_servers.count()]
    }

    def create = {
        def image_serversInstance = new Image_servers()
        image_serversInstance.properties = params
        return [image_serversInstance: image_serversInstance]
    }

    def save = {
        def image_serversInstance = new Image_servers(params)
        if (image_serversInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), image_serversInstance.id])}"
            redirect(action: "show", id: image_serversInstance.id)
        }
        else {
            render(view: "create", model: [image_serversInstance: image_serversInstance])
        }
    }

    def show = {
        def image_serversInstance = Image_servers.get(params.id)
        if (!image_serversInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
            redirect(action: "list")
        }
        else {
            [image_serversInstance: image_serversInstance]
        }
    }

    def edit = {
        def image_serversInstance = Image_servers.get(params.id)
        if (!image_serversInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [image_serversInstance: image_serversInstance]
        }
    }

    def update = {
        def image_serversInstance = Image_servers.get(params.id)
        if (image_serversInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (image_serversInstance.version > version) {
                    
                    image_serversInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'image_servers.label', default: 'Image_servers')] as Object[], "Another user has updated this Image_servers while you were editing")
                    render(view: "edit", model: [image_serversInstance: image_serversInstance])
                    return
                }
            }
            image_serversInstance.properties = params
            if (!image_serversInstance.hasErrors() && image_serversInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), image_serversInstance.id])}"
                redirect(action: "show", id: image_serversInstance.id)
            }
            else {
                render(view: "edit", model: [image_serversInstance: image_serversInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def image_serversInstance = Image_servers.get(params.id)
        if (image_serversInstance) {
            try {
                image_serversInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image_servers.label', default: 'Image_servers'), params.id])}"
            redirect(action: "list")
        }
    }
}
