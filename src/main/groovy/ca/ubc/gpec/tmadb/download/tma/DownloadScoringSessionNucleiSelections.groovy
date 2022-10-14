package ca.ubc.gpec.tmadb.download.tma;

import org.apache.commons.logging.Log;

import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Users;
import ca.ubc.gpec.tmadb.download.tma.model.*;
import ca.ubc.gpec.tmadb.download.tma.writer.DataFrameWriter;

/**
 * download nuclei selections from a scoring session
 * @author samuelc
 *
 */

class DownloadScoringSessionNucleiSelections extends DownloadCoreScores {
	
    Scoring_sessions scoring_session;
	
    /**
     * constructor
     * @param log
     * @param user
     */
    public DownloadScoringSessionNucleiSelections(Log log, Users user, String table_format, String file_format, Scoring_sessions scoring_session) {
        super(log, user);
        setTable_format(table_format);
        setFile_format(file_format);
        this.scoring_session = scoring_session;
    }
	
    /**
     * retrieve data
     */
    public void retreiveData() throws DataFrameReadException, DataFrameWriteException {
        // prep work - add id's and scores to dataFrame
        prepareToRetrieveDataWithScoring_sessionNucleiSelections(scoring_session);
    }
	
    public String toString() {
        return "request to download the following ... (scores w/ nuclei selections) scoring_session_id="+scoring_session.id+"; "+
				"table format="+table_format+"; "+
				"file format="+file_format+";\n\n"+
        super.toString();
    }
}
