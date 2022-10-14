/* 
 * functions specific to KI67 QC phase 3 study
 */

/**
 * this variable is needed to keep track of the onclick event call back function,
 * since this function will change as the scoring progresses
 */
var doneWithThisFieldButtonId_onclick_handle = null;

function enableFieldSelectorButtonHighest() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).setLabel("Hot-spot");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).set('disabled', false);
    }
}
function enableFieldSelectorButtonHigh() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).setLabel("High");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).set('disabled', false);
    }
}
function enableFieldSelectorButtonMedium() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).setLabel("Medium");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).set('disabled', false);
    }
}
function enableFieldSelectorButtonLow() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).setLabel("Low");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).set('disabled', false);
    }
}
function enableFieldSelectorButtonNegligible() {
    if (dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE)) {
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).setLabel("Negative");
        dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).set('disabled', false);
    }
}

/**
 * set field sector mode to select hot spot
 * @returns {Boolean}
 */
function setFieldSelectorToHighest() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToHighest();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).setLabel("selecting Hot-spot ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGHEST).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToHigh() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToHigh();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).setLabel("selecting High ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_HIGH).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToMedium() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToMedium();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).setLabel("selecting Medium ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_MEDIUM).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonLow();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToLow() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToLow();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).setLabel("selecting Low ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_LOW).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonNegligible();
    return false;
}

function setFieldSelectorToNegligible() {
    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setKi67SelectionStateToNegligible();
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).setLabel("selecting Negative ...");
    dijit.byId(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_NEGLIGIBLE).set('disabled', true);
    // enable other buttons ...
    enableFieldSelectorButtonHighest();
    enableFieldSelectorButtonHigh();
    enableFieldSelectorButtonMedium();
    enableFieldSelectorButtonLow();
    return false;
}

/**
 * check to see if there are any current selected field
 * 
 * @returns true if there is a current selected field, false otherwise
 */
function anyCurrentSelectedField() {
    var paramString = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString();
    var fieldSelectionParamStrings = paramString.split(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_DELIMITER);
    for (var i = 0; i < fieldSelectionParamStrings.length; i++) {
        var fieldSelectionParamString = fieldSelectionParamStrings[i];
        var tagIndex = fieldSelectionParamString.indexOf(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_KI67_PP);
        if (fieldSelectionParamString.substring(tagIndex + 3, tagIndex + 4) == KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_VIEWING_STATE_CURRENT) {
            return true; // current selected field found!
        }
    }
    return false; // no current selected field found
}

/**
 * remove curent selected field an dupdate button descriptions
 * @param {type} confirmMsg - confirmation message
 * @returns {undefined}
 */
function removeCurrentSelectedField(confirmMsg) {
    if (anyCurrentSelectedField()) {
        if (confirm(confirmMsg)) {
            window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].removeCurrentViewingField();
            updateButtonDescs();
        }
    } // do nothing if no current selected field.
}

/**
 * get current selections from applet and update the corresponding button descriptions
 * @returns {undefined}
 */
function updateButtonDescs() {
    updateButtonDescsWithInputFieldSelectionParamString(window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString());
}

/**
 * parse the input paramString and update the corresponding button descriptions
 * @param {type} paramString
 * @returns {undefined}
 */
