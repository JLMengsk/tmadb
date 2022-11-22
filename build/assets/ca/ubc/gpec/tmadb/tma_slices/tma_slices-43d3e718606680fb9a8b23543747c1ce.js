var process = process || {env: {NODE_ENV: "development"}};
/* 
 * table to browse the available tma slices
 * 
 * targetShowTma_slices: link to <g:createLink controller='tma_slices' action='show'/>
 * targetShowTma_blocks: link to <g:createLink controller='tma_blocks' action='show'/>
 * targetShowTma_arrays: link to <g:createLink controller='tma_arrays' action='show'/>
 * targetShowTma_projects: link to <g:createLink controller='tma_projects' action='show'/>
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 * slideCountTagName: name of the slide_count div
 */
function buildTma_slicesBrowserTable(
        targetShowTma_slices,
        targetShowTma_blocks,
        targetShowTma_arrays,
        targetShowTma_projects,
        targetShowBiomarkers,
        ajaxResponse,
        htmlTagName,
        slideCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_slices + "/" + items[1] + "' title='click me to view TMA slice details'>" + items[0] + "</a>";
    }
    function format_tma_block(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_blocks + "/" + items[1] + "' title='click me to view TMA block details'>" + items[0] + "</a>";
    }
    function format_tma_array(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_arrays + "/" + items[1] + "' title='click me to view TMA array details'>" + items[0] + "</a>";
    }
    function format_tma_project(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var items2 = items[0].split(AJAX_RESPONSE_DELIMITER_2);
        return "<a href='" + targetShowTma_projects + "/" + items[1] + "' title='click me to view TMA project details'>" + items2[0] + " (" + items2[1] + ")</a>";
    }
    function format_biomarker(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowBiomarkers + "/" + items[1] + "' title='click me to view biomarker details'>" + items[0] + "</a>";
    }
    function format_name_w_button(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var button = new dijit.form.Button({
            label: "view",
            title: "click me to view TMA slice (" + items[0] + ")",
            onClick: function() {
                // generate preview window
                window.location.href = targetShowTma_slices + "/" + items[1];
            }
        });
        return button;
    }

    // The response comes back as a bunch-o-JSON
    var tma_slices;
    if (ajaxResponse == null) {
        tma_slices = eval("([])") // empty JSON
    } else {
        tma_slices = ajaxResponse;
    }
    // width of table: 800 with right margin of 10px
    if (tma_slices) {
        dataStore = new dojo.data.ItemFileReadStore({data: tma_slices});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "TMA project", field: "tma_project", width: "400px", formatter: format_tma_project},
                {name: "Version", field: "tma_array", width: "40px", formatter: format_tma_array},
                {name: "Block", field: "tma_block", width: "40px", formatter: format_tma_block},
                {name: "Biomarker", field: "biomarker", width: "200px", formatter: format_biomarker},
                {name: "Slice", field: "name", width: "40px", formatter: format_name},
                {name: "View slice", field: "name", width: "80px", formatter: format_name_w_button}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the slide count ...
    var numSlides = tma_slices.items.length;
    var slideCountNode = document.createElement("a");
    slideCountNode.setAttribute("title", "this table contains " + numSlides + " slice" + (numSlides <= 1 ? "" : "s"));
    slideCountNode.appendChild(document.createTextNode(numSlides));
    dojo.byId(slideCountTagName).appendChild(slideCountNode);

    return grid;
}


/* 
 * helper functions for tma_slices
 * @param e - ajax response
 * @param tma_result_names_id - current shown tma_result_names_id; tma_result_names_id=="" if display core ids
 * @param htmlTagId - html select tag id 
 * @param url to show tma_slices
 */
function updateTmaScorersInfos(e, tma_result_names_id, htmlTagId, showTma_slicesUrl) {
    // The response comes back as a bunch-o-JSON
    var tma_scorers_infos;
    if (e == null) {
        tma_scorers_infos = eval("([])"); // generate empty JSON
    } else {
        tma_scorers_infos = e;	// e is a JSON
    }

    if (tma_scorers_infos) {
        var rselect = document.getElementById(htmlTagId)

        // Clear all previous options
        var l = rselect.length

        while (l > 0) {
            l--
            rselect.remove(l)
        }

        // Rebuild the select
        // always add display core IDS as the first one
        var displayCoreIdOpt = document.createElement('option');
        displayCoreIdOpt.text = "Core IDs";
        displayCoreIdOpt.value = showTma_slicesUrl;
        try {
            rselect.add(displayCoreIdOpt, null) // standards compliant; doesn't work in IE
        }
        catch (ex) {
            rselect.add(displayCoreIdOpt) // IE only
        }

        for (var i = 0; i < tma_scorers_infos.length; i++) {
            var tma_scorers_info = tma_scorers_infos[i];
            var opt = document.createElement('option');
            var tma_scorer1_name = "";
            if (tma_scorers_info[1] != null) {
                tma_scorer1_name = tma_scorers_info[1].name;
            }
            var tma_scorer2_name = "";
            if (tma_scorers_info[2] != null) {
                tma_scorer2_name = tma_scorers_info[2].name;
            }
            var tma_scorer3_name = "";
            if (tma_scorers_info[3] != null) {
                tma_scorer3_name = tma_scorers_info[3].name;
            }
            var score_type = tma_scorers_info[5]
            if (!(typeof(score_type) == "string")) {
                score_type = tma_scorers_info[5].name;
            }

            opt.text = ""
            if (tma_scorers_info[1] != null) {
                opt.text = tma_scorer1_name;
            }
            if (tma_scorers_info[2] != null) {
                opt.text = opt.text + " / " + tma_scorer2_name;
            }
            if (tma_scorers_info[3] != null) {
                opt.text = opt.text + " / " + tma_scorer3_name;
            }
            opt.text = opt.text + " (scored on " + tma_scorers_info[6] + "; using " + score_type + ")";
            opt.value = showTma_slicesUrl + "?tma_result_names_id=" + tma_scorers_info[7]; // this is tma_result_names id !!!
            if (tma_result_names_id==tma_scorers_info[7]) {
                opt.selected = "selected";
            }
            try {
                rselect.add(opt, null) // standards compliant; doesn't work in IE
            }
            catch (ex) {
                rselect.add(opt) // IE only
            }
        }
    }
}





