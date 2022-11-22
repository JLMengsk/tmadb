/*
 * responsible for initializing ki67 qc calibration scoring session
 */
package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator

import ca.ubc.gpec.tmadb.GenericScorings;
import ca.ubc.gpec.tmadb.Nuclei_selection_notifications;
import ca.ubc.gpec.tmadb.Scorings;
import ca.ubc.gpec.tmadb.Scoring_nuclei_selection_notifications;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Tma_core_images;
import ca.ubc.gpec.tmadb.Tma_scorers;
import ca.ubc.gpec.tmadb.Tma_scorings;
import ca.ubc.gpec.tmadb.Tma_scoring_references;
import ca.ubc.gpec.tmadb.util.ViewConstants;

/**
 *
 * @author samuelc
 */
class Scoring_sessionInitializer {

    public static final String CALIBRATOR_NAME = "Ki67-QC calibrator";
    public static final String TRAINING_SESSION_NAME_PREFIX = "Ki67-QC calibrator training session";
    public static final String TRAINING_SESSION_DESCRIPTION_PREFIX = "Nine Ki67 TMA core images (from 10-011 block B) as Ki67 calibrator training set";
    public static final String TEST_SESSION_NAME_PREFIX = "Ki67-QC calibrator test session";
    public static final String TEST_SESSION_DESCRIPTION_PREFIX = "Nine Ki67 TMA core images (from 10-011 block B) as Ki67 calibrator test set";
    
    public static final long[] TRAINING_TMA_CORE_IMAGE_IDS              = [51434l, 51429l, 51410l, 51405l, 51448l, 51404l, 51413l, 51412l, 51407l];
    public static final long[] TRAINING_REFERENCE_TMA_SCORING_IDS_MAURO = [257l,   258l,   259l,   260l,   261l,   262l,   263l,   264l,   265l]; // ORDER MUST MATCH TRAINING_TMA_CORE_IMAGE_IDS!!!
    public static final long[] TRAINING_REFERENCE_TMA_SCORING_IDS_LILA  = [284l,   285l,   286l,   287l,   288l,   289l,   290l,   291l,   292l]; // ORDER MUST MATCH TRAINING_TMA_CORE_IMAGE_IDS!!!
    public static final long[] TRAINING_REFERENCE_TMA_SCORING_IDS_DORIS = [293l,   294l,   295l,   296l,   297l,   298l,   299l,   300l,   301l]; // ORDER MUST MATCH TRAINING_TMA_CORE_IMAGE_IDS!!!
    
    public static final long[] TEST_TMA_CORE_IMAGE_IDS               = [51438l, 51418l, 51424l, 51415l, 51436l, 51433l, 51432l, 51430l, 51401l];
    public static final long[] TEST_REFERENCE_TMA_SCORING_IDS_LILA1  = [365l,   366l,   367l,   368l,   369l,   370l,   371l,   372l,   373l]; // scoring_session_id=17
    public static final long[] TEST_REFERENCE_TMA_SCORING_IDS_LILA2  = [547l,   548l,   549l,   550l,   551l,   552l,   553l,   554l,   555l]; // scoring_session_id=39
    public static final long[] TEST_REFERENCE_TMA_SCORING_IDS_MAURO1 = [374l,   375l,   376l,   377l,   378l,   379l,   380l,   381l,   382l]; // scoring_session_id=18
    public static final long[] TEST_REFERENCE_TMA_SCORING_IDS_MAURO2 = [556l,   557l,   558l,   559l,   560l,   561l,   562l,   563l,   564l]; // scoring_session_id=40

    public static final String NOTIFICATION_MESSAGE_250 = "250 nuclei have been counted.  Please proceed to the bottom of the core and count 250 nuclei."; // DO NOT CHANGE ... this is needed to match record in nuclei_selection_notifications table in database
    public static final String NOTIFICATION_MESSAGE_500 = "500 nuclei have been counted."; // DO NOT CHANGE ... this is needed to match record in nuclei_selection_notifications table in database
    
    Tma_scorers tma_scorer;
    
    /**
     * constructor
     * @param tma_scorer - the scorer to assign the scoring session to
     */
    public Scoring_sessionInitializer(Tma_scorers tma_scorer) {
        this.tma_scorer = tma_scorer;
    }
    