function updateButtonDescsWithInputFieldSelectionParamString(paramString) {
    var fieldSelectionParamStrings = paramString.split(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_DELIMITER);
    var descElementHighest = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGHEST);
    var descElementHigh = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_HIGH);
    var descElementMedium = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_MEDIUM);
    var descElementLow = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_LOW);
    var descElementNegligible = document.getElementById(HTML_ELEMENT_ID_KI67_QC_PHASE3_SELECT_REGION_BUTTON_DESC_NEGLIGIBLE);
    if (fieldSelectionParamStrings.length == 0) {
        // set all to zero
        if (descElementHighest != null) {
            descElementHighest.innerHTML = "(not selected)"; // special case for hot-spot
        }
        if (descElementHigh != null) {
            var descHigh = descElementHigh.innerHTML;
            descElementHigh.innerHTML = descHigh.replace(/\(\d+/, "(0");
        }
        if (descElementMedium != null) {
            var descMedium = descElementMedium.innerHTML;
            descElementMedium.innerHTML = descMedium.replace(/\(\d+/, "(0");
        }
        if (descElementLow != null) {
            var descLow = descElementLow.innerHTML;
            descElementLow.innerHTML = descLow.replace(/\(\d+/, "(0");
        }
        if (descElementNegligible != null) {
            var descNegligible = descElementNegligible.innerHTML;
            descElementNegligible.innerHTML = descNegligible.replace(/\(\d+/, "(0");
        }
    } else {
        // some selection!!!
        var numHighest = 0;
        var numHigh = 0;
        var numMedium = 0;
        var numLow = 0;
        var numNegligible = 0;
        for (var i = 0; i < fieldSelectionParamStrings.length; i++) {
            var fieldSelectionParamString = fieldSelectionParamStrings[i];
            // find out the ki67 level of this field selection ...
            var tagIndex = fieldSelectionParamString.indexOf(KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_TAG_KI67_PP);
            switch (parseInt(fieldSelectionParamString.substring(tagIndex + 2, tagIndex + 3))) {
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_NEGLIGIBLE:
                    numNegligible++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_LOW:
                    numLow++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_MEDIUM:
                    numMedium++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGH:
                    numHigh++;
                    break;
                case KI67_QC_PHASE3_FIELD_SELECTION_PARAMSTRING_KI67_PP_LEVEL_HIGHEST:
                    numHighest++;
                    break;
            }
        }
        if (descElementHighest != null) {
            descElementHighest.innerHTML = numHighest > 0 ? "(selected)" : "(not selected)"; // special case for hot-spot
        }
        if (descElementHigh != null) {
            var descHigh = descElementHigh.innerHTML;
            descElementHigh.innerHTML = descHigh.replace(/\(\d+/, "(" + numHigh + "");
        }
        if (descElementMedium != null) {
            var descMedium = descElementMedium.innerHTML;
            descElementMedium.innerHTML = descMedium.replace(/\(\d+/, "(" + numMedium + "");
        }
        if (descElementLow != null) {
            var descLow = descElementLow.innerHTML;
            descElementLow.innerHTML = descLow.replace(/\(\d+/, "(" + numLow + "");
        }
        if (descElementNegligible != null) {
            var descNegligible = descElementNegligible.innerHTML;
            descElementNegligible.innerHTML = descNegligible.replace(/\(\d+/, "(" + numNegligible + "");
        }
    }
}

/**
 * reset selections to original state
 * @returns {undefined}
 */
function resetSelections() {
    showConfirmDialog(
            "Please confirm ...",
            "Reset all selections to original?",
            "Yes", // confirm
            "No", // decline
            function () {
                window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].resetSelections();
                updateButtonDescs();
            }
    );
}

/**
 * do check before saving field selections
 * @param {type} confirmMsg - confirmation message
 * @returns true of ok to save, false otherwise
 */
function checkBeforeSavingFieldSelections(confirmMsg) {
    if (!window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isAllFieldsSelected()) {
        alert("please select all required fields");
        return false;
    } else if (confirm(confirmMsg)) {
        // put paramstring to html param
        document.getElementById(HTML_PARAM_NAME_KI67_QC_PHASE3_SELECT_REGION_PARAMSTRING).value = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].getfieldSelectionParamString();
        return true;
    }
    return false;
}

/**
 * response function to when the "done with all fields" button has been click ...
 * i.e. set scoring_date on current whole_section_scoring record and go to next available whole_section_scoring record
 * 
 * @param {type} ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl - this can be whole_section_scoring OR whole_section_region_scoring depending on whether its form a hot-spot scoring or not
 * @param {type} nextUrl
 * @returns {undefined}
 */
function doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl, nextUrl) {
    // save nuclei and go to next case
    nucleiCounter.uploadNucleiSelection().then(function () {
        if (!_tempSaveStatusOK) {
            alert("ERROR. failed to save nuclei count");
        } else {
            showConfirmDialog(
                    "Please confirm ...",
                    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isShowingHotspotOnly() ? "done with hotspot on this image and continue?" : "done with all fields on this image and continue?",
                    "Yes", // confirm
                    "No", // decline
                    function () {
                        require(["dojo/_base/xhr"], function (xhr) {
                            xhr.get({
                                url: ajaxSetWhole_sectionOrRegion_scoringScoring_dateUrl,
                                handleAs: "text",
                                load: function (e) {
                                    if (e == AJAX_RESPONSE_ERROR) {
                                        alert("Error occurred. Failed to save (whole section scoring) record to database.")
                                    } else if (e == AJAX_RESPONSE_NA) {
                                        alert("Error occurred. Not all fields has been scored.")
                                    } else {
                                        // do next scoring
                                        cleanUpBeforeChangeMainWindow([heWindowHandle, mmWindowHandle]); // clean-up any H&E and MM window that MAY have opened
                                        window.location.href = nextUrl;
                                    }
                                    return;
                                },
                                onError: function (e) {
                                    alert("Error occurred. Failed to save (whole section scoring) record to database. Error message: " + e);
                                }
                            }); // xhr.get
                        }); // function (xhr)
                    }
            ); // showConfirmDialog(
        }
    }) // nucleiCounter.uploadNucleiSelection().then(function() {
}

/**
 * prepare e.g. define onClick function
 * 
 * @param {int} number_of_remaining_fields
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl
 * @param {type} nextUrl
 * @returns {undefined}
 */
function prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    var saveButton = dijit.byId("doneWithThisFieldButtonId");
    var showingHotspotOnly = window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].isShowingHotspotOnly();
    if (doneWithThisFieldButtonId_onclick_handle != null) {
        dojo.disconnect(doneWithThisFieldButtonId_onclick_handle);
    }
    if (number_of_remaining_fields > 0 && !showingHotspotOnly) {
        // change text of doneWithThisFieldButton
        saveButton.set("label", "DONE with this field (" + number_of_remaining_fields + " remaining)");
        saveButton.set("title", "click me to indicate finished scoring this field.");
        doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
            doneWithThisFieldAction(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        });
    } else {
        if (showingHotspotOnly) {
            saveButton.set("label", "DONE with hotspot");
            saveButton.set("title", "click me to indicate finished scoring hotspot on this case and continue.");
            doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
                doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_section_region_scoringScoring_dateUrl, nextUrl);
            });
        } else {
            saveButton.set("label", "DONE with all fields!");
            saveButton.set("title", "click me to indicate finished scoring all fields on this case and continue.");
            doneWithThisFieldButtonId_onclick_handle = dojo.connect(saveButton, "onClick", function () {
                doneWithAllFieldsButtonResponseFunction(ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
            });
        }
    }
}

