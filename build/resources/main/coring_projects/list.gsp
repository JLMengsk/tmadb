
<%@ page import="ca.ubc.gpec.tmadb.Coring_projects" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'coring_projects.label', default: 'Coring_projects')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'coring_projects.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'coring_projects.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="end_date" title="${message(code: 'coring_projects.end_date.label', default: 'Enddate')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'coring_projects.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="start_date" title="${message(code: 'coring_projects.start_date.label', default: 'Startdate')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${coring_projectsInstanceList}" status="i" var="coring_projectsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${coring_projectsInstance.id}">${fieldValue(bean: coring_projectsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: coring_projectsInstance, field: "description")}</td>
                        
                            <td><g:formatDate date="${coring_projectsInstance.end_date}" /></td>
                        
                            <td>${fieldValue(bean: coring_projectsInstance, field: "name")}</td>
                        
                            <td><g:formatDate date="${coring_projectsInstance.start_date}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${coring_projectsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
