/* 
 * some helper functions for scoring_sessions
 */

/**
 * timer
 * @returns {undefined}
 */
function timeMsg()
{
    var t = setTimeout("alertMsg()", (SCORING_SESSION_TIMEOUT_IN_SECONDS * 1000 - 60000));
}

/**
 * show warning message when timer is up
 * @returns {undefined}
 */
function alertMsg()
{
    var now = new Date();
    showMessageDialog(
            "Warning",
            now + ": Session will time out in 1 minute.  Please save your score NOW.  Warning: if save has not occurred after session time out, all scores since last save will be lost.  No further warning messages will be shown.  Please save your score NOW."
            );
}
;


/**
 * update nuclei selection count, also check to see if need to display any notification message(s)
 * @param {type} numPos
 * @param {type} numNeg
 * @param {type} numTotal
 * @param {type} isScoringTypeNucleiCount
 * @param {type} nuclei_selection_notification_nuclei_count_array
 * @param {type} nuclei_selection_notification_message_array
 * @returns {undefined}
 * 
 * WARNING!!! assume length of nuclei_selection_notification_nuclei_count_array and nuclei_selection_notification_message_array are the same!!!
 */
function updateNucleiSelectionCountHelper(numPos, numNeg, numTotal, isScoringTypeNucleiCount, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array) {
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_ID).innerHTML = numPos;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_ID).innerHTML = numNeg;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_ID).innerHTML = numTotal;
    if (isScoringTypeNucleiCount) {
        // ONLY nuclei selection will need to show bottom nuclei count
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID).innerHTML = numPos;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID).innerHTML = numNeg;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID).innerHTML = numTotal;
    }
    for (var i = 0; i < nuclei_selection_notification_nuclei_count_array.length; i++) {
        if (numTotal == nuclei_selection_notification_nuclei_count_array[i] && (!_tempResetInProgress) && (!_tempFirstTimeLoading)) {
            try {
                nucleiCounter.playNotificationSound();
            } catch (err) {
            } // just silently ignore if failed to play notification sound
            var showMessageDialog = false; // if message dialog is showing already, do not reshow it
            if (message_dialog_object === null) {
                showMessageDialog = true;
            } else if (message_dialog_object.title !== "Please note ...") {
                showMessageDialog = true;
            }
            if (showMessageDialog) {
                showMessageDialogWithCallBack(
                        "Please note ...",
                        nuclei_selection_notification_message_array[i],
                        function () {
                            nucleiCounter.uploadNucleiSelection(); // upload now ... since upload takes a long time
                        });
            }
        }
    }
    _tempFirstTimeLoading = false; // WATCH OUT!!! make sure this is indeed the first time loaded;
}

/**
 * update nuclei selection count, also check to see if need to display any notification message(s)
 * 
 * special version for Ki67-QC phase 3 
 * 
 * @param {type} numPos
 * @param {type} numNeg
 * @param {type} numTotal
 * @param {type} isScoringTypeNucleiCount
 * @param {type} nuclei_selection_notification_nuclei_count_array
 * @param {type} nuclei_selection_notification_message_array
 * @returns {undefined}
 * 
 * WARNING!!! assume length of nuclei_selection_notification_nuclei_count_array and nuclei_selection_notification_message_array are the same!!!
 */
