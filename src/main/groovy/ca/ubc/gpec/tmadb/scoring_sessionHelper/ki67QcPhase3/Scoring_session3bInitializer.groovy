/*
 * Initializer phase 3B scoring session
 * - to be executed by admin ONLY
 */

package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3

import ca.ubc.gpec.tmadb.Tma_scorers
import ca.ubc.gpec.tmadb.Scoring_sessions
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.Scoring_sessionInitializerException
import ca.ubc.gpec.tmadb.Paraffin_blocks
import ca.ubc.gpec.tmadb.Whole_section_slices
import ca.ubc.gpec.tmadb.Scorings
import ca.ubc.gpec.tmadb.GenericScorings
import ca.ubc.gpec.tmadb.Whole_section_scorings
import ca.ubc.gpec.tmadb.User_permits
import ca.ubc.gpec.tmadb.Users;


/**
 *
 * @author samuelc
 */
class Scoring_session3bInitializer {
    Tma_scorers tma_scorer;
    int group; // group 1, 2, 3 or 4
    
    public static final String BETA_TEST_SESSION_NAME_PREFIX = "Ki67-QC phase 3b beta test";
    public static final String BETA_TEST_SESSION_DESCRIPTION_PREFIX = "Practice exercise for Ki67-QC phase 3b scoring"
    
    public static final String WS_SESSION_NAME_PREFIX = "Ki67 phase 3b – nuclei count on whole section";
    public static final String WS_SESSION_DESCRIPTION_PREFIX = "30 whole section slides for Ki67-QC phase 3b study.";
    
    public static final String BX_SESSION_NAME_PREFIX = "Ki67 phase 3b – nuclei count on core biopsy";
    public static final String BX_SESSION_DESCRIPTION_PREFIX = "15 core biopsy slides for Ki67-QC phase 3b study.";
    
    public static final long[]   BETA_TEST_PARAFFIN_BLOCK_IDS            = [8119l, 8120l];
    public static final String[] BETA_TEST_WHOLE_SECTION_SLICE_NAME_KI67 = ["-",   "-"];
    public static final String[] BETA_TEST_WHOLE_SECTION_SLICE_NAME_HE   = ["s1",  "s2"];
    
    public static final String[] PHASE3_WHOLE_SECTION_SLICE_NAME_KI67 = ["s3","s4","s5","s6"]; // for group 1,2,3,4 respectively
    public static final String PHASE3_WHOLE_SECTION_SLICE_NAME_HE = "s1";
    public static final String PHASE3_WHOLE_SECTION_SLICE_NAME_P63 = "s2"
    /**
     * constructor
     * @param tma_scorer - the scorer to assign the scoring session to
     * @param group - WARNING group = {1,2,3} 
     */
    public Scoring_session3bInitializer(Tma_scorers tma_scorer, int group) {
        this.tma_scorer = tma_scorer;
        this.group = group;
    }
    
