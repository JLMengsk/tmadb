
<%@ page import="ca.ubc.gpec.tmadb.Tma_arrays" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_arrays.label', default: 'Tma_arrays')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>            
                            <g:sortableColumn property="array_version" title="${message(code: 'tma_arrays.array_version.label', default: 'Version')}" />                        
                            <g:sortableColumn property="description" title="${message(code: 'tma_arrays.description.label', default: 'Description')}" />
                            <g:sortableColumn property="tma_project" title="${message(code: 'tma_arrays.tma_project.label', default: 'TMA project')}" />   
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_arraysInstanceList}" status="i" var="tma_arraysInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${tma_arraysInstance.id}">${fieldValue(bean: tma_arraysInstance, field: "array_version")}</g:link></td>
                            <td>${fieldValue(bean: tma_arraysInstance, field: "description")}</td>
                        	<td>${fieldValue(bean: tma_arraysInstance, field: "tma_project")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_arraysInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
