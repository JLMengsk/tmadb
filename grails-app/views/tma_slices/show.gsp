
<%@ page import="ca.ubc.gpec.tmadb.Tma_slices"%>
<%@ page import="ca.ubc.gpec.tmadb.Tma_results"%>
<%@ page import="ca.ubc.gpec.tmadb.ScoreType"%>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_slices.label', default: 'Tma slice')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div style='display: inline'>
                <g:link controller="tma_projects" action="show" id="${tma_slicesInstance.getTma_block().getTma_array().getTma_project().getId()}" title="click me to view TMA project">TMA project: ${tma_slicesInstance.getTma_block().getTma_array().getTma_project().getName()}</g:link>
                &#8594;
                <g:link controller="tma_arrays" action="show" id="${tma_slicesInstance.getTma_block().getTma_array().getId()}" title="click me to view TMA array">array version ${tma_slicesInstance.getTma_block().getTma_array().getArray_version()}</g:link>
                &#8594;
                <g:link controller="tma_blocks" action="show" id="${tma_slicesInstance.getTma_block().getId()}" title="click me to view TMA block">block ${tma_slicesInstance.getTma_block().getName()}</g:link>
                &#8594;
                <h1 style='display: inline'>slice ${tma_slicesInstance.getName()}</h1>
            </div>
            <g:showFlashMessage />
            <div class="dialog">
                <p></p>
                <g:if test="${tma_slicesInstance.getComment()}"><p><i>Comment: ${tma_slicesInstance.getComment()}</i></p></g:if>
                    <table>
                        <tbody>
                        <g:if test="${tma_slicesInstance.getCut_date() != null}">
                            <tr class="prop">
                                <td valign="top" class="value" colspan="2">Cut on: <g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${tma_slicesInstance?.cut_date}" /></td>
                            </tr>
                        </g:if>
                        <tr class="prop">
                            <td valign="top" class="value" colspan="2">Thickness: ${tma_slicesInstance.getThickness()} micron</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="value" colspan="2">Staining detail: <g:link controller="staining_details" action="show" id="${tma_slicesInstance?.staining_detail?.id}">${tma_slicesInstance?.staining_detail?.biomarker?.encodeAsHTML()}</g:link></td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="value" colspan="2"><select optionKey="id" name="tma_result_names_id" id="tma_result_names_id" onchange="window.location.href=this.value"/></td>
                            </tr>
                        <g:if test="${tma_result!=null}">
                            <tr class="prop">
                                <td valign="top" style="text-align: left;" class="value" colspan="2">
                                    <g:displayTma_resultsDefaultScoringSystem 
                                    tma_slice="${tma_slicesInstance}"
                                    tma_scorer1="${tma_scorer1}" 
                                    tma_scorer2="${tma_scorer2}" 
                                    tma_scorer3="${tma_scorer3}" 
                                    scoring_date="${scoring_date}" 
                                    scoreType="${scoreType}"/></td>
                            </tr>
                        </g:if>
                        <tr class="prop">
                            <td valign="top" style="text-align: left;" class="value">
                                <g:displayTma_sliceSectorMapInHtmlTable 
                                    id="${tma_slicesInstance.getId()}" 
                                    mode="tma_slice_view" 
                                    tma_scorer1="${tma_scorer1}" 
                                    tma_scorer2="${tma_scorer2}" 
                                    tma_scorer3="${tma_scorer3}" 
                                    scoring_date="${scoring_date}" 
                                    scoreType="${scoreType}"
                                    tma_result_rep="${tma_result}"/></td>
                            <td valign="top" style="text-align: left; white-space: nowrap" class="value">
                                Other available TMA slices ...
                                <p><g:displayTma_sliceOtherAvailable id="${tma_slicesInstance.getId()}" tma_result_name="${tma_result?.getTma_result_name()}"/></p>
                            </td>
                        </tr>
                        </td>
                    </tbody>
                </table>
            </div>
        </div>

    <r:require module="tma_slices"/>
    <r:script>
       require(["dojo/_base/xhr"], function (xhr) {
            
        var waitDialogObj = getWaitDialog();
        
        xhr.get({
            url:'${createLink(controller:"tma_slices", action:"ajaxGetAvailableScorers", id:tma_slicesInstance?.id)}',
            handleAs: "json",
            load: function(e){
                updateTmaScorersInfos(e,'${tma_result==null?"":tma_result.getTma_result_name().getId()}','tma_result_names_id', '${createLink(controller:"tma_slices", action:"show", id:tma_slicesInstance?.id)}');
                waitDialogObj.destroy();
        }}); // xhr.get
    
        }); // function(xhr)
    </r:script>
</body>
</html>
