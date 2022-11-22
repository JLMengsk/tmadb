
<%@ page import="ca.ubc.gpec.tmadb.Qpcr_results" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'qpcr_results.label', default: 'Qpcr_results')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'qpcr_results.id.label', default: 'Id')}" />
                        
                            <th><g:message code="qpcr_results.biomarker.label" default="Biomarker" /></th>
                        
                            <g:sortableColumn property="comment" title="${message(code: 'qpcr_results.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="copy_number" title="${message(code: 'qpcr_results.copy_number.label', default: 'Copynumber')}" />
                        
                            <g:sortableColumn property="crossing_point" title="${message(code: 'qpcr_results.crossing_point.label', default: 'Crossingpoint')}" />
                        
                            <g:sortableColumn property="date_received" title="${message(code: 'qpcr_results.date_received.label', default: 'Datereceived')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${qpcr_resultsInstanceList}" status="i" var="qpcr_resultsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${qpcr_resultsInstance.id}">${fieldValue(bean: qpcr_resultsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: qpcr_resultsInstance, field: "biomarker")}</td>
                        
                            <td>${fieldValue(bean: qpcr_resultsInstance, field: "comment")}</td>
                        
                            <td>${fieldValue(bean: qpcr_resultsInstance, field: "copy_number")}</td>
                        
                            <td>${fieldValue(bean: qpcr_resultsInstance, field: "crossing_point")}</td>
                        
                            <td><g:formatDate date="${qpcr_resultsInstance.date_received}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${qpcr_resultsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
