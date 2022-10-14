/*
 * something went wrong when trying to save field selection param string i.e. whole_section_region_scoring objects
 * to database
 */
package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3;

/**
 *
 * @author samuelc
 */
public class FieldSelectionParamSaveToDbException extends RuntimeException {
    public FieldSelectionParamSaveToDbException(String msg) {
        super(msg);
    }
}
