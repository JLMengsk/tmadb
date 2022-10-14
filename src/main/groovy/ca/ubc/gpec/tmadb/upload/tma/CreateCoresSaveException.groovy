package ca.ubc.gpec.tmadb.upload.tma

/**
* exception encountered during saving to database
* @author samuelc
*
*/
class CreateCoresSaveException extends Exception {

	public CreateCoresSaveException(String msg) {
		super(msg);
	}
}
