package ca.ubc.gpec.tmadb.upload.tma

/**
 * exception encountered during saving to database
 * @author samuelc
 *
 */
class UploadCoreScoresSaveException extends Exception {

	public UploadCoreScoresSaveException(String msg) {
		super(msg);
	}
}
