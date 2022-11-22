var process = process || {env: {NODE_ENV: "development"}};
/* 
 * table to browse the available whole section slices
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 * slideCountTagName: name of the slide_count div
 */
function buildWhole_section_slicesBrowserTable(
        ajaxResponse,
        htmlTagName,
        slideCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view whole section slice details'>" + items[0] + "</a>";
    }
    function format_block(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view block details'>" + items[0] + "</a>";
    }
    function format_biomarker(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + items[1] + "' title='click me to view biomarker details'>" + items[0] + "</a>";
    }
    function format_name_w_button(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        var button = new dijit.form.Button({
            label: "view",
            title: "click me to view whole section slice (" + items[0] + ")",
            onClick: function() {
                // generate preview window
                window.location.href = items[1];
            }
        });
        return button;
    }

    // The response comes back as a bunch-o-JSON
    var whole_section_slices;
    if (ajaxResponse == null) {
        whole_section_slices = eval("([])")	// evaluate JSON
    } else {
        whole_section_slices = ajaxResponse;	// ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (whole_section_slices) {
        dataStore = new dojo.data.ItemFileReadStore({data: whole_section_slices});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Block", field: "block", width: "400px", formatter: format_block},
                {name: "Biomarker", field: "biomarker", width: "280px", formatter: format_biomarker},
                {name: "Slice", field: "name", width: "40px", formatter: format_name},
                {name: "View slice", field: "name", width: "80px", formatter: format_name_w_button}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the slide count ...
    var numSlides = whole_section_slices.items.length;
    var slideCountNode = document.createElement("a");
    slideCountNode.setAttribute("title", "this table contains " + numSlides + " slice" + (numSlides <= 1 ? "" : "s"));
    slideCountNode.appendChild(document.createTextNode(numSlides));
    dojo.byId(slideCountTagName).appendChild(slideCountNode);

    return grid;
}

/* 
 * select - whole section slices
 * 
 * ajaxResponse: response from ajax call
 * currSelectId: id of currently selected whole_section_slice
 * htmlTagName: name of html tag
 */
function buildWhole_section_slicesSelect(
        ajaxResponse,
        currSelectId,
        htmlTagName) {

    // The response comes back as a bunch-o-JSON
    var whole_section_slices;
    if (ajaxResponse === null) {
        whole_section_slices = eval("([])"); // generate empty JSON
    } else {
        whole_section_slices = ajaxResponse;	// ajaxResponse is a JSON
    }
    // width of table: 800 with right margin of 10px
    if (whole_section_slices) {
        // build select
        var rselect = document.getElementById(htmlTagName)
        for (var i = 0; i < whole_section_slices.items.length; i++) {
            var whole_section_slice = whole_section_slices.items[i]
            var opt = document.createElement('option');
            var tempArr = whole_section_slice.name.split(AJAX_RESPONSE_DELIMITER);
            opt.text = whole_section_slice.block.split(AJAX_RESPONSE_DELIMITER)[0] + " " + whole_section_slice.biomarker.split(AJAX_RESPONSE_DELIMITER)[0] + " slice:" + tempArr[0];
            opt.value = tempArr[1];
            opt.title = "click me to view whole section slice details";
            if (whole_section_slice.id === currSelectId) {
                opt.selected = true;
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
