
<%@ page import="ca.ubc.gpec.tmadb.Rna_extractions" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rna_extractions.label', default: 'Rna_extractions')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'rna_extractions.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'rna_extractions.comment.label', default: 'Comment')}" />
                        
                            <th><g:message code="rna_extractions.coring.label" default="Coring" /></th>
                        
                            <g:sortableColumn property="record_date" title="${message(code: 'rna_extractions.record_date.label', default: 'Recorddate')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${rna_extractionsInstanceList}" status="i" var="rna_extractionsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${rna_extractionsInstance.id}">${fieldValue(bean: rna_extractionsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: rna_extractionsInstance, field: "comment")}</td>
                        
                            <td>${fieldValue(bean: rna_extractionsInstance, field: "coring")}</td>
                        
                            <td><g:formatDate date="${rna_extractionsInstance.record_date}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${rna_extractionsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
