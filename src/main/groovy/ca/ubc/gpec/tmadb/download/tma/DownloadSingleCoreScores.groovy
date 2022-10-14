package ca.ubc.gpec.tmadb.download.tma

import java.util.ArrayList;

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Tma_result_names;
import ca.ubc.gpec.tmadb.Tma_results;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.Tma_projects;
import ca.ubc.gpec.tmadb.Staining_details;
import ca.ubc.gpec.tmadb.download.tma.model.*;

/**
 * process download request for single maker, single scorer
 * 
 * @author samuelc
 *
 */
class DownloadSingleCoreScores extends DownloadCoreScores {
	
	int staining_details_id;
	int tma_results_id;
	
	/**
	 * constructor
	 * @param log
	 * @param user
	 */
	public DownloadSingleCoreScores(Log log, Users user) {
		super(log, user);
	}
	
	/**
	 * retrieve data
	 */
	public void retreiveData() throws DataFrameReadException, DataFrameWriteException {

		// prep work
		prepareToRetrieveDataWithStaining_detail(Staining_details.get(staining_details_id));

		// retrieve data ...
		ArrayList<Tma_result_names> tma_result_names = new ArrayList<Tma_result_names>();
		tma_result_names.add(Tma_results.get(tma_results_id).getTma_result_name())
		addVariablesOneRowPerCore(tma_result_names)
	}
	
	String toString() {
		return "request to download the following ... staining_details_id="+staining_details_id+"; "+
				"tma_results_id="+tma_results_id+"; "+
				"table format="+table_format+"; "+
				"file format="+file_format+";\n\n"+
				super.toString();
	}
}
