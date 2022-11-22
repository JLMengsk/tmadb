/*
 * responsible for connecting (read/write) the field selection to database
 * 
 * See ca.ubc.gpec.fieldselector.model.FieldSelectionParamStringParser for details regarding the fieldSelectionParamString
 * 
 * parameter string format: (all numbers are in pixels, in coordinate system of ORIGINAL image i.e. not preview/lowres image)
 * [x-coordinate]x[y-coordinate]y[field diameter]pp[categorical staining level][viewing state flag][scoring state flag]_
 * 
 * viewing state flag
 * CURRENT = c
 * PREVIEW = p
 * NOT_CURRENT = n
 * 
 * scoring state flag
 * NOT_SCORED = o
 * SCORING = i
 * SCORED = s
 * 
 * e.g.
 * 3822x4856y4000pp0no_13474x4347y4000pp1no_34563x5981y4000pp2ns_46164x3418y4000pp4no_7299x1828y4000pp3cs
 * first selection @ x=3822, y=4856, negligible Ki67, field diameter=4000, not current viewing, not scored
 * second selection @ x=13474, y=4347, low Ki67, field diameter=4000, not current scoring, not scored
 * third selection @ x=34563, y=5981, medium Ki67, field diameter=4000, not current scoring, scored
 * 
 */

package ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3

import ca.ubc.gpec.fieldselector.model.FieldSelectionParamStringParser;
import ca.ubc.gpec.fieldselector.model.FieldOfView;
import ca.ubc.gpec.tmadb.GenericScorings;
import ca.ubc.gpec.tmadb.Scorings;
import ca.ubc.gpec.tmadb.Scoring_sessions;
import ca.ubc.gpec.tmadb.Whole_section_scorings;
import ca.ubc.gpec.tmadb.Whole_section_region_scorings;
import ca.ubc.gpec.tmadb.Ihc_score_categories;
import ca.ubc.gpec.tmadb.Ihc_score_category_groups;
import ca.ubc.gpec.tmadb.Nuclei_selection_notifications;
import ca.ubc.gpec.tmadb.Scoring_nuclei_selection_notifications;

/**
 *
 * @author samuelc
 */
class FieldSelectionParamStringDbConnector {
    public static final String IHC_SCORE_CATEGORY_GROUP_NAME = "Ki67 score level for Ki67-QC study phase 3";
    // assume 500 nuclei required if and only if scoring hotspot
    public static final String NUCLEI_SELECTION_NOTIFICATION_MESSAGE_500_CELLS_COUNTED = "500 nuclei have been counted.  Please click on the 'DONE with hotspot' button to indicate that you have finished scoring the hotspot."; // WARNING: must match correponding values in database!!!
    public static final String NUCLEI_SELECTION_NOTIFICATION_MESSAGE_100_CELLS_COUNTED = "100 nuclei have been counted.  Please click on the 'done with this field' button to indicate that you have finished scoring this field."; // WARNING: must match correponding values in database!!!
    private ArrayList<FieldOfView> fieldOfViews;
    private Whole_section_scorings whole_section_scoring;
    private Ihc_score_category_groups ihc_score_category_group;
    
    /**
     * constructor
     * - parse paramString and put field sections in fieldOfViews (array list)
     * - if paramString is null, get field selection info from whole_section_region_scorings 
     */
    public FieldSelectionParamStringDbConnector(Whole_section_scorings w, String paramString) {
        whole_section_scoring = w;
        ihc_score_category_group = null;
        if (paramString != null) {
            FieldSelectionParamStringParser parser = new FieldSelectionParamStringParser(paramString);
            fieldOfViews = parser.getAllSelections();
        } else {
            // try get the field selection info from whole_section_region_scorings
            fieldOfViews = new ArrayList<FieldOfView>();
            whole_section_scoring.getWhole_section_region_scorings().each { wsrs ->
                FieldOfView f = new FieldOfView(
                    wsrs.getX(),
                    wsrs.getY(),
                    wsrs.getField_diameter_pixel(),
                    FieldOfView.ViewingState.NOT_CURRENT, 
                    null, // scoringState set later
                    FieldSelectionParamStringParser.numericCodeToKi67State(wsrs.getIhc_score_category().getNumeric_code())
                );
                // figure out scoring state ...
                if (wsrs.showScoringVisit_order() == null) {
                    f.setScoringState(FieldOfView.ScoringState.NOT_SCORED);     
                } else if (wsrs.showScoringScoring_date() == null) {
                    f.setScoringState(FieldOfView.ScoringState.SCORING); 
                } else {
                    f.setScoringState(FieldOfView.ScoringState.SCORED); 
                }
                //
                // ready to add to fieldOfViews!!
                fieldOfViews.add(f);
            }
        }
    }
    
    /**
     * return the fieldSelectionParamString based on current data
     */
    public String generateFieldSelectionParamString() {
        FieldSelectionParamStringParser parser = new FieldSelectionParamStringParser(fieldOfViews);
        return parser.generateFieldSelectionParamString();
    }
    
