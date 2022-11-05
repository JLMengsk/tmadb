package ca.ubc.gpec.tmadb

import grails.converters.*;

import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScores;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;

class Tma_slicesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /**
     * index redirects to list
     */
    def index = {
        redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params)
    }

    /**
     * list available tma slices to user
     */
    def list = {
        // view list.gsp ... data query by ajax
        return [user:session.user]
    }

    def show = {
        def tma_slicesInstance = Tma_slices.get(params.id, Users.findByLogin(session.user.login))
		
        // variables that would be available if viewing scores
        def tma_results_id = null
        Tma_results tma_result = null
        Tma_scorers tma_scorer1 = null
        Tma_scorers tma_scorer2 = null
        Tma_scorers tma_scorer3 = null
        Date scoring_date = null
        def scoreType = null
		
        if (params.containsKey("tma_result_names_id")) {
            Tma_result_names tma_result_name = Tma_result_names.get(params.tma_result_names_id)
            tma_result = Tma_results.get(tma_result_name, tma_slicesInstance, session.user) // need an interpretable score!
            tma_results_id = tma_result.getId()
            tma_scorer1 = tma_result.getTma_scorer1()
            tma_scorer2 = tma_result.getTma_scorer2()
            tma_scorer3 = tma_result.getTma_scorer3()
            scoring_date = tma_result.getScoring_date()
            scoreType = ScoreType.getScoreType(tma_result)
        } else if (params.containsKey("tma_results_id")) {
            tma_results_id = params.tma_results_id
            tma_result = Tma_results.get(tma_results_id)
            tma_scorer1 = tma_result.getTma_scorer1()
            tma_scorer2 = tma_result.getTma_scorer2()
            tma_scorer3 = tma_result.getTma_scorer3()
            scoring_date = tma_result.getScoring_date()
            scoreType = ScoreType.getScoreType(tma_result)
        }
			
        if (!tma_slicesInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tma_slices.label', default: 'Tma_slices'), params.id])}"
            flash.message = "Tma_slices "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(action: "list")
        }
        else {
            [tma_slicesInstance: tma_slicesInstance, tma_scorer1: tma_scorer1,tma_scorer2: tma_scorer2, tma_scorer3: tma_scorer3, scoring_date: scoring_date, scoreType: scoreType, tma_result: tma_result, user:Users.findByLogin(session.user.login)]
        }
    }
	
    // reference: http://www.grails.org/AJAX-Driven+SELECTs+in+GSP
    // for selecting tma_core_images (for importing scores) corresponding to selected tma_slices
    def ajaxGetScannerInfos = {
        def c = Tma_core_images.createCriteria()
		
        def scanner_infos_scanning_dates = c.list{
            eq("tma_slice",Tma_slices.get(params.id))
            projections {
                property("id")
                groupProperty("scanner_info")
                groupProperty("scanning_date")
            }
        }
        render scanner_infos_scanning_dates as JSON
    }
	
    // get all available tma scorers 
    def ajaxGetAvailableScorers = {	
        Tma_slices tma_slice = Tma_slices.get(params.id);
        // find out score type
        HashSet<ScorerInfo> scorerInfoSet = new HashSet<ScorerInfo>()
        tma_slice.getTma_core_images().each { tma_core_image ->
            tma_core_image.getTma_results().each {
                if (!ScoreType.isUninterpretableScoreType(it)) {
                    scorerInfoSet.add(new ScorerInfo(it))
                }
            }
        }
        ArrayList resultsWithUniqueScoreType = new ArrayList()
        if (tma_slice.isAvailable(session.user)) { // assume permission is based on tma_slice ONLY i.e if can view tma_slice, can view all associated images and scores!
            Iterator itr = scorerInfoSet.iterator()
            while(itr.hasNext()) {
                ScorerInfo s = itr.next()
                ArrayList item = new ArrayList()
                item.add(s.tma_result.id) //item.add(Tma_results.get(s.tma_result.tma_result_name, tma_slice, session.user).id) // get "standardized" tma_result record; assume user must be logged
                item.add(s.getTma_scorer1())
                item.add(s.getTma_scorer2())
                item.add(s.getTma_scorer3())
                item.add(s.getScoring_date())
                item.add(s.getScoreType())
                item.add(MiscUtil.formatDate(s.getScoring_date())) // nice date format for printing purposes
                // this is needed because IE doesn't parse dates 
                // very well
                //item.add(s.getTma_result_name())
                item.add(s.tma_result.getTma_result_name().getId())
				
                resultsWithUniqueScoreType.add(item)
            }
        }
        render resultsWithUniqueScoreType as JSON
    }

    /*
     * get all available tma_slices
     * - designed for datagrid in list.gsp from Tma_slices
     * - format [name]___[id]
     */
    def ajaxGetAvailableTma_slices = {        
        TreeSet<Tma_slices> tma_slices = new TreeSet<Tma_slices>();
        Users user = Users.findByLogin(session.user.login);
        if (params.containsKey("tma_projects_id")) {
            def availableTma_slices = Tma_projects.get(params.tma_projects_id)?.getAvailableTma_slices(user);
            if (availableTma_slices?.size() >0) {
                availableTma_slices.each {
                    tma_slices.add(it);
                }
            }
        } else if (params.containsKey("tma_arrays_id")) {
            def availableTma_slices = Tma_arrays.get(params.tma_arrays_id)?.getAvailableTma_slices(user);
            if (availableTma_slices?.size() >0) {
                availableTma_slices.each {
                    tma_slices.add(it);
                }
            }
        } else if (params.containsKey("biomarkers_id")) {
            def availableTma_slices = Biomarkers.get(params.biomarkers_id)?.getAvailableTma_slices(user);
            if (availableTma_slices?.size() >0) {
                availableTma_slices.each {
                    tma_slices.add(it);
                }
            }
        } else {
            if (user.showIsAdministrator()) {
                Tma_slices.list().each {
                    tma_slices.add(it); // all tma slices are available to administrator
                }
            } else {
                user.getUser_permits().each {
                    if (it.getTma_slice() != null) {
                        tma_slices.add(it.getTma_slice());
                    }
                }
            }
        }
        int unique_id=0; // unique id for dojo table
        int id = 0;
        String name;
        String tb;
        String ta;
        String tp;
        String bm;


        List array = new ArrayList(tma_slices.size());

        for (tma_slice in tma_slices) {
            Tma_blocks tma_block = tma_slice.getTma_block();
            Tma_arrays tma_array = tma_block.getTma_array();
            Tma_projects tma_project = tma_array.getTma_project();
            Biomarkers biomarker = tma_slice.getStaining_detail().getBiomarker();
            id = unique_id++;
            name = tma_slice.name+ViewConstants.AJAX_RESPONSE_DELIMITER+tma_slice.id;
            tb = tma_block.getName()+ViewConstants.AJAX_RESPONSE_DELIMITER+tma_block.getId();
            ta = ""+tma_array.getArray_version()+ViewConstants.AJAX_RESPONSE_DELIMITER+tma_array.getId();
            tp = tma_project.getName()+ViewConstants.AJAX_RESPONSE_DELIMITER_2+tma_project.getDescription()+ViewConstants.AJAX_RESPONSE_DELIMITER+tma_project.getId();
            bm = biomarker.getName()+" ("+biomarker.getBiomarker_type().getName()+")"+ViewConstants.AJAX_RESPONSE_DELIMITER+biomarker.getId();
            array.add("id":id, "name":name,"tma_block":tb,"tma_array":ta,"tma_project":tp,"biomarker":bm);
            unique_id++;
        }


        render(contentType: "text/json") {
            identifier("id")
            numRows(tma_slices.size())
            items(array)
        }
    }
}
