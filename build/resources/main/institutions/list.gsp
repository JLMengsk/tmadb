
<%@ page import="ca.ubc.gpec.tmadb.Institutions" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'institutions.label', default: 'Institutions')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'institutions.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="country" title="${message(code: 'institutions.country.label', default: 'Country')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'institutions.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'institutions.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${institutionsInstanceList}" status="i" var="institutionsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${institutionsInstance.id}">${fieldValue(bean: institutionsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: institutionsInstance, field: "country")}</td>
                        
                            <td>${fieldValue(bean: institutionsInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: institutionsInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${institutionsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
