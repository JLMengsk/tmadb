/*
 * capture exception when initilizaing scoring session for ki67 qc calibrator
 */
package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator;

/**
 *
 * @author samuelc
 */
public class Scoring_sessionInitializerException extends Exception {
    public Scoring_sessionInitializerException(String msg) {
        super(msg);
    }
}
