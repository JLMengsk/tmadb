
<%@ page import="ca.ubc.gpec.tmadb.Coring" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'coring.label', default: 'Coring')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'coring.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="checked_date" title="${message(code: 'coring.checked_date.label', default: 'Checkeddate')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'coring.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="cored_date" title="${message(code: 'coring.cored_date.label', default: 'Coreddate')}" />
                        
                            <th><g:message code="coring.coring_project.label" default="Coringproject" /></th>
                        
                            <th><g:message code="coring.coring_type.label" default="Coringtype" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${coringInstanceList}" status="i" var="coringInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${coringInstance.id}">${fieldValue(bean: coringInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${coringInstance.checked_date}" /></td>
                        
                            <td>${fieldValue(bean: coringInstance, field: "comment")}</td>
                        
                            <td><g:formatDate date="${coringInstance.cored_date}" /></td>
                        
                            <td>${fieldValue(bean: coringInstance, field: "coring_project")}</td>
                        
                            <td>${fieldValue(bean: coringInstance, field: "coring_type")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${coringInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
