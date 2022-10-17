
<%@ page import="ca.ubc.gpec.tmadb.Clinical_infos" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'clinical_infos.label', default: 'Clinical_infos')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'clinical_infos.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="received_date" title="${message(code: 'clinical_infos.received_date.label', default: 'Receiveddate')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'clinical_infos.comment.label', default: 'Comment')}" />
                        
                            <th><g:message code="clinical_infos.patient.label" default="Patient" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${clinical_infosInstanceList}" status="i" var="clinical_infosInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${clinical_infosInstance.id}">${fieldValue(bean: clinical_infosInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${clinical_infosInstance.received_date}" /></td>
                        
                            <td>${fieldValue(bean: clinical_infosInstance, field: "comment")}</td>
                        
                            <td>${fieldValue(bean: clinical_infosInstance, field: "patient")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${clinical_infosInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
