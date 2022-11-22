
<%@ page import="ca.ubc.gpec.tmadb.Site_of_mets_codings" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'site_of_mets_codings.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'site_of_mets_codings.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'site_of_mets_codings.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${site_of_mets_codingsInstanceList}" status="i" var="site_of_mets_codingsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${site_of_mets_codingsInstance.id}">${fieldValue(bean: site_of_mets_codingsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: site_of_mets_codingsInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: site_of_mets_codingsInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${site_of_mets_codingsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