    /**
     * initialize training session
     * - do nothing if test session exists already
     * - return training session name
     */
    private String initializeTrainingSession() throws Scoring_sessionInitializerException {
        // find out how many training session has the user done
        int sessionCount = 0;
        for (Scoring_sessions s:Scoring_sessions.findAllByTma_scorer(tma_scorer)) {
            if (s.getName().startsWith(TRAINING_SESSION_NAME_PREFIX)) {
                sessionCount++;
            } else if (s.getName().startsWith(TEST_SESSION_NAME_PREFIX)) {
                return null; // test session exists already!!! no need to create any more session.
            }
        }
        
        String scoring_sessionName = TRAINING_SESSION_NAME_PREFIX+(sessionCount>0 ? (" (retry #"+sessionCount+")"):"");
        String scoring_sessionDescription = TRAINING_SESSION_DESCRIPTION_PREFIX+" ("+tma_scorer.getName()+")"+(sessionCount>0 ? (" - (retry #"+sessionCount+")"):"");
        
        Scoring_sessions training_session = new Scoring_sessions();
        training_session.setName(scoring_sessionName);
        training_session.setDescription(scoring_sessionDescription);
        training_session.setStatus(Scoring_sessions.STATUS_NOT_STARTED);
        training_session.setStart_date(Calendar.getInstance().getTime());
        training_session.setTma_scorer(tma_scorer);
        if (!training_session.save(flush:true)) {
            throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to scoring session record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
        }
        
        // Scorings, Tma_scorings, Tma_scoring_references objects ...
        for (int i=0; i<TRAINING_TMA_CORE_IMAGE_IDS.length; i++) {
            Tma_core_images tma_core_image = Tma_core_images.get(TRAINING_TMA_CORE_IMAGE_IDS[i]);
            if (tma_core_image == null) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to find training tma core image (id="+TRAINING_TMA_CORE_IMAGE_IDS[i]+')');
            }
            
            // Scorings
            Scorings scoring = new Scorings();
            scoring.setType(GenericScorings.SCORING_TYPE_NUCLEI_COUNT);
            if(!scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Tma_scorings
            Tma_scorings tma_scoring = new Tma_scorings();
            tma_scoring.setScoring_session(training_session);
            tma_scoring.setTma_core_image(tma_core_image);
            tma_scoring.setScoring(scoring);
            if(!tma_scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to tma scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Scoring_nuclei_selection_notifications
            Nuclei_selection_notifications message250 = Nuclei_selection_notifications.findByMessage(NOTIFICATION_MESSAGE_250);
            Nuclei_selection_notifications message500 = Nuclei_selection_notifications.findByMessage(NOTIFICATION_MESSAGE_500);
            Scoring_nuclei_selection_notifications notification250 = new Scoring_nuclei_selection_notifications();
            Scoring_nuclei_selection_notifications notification500 = new Scoring_nuclei_selection_notifications();
            notification250.setNuclei_selection_notification(message250);
            notification500.setNuclei_selection_notification(message500);
            notification250.setScoring(scoring);
            notification500.setScoring(scoring);
            if((!notification250.save(flush:true)) || (!notification500.save(flush:true))) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to nuclei selection notification record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Tma_scoring_references
            Tma_scorings mauro = Tma_scorings.get(TRAINING_REFERENCE_TMA_SCORING_IDS_MAURO[i]);
            Tma_scorings lila  = Tma_scorings.get(TRAINING_REFERENCE_TMA_SCORING_IDS_LILA[ i]);
            Tma_scorings doris = Tma_scorings.get(TRAINING_REFERENCE_TMA_SCORING_IDS_DORIS[i]);
            Tma_scoring_references reference_mauro = new Tma_scoring_references();
            Tma_scoring_references reference_lila  = new Tma_scoring_references();
            Tma_scoring_references reference_doris = new Tma_scoring_references();
            reference_mauro.setTma_scoring(tma_scoring);
            reference_lila.setTma_scoring( tma_scoring);
            reference_doris.setTma_scoring(tma_scoring);
            reference_mauro.setReference_tma_scoring(mauro);
            reference_lila.setReference_tma_scoring( lila);
            reference_doris.setReference_tma_scoring(doris);
            if ((!reference_mauro.save(flush:true)) || (!reference_lila.save(flush:true)) || (!reference_doris.save(flush:true))) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to reference tma scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
        }
        return scoring_sessionName;
    }
    
    /**
     * initialize test session
     * - do nothing if test session exists already
     * @return test session name
     */
    private String initializeTestSession() throws Scoring_sessionInitializerException {
        // find out how many training session has the user done
        for (Scoring_sessions s:Scoring_sessions.findAllByTma_scorer(tma_scorer)) {
            if (s.getName().startsWith(TEST_SESSION_NAME_PREFIX)) {
                return s.getName(); // test session exists already!!! no need to create any more session.
            }
        }
        
        String scoring_sessionName = TEST_SESSION_NAME_PREFIX;
        String scoring_sessionDescription = TEST_SESSION_DESCRIPTION_PREFIX+" ("+tma_scorer.getName()+")";
        
        Scoring_sessions test_session = new Scoring_sessions();
        test_session.setName(scoring_sessionName);
        test_session.setDescription(scoring_sessionDescription);
        test_session.setStatus(Scoring_sessions.STATUS_NOT_STARTED);
        test_session.setStart_date(Calendar.getInstance().getTime());
        test_session.setTma_scorer(tma_scorer);
        if (!test_session.save(flush:true)) {
            throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to scoring session record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
        }
        
        // Scorings, Tma_scorings, Tma_scoring_references objects ...
        for (int i=0; i<TEST_TMA_CORE_IMAGE_IDS.length; i++) {
            Tma_core_images tma_core_image = Tma_core_images.get(TEST_TMA_CORE_IMAGE_IDS[i]);
            if (tma_core_image == null) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to find training tma core image (id="+TEST_TMA_CORE_IMAGE_IDS[i]+')');
            }
            
            // Scorings
            Scorings scoring = new Scorings();
            scoring.setType(GenericScorings.SCORING_TYPE_NUCLEI_COUNT);
            if(!scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Tma_scorings
            Tma_scorings tma_scoring = new Tma_scorings();
            tma_scoring.setScoring_session(test_session);
            tma_scoring.setTma_core_image(tma_core_image);
            tma_scoring.setScoring(scoring);
            if(!tma_scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to tma scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Scoring_nuclei_selection_notifications
            Nuclei_selection_notifications message250 = Nuclei_selection_notifications.findByMessage(NOTIFICATION_MESSAGE_250);
            Nuclei_selection_notifications message500 = Nuclei_selection_notifications.findByMessage(NOTIFICATION_MESSAGE_500);
            Scoring_nuclei_selection_notifications notification250 = new Scoring_nuclei_selection_notifications();
            Scoring_nuclei_selection_notifications notification500 = new Scoring_nuclei_selection_notifications();
            notification250.setNuclei_selection_notification(message250);
            notification500.setNuclei_selection_notification(message500);
            notification250.setScoring(scoring);
            notification500.setScoring(scoring);
            if((!notification250.save(flush:true)) || (!notification500.save(flush:true))) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to nuclei selection notification record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }
            
            // Tma_scoring_references
            Tma_scorings mauro1 = Tma_scorings.get(TEST_REFERENCE_TMA_SCORING_IDS_MAURO1[i]);
            Tma_scorings mauro2 = Tma_scorings.get(TEST_REFERENCE_TMA_SCORING_IDS_MAURO2[i]);
            Tma_scorings lila1  = Tma_scorings.get(TEST_REFERENCE_TMA_SCORING_IDS_LILA1[ i]);
            Tma_scorings lila2  = Tma_scorings.get(TEST_REFERENCE_TMA_SCORING_IDS_LILA2[ i]);
            
            Tma_scoring_references reference_mauro1 = new Tma_scoring_references();
            Tma_scoring_references reference_mauro2 = new Tma_scoring_references();
            Tma_scoring_references reference_lila1  = new Tma_scoring_references();
            Tma_scoring_references reference_lila2  = new Tma_scoring_references();
            
            reference_mauro1.setTma_scoring(tma_scoring);
            reference_mauro2.setTma_scoring(tma_scoring);
            reference_lila1.setTma_scoring( tma_scoring);
            reference_lila2.setTma_scoring( tma_scoring);
            
            reference_mauro1.setReference_tma_scoring(mauro1);
            reference_mauro2.setReference_tma_scoring(mauro2);
            reference_lila1.setReference_tma_scoring( lila1);
            reference_lila2.setReference_tma_scoring( lila2);
            
            if ((!reference_mauro1.save(flush:true)) || (!reference_mauro2.save(flush:true)) || (!reference_lila1.save(flush:true)) || (!reference_lila2.save(flush:true)) ) {
                throw new Scoring_sessionInitializerException("initializeTrainingSession(): failed to reference tma scoring record to database.  please contact "+ViewConstants.HELP_EMAIL+" for assistance.");
            }   
        }       
        
        return scoring_sessionName;
    }
    
    /**
     * method to initialize calibrator session
     * - this method will initialize training/testing session depending on the 
     *   user's current state.
     * - this is the method to call from the controller
     * return a message to be shown to user
     */
    public String initializeSession() throws Scoring_sessionInitializerException {
        // check all scoring sessions from this tma_scorer ...
        boolean trainingPassed = false;
        boolean testExists = false;
        boolean firstTime = true; // i.e. first time initializing training session
        String prevScoringSessionName = null;
        SortedSet<Scoring_sessions> scoring_sessions = new TreeSet<>();
        Scoring_sessions.findAllByTma_scorer(tma_scorer).each {
            scoring_sessions.add(it);
        }
        for (Scoring_sessions scoring_session:scoring_sessions) {
            if(scoring_session.showIsKi67QcCalibratorTest()){
                return; // test exist ... no need to do anything!
            }
            if (scoring_session.showIsKi67QcCalibratorTraining()) {
                firstTime = false;
                if (scoring_session.showPassedCalibrator()) {
                    prevScoringSessionName = scoring_session.getName();
                    trainingPassed = true;
                    break; // all you need is pass in one training set and ready for test set 
                } else {
                    if (prevScoringSessionName == null) {
                        // only want to capture the first session
                        // ASSUME the first session must be the LATEST one since
                        // scoring_session compareTo is reverse order by date
                        prevScoringSessionName = scoring_session.getName();
                    }
                }
            }
        }
        
        // if we are here, test set must not exist, since if it exists, would 
        // return in above for loop
        if (trainingPassed) {
            initializeTestSession(); // initialize test session
            return "Congratulations! "+prevScoringSessionName+" passed.  Please proceed to the test session: "
        } else {
            String trainingSessionName = initializeTrainingSession(); // iniatialize another training session.
            return (prevScoringSessionName==null?TRAINING_SESSION_NAME_PREFIX:prevScoringSessionName)+(firstTime?" has been initialized.":(" not passed.  Please do re-try training session: "+trainingSessionName));
        }
    }
    
    
}

