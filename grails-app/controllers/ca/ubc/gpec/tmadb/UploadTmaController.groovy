package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.upload.tma.CreateCores
import ca.ubc.gpec.tmadb.upload.tma.CreateCoresParseFileException
import ca.ubc.gpec.tmadb.upload.tma.CreateCoresSaveException
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreImages
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreImagesParseFileException
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreImagesSaveException
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScores
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScoresParseFileException
import ca.ubc.gpec.tmadb.upload.tma.UploadCoreScoresSaveException
import ca.ubc.gpec.tmadb.util.MiscUtil;

class UploadTmaController {

    def index = { 	
        redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), action: "list", params: params) 
    }
	
    def list = {}
	
    def cores = {
        render(view: "cores")
    }
	
    def coreImages = {
        render(view: "coreImages")
    }

    def coreScores = {
        render(view: "coreScores")
    }
	
    def createCores = {
        CreateCores cc = new CreateCores(log)
        bindData(cc, params)
        log.info("create cores started: tma_array_id="+cc.getTma_array_id()+
			"; tma_block_id="+cc.getTma_block_id()+
			"; patient_source_id="+cc.getPatient_source_id())
        try {
            cc.parseFile("\\t")
            render "finished create "+cc.getTma_coresArr().size()+" TMA cores and saved to database.  bye."
        } catch (CreateCoresParseFileException ccpfe) {
            render ccpfe.toString()
        } catch (CreateCoresSaveException ccse) {
            render ccse.toString()
        }
    }
	
    def uploadCoreImages = {
        UploadCoreImages uc = new UploadCoreImages(log)
        bindData(uc, params)
        log.info("upload core images started: tma_array_id="+uc.getTma_array_id()+
			"; tma_block_id="+uc.getTma_block_id()+
			"; tma_slice_id="+uc.getTma_slice_id()+
			"; image_server_id="+uc.getImage_server_id()
        )
        try {
            uc.parseFile("\\t")
            render "finished importing "+uc.getTma_core_imagesArr().size()+" images to database.  bye."
        } catch (UploadCoreImagesParseFileException ucipfe) {
            render ucipfe.toString()
        } catch (UploadCoreImagesSaveException ucise) {
            render ucise.toString()
        }
    }
	
    def uploadCoreScores = {
        UploadCoreScores uc = new UploadCoreScores(log)
        bindData(uc, params)
        log.info("upload core scores started: tma_array_id="+uc.getTma_array_id()+
			"; tma_block_id="+uc.getTma_block_id()+
			"; tma_slice_id="+uc.getTma_slice_id()+
			"; scanner_info_id="+uc.getScanner_info_id()+
			"; scanning_date="+uc.getScanning_date()+	
			"; score_type="+uc.getScore_type()+		
			"; ihc_score_category_group_id="+uc.getIhc_score_category_group_id()+
			"; number of missing cat="+uc.getMissing_ihc_score_category_id_arr().length+
			"; tma_score_id(s)="+uc.getTma_scorer1_id()+"/"+uc.getTma_scorer2_id()+"/"+uc.getTma_scorer3_id()
        )
        try {
            uc.parseFileSingleScore("\\t")
            render "finished importing "+uc.getTma_resultsArr().size()+" scores to database.  bye."
        } catch (UploadCoreScoresParseFileException ucspfe) {
            render ucspfe.toString()
        } catch (UploadCoreScoresSaveException ucsse) {
            render ucsse.toString()
        }
    }

}
