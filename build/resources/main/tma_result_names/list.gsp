
<%@ page import="ca.ubc.gpec.tmadb.Tma_result_names" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_result_names.label', default: 'Tma_result_names')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'tma_result_names.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'tma_result_names.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'tma_result_names.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_result_namesInstanceList}" status="i" var="tma_result_namesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tma_result_namesInstance.id}">${fieldValue(bean: tma_result_namesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tma_result_namesInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: tma_result_namesInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_result_namesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
