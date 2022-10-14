package ca.ubc.gpec.tmadb;

import java.util.Calendar;
import java.text.NumberFormat;

import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants;
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.scoreTma.UploadCoreImagesForScoring;
import ca.ubc.gpec.tmadb.scoreTma.UploadCoreImagesForScoringParseFileException;
import ca.ubc.gpec.tmadb.scoreTma.UploadCoreImagesForScoringSaveException;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.ScoringFieldsAllocator;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.FieldSelectionParamStringDbConnector
import java.text.SimpleDateFormat
import java.text.DateFormat
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.Scoring_sessionInitializer;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.Scoring_session3bInitializer;

class Scoring_sessionsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def setup = {
        render(view: "setup");
    }
    
    /**
     * submit scores
     **/
    def submitScores = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id);
        // Before changing the state of the scoring_session to submitted, need some error checking
        // some error checking are specific to certain types of test
        boolean ok = true;
        // Test #1 (for Ki67 QC phase 3 only
        if (!scoring_sessionInstance.showIsAllKi67QcPhase3ScoringsCompleted()) {
            ok = false;
            redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), controller: "scoring_sessions", action: "score", id:params.id);
        }
        
        
        if (ok) {
            scoring_sessionInstance.changeStatusToSubmitted();
            scoring_sessionInstance.setSubmitted_date(Calendar.getInstance().getTime());
            // special procedure for Ki67 QC phase 3 ... need to set all whole_section_scoring state to nuclei_count
            // otherwise, will expose editable parts to session that is submitted already!!!
            for (Whole_section_scorings whole_section_scoring:scoring_sessionInstance.getWhole_section_scorings()) {
                if(whole_section_scoring.showIsScoringTypeKi67Phase3()) {
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_NUCLEI);
                    if (!whole_section_scoring.save(flush:true)) {
                        render("something went wrong in submitting score!!! (failed to save whole_section_scoring)");
                        return;
                    }
                }
            }
            if (scoring_sessionInstance.save(flush:true)) {
                if (scoring_sessionInstance.showIsKi67QcCalibratorTraining()) {
                    // user is submitting scores for ki67 qc calibrator training ... the user expects the following outcome
                    // 1. training failed, set up retraiing
                    // 2. training passed, set up test
                    ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer scoring_sessionInitializer = new ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcCalibrator.Scoring_sessionInitializer(scoring_sessionInstance.getTma_scorer());
                    flash.message = scoring_sessionInitializer.initializeSession(); // let the scoring_sessionInitializer decide what (if any) scoring session to initialize
                } else if (scoring_sessionInstance.showIsKi67QcCalibratorTest()) {
                    // user is submitting scores for ki67 qc calibrator test
                    // generate a  passed / not passed message
                    if (scoring_sessionInstance.showPassedCalibrator()) {
                        flash.message = "Congratulations! "+scoring_sessionInstance.getName()+" passed."
                    } else {
                        flash.message = scoring_sessionInstance.getName()+" not passed."
                    }
                }
                redirect(base: (MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL:grailsApplication.config.grails.serverURL), controller: "scoreTma", action: "list");
            } else {
                render("something went wrong in submitting score!!!")
            }
        }
    }
    
    // reset all scores!!!
    def resetScores = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id);
        // iterate through all tma_scorings ...
        boolean status = true;
        scoring_sessionInstance.tma_scorings.each {
            status = status && it.resetScore();
        }
        scoring_sessionInstance.status=Scoring_sessions.STATUS_NOT_STARTED;
        status = status && scoring_sessionInstance.save(flush: true);
        if (!status) {
            render("something went wrong while resseting scores");
        } else {
            render("reset score successful");
        }
    }
       
    def uploadCoreImagesForScoring = {
        UploadCoreImagesForScoring uc = new UploadCoreImagesForScoring(log)
        bindData(uc, params)
        log.info("upload core images for scoring started: tma_project_id="+uc.getTma_project_id()+
				"; biomarker_id="+uc.getBiomarker_id()+
				"; scoring_session_id="+uc.getScoring_session_id()
        )
        try {
            uc.parseFile("\\t")
            render "finished importing "+uc.getTma_scoringsArr().size()+" tma_scoring objects to database.  bye."
        } catch (UploadCoreImagesForScoringParseFileException ucipfe) {
            render ucipfe.toString()
        } catch (UploadCoreImagesForScoringSaveException ucise) {
            render ucise.toString()
        }
    }

    def saveScore = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id);
        Tma_scorings tma_scoringInstance = Tma_scorings.get(Long.parseLong(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID)));
               
        if (tma_scoringInstance.showIsScoringTypeFreeText()) {
            // only do the following if free text scoring
        
            // all types of scoring support input of comments ... for nuclei count ... comment saved via uploadNucleiSelection in Tma_scoringsController.groovy
            tma_scoringInstance.inputScoringComment(params.get("inputComment"));
            
            tma_scoringInstance.inputScoringScore(params.get("inputScore"));
            tma_scoringInstance.inputScoringScoring_date(Calendar.getInstance().getTime());
            // visit_order is set in "score" method

            if (tma_scoringInstance.saveScoring(true)) {
                // check to see if this is the most currently scored image
                Tma_scorings nextTma_scoringInstance = scoring_sessionInstance.showNextTma_scoring(tma_scoringInstance);
                if (nextTma_scoringInstance == null) {
                    // show new image for scoring
                    redirect(base: grailsApplication.config.grails.serverSecureURL, action: "score", id: params.get("id"));
                } else {
                    redirect(base: grailsApplication.config.grails.serverSecureURL, action: "score", id: params.get("id"), params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID):nextTma_scoringInstance.id]);
                }
            } else {
                render("something went wrong in save score!!!")
            }
        } else if (tma_scoringInstance.showIsScoringTypeNucleiCount() || tma_scoringInstance.showIsScoringTypeNucleiCountNoCoord()){
            // nuclei selection saved already (via applet function ...)
            // save scoring_date to show finished scoring on this image
            tma_scoringInstance.inputScoringScoring_date(Calendar.getInstance().getTime());
            if (tma_scoringInstance.saveScoring(true)) {
                // check to see if this is the most currently scored image
                Tma_scorings nextTma_scoringInstance = scoring_sessionInstance.showNextTma_scoring(tma_scoringInstance);
                if (nextTma_scoringInstance == null) {
                    // show new image for scoring
                    redirect(base: grailsApplication.config.grails.serverSecureURL, action: "score", id: params.get("id"));
                } else {
                    redirect(base: grailsApplication.config.grails.serverSecureURL, action: "score", id: params.get("id"), params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID):nextTma_scoringInstance.id]);
                }
            } else {
                render("something went wrong in save score!!!")
            }
        }
    }

    /**
     * save answer to scoring_session_questions
     */
    def save_scoring_session_questions_answers = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id);
        scoring_sessionInstance.scoring_session_questions.each {
            it.setAnswer(params.get(Scoring_session_questions.ANSWER_HTML_INPUT_PARAM_NAME+it.display_order)?.trim());
        }
        scoring_sessionInstance.save(flush:true);
        redirect(action: "score", id: scoring_sessionInstance.id)
    }
    
    /*
     * do scoring!!!
     * - display different depending on whether scoring session is not started, during scoring, completed or submitted.
     * 
     * 1. if scoring session is completed or submitted, redirect to report if there is no tma_scoring_id param available
     * 
     */
    def score = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id)
        if (!scoring_sessionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoring_sessions.label', default: 'Scoring_sessions'), params.id])}"
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
            return; // no need to go further
        }
        
        // determine the type of scorings ..       
        Tma_scorings tma_scoring = null;
        Whole_section_scorings whole_section_scoring = null;
        if (params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID)) {
            tma_scoring = Tma_scorings.get(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID));
        } else if (params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)) {
            whole_section_scoring = Whole_section_scorings.get(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID));
        } else {
            // cannot figure out scoring type by param name ... need to check scoring_sessionInstance
            GenericScorings genericScoring = scoring_sessionInstance.showUnscoredScoring(); // showUnscoredScoring updates scoring_session status!!! therefore need to run this BEFORE e.g. scoring_sessionInstance.showCompleted();
            if (!genericScoring) {
                genericScoring = scoring_sessionInstance.showMaxVisitOrderScoring();
            }
            if (genericScoring instanceof Tma_scorings) {
                tma_scoring = (Tma_scorings)genericScoring;
            } else if (genericScoring instanceof Whole_section_scorings) {
                whole_section_scoring = (Whole_section_scorings)genericScoring;
            } else {
                render "unknown scoring type!!!";
                return;
            }
        }
        
        // some variables indicating the scoring session status
        boolean sessionCompleted = scoring_sessionInstance.showCompleted();
        boolean sessionSubmitted = scoring_sessionInstance.showSubmitted();

        if ((sessionCompleted || sessionSubmitted) && (!params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID)) && (!params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID))) {
            // if scoring session is completed or submitted, redirect to report
            redirect(base: grailsApplication.config.grails.serverSecureURL, action: "report", id: scoring_sessionInstance.id);
            return;
        }
              
        // ask pre-test questions ... these are likely question to just collect some statistics on tester
        if (scoring_sessionInstance.showHasUnansweredQuestions()) {
            render(view: "askquestions", model: [scoring_sessionInstance: scoring_sessionInstance])
            return;
        }
                
        // determine flash message:
        if (sessionSubmitted) {
            flash.message = scoring_sessionInstance.name+" ... scores submitted.  Please note: scores can no longer be updated.";
        } else if (sessionCompleted) {
            flash.message = scoring_sessionInstance.name+" ... scoring completed!  Please review and submit scores.";
        }
                        
	// put xxx_scoring_id in params to keep track
        if (tma_scoring != null) {
            params.put(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_TMA_SCORING_ID, tma_scoring.id);
        } else if (whole_section_scoring != null) {
            params.put(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID, whole_section_scoring.id);
        }
                       
        // save visit_order
        if (tma_scoring != null) {
            if (tma_scoring.showScoringVisit_order() == null) {
                tma_scoring.inputScoringVisit_order(scoring_sessionInstance.showNextVisit_order()); // do not overwrite existing visit_order
                if (!tma_scoring.saveScoring(true)) {
                    render "something went wrong trying to save scoring of tma_scorings!!!"
                }
            }
        } else if (whole_section_scoring != null) {
            if (whole_section_scoring.showScoringVisit_order() == null) {
                whole_section_scoring.inputScoringVisit_order(new Integer(scoring_sessionInstance.showNextVisit_order())); // do not overwrite existing visit_order
                if (!whole_section_scoring.saveScoring(true)) {
                    render "something went wrong trying to save scoring of whole_section_scorings!!!";
                }
            }
        }
        
        // give user some time to do nuclei selection by extending
        // session time out to 3 hour
        boolean needGive3HoursTimeout = (tma_scoring!=null) ? ((tma_scoring.showIsScoringTypeNucleiCount() || tma_scoring.showIsScoringTypeNucleiCountNoCoord()) && tma_scoring.showIsAllowedToUpdateScore()) : false;
        if (!needGive3HoursTimeout) {
            // for whole section, set time out to 3 hours regardless of scoring type ... since whole section scoring requires time regardless of scoring type.
            needGive3HoursTimeout = (whole_section_scoring!=null) ? whole_section_scoring.showIsAllowedToUpdateScore() : false; 
        }
        if (needGive3HoursTimeout) {
            session.setMaxInactiveInterval(Scoring_sessionsConstants.SCORING_SESSION_TIMEOUT_IN_SECONDS); // 3 hours time out
        }
        
        // check to see if need to show H&E
        boolean showHE = false;
        boolean showMM = false; // as of 2014-02-04, only Ki67-QC phase 3 uses P63 as the myoepithelial marker (for differentiate invasive from in situ carcinoma)
        boolean showReference = false;
        boolean showPageBodyOnly = false;
        String referenceTitle = null;
        if (params.containsKey('showHE')) {showHE=true;}
        if (params.containsKey('showMM')) {showMM=true;} // show myoepithelial marker
        if (params.containsKey(ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG)) {showPageBodyOnly=true;}
        if (scoring_sessionInstance.showSubmitted()) {  
            // only show reference if submitted
            if (params.containsKey('showRef')) {            
                int refNum = Integer.parseInt(params.get("showRef")).intValue(); 
                if (tma_scoring != null) { // only tma_scoring has reference
                    int totalNumRef = tma_scoring.showTma_scoring_references().size()
                    if (totalNumRef > refNum) {
                        showReference = true;
                        Iterator<Tma_scoring_references> itr = tma_scoring.showTma_scoring_references().iterator();
                        for (int i=0; i<refNum; i++) {
                            itr.next();
                        }
                        tma_scoring = itr.next().reference_tma_scoring;
                        referenceTitle = "Reference image #"+(refNum+1); // refNum is 0-based
                    }
                }
            }
        }
            
        // for Ki67 QC phase 3 ... specific test pattern ...
        if(whole_section_scoring?.showIsScoringTypeKi67Phase3() && !showHE && !showMM) {
            ki67_qc_phase3(scoring_sessionInstance, whole_section_scoring, showHE, showMM, showPageBodyOnly);
        } else { // other generic scoring ...
            render(view: "show",
                model: [
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (tma_scoring != null ? tma_scoring.getScoring() : (whole_section_scoring != null ? whole_section_scoring.getScoring(): null)),
                    tma_scoringInstance: tma_scoring,
                    whole_section_scoring: whole_section_scoring,
                    applet_server_root_url: MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName,
                    showHE: showHE,
                    showMM: showMM,
                    showReference: showReference,
                    showPageBodyOnly: showPageBodyOnly,
                    referenceTitle: referenceTitle
                ])
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /// KI67-QC PHASE 3 RELATED                                              ///
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * initialize ki67 qc phase 3 scoring session ...
     */
    def initialize_ki67_qc_phase3 = {
        def tma_scorers = Tma_scorers.findAllByNameLike("%"+params.get("tma_scorer_name")+"%")
        if (tma_scorers.isEmpty()) {
            render "failed to find tma_scorer: "+params.get("tma_scorer_name");
            return;
        } else if (tma_scorers.size() > 1) {
            render "multiple matches for tma_scorer with name: "+params.get("tma_scorer_name");
            return;
        } else {
            Integer group = Integer.parseInt(params.get("group"));
            if (group == null) {
                render "missing group!!!"
                return;
            } else {
                if (group > 3 || group < 1) {
                    render "invalid group value: "+group;
                    return;
                } else {
                    Tma_scorers tma_scorer = tma_scorers.first();
                    ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.Scoring_sessionInitializer scoring_sessionInitializer = new ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.Scoring_sessionInitializer(tma_scorer, group.intValue());
                    //String betaTestSessionName   = scoring_sessionInitializer.initializeBetaTestSession();
                    String phase3TestSessionName = scoring_sessionInitializer.initializePhase3TestSession();
                    //render betaTestSessionName+", "+phase3TestSessionName+" initialized for "+tma_scorer.getName();
                    render phase3TestSessionName+" initialized for "+tma_scorer.getName();
                }
            }
        }
    }
    
    /**
     * initialize ki67 qc phase 3b (whole section) scoring session ...
     */
    def initialize_ki67_qc_phase3b = {
        def tma_scorers = Tma_scorers.findAllByNameLike("%"+params.get("tma_scorer_name")+"%")
        if (tma_scorers.isEmpty()) {
            render "failed to find tma_scorer: "+params.get("tma_scorer_name");
            return;
        } else if (tma_scorers.size() > 1) {
            render "multiple matches for tma_scorer with name: "+params.get("tma_scorer_name");
            return;
        } else {
            Integer group = Integer.parseInt(params.get("group"));
            if (group == null) {
                render "missing group!!!"
                return;
            } else {
                if (group > 4 || group < 1) {
                    render "invalid group value: "+group;
                    return;
                } else {
                    String type = params.get("type");
                    boolean typeOK = false;
                    String typeWS = "ws";
                    String typeBX = "bx";
                    String typeBeta = "beta";
                    TreeSet<Paraffin_blocks> paraffin_blocks = new TreeSet<Paraffin_blocks>();
                    if (type==typeWS) {
                        Paraffin_blocks.findAllByCommentLike("%Ki67-QC phase 3b%").each {
                            if (it.showIsSurgical_block()) {
                                paraffin_blocks.add(it);
                            }
                        }
                        typeOK = true;
                    } else if (type==typeBX) {
                        Paraffin_blocks.findAllByCommentLike("%Ki67-QC phase 3b%").each {
                            if (it.showIsCore_biopsy_block()){
                                paraffin_blocks.add(it);
                            }
                        }
                        typeOK = true;
                    } else if (type==typeBeta) {
                        // paraffin blocks predefined - no need to search
                        typeOK = true;
                    } else{
                        render "unknown scoring session type: "+type;
                        return;
                    }
                    if (typeOK) {
                        Tma_scorers tma_scorer = tma_scorers.first();
                        Scoring_session3bInitializer scoring_session3bInitializer = new Scoring_session3bInitializer(tma_scorer, group.intValue());
                        if (type==typeBeta) {
                            String betaTestSessionName = scoring_session3bInitializer.initializeBetaTestSession();
                            render betaTestSessionName+" initialized for "+tma_scorer.getName();
                        } else {
                            String phase3TestSessionName = type==typeWS ? scoring_session3bInitializer.initializePhase3bTestSession(
                                Scoring_session3bInitializer.WS_SESSION_NAME_PREFIX,
                                Scoring_session3bInitializer.WS_SESSION_DESCRIPTION_PREFIX,
                                paraffin_blocks
                            ) : scoring_session3bInitializer.initializePhase3bTestSession(
                                Scoring_session3bInitializer.BX_SESSION_NAME_PREFIX,
                                Scoring_session3bInitializer.BX_SESSION_DESCRIPTION_PREFIX,
                                paraffin_blocks
                            );  
                            if (phase3TestSessionName==null) {
                                render "scoring session initialised for "+tma_scorer.getName()+" already."
                            } else {
                                render phase3TestSessionName+" initialized for "+tma_scorer.getName();
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * do ki67 qc phase 3 
     */
    private void ki67_qc_phase3(Scoring_sessions scoring_sessionInstance, Whole_section_scorings whole_section_scoring, boolean showHE, boolean showMM, boolean showPageBodyOnly) {
        // first determine the state ...
        switch (whole_section_scoring.getState()) {
        case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_INIT:
            whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_SCORING);
            render(view:"ki67Qc\\phase3\\init",
                model:[
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (whole_section_scoring != null ? whole_section_scoring.getScoring(): null),
                    whole_section_scoring: whole_section_scoring,
                    showHE: showHE,
                    showMM: showMM,
                    showPageBodyOnly: showPageBodyOnly
                ]
            )
            break;
        case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT:
            whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_SELECT_HOT_SPOT);
            render(view:"ki67Qc\\phase3\\select_region",
                model:[
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (whole_section_scoring != null ? whole_section_scoring.getScoring(): null),
                    whole_section_scoring: whole_section_scoring,
                    scoringFieldsAllocator: null,
                    selectHotspot: true,
                    showHE: showHE,
                    showMM: showMM,
                    showPageBodyOnly: showPageBodyOnly
                ]
            )
            break;   
        case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_ESTIMATE_PERCENT:
            whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_ESTIMATE_PERCENTAGES);
            render(view:"ki67Qc\\phase3\\estimate_percent",
                model:[
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (whole_section_scoring != null ? whole_section_scoring.getScoring(): null),
                    whole_section_scoring: whole_section_scoring,
                    showHE: showHE,
                    showMM: showMM,
                    showPageBodyOnly: showPageBodyOnly
                ]
            )
            break;
        case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS:
            whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_SELECT_FIELDS);
            // need to figure out if field selections exist already ... this is done in display_field_selector_applet
            //
            // figure out the number of fields allocated for each ki67 score level (negligible/low/medium/high)
            ScoringFieldsAllocator scoringFieldsAllocator = new ScoringFieldsAllocator(
                whole_section_scoring.getPercent_negligible(), 
                whole_section_scoring.getPercent_low(), 
                whole_section_scoring.getPercent_medium(), 
                whole_section_scoring.getPercent_high());
                        
            render(view:"ki67Qc\\phase3\\select_region",
                model:[
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (whole_section_scoring != null ? whole_section_scoring.getScoring(): null),
                    whole_section_scoring: whole_section_scoring,
                    scoringFieldsAllocator: scoringFieldsAllocator,
                    showHE: showHE,
                    showMM: showMM,
                    showPageBodyOnly: showPageBodyOnly
                ]
            )
            break;
        case [Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT, Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_NUCLEI]:
            // WARNING ...
            // advance from Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT to Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS
            // is set in set_scoring_date in Whole_section_scorings.groovy
            
            if (whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT) {
                whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_SCORE_HOT_SPOT);
            } else {
                whole_section_scoring.log(Scoring_sessionsConstants.BEGIN_SCORE_FIELDS);
            }
            
            // check to see if all fields are scored, if so, need to set the 
            render(view:"ki67Qc\\phase3\\score",
                model:[
                    scoring_sessionInstance: scoring_sessionInstance,
                    scoring: (whole_section_scoring != null ? whole_section_scoring.getScoring(): null),
                    whole_section_scoring: whole_section_scoring,
                    showHE: showHE,
                    showMM: showMM,
                    showPageBodyOnly: showPageBodyOnly
                ]
            )
            break;
        default:
            break; // do nothing
        }
    }
    
    /**
     * go back ONE step within a whole_section_scoring for ki67 qc phase 3 scoring
     */
    def ajax_ki67_qc_phase3_go_back = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id)
        if (!scoring_sessionInstance || !params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)) {
            render ViewConstants.AJAX_RESPONSE_ERROR;
        } else if (scoring_sessionInstance.showSubmitted()) {
            render ViewConstants.AJAX_RESPONSE_NA;
        } else {
            // get whole_section_scoring object
            Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(Long.parseLong(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)));
            if (!whole_section_scoring.showIsScoringTypeKi67Phase3()) {
                render ViewConstants.AJAX_RESPONSE_NA;
            } else {
                switch(whole_section_scoring.getState()) {
                case [Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_INIT, Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT]:
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_INIT);
                    break;
                case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT:
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT);
                    break;
                case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_ESTIMATE_PERCENT:
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT);
                    break;
                case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS:
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_ESTIMATE_PERCENT);
                    break;             
                case Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_NUCLEI:
                    whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS);
                    break;
                default: // unknown state!
                    render ViewConstants.AJAX_RESPONSE_ERROR;
                    return;
                }
                if (!whole_section_scoring.save(flush:true)) {
                    render ViewConstants.AJAX_RESPONSE_ERROR;
                } else {
                    render ViewConstants.AJAX_RESPONSE_OK;
                }
            }
        }
    }

    /**
     * save data from ki67_qc/phase3/init
     * and advance ki67 scoring state
     */
    def save_ki67_qc_phase3_init = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id)
        if (!scoring_sessionInstance || !params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoring_sessions.label', default: 'Scoring_sessions'), params.id])}"
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
            return; // no need to go further
        } else {
            // get whole_section_scoring object
            Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(Long.parseLong(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)));
            // save the data from ki67 qc phase 3 init step ... assume all fields are available since check was done by javascript
            // iterate through params and see if there are any new input ...
            params.each { key, value ->
                if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_40X_FIELD_DIAMETER_MM)) {
                    whole_section_scoring.setField_diameter_40x_mm(Float.parseFloat(value));
                } else if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT)) {
                    whole_section_scoring.inputScoringComment(value==null?"":value.trim());
                }
            }
            // advance a state on whole_section_scoring.
            whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT);
            if (!whole_section_scoring.save(flush:true)) {
                flash.message=ViewConstants.ERROR_MSG_DB_SAVE_FAILED_PLS_CONTACT_SUPPORT+" (detail error message: trying to save whole_section_scoring, id:"+whole_section_scoring.getId()+")";
                redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
                return; // no need to go further
            } else {
                ki67_qc_phase3(scoring_sessionInstance, whole_section_scoring, false/*showHE*/, false/*showMM*/, false/*showPageBodyOnly*/);
            }
        }
    }
    
    /**
     * save data from ki67_qc/phase3/estimate_percent
     * and advance ki67 scoring state
     */
    def save_ki67_qc_phase3_estimate_percent = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id)
        if (!scoring_sessionInstance || !params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoring_sessions.label', default: 'Scoring_sessions'), params.id])}"
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
            return; // no need to go further
        } else {
            // get whole_section_scoring object
            Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(Long.parseLong(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)));
            // save the data from ki67 qc phase 3 init step ... assume all fields are available since check was done by javascript
            // iterate through params and see if there are any new input ...
            params.each { key, value ->
                if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE)) {
                    whole_section_scoring.setPercent_negligible(Float.parseFloat(value));
                } else if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW)) {
                    whole_section_scoring.setPercent_low(Float.parseFloat(value));
                } else if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM)) {
                    whole_section_scoring.setPercent_medium(Float.parseFloat(value));
                } else if (key.equals(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH)) {
                    whole_section_scoring.setPercent_high(Float.parseFloat(value));
                }
            }
            // advance a state on whole_section_scoring.
            whole_section_scoring.setState(Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_REGIONS);
            if (!whole_section_scoring.save(flush:true)) {
                flash.message=ViewConstants.ERROR_MSG_DB_SAVE_FAILED_PLS_CONTACT_SUPPORT+" (detail error message: trying to save whole_section_scoring, id:"+whole_section_scoring.getId()+")";
                redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
                return; // no need to go further
            } else {
                ki67_qc_phase3(scoring_sessionInstance, whole_section_scoring, false/*showHE*/, false/*showMM*/, false/*showPageBodyOnly*/);
            }
        }
    }
    
    /**
     * save data from ki67_qc/phase3/select_region
     * and advance ki67 scoring state
     */
    def save_ki67_qc_phase3_select_region = {
        Scoring_sessions scoring_sessionInstance = Scoring_sessions.get(params.id)
        if (!scoring_sessionInstance || !params.containsKey(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scoring_sessions.label', default: 'Scoring_sessions'), params.id])}"
            redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
            return; // no need to go further
        } else {
            if (!params.containsKey(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING)) {
                flash.message = "missing parameter: "+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING;
                redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
                return; // no need to go further
            }
            // get whole_section_scoring object
            Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(Long.parseLong(params.get(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID)));
            String fieldSelectionParamString = params.get(ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING);
            fieldSelectionParamString = fieldSelectionParamString == null ? "" : fieldSelectionParamString.trim();
            FieldSelectionParamStringDbConnector fieldSelectionParamStringDbConnector = new FieldSelectionParamStringDbConnector(whole_section_scoring, fieldSelectionParamString);
            if (!fieldSelectionParamStringDbConnector.saveToDatabase()) {
                flash.message = ViewConstants.ERROR_MSG_DB_SAVE_FAILED_PLS_CONTACT_SUPPORT;
                // error occured :(  go back to list ...
                redirect(controller:"scoreTma", action: "list");
            } else {
                // ok to continue!!!  advance a state on whole_section_scoring.
                // if selecting hotspot ... next step = count hotspot
                // if selecting other fields ... next step = count nuclei
                whole_section_scoring.setState(whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT ? Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT : Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_NUCLEI);
                if (!whole_section_scoring.save(flush:true)) {
                    flash.message=ViewConstants.ERROR_MSG_DB_SAVE_FAILED_PLS_CONTACT_SUPPORT+" (detail error message: trying to save whole_section_scoring, id:"+whole_section_scoring.getId()+")";
                    redirect(url: MiscUtil.showUserPreferredHomeUrl(session.user, grailsApplication.config.grails.serverSecureURL.toString()));
                    return; // no need to go further
                } else {
                    ki67_qc_phase3(scoring_sessionInstance, whole_section_scoring, false/*showHE*/, false/*showMM*/, false/*showPageBodyOnly*/);
                }
            }
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /// END OF KI67-QC PHASE 3 RELATED                                       ///
    ////////////////////////////////////////////////////////////////////////////
    
    def report = {
        Scoring_sessions scoring_sessionsInstance = Scoring_sessions.get(params.id);
        if (!scoring_sessionsInstance.showCompleted()) {
            flash.message=scoring_sessionsInstance.name+" ... not completed yet.  Report not available.";
            redirect(controller:"scoreTma", action: "list")
        } else {
            // determine flash message:
            if (scoring_sessionsInstance.showSubmitted()) {
                flash.message = scoring_sessionsInstance.name+" ... scores submitted.  Please note: scores can no longer be updated.";
            } else { // must be completed but not submitted
                if (!scoring_sessionsInstance.showIsAllKi67QcPhase3ScoringsCompleted()) {
                    flash.message = scoring_sessionsInstance.name+" ... some field(s) did not receive any nuclei count.  Please count at least one nuclei on all fields.";
                } else {
                    flash.message = scoring_sessionsInstance.name+" ... scoring completed!  Please review and submit scores.";
                }
            } 
        }
        // check to see if what (if any) statistics to show ...
        boolean showKi67QcStats = false;
        String rMSE = "";
        String maxdev = ""
        int numDecimal = 3; // number of decimal to show
        if (scoring_sessionsInstance.showTma_scoring_referencesAvailable() &&
            scoring_sessionsInstance.showHasNucleiSelection() &&
            scoring_sessionsInstance.showSubmitted()
        ) {
            showKi67QcStats = true;
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(numDecimal);
            rMSE = nf.format(scoring_sessionsInstance.showRMSE());
            maxdev = nf.format(scoring_sessionsInstance.showMaxdev());
        }
        render(view: "report", 
            model: [scoring_sessionsInstance: scoring_sessionsInstance,
                showKi67QcStats: showKi67QcStats,
                rMSE: rMSE,
                maxdev: maxdev]
        )
    }
    
    /**
     * get json data for building the result table
     * render AJAX_RESPONSE_NA if the scoring session is not completed OR if scoring session does not contains ONLY tma_scorings or whole_section_scorings
     * 
     * TODO: currently DOES NOT support scoring sessions with mixed tma scoring and whole section scoring!!!
     * 
     * render json with the following fields ...
     * TMA core ID 
     * TMA core name / whole section surgical number with url link to scoring object 
     * Score type 
     * Score
     * Scoring date/time
     */
    def ajax_get_report_table = {
        Scoring_sessions scoring_sessionsInstance = Scoring_sessions.get(params.id);
        if (!scoring_sessionsInstance.showCompleted()) {
            render ViewConstants.AJAX_RESPONSE_NA;
        } else {
            int unique_id=0;
            // data formatter ... display purposes
            DateFormat df = new SimpleDateFormat(ViewConstants.DATE_FORMAT_LONG);
            if (scoring_sessionsInstance.showContainsOnlyTma_scorings()) {
                boolean showReference = scoring_sessionsInstance.showSubmitted() && scoring_sessionsInstance.getTma_scorings().first()?.showTma_scoring_referencesAvailable() && (!scoring_sessionsInstance.showIsKi67QcCalibratorTest()) // DO NOT show reference if ki67 calibrator test
                render(contentType: "text/json") {
                    scoring_session_scoring_type = ViewConstants.SCORING_SESSION_SCORING_TYPE_TMA_SCORING
                    references_available = showReference
                    identifier = "id"
                    numRows = scoring_sessionsInstance.getTma_scorings().size()
                    items = array{
                        scoring_sessionsInstance.getTma_scorings().each { s ->
                            // NOTE: items in array canNOT be null 
                            String title = "Click me to show TMA core; you "+(scoring_sessionsInstance.showSubmitted()==true ? 'cannot' : 'can')+" update score";
                            String url = createLink(controller:"scoring_sessions", action:"score", params:[id:scoring_sessionsInstance.getId(), tma_scoring_id:s.getId()]);
                            item(
                            "id":unique_id++,
                            "core_id":s.getTma_core_image().getTma_core().getCore_id(),
                            "description":s.showScoringDescription()+ViewConstants.AJAX_RESPONSE_DELIMITER+url+ViewConstants.AJAX_RESPONSE_DELIMITER+title,
                            "type":s.showScoreType(),
                            "score":s.showScore(),
                            "reference_score":(showReference?s.showAveragePercentPositiveTma_scoring_referencesText():Double.NaN)+"%",
                            "scoring_date":df.format(s.showScoringScoring_date()),
                            )            
                        }
                    }
                }
            } else if (scoring_sessionsInstance.showContainsOnlyWhole_section_scorings()) {                
                render(contentType: "text/json") {
                    scoring_session_scoring_type = ViewConstants.SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING;
                    identifier = "id"
                    numRows = scoring_sessionsInstance.getWhole_section_scorings().size()
                    items = array{
                        scoring_sessionsInstance.getWhole_section_scorings().each { s ->
                            // NOTE: items in array canNOT be null 
                            String title = "Click me to show whole section; you "+(scoring_sessionsInstance.showSubmitted()==true ? 'cannot' : 'can')+" update score";
                            String url = createLink(controller:"scoring_sessions", action:"score", params:[id:scoring_sessionsInstance.getId(), whole_section_scoring_id:s.getId()]);
                            item(
                            "id":unique_id++,
                            "whole_section_specimen_num":s.getWhole_section_image().getWhole_section_slice().getParaffin_block().getSpecimen_number(),
                            "description":s.showScoringDescription()+ViewConstants.AJAX_RESPONSE_DELIMITER+url+ViewConstants.AJAX_RESPONSE_DELIMITER+title,
                            "type":s.showScoreType(),
                            "score":s.showScoreShort(),
                            "scoring_date":df.format(s.showScoringScoring_date()),
                            )            
                        }
                    }
                }
                
            } else { // TODO: currently DOES NOT support scoring sessions with mixed tma scoring and whole section scoring
                return ViewConstants.AJAX_RESPONSE_NA; 
                return;
            }
        }
    }
}