function updateNucleiSelectionCountHelperKi67QcPhase3(numPos, numNeg, numTotal, isScoringTypeNucleiCount, nuclei_selection_notification_nuclei_count_array, nuclei_selection_notification_message_array) {
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_ID).innerHTML = numPos;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_ID).innerHTML = numNeg;
    document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_ID).innerHTML = numTotal;
    if (isScoringTypeNucleiCount) {
        // ONLY nuclei selection will need to show bottom nuclei count
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_POS_BOTTOM_ID).innerHTML = numPos;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_NEG_BOTTOM_ID).innerHTML = numNeg;
        document.getElementById(NUCLEI_COUNT_STATUS_NUM_TOTAL_BOTTOM_ID).innerHTML = numTotal;
    }
    for (var i = 0; i < nuclei_selection_notification_nuclei_count_array.length; i++) {
        if (numTotal >= nuclei_selection_notification_nuclei_count_array[i] && (!_tempResetInProgress) && (!_tempFirstTimeLoading)) {
            try {
                nucleiCounter.playNotificationSound();
            } catch (err) {
            } // just silently ignore if failed to play notification sound
            _tempResetInProgress = true
            while (numTotal > nuclei_selection_notification_nuclei_count_array[i]) {
                // decrement nuclei counter back to numTotal
                nucleiCounter.undoNucleiCount();
                numTotal--;
            }
            _tempResetInProgress = false;
            var showMessageDialog = false; // if message dialog is showing already, do not reshow it
            if (message_dialog_object === null) {
                showMessageDialog = true;
            } else if (message_dialog_object.title !== "Please note ...") {
                showMessageDialog = true;
            }
            if (showMessageDialog) {
                showMessageDialogWithCallBack(
                        "Please note ...",
                        nuclei_selection_notification_message_array[i],
                        function () {
                            nucleiCounter.uploadNucleiSelection(); // upload now ... since upload takes a long time
                        }
                );
            }
        }
    }
    _tempFirstTimeLoading = false; // WATCH OUT!!! make sure this is indeed the first time loaded;
}

/**
 * check to see if save (i.e. udpate) nuclei selection is successful
 * - this is a function to deal with the response of an ajax request to save nuclei selection
 * - show MESSAGE DIALOG: Nuclei selection saved successfully ...
 * 
 * @param {type} e
 * @param {type} curr_location
 * @returns {undefined}
 */
function checkUpdateNucleiSelection(e, curr_location) {
    _tempSaveStatusOK = false;
    if (e !== "SAVE SUCCESSFUL" && e !== "REMOVE SUCCESSFUL") {
        showMessageDialog(
                "ERROR in saving nuclei selection",
                "ERROR in saving nuclei selection.  Nuclei selection NOT SAVED.  This webpage will refresh/reload now.  You may continue to score after the webpage refresh.  We apologize for the inconvenience.");
        window.location = curr_location;
    } else {
        _tempSaveStatusOK = true;
    }
}

/**
 * send cell counter key stroke to applet
 * 
 * @param {type} e
 * @returns {Boolean}
 */
function sendCellCounterKeyStrokeToApplet(e) {
    var x;

    // for firefox, event needs to be passed in i.e. input param 'e'
    var evt = e || window.event;

    if (window.event) // IE8 and earlier
    {
        x = evt.keyCode;
    } else if (evt.which) // IE9/Firefox/Chrome/Opera/Safari
    {
        x = evt.which;
    }
    var keychar = String.fromCharCode(x);
    document.getElementById('keyStrokeListener').value = "";
    if (keychar == NUCLEI_COUNT_KEY_POSITIVE_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_POSITIVE_UPPER_CASE) {
        nucleiCounter.addPositiveNucleiCount();
    } else if (keychar == NUCLEI_COUNT_KEY_NEGATIVE_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_NEGATIVE_UPPER_CASE) {
        nucleiCounter.addNegativeNucleiCount();
    } else if (keychar == NUCLEI_COUNT_KEY_UNDO_LOWER_CASE || keychar == NUCLEI_COUNT_KEY_UNDO_UPPER_CASE) {
        nucleiCounter.undoNucleiCount();
    }

    if (typeof evt.stopPropagation != "undefined") {
        evt.stopPropagation();
    } else {
        evt.cancelBubble = true;
    }

    return false;
}

/**
 * reset cell counter
 * 
 * @returns {Boolean}
 */
function resetCellCounter() {
    showConfirmDialog(
            "Please confirm ...",
            "RESET cell counter?",
            "Yes", // confirm
            "No", // decline
            function () {
                _tempResetInProgress = true; // to suppress notification messages
                // reset cell counter by doing undocell count for numTotal times
                while (parseInt(document.getElementById('numTotal').innerHTML) > 0) {
                    nucleiCounter.undoNucleiCount();
                }
                _tempResetInProgress = false;
                nucleiCounter.uploadNucleiSelection(); // do a save first, since save takes a long time
            }
    );
    return false; // since is this called by a href/onclick element
}

