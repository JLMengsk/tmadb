
<%@ page import="ca.ubc.gpec.tmadb.Tma_arrays" %>
<%@ page import="ca.ubc.gpec.tmadb.Users" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_arrays.label', default: 'TMA array')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
        <style type="text/css">
        @import "https://ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojox/grid/resources/claroGrid.css";
        /*Grid needs an explicit height by default*/
        #tma_slicesGrid {
            height: 20em;
        }
        </style>
    </head>
    <body>
        <div class="body">
            <div style='display: inline'>
                <h1></h1>
                <g:link controller="tma_projects" action="show" id="${tma_arraysInstance?.tma_project?.id}" title="click me to view TMA project">TMA project: ${tma_arraysInstance?.tma_project?.getName()}</g:link>
                &#8594;
                <h1 style='display: inline'>array version ${tma_arraysInstance.getArray_version()}</h1>
            </div>
            <g:showFlashMessage />
            <div class="dialog">
                <h2>TMA slices available to ${user.getName()} (n=<div id="slide_count" style='display: inline'></div>)</h2>
                <div id="tma_slicesGrid">
                ... loading ... please wait</div>
        </div>
    </div>

<asset:script type="text/javascript">
    require(["dojo/_base/xhr"], function (xhr) {

    //var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"tma_slices", action:"ajaxGetAvailableTma_slices", params:[tma_arrays_id:tma_arraysInstance.id])}",
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
    //waitDialogObj.destroy();
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
