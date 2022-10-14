package ca.ubc.gpec.tmadb

import ca.ubc.gpec.tmadb.DisplayConstant;
import ca.ubc.gpec.tmadb.util.MiscUtil;
import ca.ubc.gpec.tmadb.util.ViewConstants;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.FieldSelectionParamStringDbConnector;
import ca.ubc.gpec.tmadb.scoring_sessionHelper.ki67QcPhase3.ScoringFieldsAllocator;
import ca.ubc.gpec.tmadb.util.OddEvenRowFlag;

class Whole_section_scoringsTagLib {

    /**
     * display input text boxes for Ki67-QC phase3 init state.  
     * Input includes field diameter
     * 
     * @attr whole_section_scoring object
     */
    def display_ki67_qc_phase3_init_input = { attr, body ->
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        // check to see if the corresponding scoring_session is allowed to update ...
        boolean allowedToUpdateScore = whole_section_scoring.showIsAllowedToUpdateScore();
        out << "<table>";
        out << "<tr>";
        out << "<td><br><br>";
        out << "40x field diameter: "
        out << "<input type=\"text\""; 
        out << "       style=\"width: 20em;\""; 
        out << "       dojoType=\"dijit.form.NumberTextBox\"";
        out << "       constraints='{min:0}'";
        out << "       required=\"true\"";
        if (!allowedToUpdateScore) {
            out << "   readOnly=true";
        }
        out << "       placeHolder=\"40x field diameter (mm)\"";
        out << "       value=\""+(whole_section_scoring.getField_diameter_40x_mm() != null ? whole_section_scoring.getField_diameter_40x_mm() : whole_section_scoring.showDefaultField_diameter_40x_mm())+"\"";
        out << "       name=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_40X_FIELD_DIAMETER_MM+"\"";
        out << "       id=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_40X_FIELD_DIAMETER_MM+"\"/>mm<br><br><br><br>";
        out << "</td>";
        out << "</tr>";
        out << "</table>";
    }
    
