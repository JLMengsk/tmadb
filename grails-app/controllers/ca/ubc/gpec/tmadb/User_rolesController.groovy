package ca.ubc.gpec.tmadb

class User_rolesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [user_rolesInstanceList: User_roles.list(params), user_rolesInstanceTotal: User_roles.count()]
    }

    def create = {
        def user_rolesInstance = new User_roles()
        user_rolesInstance.properties = params
        return [user_rolesInstance: user_rolesInstance]
    }

    def save = {
        def user_rolesInstance = new User_roles(params)
        if (user_rolesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user_roles.label', default: 'User_roles'), user_rolesInstance.id])}"
            redirect(action: "show", id: user_rolesInstance.id)
        }
        else {
            render(view: "create", model: [user_rolesInstance: user_rolesInstance])
        }
    }

    def show = {
        def user_rolesInstance = User_roles.get(params.id)
        if (!user_rolesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
            redirect(action: "list")
        }
        else {
            [user_rolesInstance: user_rolesInstance]
        }
    }

    def edit = {
        def user_rolesInstance = User_roles.get(params.id)
        if (!user_rolesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [user_rolesInstance: user_rolesInstance]
        }
    }

    def update = {
        def user_rolesInstance = User_roles.get(params.id)
        if (user_rolesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (user_rolesInstance.version > version) {
                    
                    user_rolesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user_roles.label', default: 'User_roles')] as Object[], "Another user has updated this User_roles while you were editing")
                    render(view: "edit", model: [user_rolesInstance: user_rolesInstance])
                    return
                }
            }
            user_rolesInstance.properties = params
            if (!user_rolesInstance.hasErrors() && user_rolesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user_roles.label', default: 'User_roles'), user_rolesInstance.id])}"
                redirect(action: "show", id: user_rolesInstance.id)
            }
            else {
                render(view: "edit", model: [user_rolesInstance: user_rolesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def user_rolesInstance = User_roles.get(params.id)
        if (user_rolesInstance) {
            try {
                user_rolesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_roles.label', default: 'User_roles'), params.id])}"
            redirect(action: "list")
        }
    }
}
