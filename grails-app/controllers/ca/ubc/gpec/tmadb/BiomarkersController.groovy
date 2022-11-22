package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class BiomarkersController {
	
    static allowedMethods = [save: "POST", update: "POST", delete: "GET"]

    def index = {
        redirect(base:(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : DisplayConstant.NUMBER_OF_ITEM_TO_SHOW, 100)
        // assume user is found!!!
        Users user = Users.findByLogin(session.user.login)
        
        [biomarkersInstanceList: Biomarkers.list(params, user), biomarkersInstanceTotal: Biomarkers.count(user), user: user]
    }

    /**
     * show biomarker details
     * optional param HTML_PARAM_NAME_TMA_PROJECT_ID
     */
    def show = {
        Users user = Users.findByLogin(session.user.login)
        Biomarkers biomarkersInstance = Biomarkers.get(params.id, user)
        // check to see if HTML_PARAM_NAME_TMA_PROJECT_ID or HTML_PARAM_NAME_TMA_ARRAY_ID is specified in param
        Tma_projects tma_project = null;
        Tma_arrays tma_array = null;
        if (params.containsKey(ViewConstants.HTML_PARAM_NAME_TMA_ARRAY_ID)) {
            tma_array = Tma_arrays.get(params.get(ViewConstants.HTML_PARAM_NAME_TMA_ARRAY_ID));
        } else if (params.containsKey(ViewConstants.HTML_PARAM_NAME_TMA_PROJECT_ID)) {
            tma_project = Tma_projects.get(params.get(ViewConstants.HTML_PARAM_NAME_TMA_PROJECT_ID));
        }
        
        OddEvenRowFlag oddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD, ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
        if (!biomarkersInstance) {
            flash.message = "Biomarkers "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(base:(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list")
        }
        else {
            render(view:"show", model:[biomarkersInstance: biomarkersInstance, availableStainingDetails: biomarkersInstance.getAvailableStainingDetails(user), usersInstance:user, oddEvenRowFlag:oddEvenRowFlag, tma_projectsInstance:tma_project, tma_arraysInstance:tma_array])
        }
    }
    
    /**
     * show form to add new biomarker
     * - only admin can do this ... filter out already by BiomarkersSecurityCheck
     */
    def create = {
        Users user = Users.findByLogin(session.user.login)
        render(view:"create", model:[user: user])
    }
    
    /**
     * show form to edit existing biomarker
     */
    def edit = {
        Users user = Users.findByLogin(session.user.login)
        Biomarkers biomarker = Biomarkers.get(params.id, user)
        if (!biomarker) {
            flash.message = "Biomarkers "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(base:(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list")
        }
        render(view:"edit", model:[user: user, biomarker: biomarker])
    }
    
    /**
     * save or update biomarker info 
     * - only admin can do this ... filter out already by BiomarkersSecurityCheck
     */
    def update = {
        Users user = Users.findByLogin(session.user.login)
        boolean saved = false;
        Biomarkers biomarker;
        boolean edit = params.containsKey('id');
        if (edit) {
            biomarker = Biomarkers.get(params.get('id'))
        } else {
            biomarker = new Biomarkers();
        }
        biomarker.setBiomarker_type(Biomarker_types.get(params.get(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_BIOMARKER_TYPE_ID)))
        biomarker.setName(params.get(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME))
        biomarker.setDescription(params.get(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME_DESCRIPTION))
        if (!biomarker.save(flush:true)) {
            flash.message = "Something went wrong.  Failed to save new biomarker info."
            render(view:"create", model:[user: user])
        } else {
            flash.message = edit ? "Biomarker updated successfully" : "New biomarker saved successfully."
            saved = true;
        }
        if (saved) {
            OddEvenRowFlag oddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD, ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
            render(view:"show", model:[biomarkersInstance: biomarker, availableStainingDetails: biomarker.getAvailableStainingDetails(user), usersInstance:user, oddEvenRowFlag:oddEvenRowFlag])
        }
    }
    
    /**
     * delete a biomarker record 
     */
    def delete = {
        Users user = Users.findByLogin(session.user.login)
        if (params.containsKey('id')) {
            Biomarkers biomarker = Biomarkers.get(params.get('id'));
            if (biomarker.showCanDelete(session)) {
                String biomarkerName = biomarker.getName();
                biomarker.delete(flush: true);
                flash.message = "Biomarker ("+biomarkerName+") delete successfully."
            } else {
                flash.message = "Delete biomarker ("+biomarkerName+") permission denied."
            }
        }
        params.max = Math.min(params.max ? params.int('max') : DisplayConstant.NUMBER_OF_ITEM_TO_SHOW, 100)
        // assume user is found!!!
        [biomarkersInstanceList: Biomarkers.list(params, user), biomarkersInstanceTotal: Biomarkers.count(user), user: user]
    }
    
    /**
     * find biomarker by name
     */
    def ajax_get_biomarker_by_name = {
        def existingBiomarkers = Biomarkers.findAllByName(params.get(ViewConstants.HTML_PARAM_NAME_EDIT_BIOMARKER_INPUT_NAME))
        String name;
        String description;
        String biomarker_type; 
        List array = new ArrayList(existingBiomarkers.size());
        for (existingBiomarker in existingBiomarkers){
            name = existingBiomarker.name;
            description = existingBiomarker.description==null?"":existingBiomarker.description; // NOTE: items in array canNOT be null
            biomarker_type = existingBiomarker.biomarker_type.name;
            array.add("name":name,"description":description,"biomarker_type":biomarker_type);
        }

        render(contentType: "text/json") {
            numRows(existingBiomarkers.size())
            items(array)
        }
    }
}