    /**
     * check to see if fieldOfView refers to the same area as whole_section_region_scoring
     * 
     * NOTE: not matching Ki67State / ihc_score_category since even if they do not match, the two
     *       object would be representing the same area.
     * 
     * @return true if fieldOfView and whole_section_region_scoring represent the same area, false otherwise
     */
    private boolean representsSameArea(FieldOfView fieldOfView, Whole_section_region_scorings whole_section_region_scoring) {
        if (fieldOfView.getX() != whole_section_region_scoring.getX()) {
            return false;
        } else if (fieldOfView.getY() != whole_section_region_scoring.getY()) {
            return false;
        } else if (fieldOfView.getDiamter() != whole_section_region_scoring.getField_diameter_pixel()) {
            return false;
        } else if (FieldSelectionParamStringParser.ki67StateToNumericCode(fieldOfView.getKi67State()) != whole_section_region_scoring.getIhc_score_category().getNumeric_code()) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * synchronize with database
     * @return true if save successfully
     */
    public boolean saveToDatabase() {
        // 1. go through fieldOfViews and if any fieldOfView is not in database,
        //    create a new whole_section_region_scoring and corresponding scoring object
        for (FieldOfView f:fieldOfViews) {
            boolean inDb = false;
            for (Whole_section_region_scorings w:whole_section_scoring.getWhole_section_region_scorings()) {
                if (representsSameArea(f,w)) {
                    inDb = true;
                    break;
                }
            }
            if (!inDb) { // not in database ... need new whole_section_region_scoring object
                Scorings scoring = new Scorings();
                scoring.setType(GenericScorings.SCORING_TYPE_NUCLEI_COUNT_NO_COORD);
                if (!scoring.save(flush:true)) {
                    return false; // something went wrong!!! do not continue
                }
                Whole_section_region_scorings whole_section_region_scoring = new Whole_section_region_scorings();
                whole_section_region_scoring.setField_diameter_pixel(f.getDiamter());
                whole_section_region_scoring.setX(f.getX());
                whole_section_region_scoring.setY(f.getY());
                whole_section_region_scoring.setScoring(scoring);
                whole_section_region_scoring.setWhole_section_scoring(whole_section_scoring);
                // need fo find the appropriate ihc_score_category ...
                if (ihc_score_category_group == null) {
                    ihc_score_category_group = Ihc_score_category_groups.findByName(IHC_SCORE_CATEGORY_GROUP_NAME);
                }
                if (ihc_score_category_group == null) {
                    throw new FieldSelectionParamSaveToDbException("failed to find ihc_score_category_groups: "+IHC_SCORE_CATEGORY_GROUP_NAME);
                }
                Ihc_score_categories ihc_score_category = Ihc_score_categories.findByIhc_score_category_groupAndNumeric_code(ihc_score_category_group, FieldSelectionParamStringParser.ki67StateToNumericCode(f.getKi67State()));
                if (ihc_score_category == null) {
                    throw new FieldSelectionParamSaveToDbException("failed to find ihc_score_categories: "+f.getKi67State());
                }
                whole_section_region_scoring.setIhc_score_category(ihc_score_category);
                // need to set nuclei selection notification message
                Nuclei_selection_notifications notice = f.getKi67State() == FieldOfView.Ki67State.HOT_SPOT ? Nuclei_selection_notifications.findByMessage(NUCLEI_SELECTION_NOTIFICATION_MESSAGE_500_CELLS_COUNTED) : Nuclei_selection_notifications.findByMessage(NUCLEI_SELECTION_NOTIFICATION_MESSAGE_100_CELLS_COUNTED);
                if (notice == null) {
                    throw new FieldSelectionParamSaveToDbException("failed to nuclei selection notification message: "+NUCLEI_SELECTION_NOTIFICATION_MESSAGE_500_CELLS_COUNTED+" OR "+NUCLEI_SELECTION_NOTIFICATION_MESSAGE_100_CELLS_COUNTED);
                }
                Scoring_nuclei_selection_notifications scoring_nuclei_selection_notification = new Scoring_nuclei_selection_notifications();
                scoring_nuclei_selection_notification.setNuclei_selection_notification(notice);
                scoring_nuclei_selection_notification.setScoring(scoring);
                
                if (!scoring_nuclei_selection_notification.save(flush:true) || !whole_section_region_scoring.save(flush:true)) {
                    return false; // something went wrong!! do not continue
                }
            }
        }
        
        // 2. go through all the whole_section_region_scoring object and if any is
        //    not in fieldOfView, remove the whole_section_region_scoring and the
        //    corresponding scoring object
        for (Whole_section_region_scorings w:Whole_section_region_scorings.findAllByWhole_section_scoring(whole_section_scoring)){ //whole_section_scoring.getWhole_section_region_scorings()) {
            // NOTE: using whole_section_scoring.getWhole_section_region_scorings()) { 
            //       to loop will cause ConcurrentModificationException
            boolean needDelete = true;
            for (FieldOfView f:fieldOfViews) {
                if (representsSameArea(f,w)) {
                    needDelete = false;
                    break;
                }
            }
            if (needDelete) {
                whole_section_scoring.removeFromWhole_section_region_scorings(w);
                // note scoring_nuclei_selection_notification records should be delete by scoring's cascade:'all-delete-orphan'
                Scorings s = w.getScoring();
                w.setScoring(null); // need to unlink scoring with whole_section_region_scoring
                s.delete();
                w.delete();
                
                // if deleted a field, this means the scoring session must NOT be completed!!!
                Scorings ws = whole_section_scoring.getScoring();
                ws.setScoring_date(null);
                ws.save(flush:true);
                Scoring_sessions scoring_session = whole_section_scoring.getScoring_session();
                if (scoring_session.showCompleted()) {
                    scoring_session.setStatus(Scoring_sessions.STATUS_STARTED);
                    scoring_session.save(flush:true);
                }
            }
        }
        whole_section_scoring.save(flush:true); // save & refresh will avoid ConcurrentModificationException if we iterate through whole_section_scoring.getWhole_section_region_scorings() again
        whole_section_scoring.refresh(); 
        return true; // everything went ok.
    } 
}

