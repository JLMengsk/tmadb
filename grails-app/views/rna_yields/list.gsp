
<%@ page import="ca.ubc.gpec.tmadb.Rna_yields" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rna_yields.label', default: 'Rna_yields')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'rna_yields.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="concentration_ug_per_ul" title="${message(code: 'rna_yields.concentration_ug_per_ul.label', default: 'Concentrationugperul')}" />
                        
                            <g:sortableColumn property="yield_ug" title="${message(code: 'rna_yields.yield_ug.label', default: 'Yieldug')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'rna_yields.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="record_date" title="${message(code: 'rna_yields.record_date.label', default: 'Recorddate')}" />
                        
                            <th><g:message code="rna_yields.rna_extraction.label" default="Rnaextraction" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${rna_yieldsInstanceList}" status="i" var="rna_yieldsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${rna_yieldsInstance.id}">${fieldValue(bean: rna_yieldsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: rna_yieldsInstance, field: "concentration_ug_per_ul")}</td>
                        
                            <td>${fieldValue(bean: rna_yieldsInstance, field: "yield_ug")}</td>
                        
                            <td>${fieldValue(bean: rna_yieldsInstance, field: "comment")}</td>
                        
                            <td><g:formatDate date="${rna_yieldsInstance.record_date}" /></td>
                        
                            <td>${fieldValue(bean: rna_yieldsInstance, field: "rna_extraction")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${rna_yieldsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
