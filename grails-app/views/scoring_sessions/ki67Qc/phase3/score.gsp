<!--
  actual scoring (nuclei count on core biopsy glass) for Ki67-QC phase 3
-->
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant"%>
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.util.MiscUtil"%>
<%@ page import="ca.ubc.gpec.tmadb.util.Scoring_sessionsConstants"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_scorings"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Scoring session: ${scoring_sessionInstance.getName()}</title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
</head>
<body>
    <div class="body">
        <g:if test="${!showPageBodyOnly}">
            <h1>Scoring session: ${scoring_sessionInstance.getName()}</h1>
            <g:showFlashMessage />
            <p><g:display_scoring_session_progress id="${scoring_sessionInstance.getId()}"/></p>
            <!-- show timer if user has not submitted the results yet ...-->
            <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"><asset:script>timeMsg();</asset:script></g:if>
            </g:if>
            <g:display_scoring_image_option scoring="${scoring}"  showReference="${showReference}"  showPageBodyOnly="${showPageBodyOnly}" showHE="${showHE}" showMM="${showMM}" />
        <br>
        <div dojoType='dijit.form.Form' action="" method='POST' id="${ViewConstants.HTML_FORM_NAME_KI67_QC_PHASE3_INIT}">
            <g:display_scoring_objects_ids scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}"/>
            <div class="dialog">
                <g:display_scoring_objects_description_and_navigation scoring_session="${scoring_sessionInstance}" tma_scoring="${null}" whole_section_scoring="${whole_section_scoring}" showPageBodyOnly="${showPageBodyOnly}"/>
                <table>
                    <tr>
                        <td><g:display_field_selector_applet whole_section_scoring="${whole_section_scoring}" scoringFieldsAllocator="${scoringFieldsAllocator}" width="550" height="550" state="${ImageViewerMethods.FIELD_SELECTOR_STATE_SCORING}"/></td>
                        <td>
                            <table>
                                <tr><g:display_scoring_commands id="scoring_command_id" whole_section_scoring="${whole_section_scoring}" displayInOneTd="true"/></tr>
                                <tr><td style="vertical-align: middle"><div id="beforeNucleiCounterAppletActiveDiv">
                                            <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}">
                                                <span style="background-color: yellow">Please click on ${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT ? "the hotspot":"a field of view"} to begin scoring.</span><br><br>
                                                <button dojoType='dijit.form.Button'
                                                onclick="return go_back();"
                                                title="click me to go back to previous step i.e. select ${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT ? "hotspot":"fields of view"}">BACK</button>
                                            </g:if>
                                            <g:else>
                                                <span style="background-color: yellow">Please select a field of view to view scores.</span> 
                                            </g:else>
                                        </div>
                                        <div id="${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}"><g:display_nuclei_counter_for_field_selector_applet whole_section_scoring="${whole_section_scoring}"/></div>
                                        <g:if test="${!(whole_section_scoring?.showIsAllowedToUpdateScore())}">
                                            <br><br>
                                            <div>
                                                <table>
                                                    <tr><td><g:display_ki67_qc_phase3_init_input whole_section_scoring="${whole_section_scoring}" /></td></tr>
                                                    <tr><td><g:display_ki67_qc_phase3_estimate_percent_input whole_section_scoring="${whole_section_scoring}" /></td></tr>
                                                </table>
                                                <br><br>
                                                <g:if test="${whole_section_scoring.showScoringComment().length()>0}">
                                                    User's comment (on whole slide):<br>
                                                    <textarea
                                                id='${ViewConstants.HTML_PARAM_NAME_KI67_QC_PHASE3_WHOLE_SECTION_SCORING_COMMENT}'
                                                    style='width:30em;'
                                                disabled='disabled'>${whole_section_scoring.showScoringComment()}</textarea>
                                            </g:if>
                                        </div>
                                    </g:if>
                                </td></tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_POS_ID}"></div>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_NEG_ID}"></div>
            <div style="display: none" id="${Scoring_sessionsConstants.NUCLEI_COUNT_STATUS_NUM_TOTAL_ID}"></div>
        </div> <!-- <div class="dialog"> -->
    </div> <!-- <div dojoType='dijit.form.Form'> -->
