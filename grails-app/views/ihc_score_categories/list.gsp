
<%@ page import="ca.ubc.gpec.tmadb.Ihc_score_categories" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ihc_score_categories.label', default: 'Ihc_score_categories')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'ihc_score_categories.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'ihc_score_categories.description.label', default: 'Description')}" />
                        
                            <th><g:message code="ihc_score_categories.ihc_score_category_group.label" default="Ihcscorecategorygroup" /></th>
                        
                            <g:sortableColumn property="name" title="${message(code: 'ihc_score_categories.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="numeric_code" title="${message(code: 'ihc_score_categories.numeric_code.label', default: 'Numericcode')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ihc_score_categoriesInstanceList}" status="i" var="ihc_score_categoriesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${ihc_score_categoriesInstance.id}">${fieldValue(bean: ihc_score_categoriesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: ihc_score_categoriesInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: ihc_score_categoriesInstance, field: "ihc_score_category_group")}</td>
                        
                            <td>${fieldValue(bean: ihc_score_categoriesInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: ihc_score_categoriesInstance, field: "numeric_code")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${ihc_score_categoriesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
