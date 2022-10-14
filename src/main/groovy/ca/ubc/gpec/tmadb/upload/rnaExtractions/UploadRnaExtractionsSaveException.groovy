package ca.ubc.gpec.tmadb.upload.rnaExtractions

/**
 * exception encountered when saving RNA extractions records
 * @author samuelc
 *
 */
class UploadRnaExtractionsSaveException extends Exception {
	public UploadRnaExtractionsSaveException(String msg) {
		super(msg);
	}
}