    /**
     * display input text boxes for Ki67-QC phase3 estimate percent state.
     * Input includes high/med/low/negligible Ki67 percentage estimates
     * 
     * @attr whole_section_scoring object
     */
    def display_ki67_qc_phase3_estimate_percent_input = { attr, body ->
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        // check to see if the corresponding scoring_session is allowed to update ...
        boolean allowedToUpdateScore = whole_section_scoring.showIsAllowedToUpdateScore();
        out << "<table>";
        out << "<tr class=\"odd\"><td style=\"text-align:right\">Percent high:</td>";
        out << "<td>"
        out << "<input type=\"text\""; 
        out << "       style=\"width: 15em;\""; 
        out << "       dojoType=\"dijit.form.NumberTextBox\"";
        out << "       constraints='{min:0, max:100}'";
        out << "       required=\"true\"";
        if (!allowedToUpdateScore) {
            out << "   readOnly=true";
        }
        out << "       placeHolder=\""+ViewConstants.MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       title=\""+ViewConstants.MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING+" high Ki67 score"+"\"";
        out << "       invalidMessage=\""+ViewConstants.MSG_INVALID_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       missingMessage=\""+ViewConstants.MSG_MISSING_KI67_QC_PHASE3_PERCENTAGE+" high Ki67 score"+"\"";
        out << "       value=\""+(whole_section_scoring.getPercent_high() != null ? whole_section_scoring.getPercent_high() : "")+"\"";
        out << "       name=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH+"\"";
        out << "       id=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_HIGH+"\"/>%</td>";
        out << "</tr>";
        out << "<tr class=\"even\"><td style=\"text-align:right\">Percent medium:</td>";
        out << "<td>";
        out << "<input type=\"text\""; 
        out << "       style=\"width: 15em;\""; 
        out << "       dojoType=\"dijit.form.NumberTextBox\"";
        out << "       constraints='{min:0, max:100}'";
        out << "       required=\"true\"";
        if (!allowedToUpdateScore) {
            out << "   readOnly=true";
        }
        out << "       placeHolder=\""+ViewConstants.MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       title=\""+ViewConstants.MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING+" medium Ki67 score"+"\"";
        out << "       invalidMessage=\""+ViewConstants.MSG_INVALID_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       missingMessage=\""+ViewConstants.MSG_MISSING_KI67_QC_PHASE3_PERCENTAGE+" medium Ki67 score"+"\"";
        out << "       value=\""+(whole_section_scoring.getPercent_high() != null ? whole_section_scoring.getPercent_medium() : "")+"\"";
        out << "       name=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM+"\"";
        out << "       id=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_MEDIUM+"\"/>%</td>";
        out << "</tr>";
        out << "<tr class=\"odd\"><td style=\"text-align:right\">Percent low:</td>";
        out << "<td>";
        out << "<input type=\"text\""; 
        out << "       style=\"width: 15em;\""; 
        out << "       dojoType=\"dijit.form.NumberTextBox\"";
        out << "       constraints='{min:0, max:100}'";
        out << "       required=\"true\"";
        if (!allowedToUpdateScore) {
            out << "   readOnly=true";
        }
        out << "       placeHolder=\""+ViewConstants.MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       title=\""+ViewConstants.MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING+" low Ki67 score"+"\"";
        out << "       invalidMessage=\""+ViewConstants.MSG_INVALID_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       missingMessage=\""+ViewConstants.MSG_MISSING_KI67_QC_PHASE3_PERCENTAGE+" low Ki67 score"+"\"";
        out << "       value=\""+(whole_section_scoring.getPercent_high() != null ? whole_section_scoring.getPercent_low() : "")+"\"";
        out << "       name=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW+"\"";
        out << "       id=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_LOW+"\"/>%</td>";
        out << "</tr>";
        out << "<tr class=\"even\"><td style=\"text-align:right\">Percent "+ViewConstants.NEGLIGIBLE_TEXT+":</td>";
        out << "<td>";
        out << "<input type=\"text\""; 
        out << "       style=\"width: 15em;\""; 
        out << "       dojoType=\"dijit.form.NumberTextBox\"";
        out << "       constraints='{min:0, max:100}'";
        out << "       required=\"true\"";
        if (!allowedToUpdateScore) {
            out << "   readOnly=true";
        }
        out << "       placeHolder=\""+ViewConstants.MSG_PLACEHOLDER_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       title=\""+ViewConstants.MSG_TOOLTIP_KI67_QC_PHASE3_PLEASE_ENTER_0_FOR_MISSING+" "+ViewConstants.NEGLIGIBLE_TEXT+" Ki67 score"+"\"";
        out << "       invalidMessage=\""+ViewConstants.MSG_INVALID_KI67_QC_PHASE3_PERCENTAGE+"\"";
        out << "       missingMessage=\""+ViewConstants.MSG_MISSING_KI67_QC_PHASE3_PERCENTAGE+" "+ViewConstants.NEGLIGIBLE_TEXT+" Ki67 score"+"\"";
        out << "       value=\""+(whole_section_scoring.getPercent_high() != null ? whole_section_scoring.getPercent_negligible() : "")+"\"";
        out << "       name=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE+"\"";
        out << "       id=\""+ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_ESTIMATE_LEVEL_NEGLIGIBLE+"\"/>%</td>";
        out << "</tr>";
        out << "</table>";
    }
    
