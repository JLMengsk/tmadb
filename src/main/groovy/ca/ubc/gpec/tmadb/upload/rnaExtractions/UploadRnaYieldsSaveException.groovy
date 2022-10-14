package ca.ubc.gpec.tmadb.upload.rnaExtractions

/**
* exception encountered when saving RNA yields records
* @author samuelc
*
*/
class UploadRnaYieldsSaveException extends Exception {
	public UploadRnaYieldsSaveException(String msg) {
		super(msg);
	}
}
