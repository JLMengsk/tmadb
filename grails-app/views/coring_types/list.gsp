
<%@ page import="ca.ubc.gpec.tmadb.Coring_types" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'coring_types.label', default: 'Coring_types')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'coring_types.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'coring_types.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'coring_types.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${coring_typesInstanceList}" status="i" var="coring_typesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${coring_typesInstance.id}">${fieldValue(bean: coring_typesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: coring_typesInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: coring_typesInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${coring_typesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