/**
 * show wait message at html tag
 * 
 * @param {type} id - html tag id
 * @param {type} msg
 * @returns {Boolean}
 */
function showWaitMessage(id, msg) {
    var e = document.getElementById(id);
    e.value = msg;
    return true;
}

/**
 * close the H&E image window
 * @param {type} heWindowHandle H&E window name
 * @returns {undefined}
 */
function closeHeWindow(heWindowHandle) {
    if (heWindowHandle) {
        heWindowHandle.close();
    }
}

/**
 *  this to do before changing the main window 
 *  - close H&E window if opened
 *  
 * @param {type} windowHandles - array of window handles
 * @returns true
 */
function cleanUpBeforeChangeMainWindow(windowHandles) {
    for (var i = 0; i < windowHandles.length; i++) {
        closeHeWindow(windowHandles[i]);
    }
    true;
}

/**
 * look though the fields (assume input textboxes) with ids in field_ids
 * and sum them up.  If sum is not equal to required_sum, popup error message
 * err_msg and return false.  otherwise, return true
 * 
 * @param {type} field_ids
 * @param {type} required_sum
 * @param {type} err_msg
 * @returns {undefined}
 */
function checkSumOfField(field_ids, required_sum, err_msg) {
    var curr_sum = 0;
    for (var i = 0; i < field_ids.length; i++) {
        curr_sum = curr_sum + parseFloat(document.getElementById(field_ids[i]).value)
    }
    if (curr_sum !== required_sum) {
        showMessageDialog("Error", err_msg);
        return false;
    } else {
        return true;
    }
}

/* 
 * table to browse the available scoring sessions
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildScoring_sessionReportTable(
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;

    function format_description(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='" + items[2] + "'>" + items[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var scoring_sessionReportTable;
    if (ajaxResponse === null) {
        scoring_sessionReportTable = eval("([])"); // generate empty JSON
    } else {
        scoring_sessionReportTable = ajaxResponse; // ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px  
    if (scoring_sessionReportTable) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessionReportTable});
        // default ... not doing anything special
        if (scoring_sessionReportTable.scoring_session_scoring_type === SCORING_SESSION_SCORING_TYPE_TMA_SCORING) {
            if (scoring_sessionReportTable.references_available) {
                grid = new dojox.grid.DataGrid({
                    store: dataStore,
                    structure: [
                        {name: "TMA core ID", field: "core_id", width: "50px"},
                        {name: "Description", field: "description", width: "250px", formatter: format_description},
                        {name: "Score type", field: "type", width: "80px"},
                        {name: "Score", field: "score", width: "250px"},
                        {name: "Scoring date/time", field: "scoring_date", width: "100px"},
                        {name: "Reference score", field: "reference_score", width: "70px"}
                    ],
                    autoWidth: true,
                    loadingMessage: "... loading ... please wait"
                }, htmlTagName);
            } else {
                grid = new dojox.grid.DataGrid({
                    store: dataStore,
                    structure: [
                        {name: "TMA core ID", field: "core_id", width: "50px"},
                        {name: "Description", field: "description", width: "285px", formatter: format_description},
                        {name: "Score type", field: "type", width: "80px"},
                        {name: "Score", field: "score", width: "285px"},
                        {name: "Scoring date/time", field: "scoring_date", width: "100px"}
                    ],
                    autoWidth: true,
                    loadingMessage: "... loading ... please wait"
                }, htmlTagName);
            }
        } else if (scoring_sessionReportTable.scoring_session_scoring_type === SCORING_SESSION_SCORING_TYPE_WHOLE_SECTION_SCORING) {
            grid = new dojox.grid.DataGrid({
                store: dataStore,
                structure: [
                    {name: "Specimen #", field: "whole_section_specimen_num", width: "100px"},
                    {name: "Description", field: "description", width: "220px", formatter: format_description},
                    {name: "Score type", field: "type", width: "80px"},
                    {name: "Score", field: "score", width: "300px"},
                    {name: "Scoring date/time", field: "scoring_date", width: "100px"}
                ],
                autoWidth: true,
                loadingMessage: "... loading ... please wait"
            }, htmlTagName);
        }
    }
    grid.startup();
    return grid;
}