/**
 * display nuclei counter applet
 * 
 * - requires the following function to be defined (in gsp file): finishedScoringCurrentField()
 * 
 * @param {type} appletDomElementId
 * @param {type} baseUrl
 * @param {type} allowedToUpdateScore
 * @param {type} nucleiSelectionParamString
 * @param {type} ihc_score_category_name
 * @param {type} numNucleiToCount
 * @param {type} comment
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}" ... need id of whole_section_region_scoring
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score")}" - no id so that scoring_sessions controller can decide what to show next
 *
 * 
 * @returns {undefined}
 */
function  displayNucleiCounterAppletBasedOnNucleiSelectionParamString(
        appletDomElementId,
        baseUrl,
        allowedToUpdateScore,
        nucleiSelectionParamString,
        ihc_score_category_name,
        numNucleiToCount,
        comment,
        ajaxSetWhole_section_region_scoringScoring_dateUrl,
        ajaxSetWhole_section_scoringScoring_dateUrl,
        nextUrl
        ) {
    var appletSource = "<br>";
    if (allowedToUpdateScore) {
        //appletSource += "<br><span style='background-color: yellow'>please type the cell counter commands:</span><br>(i.e. '" + NUCLEI_COUNT_KEY_POSITIVE_LOWER_CASE + "','" + NUCLEI_COUNT_KEY_NEGATIVE_LOWER_CASE + "','" + NUCLEI_COUNT_KEY_UNDO_LOWER_CASE + "') in text box below:<br>";
        appletSource += "<input id='keyStrokeListener' type='text' dojoType='dijit.form.TextBox' style='width: 30em; border-style:solid; border-width:medium; border-color:red; value='please wait ...' disabled='disabled' onkeypress='return sendCellCounterKeyStrokeToApplet(event);'/>";
        appletSource += "<br><br>";
    }
    var scoringInstruction = "User indicated Ki67 level: <b>" + ihc_score_category_name.toUpperCase() + "</b><br>" + (allowedToUpdateScore ? "Please count up to " : "Require ") + "<b>" + numNucleiToCount + " invasive tumour nuclei" + (allowedToUpdateScore ? "" : " count") + "</b>";
    appletSource += "<span id='scoringInstruction' style=\"font-size:larger; background-color: #F6CECE; display: inline-block;\">" + scoringInstruction + "</span><br>";

    appletSource += "<table>";
    appletSource += "<tr>";
    appletSource += "<td id='" + appletDomElementId + "_deployed'></td>";
    if (allowedToUpdateScore) {
        // start of save/reset buttons
        appletSource += "<td style='vertical-align:middle;'>";
        appletSource += "<button id=\"saveNucleiCountButtonId\" type=\"button\"></button>";
        appletSource += "<br><br>"
        appletSource += "<button id=\"resetCounterButtonId\" type=\"button\"></button>";
        appletSource += "</td>";
        // end of save/reset buttons
    }
    appletSource += "</tr>"
    appletSource += "</table>";

    if (allowedToUpdateScore) {
        appletSource += "<br>";
        appletSource += "Please enter any comments in the box below (optional)<br>";
        appletSource += "<textarea id='inputComment' name='inputComment' dojoType='dijit.form.Textarea' style='width:30em; border-color:black;'>" + escape_javascript_for_html(comment) + "</textarea>";
        appletSource += "<br><br>";
        appletSource += "<button id=\"doneWithThisFieldButtonId\" type=\"button\"></button>";
        appletSource += "<div style=\"display: inline; float: right\"><button id=\"goBackButtonId\" type=\"button\"></button></div>";
    } else {
        appletSource += "<br><br>";
        appletSource += "User's comment (on this field):<br>";
        appletSource += "<textarea id='inputComment' dojoType='dijit.form.Textarea' style='width:30em;' disabled='disabled'>" + escape_javascript_for_html(comment) + "</textarea>";
    }

    if (typeof nucleiCounter === 'object' || typeof nucleiCounter == 'function') {
        // nuclei counter loaded already ... update the nuclei selection, do not reload
        // update comment
        dojo.byId("inputComment").value = escape_javascript_for_html(comment);
        if (allowedToUpdateScore) {
            // if allow to update ... disable keyStrokeListener first
            dojo.byId("keyStrokeListener").disabled = "disabled";
            dojo.byId("keyStrokeListener").placeholder = "please wait ...";
        }
        document.getElementById("scoringInstruction").innerHTML = scoringInstruction;
        nucleiCounter.reinit(nucleiSelectionParamString, !allowedToUpdateScore);
    }
    if (allowedToUpdateScore) {
        dojo.ready(function () {
            drawCounterButtons();
            prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        });
        if (dijit.byId("doneWithThisFieldButtonId") != null) {
            prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        }
    }
}

