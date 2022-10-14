package ca.ubc.gpec.tmadb

import grails.converters.*;

import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScores;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Tma_projectsController {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(base:(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    def list = {
        // view list.gsp ... data query by ajax
        return [user:session.user]
    }

    def create = {
        def tma_projectsInstance = new Tma_projects()
        tma_projectsInstance.properties = params
        return [tma_projectsInstance: tma_projectsInstance]
    }

    def save = {
        def tma_projectsInstance = new Tma_projects(params)
        if (tma_projectsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), tma_projectsInstance.id])}"
            redirect(action: "show", id: tma_projectsInstance.id)
        }
        else {
            render(view: "create", model: [tma_projectsInstance: tma_projectsInstance])
        }
    }

    def show = {
        def tma_projectsInstance = Tma_projects.get(params.id, Users.findByLogin(session.user.login))
        if (!tma_projectsInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
            flash.message = "Tma_projects "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(action: "list")
        }
        else {
            [tma_projectsInstance: tma_projectsInstance, user:session.user]
        }
    }

    def edit = {
        def tma_projectsInstance = Tma_projects.get(params.id)
        if (!tma_projectsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tma_projectsInstance: tma_projectsInstance]
        }
    }

    def update = {
        def tma_projectsInstance = Tma_projects.get(params.id)
        if (tma_projectsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tma_projectsInstance.version > version) {
                    
                    tma_projectsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tma_projects.label', default: 'Tma_projects')] as Object[], "Another user has updated this Tma_projects while you were editing")
                    render(view: "edit", model: [tma_projectsInstance: tma_projectsInstance])
                    return
                }
            }
            tma_projectsInstance.properties = params
            if (!tma_projectsInstance.hasErrors() && tma_projectsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), tma_projectsInstance.id])}"
                redirect(action: "show", id: tma_projectsInstance.id)
            }
            else {
                render(view: "edit", model: [tma_projectsInstance: tma_projectsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tma_projectsInstance = Tma_projects.get(params.id)
        if (tma_projectsInstance) {
            try {
                tma_projectsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_projects.label', default: 'Tma_projects'), params.id])}"
            redirect(action: "list")
        }
    }
	
    ////////////////////////////////////////////////////////////////////////////
    // reference: http://www.grails.org/AJAX-Driven+SELECTs+in+GSP

    // for listing all tma projects
    def ajaxGetTma_projects = {
        Users user = Users.findByLogin(session.user.login)
        def tma_projects = Tma_projects.list(params, user)
        int unique_id=0; // unique id for dojo table
        render(contentType: "text/json") {
            identifier = "id"
            numRows = tma_projects.size()
            items = array{
                tma_projects.each { w -> 
                    // NOTE: items in array canNOT be null
                    item(
                        "id":unique_id++,
                        "name":w.name+ViewConstants.AJAX_RESPONSE_DELIMITER+w.id,
                        "built_by":w.getBuilt_by(),
                        "description":w.getDescription(),
                        "core_id_name":w.getCore_id_name()
                    )         
                }
            }
        }
    }
    
    // for selecting tma_arrays corresponding to selected tma_projects
    def ajaxGetArrays = {
        def tma_project = Tma_projects.get(params.id)
        render tma_project?.tma_arrays as JSON
    }
	
    // for selecting biomarkers (via staining details) on arrays from the selected tma_projects 
    def ajaxGetStainingDetails = {

        def tma_arrays = Tma_arrays.withCriteria {
			'in'('tma_project', Tma_projects.get(params.id))
        }
        if (tma_arrays.size() == 0) {
            render new ArrayList() as JSON 
            return // end here ... nothing to display
        }
		
        def tma_blocks = Tma_blocks.withCriteria {
			'in'('tma_array', tma_arrays)
        }
        if (tma_blocks.size() == 0) {
            render new ArrayList() as JSON
            return // end here ... nothing to display
        }
		
        def results = Tma_slices.withCriteria{
			'in'('tma_block',tma_blocks)
            projections {
                groupProperty("staining_detail")
            }
        }
        ArrayList resultsForJSON = new ArrayList();
        results.each {
            ArrayList item = new ArrayList();
            item.add(it.id);
            item.add(it.toStringForDownloadSelection())	
            resultsForJSON.add(item)
        }
        render resultsForJSON as JSON
    }
}
