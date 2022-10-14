/* 
 * table to browse the available tma projects
 * 
 * ajaxResponse: response from ajax call
 * htmlTagName: name of html tag
 */
function buildTma_projectsBrowserTable(
        targetShowTma_projects,
        ajaxResponse,
        htmlTagName,
        projectCountTagName) {
    var dataStore;
    var grid;
    function format_name(item) {
        var items = item.split(AJAX_RESPONSE_DELIMITER);
        return "<a href='" + targetShowTma_projects + "/" + items[1] + "' title='click me to view TMA project details'>" + items[0] + "</a>";
    }

    // The response comes back as a bunch-o-JSON
    var tma_projects;
    if (ajaxResponse === null) {
        tma_projects = eval("([])") // empty JSON
    } else {
        tma_projects = ajaxResponse;
    }
    // width of table: 800 with right margin of 10px
    if (tma_projects) {
        dataStore = new dojo.data.ItemFileReadStore({data: tma_projects});
        // default ... not doing anything special
        grid = new dojox.grid.DataGrid({
            store: dataStore,
            structure: [
                {name: "Name", field: "name", width: "80px", formatter: format_name},
                {name: "Built by", field: "built_by", width: "150px"},
                {name: "Description", field: "description", width: "350px"},
                {name: "Core ID name", field: "core_id_name", width: "80px"}
            ],
            autoWidth: true,
            loadingMessage: "... loading ... please wait"
        }, htmlTagName);
    }
    grid.startup();

    // set the project count ...
    var numProjects = tma_projects.items.length;
    var projectCountNode = document.createElement("a");
    projectCountNode.setAttribute("title", "this table contains " + numProjects + " TMA project" + (numProjects <= 1 ? "" : "s"));
    projectCountNode.appendChild(document.createTextNode(numProjects));
    dojo.byId(projectCountTagName).appendChild(projectCountNode);

    return grid;
}
