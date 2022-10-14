
<%@ page import="ca.ubc.gpec.tmadb.Tma_blocks"%>
<%@ page import="ca.ubc.gpec.tmadb.Users"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName"
        value="${message(code: 'tma_blocks.label', default: 'TMA block')}" />
        <title><g:message code="default.show.label" args="[entityName]" />
        </title>
    </head>
    <body>

        <div class="body">
            <div style='display: inline'>
                <h1></h1>
                <g:link controller="tma_projects" action="show" id="${tma_blocksInstance.getTma_array().getTma_project().getId()}" title="click me to view TMA project">TMA project: ${tma_blocksInstance.getTma_array().getTma_project().getName()}</g:link>
                &#8594;
                <g:link controller="tma_arrays" action="show" id="${tma_blocksInstance.getTma_array().getId()}" title="click me to view TMA array">array version ${tma_blocksInstance.getTma_array().getArray_version()}</g:link>
                &#8594;
                <h1 style='display: inline'>block ${tma_blocksInstance.getName()}</h1>
            </div>
            <g:showFlashMessage />
            <div class="dialog">
                <p></p>
                <select onchange="window.location = this.value">
                    <option value="" selected="true">Available TMA slice(s)</option>
                    <g:each in="${tma_blocksInstance.getAvailableTma_slices(Users.findByLogin(session.user.login))}" var="t">
                        <option value="${createLink (controller:"tma_slices", action:"show", id:(t.id))}" title="${t?.description}">${t?.encodeAsHTML()},${t?.staining_detail?.biomarker?.encodeAsHTML()}</option>
                    </g:each>
                </select>
                <table>
                    <tr>
                        <td>   
                            <g:displayTma_blockSectorMapInHtmlTable id="${tma_blocksInstance.getId()}" mode="tma_block_view"/>
                        </td>
                        <td valign="top" style="text-align: left; white-space: nowrap" class="value">
                            Other available<br>TMA blocks ...
                            <p>
                                <g:each in="${tma_blocksInstance.tma_array.getAvailableTma_blocks(session.user)}" var="other_block">
                                    <g:if test="${other_block.compareTo(tma_blocksInstance)==0}"><strong><i>block ${other_block?.name}</i></strong></g:if>
                                    <g:else><g:link controller="tma_blocks" action="show" id="${other_block?.id}" title="${other_block?.description}">block ${other_block?.name}</g:link></g:else>
                                    <br>
                                </g:each>
                            </p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>