/**
 * define action to perform if/when doneWithThisField button is pressed
 * and NOT all fields are done
 * 
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl
 * @param {type} nextUrl
 * @returns {undefined}
 */
function doneWithThisFieldAction(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    // save nuclei and ask user to go to the next field
    if (typeof(_tempSuppressSaveOkMsg) !== 'undefined') {
        _tempSuppressSaveOkMsg = true; // suppress nuclei save ok message
    }
    nucleiCounter.uploadNucleiSelection().then(function () {
        if (!_tempSaveStatusOK) {
            alert("ERROR. failed to save nuclei count");
        } else {
            // set scoring date on whole_section_region_scoring object 
            // and reload page to prompt user to select next field to score
            finishedScoringCurrentField(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
        }
    }); // nucleiCounter.uploadNucleiSelection().then(function () {
}

/**
 * draw or destroy then redraw the buttons associated with the counter
 * i.e. save, reset, back done
 * 
 * if button exist already, do not redraw!!!
 * 
 * @returns {undefined}
 */
function drawCounterButtons() {
    if (dijit.byId("saveNucleiCountButtonId") == null) {
        new dijit.form.Button({
            label: "SAVE nuclei count",
            title: "click me to save nuclei counts",
            onClick: function () {
                nucleiCounter.uploadNucleiSelection();
                // since nucleiCounter.uploadNucleiSelection is a ajax function call 
                // will not be able to check status here ... leave it for the 
                // loading function ... see function uploadNucleiSelection(nucleiSelectionParamString) implemented in gsp file
            }
        },
                "saveNucleiCountButtonId");
    }

    if (dijit.byId("resetCounterButtonId") == null) {
        new dijit.form.Button({
            label: "RESET counter",
            title: "click me to reset cell counter",
            onClick: function () {
                return resetCellCounter(); // defined in scoring_session.js
            }
        },
                "resetCounterButtonId");
    }

    if (dijit.byId("goBackButtonId") == null) {
        new dijit.form.Button({
            label: "BACK",
            title: "click me to go back to previous step i.e. select fields",
            onClick: function () {
                go_back(); // defined in score.gsp
                return false;
            }
        },
                "goBackButtonId");
    }

    if (dijit.byId("doneWithThisFieldButtonId") == null) {
        // details e.g. call back function will be added later
        new dijit.form.Button({}, "doneWithThisFieldButtonId");
    }
}

/**
 * display nuclie counter applet on the specified whole_section_region_scoring ...
 * - need ajaxUrl ... therefore, need a version defined on gsp with the ajaxUrl defined
 * 
 * @requires whole_section_region_scoring_id ... defined in score.gsp
 * @requires {array} nuclei_selection_notification_nuclei_count_array
 * @requires {array} nuclei_selection_notification_message_array
 * 
 * @param {type} appletDomElementId
 * @param {type} baseUrl
 * @param {type} allowedToUpdateScore
 * @param {type} ajaxGetWhole_section_region_scoringUrl
 * @param {type} x
 * @param {type} y
 * @param {type} diameter
 * @param {type} ki67state
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}"
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}"
 * 
 * NOTE: expect to update a GLOBAL variable call whole_sectin_region_scoring_id
 */
function displayNucleiCounterAppletBasedOnWhole_section_region_scoringCoordinatesNeedAjaxUrl(
        appletDomElementId,
        baseUrl,
        allowedToUpdateScore,
        ajaxGetWhole_section_region_scoringUrl,
        x,
        y,
        diameter,
        ki67state,
        ajaxSetWhole_section_region_scoringScoring_dateUrl,
        ajaxSetWhole_section_scoringScoring_dateUrl,
        nextUrl
        ) {
    // ajax ask for whole_section_region_scoring id and nucleiSelectionParamString
    var requestUrl = ajaxGetWhole_section_region_scoringUrl + "?" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_X + "=" + x + "&" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_Y + "=" + y + "&" +
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_DIAMETER + "=" + diameter + "&" + 
            HTML_PARAM_NAME_SCORING_SESSION_WHOLE_SECTION_REGION_SCORING_KI67STATE + "=" + ki67state;

    require(["dojo/_base/xhr"], function (xhr) {
        xhr.get({
            url: requestUrl,
            handleAs: "json",
            load: function (e) {
                if (e === null) {
                    alert("Error occurred. Failed to retrieve whole section region scoring record.")
                } else {
                    var response = e; // e is a JSON
                    whole_section_region_scoring_id = response.items[0].id; // defined in score.gsp
                    var nucleiSelectionParamString = response.items[0].nucleiSelectionParamString;
                    var ihc_score_category_name = response.items[0].ihc_score_category_name;
                    var comment = response.items[0].comment;
                    nuclei_selection_notification_nuclei_count_array[0] = response.items[0].nuclei_selection_notification_nuclei_count;
                    nuclei_selection_notification_message_array[0] = response.items[0].nuclei_selection_notification_message;
                    var numNucleiToCount = response.items[0].nuclei_selection_notification_nuclei_count;
                    // (re-)activate nuclei counter applet!!!
                    _tempFirstTimeLoading = true;
                    displayNucleiCounterAppletBasedOnNucleiSelectionParamString(
                            appletDomElementId,
                            baseUrl,
                            allowedToUpdateScore,
                            nucleiSelectionParamString,
                            ihc_score_category_name,
                            numNucleiToCount,
                            comment,
                            ajaxSetWhole_section_region_scoringScoring_dateUrl + "/" + whole_section_region_scoring_id, // NOTE: add whole_section_region_scoring_id!!!
                            ajaxSetWhole_section_scoringScoring_dateUrl,
                            nextUrl
                            );
                    // show pop message to give some instructions to user regarding how many nuclei he/she should count
                    if (allowedToUpdateScore) {
                        nucleiCounter.updateNucleiCountOnBrowser(); // initialize any help elements (e.g. cell counter input text box) on html page
                        //alert((numNucleiToCount === 500 ? "This is a HOT-SPOT.  " : "") + "Please count up to " + numNucleiToCount + " invasive tumour nuclei.\n\nNote: please count nuclei by viewing the glass slide (via microscope)\nand not the zoomable online image.");
                        showMessageDialog(
                                "Please count up to " + numNucleiToCount + " invasive tumour nuclei",
                                (numNucleiToCount === 500 ? "This is a HOT-SPOT.  " : "") + "Please count up to " + numNucleiToCount + " invasive tumour nuclei. Note: please count nuclei by viewing the glass slide (via microscope) and not the zoomable online image.");
                    }
                }
                return;
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.get
    }); // function (xhr)
}


/**
 *  helper function to the function to indicate finished scoring the current field
 *  
 *  - reason for a helper function ... need to get ajaxUrl
 *  - this function updates number_of_remaining_fields ... defined in score.gsp
 *  
 *  if ajaxSetWhole_section_scoringScoring_dateUrl==null, means scoring HOT-SPOT
 *  
 * @requires number_of_remaining_fields
 * @requires fieldSelector - the applet
 * 
 * @param {type} ajaxSetWhole_section_region_scoringScoring_dateUrl = "${createLink(controller:"whole_section_region_scorings", action:"set_scoring_date")}"
 * @param {type} ajaxSetWhole_section_scoringScoring_dateUrl = "${createLink(controller:"whole_section_scorings", action:"set_scoring_date")}"
 * @param {type} nextUrl = "${createLink(controller:"scoring_sessions", action:"score", id:scoring_sessionInstance.getId())}"
 * @returns {undefined}
 */
function finishedScoringCurrentField(ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl) {
    require(["dojo/_base/xhr"], function (xhr) {
        xhr.get({
            url: ajaxSetWhole_section_region_scoringScoring_dateUrl,
            handleAs: "text",
            load: function (e) {
                if (e === AJAX_RESPONSE_ERROR) {
                    alert("Error occurred. Failed to set scoring date on whole section region scoring record.");
                } else {
                    number_of_remaining_fields = e;
                    if (number_of_remaining_fields > 0) {
                        showMessageDialog("Please select another field","Please select another field to score ("+number_of_remaining_fields+" remaining).");
                    }
                    window[KI67_QC_PHASE3_FIELD_SELECTOR_ID].setCurrentFieldOfViewToScoringStateScored();
                    // update done button (doneWithThisFieldButtonId)
                    prepareSaveButton(number_of_remaining_fields, ajaxSetWhole_section_region_scoringScoring_dateUrl, ajaxSetWhole_section_scoringScoring_dateUrl, nextUrl);
                }
                return;
            },
            onError: function (e) {
                alert("Error occurred. Failed to retrieve whole section region scoring record. Error message: " + e);
            }
        }); // xhr.get
    }); // function (xhr)
}

/**
 * function to go back to previous step i.e. count nuclei -> select fields 
 * 
 * @param {type} goBackUrl - go back on step
 * @param {type} nextUrl
 * @returns {undefined}
 */
function ki67_qc_phase3_go_back_one_step(goBackUrl, nextUrl) {
    showConfirmDialog(
            "Please confirm ...",
            "Going back to previous step MAY result in lost of ALL nuclei counts.  OK to continue?",
            "Yes", // confirm
            "No", // decline
            function () {
                showWaitDialog();
                require(["dojo/_base/xhr"], function (xhr) {
                    xhr.get({
                        url: goBackUrl,
                        handleAs: "text",
                        load: function (e) {
                            if (e === AJAX_RESPONSE_ERROR) {
                                alert("Error occurred. Failed to save (whole section scoring) record to database.");
                            } else if (e === AJAX_RESPONSE_NA) {
                                alert("Error occurred. Option not available.");
                            } else {
                                // do next scoring
                                window.location.href = nextUrl;
                            }
                            return;
                        },
                        onError: function (e) {
                            alert("Error occurred. Failed to save (whole section scoring) record to database. Error message: " + e);
                        }
                    }); // xhr.get
                }); // function (xhr)
            }
    );
    return false; // prevent page refresh
}
;