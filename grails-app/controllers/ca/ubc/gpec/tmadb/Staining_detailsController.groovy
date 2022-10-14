package ca.ubc.gpec.tmadb

import grails.converters.*;

import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScores;
import ca.ubc.gpec.tmadb.util.MiscUtil;

class Staining_detailsController {

    def scaffold = Staining_details // def index = { }
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [staining_detailsInstanceList: Staining_details.list(params), staining_detailsInstanceTotal: Staining_details.count()]
    }

    def show = {
        Users user = Users.findByLogin(session.user.login)
        def staining_detailsInstance = Staining_details.get(params.id, user)
        if (!staining_detailsInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'staining_details.label', default: 'Staining_details'), params.id])}"
            flash.message = "Staining_details "+MessageConstant.ITEM_NOT_FOUND_OR_ACCESS_DENIED+" with id "+params.id
            redirect(action: "list")
        }
        else {
            [staining_detailsInstance: staining_detailsInstance, availableTma_slices: staining_detailsInstance.getAvailableTma_slices(user), availableWhole_section_slices: staining_detailsInstance.getAvailableWhole_section_slices(user)]
        }
    }

    // get all available tma scorers
    def ajaxGetAvailableScorers = {
        def c = Tma_results.createCriteria()
        def tma_core_images = Tma_core_images.withCriteria {
			'in'('tma_slice', Staining_details.get(params.id).getTma_slices())
        }
        def results = c {
			'in'('tma_core_image', tma_core_images)
            projections {
                property("id")
                groupProperty("tma_scorer1")
                groupProperty("tma_scorer2")
                groupProperty("tma_scorer3")
                groupProperty("received_date")
                groupProperty(UploadCoreScores.SCORE_TYPE_TOTAL_NUCLEI_COUNT)
                groupProperty("positive_nuclei_count")
                groupProperty("positive_membrane_count")
                groupProperty("positive_cytoplasmic_count")
                groupProperty(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE)
                groupProperty(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE)
                groupProperty(UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE)
                groupProperty(UploadCoreScores.SCORE_TYPE_POSITIVE_ITIL_COUNT)
                groupProperty(UploadCoreScores.SCORE_TYPE_POSITIVE_STIL_COUNT)
                groupProperty(UploadCoreScores.SCORE_TYPE_IHC_SCORE_CATEGORICAL)
                groupProperty("fish_amplification_ratio")
                groupProperty("fish_average_signal")
            }
        }
		
        // find out score type
        HashSet<ScorerInfo> scorerInfoSet = new HashSet<ScorerInfo>()
        results.each {
            scorerInfoSet.add(new ScorerInfo(Tma_results.get(((Long)it[0]).intValue()))  )
        }
        ArrayList resultsWithUniqueScoreType = new ArrayList()
        Iterator itr = scorerInfoSet.iterator()
        while(itr.hasNext()) {
            ScorerInfo s = itr.next()
            if (!ScoreType.isUninterpretableScoreType(s.tma_result)) {
                ArrayList item = new ArrayList()
                item.add(s.tma_result.id)
                item.add(s.getTma_scorer1())
                item.add(s.getTma_scorer2())
                item.add(s.getTma_scorer3())
                item.add(s.getReceived_date())
                item.add(s.getScoreType())
                item.add(MiscUtil.formatDate(s.getReceived_date())) // nice date format for printing purposes
                // this is needed because IE doesn't parse dates
                // very well
                item.add(s.getTma_result_name())
                resultsWithUniqueScoreType.add(item)
            }
        }
        render resultsWithUniqueScoreType as JSON
    }
}