</div>
<asset:script type="text/javascript">
    var _tempFirstTimeLoading = true;
    var _tempSaveStatusOK = false;
    var _tempResetInProgress = false;
    var _tempSuppressSaveOkMsg = false;
    
    <g:display_whole_section_scoring_he_mm_javascripts id="${whole_section_scoring?.getId()}"/>

    var whole_section_region_scoring_id = null; // this variable will be updated by displayNucleiCounterAppletBasedOnWhole_section_region_scoring()
    var number_of_remaining_fields = ${whole_section_scoring.showNumberOfUnscoredWhole_section_region_scorings()}; // number of remaining fields
    var scoring_command_display_style="";
    var nuclei_selection_notification_nuclei_count_array = [];
    var nuclei_selection_notification_message_array = [];

    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        // connect onkeypress event for keyStrokeListener
        require(['dojo/ready'], function(ready){ready(function(){
        dojo.connect(dojo.byId("keyStrokeListener"), "onkeypress", function(e){
        var result = sendCellCounterKeyStrokeToApplet(e);
        dojo.stopEvent(e); // prevent the keystroke event propagating to the text box
        return result;
        }); // dojo.connect(dojo.byId("keyStrokeListener"), "onkeypress", function(e){
        });}); // require(['dojo/ready'], function(ready){ready(function(){
    </g:if>

    // this function is called by nucleiCounter
    function updateNucleiSelectionCount(numPos, numNeg, numTotal) {
    // NOTE: currently the nuclei counter is deployed at page loade
    //       therefore, need to hide the associated input text boxes initially 
    //       as the user is NOT scoring yet
    //       whole_section_region_scoring_id is null initally
    if (whole_section_region_scoring_id == null) {
    // hide counter related buttons and text box
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        document.getElementById("keyStrokeListenerTdId").style.display="none";
        document.getElementById("saveNucleiCountButtonId").style.display="none";
        document.getElementById("resetCounterButtonId").style.display="none";
        document.getElementById("doneWithThisFieldButtonId").style.display="none";
        document.getElementById("goBackButtonId").style.display="none";
    </g:if>
    document.getElementById("scoringInstruction").style.display="none";
    document.getElementById("commentInstruction").style.display="none";
    document.getElementById("inputComment").style.display="none";
    } else {
    // only update if really scoring!!!
    // special version for Ki67-QC phase 3 ... prevent counting when count >= nuclei_selection_notification_nuclei_count
    updateNucleiSelectionCountHelperKi67QcPhase3(numPos, numNeg, numTotal, false, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array);
    }
    // enable the nucleiCounter textbox since the nucleiCounter must be ready by this point
    if (dojo.byId("keyStrokeListener")!=null) {
    if (dojo.byId("keyStrokeListener").disabled) {
    dojo.byId("keyStrokeListener").value="";
    dojo.byId("keyStrokeListener").placeholder="Click here to begin counting using '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_POSITIVE}', '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_NEGATIVE}' and '${Scoring_sessionsConstants.NUCLEI_COUNT_KEY_UNDO}' keys"
    dojo.byId("keyStrokeListener").disabled=false;
    } // if (dojo.byId("keyStrokeListener")!=null)
    } // if (dojo.byId("keyStrokeListener").disabled)
    }

    function uploadNucleiSelection(nucleiSelectionParamString) {
    var inputComment = document.getElementById('inputComment').value;
    var xhrHandle = null;
    //showWaitDialogMsg(1,"uploading ...","uploading nuclei selections ... please wait");
    
    require(["dojo/_base/xhr"], function (xhr) {
        xhrHandle = xhr.post({
            url:  "${createLink(controller:"whole_section_region_scorings", action:"uploadNucleiSelection")}", // read the url: from the action="" of the <form>
            content: { id:whole_section_region_scoring_id, nucleiSelectionParamString:nucleiSelectionParamString, inputComment:inputComment },
            handleAs: "text",
            load: function (e) {
                //closeWaitDialog();
                checkUpdateNucleiSelection(e, "${createLink(controller:"scoring_sessions", action:"score",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
                if (!_tempSaveStatusOK) {
                    showMessageDialog("ERROR", "ERROR. failed to save nuclei count.");
                } else {
                    if (nucleiSelectionParamString!=="0" && !_tempSuppressSaveOkMsg) { // nothing changed ... no need to show messsage
                        showMessageDialog("Nuclei count saved.", "Nuclei count saved.");
                    }
                    _tempSuppressSaveOkMsg = false; // reset this flag
                }
                nucleiCounter.nucleiSelection.resetUpdateCount(); // NEED TO RESET COUNTERS!!!        
                nucleiCounter.repaint();
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.post
    }); // function (xhr)
    return xhrHandle;
    } // function uploadNucleiSelection()
    
    //
    // helper function for displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates
    //
    function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state) {
        document.getElementById("beforeNucleiCounterAppletActiveDiv").style.display="none"; // hide the extra "back" button
    // display the hidden counter related td's
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> 
        document.getElementById("keyStrokeListenerTdId").style.display="block";
        document.getElementById("saveNucleiCountButtonId").style.display="block";
        document.getElementById("resetCounterButtonId").style.display="block";
        document.getElementById("doneWithThisFieldButtonId").style.display="block";
        document.getElementById("goBackButtonId").style.display="block";
    </g:if>
    document.getElementById("scoringInstruction").style.display="block";
    document.getElementById("commentInstruction").style.display="block";
    document.getElementById("inputComment").style.display="block";
    document.getElementById("inputComment").style.resize="both";
    // end of display the hidden counter related td's
    displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinatesNeedAjaxUrl(
    "${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}", // appletDomElementId
    "${(MiscUtil.showIsLoggedIn(session)?grailsApplication.config.grails.serverSecureURL_noAppName:grailsApplication.config.grails.serverURL_noAppName)}", // baseUrl
    ${whole_section_scoring?.showIsAllowedToUpdateScore()}, // allowToUpdateScore
    "${createLink(controller:"whole_section_scorings", action:"ajax_start_scoring_whole_section_region_scoring_and_get_id", id:whole_section_scoring.getId())}",  
    x, 
    y, 
    diameter,
    ki67state,
    "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}",
    <g:if test="${whole_section_scoring.getState()==Whole_section_scorings.SCORING_STATE_KI67_QC_PHASE3_COUNT_HOTSPOT}">
        "${null}", // null when scoring HOTSPOT
        "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId(), params:[(ViewConstants.HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_SCORING_ID):whole_section_scoring.getId()])}" // nextUrl 
    </g:if>
    <g:else>
        "${createLink(controller:"whole_section_scorings", action:"set_scoring_date", id:whole_section_scoring.getId())}",
        "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}" // nextUrl 
    </g:else>
    );
    document.getElementById('scoring_command_id').style.display=scoring_command_display_style; // display scoring commands
    } // function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state) {
    
    //
    // function to activate nuclei counter as well as get the whole_section_region_scoring_id
    // - will try to save nuclei selection first
    // NOTE: x, y are in full res image coordinates (NOT low res, as stored in applet)
    //       ki67state is name of the ki67state on SERVER NOT how it is represented on the client
    function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates (x, y, diameter, ki67state) {
    // show nuclei counter
    document.getElementById("${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}").style.visibility='visible';
    <g:if test="${whole_section_scoring?.showIsAllowedToUpdateScore()}"> // save current nuclei selection - ONLY if allowed to update
        if (whole_section_region_scoring_id != null) {
            try {
                // NOTE: nucleiCounter.uploadNucleiSelection() will call the javascript function uploadNucleiSelection to do the real uploading
                nucleiCounter.uploadNucleiSelection().then(function(){
                    if (!_tempSaveStatusOK) {
                        alert('ERROR! nuclei selection save failed');
                    } else {
                        displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state); 
                    }
                });
            } catch (err) { // nucleiCounter failed to load!!!
                alert("An runtime error has occured.  All scores has been saved.  The page will now reload.  Please re-select the field to activate nuclei counter.");
                window.location = "${createLink(controller:"scoring_sessions", action:"score",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}";
            }
        } else {
            displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state); 
        }
    </g:if>
    <g:else>
        displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates_wo_uploadNucleiSelection (x, y, diameter, ki67state);
    </g:else>
    } // function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinates (x, y, diameter, ki67state) {
            
    // function to go back to previous step i.e. select fields
    function go_back() {
    ki67_qc_phase3_go_back_one_step(
    "${createLink(controller:"scoring_sessions", action:"ajax_ki67_qc_phase3_go_back",params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}", 
    "${createLink(controller:"scoring_sessions", action:"score",                      params:[id:scoring_sessionInstance.getId(), whole_section_scoring_id:whole_section_scoring.getId()])}");
    // clean up
    cleanUp();
    return false; // prevent page refresh
    } // function go_back() {

    <g:if test="${!showHE && !showMM}">
        require(['dojo/ready'], function(ready){ready(function(){
        scoring_command_display_style=document.getElementById('scoring_command_id').style.display;
        document.getElementById('scoring_command_id').style.display="none"; // need to initially hide scoring command since its not applicable until nuclei counter appears
        document.getElementById("${ViewConstants.HTML_ELEMENT_ID_NUCLEI_COUNTER_DIV}").style.visibility="hidden"; // want to hide the counter initially
        });});
    </g:if>

</asset:script>
</body>
</html>

