
<%@ page import="ca.ubc.gpec.tmadb.Tma_projects" %>
<%@ page import="ca.ubc.gpec.tmadb.Users" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_projects.label', default: 'TMA project')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
    </head>
    <body>
        <div class="body">
            <h1>TMA project: ${fieldValue(bean: tma_projectsInstance, field: "name")}</h1>
            <g:showFlashMessage />
            <div class="dialog">
                <p>${tma_projectsInstance.getDescription()}</p>

                <g:if test="${tma_projectsInstance.getBuilt_by()!=null}">
                    <p><i>Built by: ${fieldValue(bean: tma_projectsInstance, field: "built_by")}</i></p>
                </g:if>

                <h2>TMA slices available to ${user.getName()} (n=<div id="slide_count" style='display: inline'></div>)</h2>
                <div id="tma_slicesGrid" 
                style="width: ${ViewConstants.TMA_SLICES_GRID_WIDTH}px; height: ${ViewConstants.TMA_SLICES_GRID_HEIGHT}px;">
                ... loading ... please wait</div>

        </div>
    </div>


<asset:script type="text/javascript" disposition="head">
    require(["dojo/_base/xhr"], function (xhr) {

    var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"tma_slices", action:"ajaxGetAvailableTma_slices", params:[tma_projects_id:tma_projectsInstance.id])}",
    handleAs:"json",
    load: function(e){
    buildTma_slicesBrowserTable(
    "<g:createLink controller="tma_slices"   action="show"/>",
    "<g:createLink controller="tma_blocks"   action="show"/>",
    "<g:createLink controller="tma_arrays"   action="show"/>",
    "<g:createLink controller="tma_projects" action="show"/>",
    "<g:createLink controller="biomarkers"   action="show"/>",
    e,
    "tma_slicesGrid","slide_count");
    resizeDojoDataGrid_table('tma_slicesGrid');
    waitDialogObj.destroy();
    },onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE }");}
    }); // xhr.get

    window.onresize=function(){
    setInitialPageBodyHeight();
    setInitialFooterPosition();
    resizeDojoDataGrid_table('tma_slicesGrid'); // set window resize event handler
    }

    }); // function (xhr)
</asset:script>
</body>
</html>
