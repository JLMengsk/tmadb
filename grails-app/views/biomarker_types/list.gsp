
<%@ page import="ca.ubc.gpec.tmadb.Biomarker_types" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'biomarker_types.label', default: 'Biomarker_types')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'biomarker_types.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'biomarker_types.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'biomarker_types.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${biomarker_typesInstanceList}" status="i" var="biomarker_typesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${biomarker_typesInstance.id}">${fieldValue(bean: biomarker_typesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: biomarker_typesInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: biomarker_typesInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${biomarker_typesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
