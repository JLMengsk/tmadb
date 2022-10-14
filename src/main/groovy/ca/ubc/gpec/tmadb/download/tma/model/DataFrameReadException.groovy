package ca.ubc.gpec.tmadb.download.tma.model

/**
 * exception encountered when reading data from data frame object
 * @author samuelc
 *
 */
class DataFrameReadException extends Exception {

	public DataFrameReadException(String msg) {
		super(msg);
	}
}
