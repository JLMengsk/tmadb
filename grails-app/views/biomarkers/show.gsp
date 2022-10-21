
<%@ page import="ca.ubc.gpec.tmadb.Biomarkers" %>
<%@ page import="ca.ubc.gpec.tmadb.Staining_details" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'biomarkers.label', default: 'Biomarker')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
    </head>
    <body>
        <div class="body">
            <h1>Biomarker: ${fieldValue(bean: biomarkersInstance, field: "name")}
                <g:if test="${tma_arraysInstance != null}">
                    on <g:link controller="tma_arrays" action="show" id="${tma_arraysInstance.id}" title="${tma_arraysInstance.getDescription()}">TMA: ${tma_arraysInstance.toString()}</g:link>
                </g:if>
                <g:elseif test="${tma_projectsInstance != null}">
                    on <g:link controller="tma_projects" action="show" id="${tma_projectsInstance.id}" title="${tma_projectsInstance.getDescription()}">TMA: ${tma_projectsInstance.getName()}</g:link>
                </g:elseif>
                <g:displayAvailableBiomarkerActions id="${biomarkersInstance?.id}"/>
            </h1>
            <g:showFlashMessage />
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="biomarkers.biomarker_type.label" default="Biomarker type" /></td><td valign="top" class="value"><g:link controller="biomarker_types" action="show" id="${biomarkersInstance?.biomarker_type?.id}">${biomarkersInstance?.biomarker_type?.encodeAsHTML()}</g:link></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name"><g:message code="biomarkers.description.label" default="Description" /></td><td valign="top" class="value">${fieldValue(bean: biomarkersInstance, field: "description")}</td>
                        </tr>
                    </tbody>
                </table>
                <h2>Available staining record(s)</h2>
                <select onchange="window.location = this.value">
                    <g:each in="${availableStainingDetails}" var="s">
                        <option value="${createLink(controller:"staining_details", action:"show", id:(s.id))}" title="${s?.showStainingNames()}">${s?.toString()}</option>
                    </g:each>
                </select>
                <g:if test="${Staining_details.showCanCreate(session)}">
                    <br>
                    <button dojoType="dijit.form.Button" type="submit" name="submitButton" title="click me to add staining record" onclick=window.location="${createLink(controller:'staining_details', action: 'create')}">add staining record</button>
                </g:if>
                <h2>TMA slices available to ${usersInstance.getName()} (n=<div id="slide_count" style='display: inline'></div>)</h2>
                <div id="tma_slicesGrid" 
                style="width: ${ViewConstants.TMA_SLICES_GRID_WIDTH}px; height: ${ViewConstants.TMA_SLICES_GRID_HEIGHT}px;">
                ... loading ... please wait</div>
        </div> <!-- dialog -->
    </div> <!-- body -->

<asset:script type="text/javascript" disposition="head">
    require(["dojo/_base/xhr"], function (xhr) {

    var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"tma_slices", action:"ajaxGetAvailableTma_slices", params:[biomarkers_id:biomarkersInstance?.id])}",
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