    /**
     * show image diaplay options
     * 
     * @attr whole_section_scoring object 
     * @attr showPageBodyOnly indicate whether to show page body only
     * @attr showHE indicate whether to show H&E
     * @attr showMM indicates whether to show myoepithelial marker
     */
    def display_whole_section_scoring_image_option = { attr, body ->
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        long scoring_session_id = whole_section_scoring.getScoring_session().getId()
        boolean showPageBodyOnly = attr[ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG]
        boolean ki67QcPhase3 = whole_section_scoring.showIsScoringTypeKi67Phase3();
        boolean showHE = attr['showHE']
        boolean showMM = attr['showMM']
        if (!showPageBodyOnly) {
            if (showHE) {
                out << "showing <strong>H&E</strong> image " // need space at end 
                out << "["
                if(whole_section_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])+"' title=\"click me to show cell counter\">cell counter</a>"
                } else {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])+"' title=\"click me to show marker image\">"+whole_section_scoring.getWhole_section_image().getWhole_section_slice().getStaining_detail().getBiomarker().getName()+" image</a>"
                }
                out << "] " // remember space after ']'
            } else if (showMM) {
                out << "showing <strong>myoepithelial marker</strong> image " // need space at end 
                out << "["
                if(whole_section_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])+"' title=\"click me to show cell counter\">cell counter</a>"
                } else {
                    out << "<a href='"+createLink(controller:"scoring_sessions",action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])+"' title=\"click me to show marker image\">"+whole_section_scoring.getWhole_section_image().getWhole_section_slice().getStaining_detail().getBiomarker().getName()+" image</a>"
                }
                out << "] " // remember space after ']' 
            } else {
                out << "showing <strong>"
                if (whole_section_scoring.showIsScoringTypeNucleiCountNoCoord()) {
                    out << "cell counter</strong> " // need space at end
                } else {
                    out << whole_section_scoring.getWhole_section_image().getWhole_section_slice().getStaining_detail().getBiomarker().getName()+"</strong> image "
                }
                out <<"["
                if (whole_section_scoring.showIsAllowedToUpdateScore()) {
                    out << "<a href=\"\" onClick=\""
                    out << ((whole_section_scoring.showIsScoringTypeNucleiCountNoCoord() | whole_section_scoring.showIsScoringTypeNucleiCount()) ? "nucleiCounter.uploadNucleiSelection(); " : "")
                    out << "openHeWindow();return false;\" title=\"click me to show H&E image in separate window\"><span style='background-color:"+ViewConstants.COLOR_CODE_PINK+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>H&E image</span></a>"
                    if (ki67QcPhase3) {
                        out << " | ";
                        out << "<a href=\"\" onClick=\""
                        out << ((whole_section_scoring.showIsScoringTypeNucleiCountNoCoord() | whole_section_scoring.showIsScoringTypeNucleiCount()) ? "nucleiCounter.uploadNucleiSelection(); " : "")
                        out << "openMmWindow();return false;\" title=\"click me to show myoepithelial marker image in separate window\"><span style='background-color:"+ViewConstants.COLOR_CODE_DARK_BROWN+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>myoepithelial marker image</span></a>"
                    }
                } else {
                    out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId(),showHE:''])+"' title=\"click me to show H&E image\"><span style='background-color:"+ViewConstants.COLOR_CODE_PINK+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>H&E image</span></a>"
                    if (ki67QcPhase3) {
                        out << " | ";
                        out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:scoring_session_id, params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId(),showMM:''])+"' title=\"click me to show myoepithelial marker image\"><span style='background-color:"+ViewConstants.COLOR_CODE_DARK_BROWN+"; color:"+ViewConstants.COLOR_CODE_WHITE+";'>myoepithelial marker image</span></a>"
                    }
                }
                out << "] " // remember space after ']'
            }
        }
    }
     
    /**
     * display navigation i.e. next/previous whole_section_scoring
     * 
     * @attr whole_section_scoring
     * @attr showPageBodyOnly
     * 
     * requires a function cleanUp() ... this function is responsible for closing any possible popup windows
     */
    def display_whole_section_scoring_navigation = { attr, body ->
        boolean showPageBodyOnly = attr[ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG]
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        Whole_section_scorings prevWhole_section_scoring = whole_section_scoring.getScoring_session().showPrevWhole_section_scoring(whole_section_scoring);
        Whole_section_scorings nextWhole_section_scoring = whole_section_scoring.getScoring_session().showNextWhole_section_scoring(whole_section_scoring);
        boolean ki67QcPhase3 = whole_section_scoring.showIsScoringTypeKi67Phase3();
        if (prevWhole_section_scoring != null | nextWhole_section_scoring != null | showPageBodyOnly) {
            out << "<tr class='prop'><td valign='top' class='name' colspan='2'>Show " // need space after!
            if (prevWhole_section_scoring != null) {
                out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:whole_section_scoring.getScoring_session().getId(), params:["whole_section_scoring_id":prevWhole_section_scoring.getId()])+"' "
                out << "title='click me to show the previous scored image' onClick='cleanUp();' "
                out << ">previous</a>"
            }
            if (prevWhole_section_scoring != null & nextWhole_section_scoring != null) {
                out << " | "
            }
            if (nextWhole_section_scoring != null) {
                out << "<a href='"+createLink(controller:"scoring_sessions", action:"score", id:whole_section_scoring.getScoring_session().getId(), params:["whole_section_scoring_id":nextWhole_section_scoring.getId()])+"' "
                out << "title='click me to show the next scored image' onClick='cleanUp();' "
                out << ">next</a>" 
            }            
            out << "</td></tr>"
        }
    }     
    
    /**
     * display link to corresponding H&E image
     * 
     * NOTE: use http (NOT https) to avoid mix non-security content complaints from browser
     * 
     * @attr id of the whole_section_scoring object 
     */
    def display_whole_section_scoring_he_link = { attr, body ->
        long id = attr['id']
        Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(id);
        out << createLink(base:grailsApplication.config.grails.serverURL, controller:"scoring_sessions", action:"score", id:whole_section_scoring.getScoring_session().getId(), params:["whole_section_scoring_id":id,"showHE":"",(ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG):true])
    }
    
    /**
     * display link to corresponding myoepithelial marker image
     *
     * NOTE: use http (NOT https) to avoid mix non-security content complaints from browser
     *
     * @attr id of the whole_section_scoring object 
     */
    def display_whole_section_scoring_mm_link = { attr, body ->
        long id = attr['id']
        Whole_section_scorings whole_section_scoring = Whole_section_scorings.get(id);
        out << createLink(base:grailsApplication.config.grails.serverURL, controller:"scoring_sessions", action:"score", id:whole_section_scoring.getScoring_session().getId(), params:["whole_section_scoring_id":id,"showMM":"",(ViewConstants.SHOW_BODY_ONLY_PARAM_FLAG):true])
    }
    
    /**
     * display javascript functions to allow HE & MM windows open & close (i.e. clean up before reload page)
     *
     * @attr id of the whole_section_scoring object 
     */
    def display_whole_section_scoring_he_mm_javascripts = { attr, body ->
        long id = attr['id'];
        out << "var heWindowHandle = null; ";
        out << "var mmWindowHandle = null; ";
        out << "function openHeWindow() {heWindowHandle=window.open(\""+g.display_whole_section_scoring_he_link(id:id)+"\",\""+DisplayConstant.H_AND_E_WINDOW+"\",JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS);} ";
        out << "function openMmWindow() {mmWindowHandle=window.open(\""+g.display_whole_section_scoring_mm_link(id:id)+"\",\""+DisplayConstant.MM_WINDOW+     "\",JAVASCRIPT_OPEN_POPUP_WINDOW_SPECS);} ";
        out << "function cleanUp() {cleanUpBeforeChangeMainWindow([heWindowHandle,mmWindowHandle]); return true;} ";
    }
    
    /**
     * display field selector applet
     * 
     * NOTE: when scoringFieldsAllocator is null, ASSUME, it must be trying show hotspot only!!!
     * 
     * @attr whole_section_scoring object
     * @attr scoringFieldsAllocator
     */
    def display_field_selector_applet = { attr, body ->
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        Scoring_sessions scoring_session = whole_section_scoring.getScoring_session();
        ScoringFieldsAllocator scoringFieldsAllocator = attr['scoringFieldsAllocator'];
        String state = attr['state'];
        boolean selectingFields = state.equals(ImageViewerMethods.FIELD_SELECTOR_STATE_SELECTING);
        boolean showHotspotOnly = (whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_SELECT_HOTSPOT || whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT);
        
        
        out << "<canvas id='"+ImageViewerMethods.FIELD_SELECTOR_CANVAS_ID+"' width='"+attr['width']+"' height='"+attr['height']+"'>Failed to load image.</canvas>";
        out << "<script type='text/javascript'>"; 
        out << "var "+ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR+";"
        out << "require(['dojo/ready'], function(ready){ready(function(){";
        out << ViewConstants.JAVASCRIPT_OBJECT_NAME_FIELD_SELECTOR+" = new FieldSelector(";
        out << "'"+whole_section_scoring.getWhole_section_image().showLowResImageURL()+"',"; // image URL
        out << "'"+ImageViewerMethods.FIELD_SELECTOR_CANVAS_ID+"',"; // canvas ID
        out << Math.round(whole_section_scoring.getWhole_section_image().showLowResImage().showScale_to_original())+",";// scaleToOriginal,
        out << "'"+(new FieldSelectionParamStringDbConnector(whole_section_scoring, null)).generateFieldSelectionParamString()+"',";// fieldSelectionParamString,
        out << Math.round(whole_section_scoring.getField_diameter_40x_mm() * 1000f / whole_section_scoring.getWhole_section_image().getMicrons_per_pixel())+",";// fieldDiameterInPixel, // 0.498700 micron per pixel ... 1mm ~ 2000 pixel 
        out << "'"+state+"',";// state,
        boolean needGetNumFieldsFromScoringFieldAllocator = selectingFields && scoringFieldsAllocator!=null;
        out << (needGetNumFieldsFromScoringFieldAllocator ? scoringFieldsAllocator.getNumHigh()       : -1) + ","; // maxNumFieldHigh,
        out << (needGetNumFieldsFromScoringFieldAllocator ? scoringFieldsAllocator.getNumMedium()     : -1) + ",";// maxNumFieldMedium,
        out << (needGetNumFieldsFromScoringFieldAllocator ? scoringFieldsAllocator.getNumLow()        : -1) + ",";// maxNumFieldLow,
        out << (needGetNumFieldsFromScoringFieldAllocator ? scoringFieldsAllocator.getNumNegligible() : -1) + ","; // maxNumFieldNegligible,
        out << ((scoring_session.showSubmitted() || showHotspotOnly)?"true" :"false")+",";// showHotspot,
        out << ((!scoring_session.showSubmitted() && showHotspotOnly)?"false":"true" )+",";// showOtherFields
        if (whole_section_scoring.showIsAllowedToUpdateScore()){
            out << "false";
        } else {
            out << "true";
        }        
        out << ");"; // new FieldSelector(
        out << "});});"; // require(['dojo/ready'], function(ready){ready(function(){
        out << "</script>";

    }
    
    /**
     * display field selector buttons
     * 
     * NOTE: when scoringFieldsAllocator is null, ASSUME, it must be trying show hotspot only!!!
     * 
     * @attr whole_section_scoring object
     * @attr scoringFieldsAllocator
     * @attr selectHotspot
     */
    def display_field_selector_buttons = { attr, body ->
        ScoringFieldsAllocator scoringFieldsAllocator = attr['scoringFieldsAllocator'];  
        boolean selectHotspot = attr['selectHotspot'];
        OddEvenRowFlag oddEvenRowFlag = new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)
        out << "<table>";
        if (scoringFieldsAllocator == null) {
            out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
            out << "<td><span style='vertical-align:middle; background-color:red'>&nbsp;&nbsp;&nbsp;</span></td>"
            out << "<td><button dojoType='dijit.form.Button'";
            out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST+"' ";
            out << " disabled='disabled'"; // initially, start with hot-spot selection
            out << " onclick=\"setFieldSelectorToHighest()\">selecting Hot-spot ...</button></td>";
            out << "<td><span id="+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST+" style='vertical-align:middle;'>(not selected)</span></td>";
            out << "</tr>";
        } else {
            // for non-hotspot ... need to determine which field (high/low/med/neg) to be selected at start
            // so to MATCH the fieldSelector applet!!!
            // NOTE: fieldSelector applet will by default be selecting the highest possible field first
            boolean buttonSelected = false; // indicate whether the button is selected already           
            int numHighRequired = scoringFieldsAllocator.getNumHigh();
            if (numHighRequired > 0) {
                out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
                out << "<td><span style='vertical-align:middle; background-color:orange'>&nbsp;&nbsp;&nbsp;</span></td>"
                out << "<td><button dojoType='dijit.form.Button' ";
                out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH+"' ";
                out << (buttonSelected ? "" : " disabled='disabled'");
                out << " onclick=\"setFieldSelectorToHigh(); return false;\">"+(buttonSelected? "High" : "selecting High ...")+"</button></td>";
                out << "<td><span id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH+"' style='vertical-align:middle;'>(0/"+numHighRequired+" selected)</span></td>";
                out << "</tr>";
                buttonSelected = true;
            }
            int numMediumRequired = scoringFieldsAllocator.getNumMedium();
            if (numMediumRequired > 0) {
                out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
                out << "<td><span style='vertical-align:middle; background-color:yellow'>&nbsp;&nbsp;&nbsp;</span></td>"
                out << "<td><button dojoType='dijit.form.Button' ";
                out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM+"' ";
                out << (buttonSelected ? "" : " disabled='disabled'");
                out << " onclick=\"setFieldSelectorToMedium(); return false;\">"+(buttonSelected? "Medium" : "selecting Medium ...")+"</button></td>";
                out << "<td><span id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM+"' style='vertical-align:middle;'>(0/"+numMediumRequired+" selected)</span></td>";
                out << "</tr>";
                buttonSelected = true;
            }
            int numLowRequired = scoringFieldsAllocator.getNumLow();
            if (numLowRequired > 0) { 
                out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
                out << "<td><span style='vertical-align:middle; background-color:green'>&nbsp;&nbsp;&nbsp;</span></td>"
                out << "<td><button dojoType='dijit.form.Button' ";
                out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW+"' ";
                out << (buttonSelected ? "" : " disabled='disabled'");
                out << " onclick=\"setFieldSelectorToLow(); return false;\">"+(buttonSelected? "Low" : "selecting Low ...")+"</button></td>";
                out << "<td><span id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW+"' style='vertical-align:middle;'>(0/"+numLowRequired+" selected)</span></td>";
                out << "</tr>";
                buttonSelected = true;
            }
            int numNegligibleRequired = scoringFieldsAllocator.getNumNegligible();
            if (numNegligibleRequired > 0) {
                out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
                out << "<td><span style='vertical-align:middle; background-color:black'>&nbsp;&nbsp;&nbsp;</span></td>"
                out << "<td><button dojoType='dijit.form.Button' ";
                out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE+"' ";
                out << (buttonSelected ? "" : " disabled='disabled'");
                out << " onclick=\"setFieldSelectorToNegligible(); return false;\">"+(buttonSelected? "Negative" : ("selecting Negative ..."))+"</button></td>";
                out << "<td><span id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE+"' style='vertical-align:middle;'>(0/"+numNegligibleRequired+" selected)</span></td>";
                out << "</tr>";
                buttonSelected = true;
            }
        }
        out << "<tr><td colspan=3></td><br><br></tr>";
        out << "<tr><td colspan=3></td><br><br></tr>";
        out << "<tr><td colspan=3></td><br><br></tr>";
        out << "<tr class='"+oddEvenRowFlag.showFlag()+"'>";
        out << "<td colspan=3><button dojoType='dijit.form.Button'";
        out << " id='"+ViewConstants.HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DELETE+"' ";
        out << " onclick=\"removeCurrentSelectedField('remove "+(selectHotspot?"hotspot selection":"current selected field")+"?'); return false;\">Remove "+(selectHotspot?"hotspot selection":"current selected")+"</button></td>";
        out << "</tr>";             
        out << "</table>";
    }
    

    /**
     * display the nuclei counter applet 
     * - this is needed since there seems to be a problem deploying applet
     *   after page load, therefore, need to deploy applet at page load, 
     *   hide it, and show it when required.
     * 
     * @attr whole_section_scoring object
     */
    def display_nuclei_counter_for_field_selector_applet = { attr, body ->
        Whole_section_scorings whole_section_scoring = attr['whole_section_scoring'];
        boolean allowedToUpdateScore = whole_section_scoring?.showIsAllowedToUpdateScore();
        String appletDomElementId = "nucleiCounterAppletId";
        
        out << "<br>";
        out << "<div id='keyStrokeListenerTdId'>"
        if (allowedToUpdateScore) {
            out << "<input id='keyStrokeListener' type='text' dojoType='dijit.form.TextBox' style='width: 30em; border-style:solid; border-width:medium; border-color:red;' value='please wait ...'/>";
            out << "<br><br>";
        }
        out << "</div>";
        String scoringInstruction = "User indicated Ki67 level: <b></b><br>" + (allowedToUpdateScore ? "Please count up to " : "Require ") + "<b>0 invasive tumour nuclei" + (allowedToUpdateScore ? "" : " count") + "</b>";
        out << "<span id='scoringInstruction' style=\"font-size:larger; background-color: #F6CECE; display: inline-block;\">" + scoringInstruction + "</span><br>";
        
        out << "<table>";
        out << "<tr>";
        
        out << "<td id='" + appletDomElementId + "_deployed'>";
        // load nuclei counter!!!
        
        out << "<canvas id='"+ImageViewerMethods.NUCLEI_COUNTER_CANVAS_ID+"' width='249' height='249'>Failed to load counter.</canvas>";
        out << "<script type='text/javascript'>"; 
        out << "var nucleiCounter;"
        out << "require(['dojo/ready'], function(ready){ready(function(){";
        out << "nucleiCounter = new NucleiCounter(";
        out << "'',"; // no path image url for nuclei counter
        out << "'"+ImageViewerMethods.NUCLEI_COUNTER_CANVAS_ID+"',"; // canvas id
        out << "false,"; // drawThumbnailWindow
        out << "MEDIA_IMAGE_NUCLEI_COUNTER_URL,"; // counterImageURL i.e. nuclei counter; MEDIA_IMAGE_NUCLEI_COUNTER_URL defined in main.gsp
        out << "MEDIA_SOUND_FILE_BEEP,"; // negative count sound url
        out << "MEDIA_SOUND_FILE_CLICK1,"; // positive count sound url
        out << "MEDIA_SOUND_FILE_CLICK2,"; // undo count sound url
        out << "MEDIA_SOUND_FILE_DINGLING,"; // notification sound url
        out << "'',"; // nuclei selection string - won't know this until click on a scoring field
        out << (whole_section_scoring.showIsAllowedToUpdateScore()?"false":"true");
        out << ");";
        out << "});});";
        out << "</script>";        
        out << "</td>";
        
        
        
        
        
        if (allowedToUpdateScore) {
            // start of save/reset buttons
            out << "<td style='vertical-align:middle;'>";
            out << "<button id=\"saveNucleiCountButtonId\" type=\"button\"></button>";
            out << "<br><br>"
            out << "<button id=\"resetCounterButtonId\" type=\"button\"></button>";
            out << "</td>";
            // end of save/reset buttons
        }
        out << "</tr>"
        out << "</table>";

        if (allowedToUpdateScore) {
            out << "<br>";
            out << "<span id='commentInstruction'>Please enter any comments in the box below (optional)</span><br>";
            out << "<textarea id='inputComment' name='inputComment' dojoType='dijit.form.Textarea' style='width:30em; border-color:black;'></textarea>";
            out << "<br><br>";
            out << "<button id=\"doneWithThisFieldButtonId\" type=\"button\"></button>";
            out << "<div style=\"display: inline; float: right\"><button id=\"goBackButtonId\" type=\"button\"></button></div>";
        } else {
            out << "<br><br>";
            out << "<span id='commentInstruction'>User's comment (on this field):</span><br>";
            out << "<textarea id='inputComment' dojoType='dijit.form.Textarea' style='width:30em;' disabled='disabled'></textarea>";
        }
    }
    
}
