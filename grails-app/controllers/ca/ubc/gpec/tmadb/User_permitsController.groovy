package ca.ubc.gpec.tmadb

class User_permitsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [user_permitsInstanceList: User_permits.list(params), user_permitsInstanceTotal: User_permits.count()]
    }

    def create = {
        def user_permitsInstance = new User_permits()
        user_permitsInstance.properties = params
        return [user_permitsInstance: user_permitsInstance]
    }

    def save = {
        def user_permitsInstance = new User_permits(params)
        if (user_permitsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user_permits.label', default: 'User_permits'), user_permitsInstance.id])}"
            redirect(action: "show", id: user_permitsInstance.id)
        }
        else {
            render(view: "create", model: [user_permitsInstance: user_permitsInstance])
        }
    }

    def show = {
        def user_permitsInstance = User_permits.get(params.id)
        if (!user_permitsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
            redirect(action: "list")
        }
        else {
            [user_permitsInstance: user_permitsInstance]
        }
    }

    def edit = {
        def user_permitsInstance = User_permits.get(params.id)
        if (!user_permitsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [user_permitsInstance: user_permitsInstance]
        }
    }

    def update = {
        def user_permitsInstance = User_permits.get(params.id)
        if (user_permitsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (user_permitsInstance.version > version) {
                    
                    user_permitsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user_permits.label', default: 'User_permits')] as Object[], "Another user has updated this User_permits while you were editing")
                    render(view: "edit", model: [user_permitsInstance: user_permitsInstance])
                    return
                }
            }
            user_permitsInstance.properties = params
            if (!user_permitsInstance.hasErrors() && user_permitsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user_permits.label', default: 'User_permits'), user_permitsInstance.id])}"
                redirect(action: "show", id: user_permitsInstance.id)
            }
            else {
                render(view: "edit", model: [user_permitsInstance: user_permitsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def user_permitsInstance = User_permits.get(params.id)
        if (user_permitsInstance) {
            try {
                user_permitsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user_permits.label', default: 'User_permits'), params.id])}"
            redirect(action: "list")
        }
    }
}