    /**
     * initialize beta test session
     * - do nothing if beta test session exists already
     * - return beta test session name
     */
    public String initializeBetaTestSession() {
        // find out how many training session has the user done
        for (Scoring_sessions s:Scoring_sessions.findAllByTma_scorer(tma_scorer)) {
            if (s.getName().startsWith(BETA_TEST_SESSION_NAME_PREFIX)) {
                return null; // beta test session exists already!!! no need to create any more session.
            }
        }
        
        String scoring_sessionName = BETA_TEST_SESSION_NAME_PREFIX;
        String scoring_sessionDescription = BETA_TEST_SESSION_DESCRIPTION_PREFIX+" ("+tma_scorer.getName()+")";
       
        Scoring_sessions beta_test_session = new Scoring_sessions();
        beta_test_session.setName(scoring_sessionName);
        beta_test_session.setDescription(scoring_sessionDescription);
        beta_test_session.setStatus(Scoring_sessions.STATUS_NOT_STARTED);
        beta_test_session.setStart_date(Calendar.getInstance().getTime());
        beta_test_session.setTma_scorer(tma_scorer);
        if (!beta_test_session.save(flush:true)) {
            throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to scoring session record to database.");
        }
        
        // Scorings, Whole_section_scorings, User_permits objects ...
        for (int i=0; i<BETA_TEST_PARAFFIN_BLOCK_IDS.length; i++) {
            Paraffin_blocks paraffin_block = Paraffin_blocks.get(BETA_TEST_PARAFFIN_BLOCK_IDS[i]);
            if (paraffin_block == null) {
                throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to find paraffin_block record id:"+BETA_TEST_PARAFFIN_BLOCK_IDS[i]);
            }
            Whole_section_slices ki67_slice = Whole_section_slices.findByParaffin_blockAndName(paraffin_block,BETA_TEST_WHOLE_SECTION_SLICE_NAME_KI67[i]);
            Whole_section_slices he_slice   = Whole_section_slices.findByParaffin_blockAndName(paraffin_block,BETA_TEST_WHOLE_SECTION_SLICE_NAME_HE[  i]);
            
            // Scorings
            Scorings scoring = new Scorings();
            scoring.setType(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3);
            if(!scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to scoring record to database.");
            }
            
            // Whole_section_scorings
            Whole_section_scorings whole_section_scoring = new Whole_section_scorings();
            whole_section_scoring.setScoring_session(beta_test_session);
            if (ki67_slice.getWhole_section_images().size() != 1) {
                throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to find UNIQUE whole section images for slice id:"+ki67_slice.getId());
            }
            whole_section_scoring.setWhole_section_image(ki67_slice.getWhole_section_images().first());
            whole_section_scoring.setScoring(scoring);
            whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_INIT);
            if(!whole_section_scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to whole section scoring record to database.");
            }
            
            // User permits
            // NOTE: if user permit exists, just ignore silently
            Users.findAllByTma_scorer(tma_scorer).each { user ->
                if (User_permits.findByUserAndWhole_section_slice(user,ki67_slice) == null) {
                    User_permits user_permit = new User_permits();
                    user_permit.setUser(user);
                    user_permit.setWhole_section_slice(ki67_slice);
                    if (!user_permit.save(flush:true)) {
                        throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to user permit (ki67) record to database.");
                    }
                }
                if (User_permits.findByUserAndWhole_section_slice(user,he_slice) == null) {
                    User_permits user_permit = new User_permits();
                    user_permit.setUser(user);
                    user_permit.setWhole_section_slice(he_slice);
                    if (!user_permit.save(flush:true)) {
                        throw new Scoring_sessionInitializerException("initializeBetaTestSession(): failed to user permit (H&E) record to database.");
                    }          
                }
            }
        }
        return scoring_sessionName;
    }
    
    /**
     * initialize phase 3 test session
     * - do nothing if phase 3 test session exists already
     * - return beta test session name
     */
    public String initializePhase3bTestSession(
        String test_session_name_prefix,
        String test_session_description_prefix,
        SortedSet<Paraffin_blocks> paraffin_blocks
    ) {
        // find out how many training session has the user done
        for (Scoring_sessions s:Scoring_sessions.findAllByTma_scorer(tma_scorer)) {
            if (s.getName().startsWith(test_session_name_prefix)) {
                return null; // beta test session exists already!!! no need to create any more session.
            }
        }
        
        String scoring_sessionName = test_session_name_prefix;
        String scoring_sessionDescription = test_session_description_prefix+" ("+tma_scorer.getName()+")";
       
        Scoring_sessions phase3_test_session = new Scoring_sessions();
        phase3_test_session.setName(scoring_sessionName);
        phase3_test_session.setDescription(scoring_sessionDescription);
        phase3_test_session.setStatus(Scoring_sessions.STATUS_NOT_STARTED);
        phase3_test_session.setStart_date(Calendar.getInstance().getTime());
        phase3_test_session.setTma_scorer(tma_scorer);
        if (!phase3_test_session.save(flush:true)) {
            throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to scoring session record to database.");
        }
        
        // Scorings, Whole_section_scorings, User_permits objects ...
        for (Paraffin_blocks paraffin_block : paraffin_blocks) {
            Whole_section_slices ki67_slice = Whole_section_slices.findByParaffin_blockAndName(paraffin_block,PHASE3_WHOLE_SECTION_SLICE_NAME_KI67[group-1]);
            Whole_section_slices he_slice   = Whole_section_slices.findByParaffin_blockAndName(paraffin_block,PHASE3_WHOLE_SECTION_SLICE_NAME_HE         );
            Whole_section_slices p63_slice  = Whole_section_slices.findByParaffin_blockAndName(paraffin_block,PHASE3_WHOLE_SECTION_SLICE_NAME_P63        );
            
            // Scorings
            Scorings scoring = new Scorings();
            scoring.setType(GenericScorings.SCORING_TYPE_KI67_QC_PHASE3);
            if(!scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to scoring record to database.");
            }
            
            // Whole_section_scorings
            Whole_section_scorings whole_section_scoring = new Whole_section_scorings();
            whole_section_scoring.setScoring_session(phase3_test_session);
            if (ki67_slice.getWhole_section_images().size() != 1) {
                throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to find UNIQUE whole section images for slice id:"+ki67_slice.getId());
            }
            whole_section_scoring.setWhole_section_image(ki67_slice.getWhole_section_images().first());
            whole_section_scoring.setScoring(scoring);
            whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_INIT);
            if(!whole_section_scoring.save(flush:true)) {
                throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to whole section scoring record to database.");
            }
            
            // User permits
            // NOTE: if user permit exists, just ignore silently
            Users.findAllByTma_scorer(tma_scorer).each { user ->
                if (User_permits.findByUserAndWhole_section_slice(user,ki67_slice) == null) {
                    User_permits user_permit = new User_permits();
                    user_permit.setUser(user);
                    user_permit.setWhole_section_slice(ki67_slice);
                    if (!user_permit.save(flush:true)) {
                        throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to user permit (ki67) record to database.");
                    }
                }
                if (User_permits.findByUserAndWhole_section_slice(user,he_slice) == null) {
                    User_permits user_permit = new User_permits();
                    user_permit.setUser(user);
                    user_permit.setWhole_section_slice(he_slice);
                    if (!user_permit.save(flush:true)) {
                        throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to user permit (H&E) record to database.");
                    }          
                }
                if (User_permits.findByUserAndWhole_section_slice(user,p63_slice) == null) {
                    User_permits user_permit = new User_permits();
                    user_permit.setUser(user);
                    user_permit.setWhole_section_slice(p63_slice);
                    if (!user_permit.save(flush:true)) {
                        throw new Scoring_sessionInitializerException("initializePhase3TestSession(): failed to user permit (H&E) record to database.");
                    }          
                }
            }
        }
        return scoring_sessionName;
    }	
}

