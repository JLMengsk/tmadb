package ca.ubc.gpec.tmadb.upload.rnaExtractions

/**
* exception encountered when parsing file to upload RNA yield records
* @author samuelc
*
*/
class UploadRnaYieldsParseFileException extends Exception {
	public UploadRnaYieldsParseFileException(String msg) {
		super(msg);
	}
}
