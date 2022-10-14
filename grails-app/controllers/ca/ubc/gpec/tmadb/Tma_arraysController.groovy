package ca.ubc.gpec.tmadb

import grails.converters.*;
import ca.ubc.gpec.tmadb.util.MiscUtil;

class Tma_arraysController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : DisplayConstant.NUMBER_OF_ITEM_TO_SHOW, 100)
        // assume user is found!!!
        Users user = Users.findByLogin(session.user.login)
        [tma_arraysInstanceList: Tma_arrays.list(params, user), tma_arraysInstanceTotal: Tma_arrays.count(user)]
    }

    def show = {
        def tma_arraysInstance = Tma_arrays.get(params.id, Users.findByLogin(session.user.login))
        if (!tma_arraysInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_arrays.label', default: 'Tma_arrays'), params.id])}"
            flash.message = "Tma_arrays "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(action: "list")
        }
        else {
            [tma_arraysInstance: tma_arraysInstance, user:session.user]
        }
    }
	
    // reference: http://www.grails.org/AJAX-Driven+SELECTs+in+GSP
    // for selecting blocks corresponding to selected arrays 
    def ajaxGetBlocks = {
        def tma_array = Tma_arrays.get(params.id)
        render tma_array?.tma_blocks as JSON
    }
}
