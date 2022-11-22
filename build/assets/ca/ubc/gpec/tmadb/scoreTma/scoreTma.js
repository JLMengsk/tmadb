var process = process || {env: {NODE_ENV: "development"}};
/* 
 * helper functions related to scoreTma
 */

/* 
 * table to browse the available scoring sessions
 * 
 * targetShowScoring_sessions: link to <g:createLink controller='scoring_sessions' action='score'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildAvailableScoring_sessionsBrowserTable(
        targetShowScoring_sessions,
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetShowScoring_sessions + "/" + items[1] + "' title='" + items2[1] + "; Please click me to view available actions on this scoring session.'>" + items2[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var scoring_sessions;
    if (ajaxResponse === null) {
        scoring_sessions = eval("([])"); // generate empty JSON
    } else {
        scoring_sessions = ajaxResponse; // ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (scoring_sessions) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessions});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            structure: [
                {name: "Assigned date", field: "start_date", width: "150px"},
                {name: "Name", field: "name", width: "550px", formatter: format_name},
                {name: "Status", field: "status", width: "100px"}
            ],
            store: dataStore,
            autoWidth: true,
            loadingMessage: "... loading ... please wait"

        }, htmlTagName);
    }
    grid.startup();
    return grid;
}

/* 
 * table to browse submitted scoring sessions (for score audit)
 * 
 * targetReportScoring_sessions: link to <g:createLink controller='scoring_sessions' action='report'/>
 * targetDownloadScoring_sessions: link to <g:createLink controller='downloadData' action='downloadScoringSessionScores'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildSubmittedScoring_sessionsBrowserTable(
        targetReportScoring_sessions,
        targetDownloadScoring_sessions,
        ajaxResponse,
        htmlTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetReportScoring_sessions + "/" + items[1] + "' title='" + items2[1] + "; Please click me to view scoring session report and available actions on this scoring session.'>" + items2[0] + "</a>";
    }
    function format_download(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetDownloadScoring_sessions + "/" + items[1] + "' title='Please click me to download scores in tab-delimited text format.'>download</a>";
    }
    // The response comes back as a bunch-o-JSON
    var scoring_sessions;
    if (ajaxResponse == null) {
        scoring_sessions = eval("([])") // evaluate JSON
    } else {
        scoring_sessions = eval("(" + ajaxResponse.responseText + ")")  // evaluate JSON
    }
    // width of table: 620 with right margin of 10px
    if (scoring_sessions) {
        dataStore = new dojo.data.ItemFileReadStore({data: scoring_sessions});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Scorer", field: "scorer", width: "150px"},
                {name: "Scoring session", field: "name", width: "450px", formatter: format_name},
                {name: "Completed date", field: "scoring_date", width: "100px"},
                {name: "Download scores", field: "name", width: "100px", formatter: format_download}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();
    return grid;
}
