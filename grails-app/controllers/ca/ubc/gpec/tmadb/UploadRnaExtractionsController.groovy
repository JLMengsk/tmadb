package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaExtractions
import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaExtractionsParseFileException
import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaExtractionsSaveException
import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaYields
import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaYieldsParseFileException
import ca.ubc.gpec.tmadb.upload.rnaExtractions.UploadRnaYieldsSaveException

class UploadRnaExtractionsController {

	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {}
	
	def rnaExtractions = {
		render(view: "rnaExtractions")
	}
	
	def rnaYields = {
		render(view: "rnaYields")
	}
	
	def uploadRnaExtractions = {
		UploadRnaExtractions ure = new UploadRnaExtractions(log)
		bindData(ure, params)
		log.info("upload RNA extraction records started: patient_source_id="+ure.getPatient_source_id()+
			"; coring_project_id="+ure.getCoring_project_id())
		try {
			ure.parseFile("\\t")
			render "finished upload "+ure.getRna_extractionsArr().size()+" RNA extraction records and saved to database.  bye."
		} catch (UploadRnaExtractionsParseFileException urefe) {
			render urefe.toString()
		} catch (UploadRnaExtractionsSaveException urese) {
			render urese.toString()
		}
	}
	
	def uploadRnaYields = {
		UploadRnaYields ury = new UploadRnaYields(log)
		bindData(ury, params)
		log.info("upload RNA yield records started: patient_source_id="+ury.getPatient_source_id()+
			"; coring_project_id="+ury.getCoring_project_id())
		try {
			ury.parseFile("\\t")
			render "finished upload "+ury.getRna_yieldsArr().size()+" RNA yield records and saved to database.  bye."
		} catch (UploadRnaYieldsParseFileException uryfe) {
			render uryfe.toString()
		} catch (UploadRnaYieldsSaveException uryse) {
			render uryse.toString()
		}
	}
}
