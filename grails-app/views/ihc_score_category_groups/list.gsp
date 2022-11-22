
<%@ page import="ca.ubc.gpec.tmadb.Ihc_score_category_groups" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ihc_score_category_groups.label', default: 'Ihc_score_category_groups')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'ihc_score_category_groups.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'ihc_score_category_groups.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'ihc_score_category_groups.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ihc_score_category_groupsInstanceList}" status="i" var="ihc_score_category_groupsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${ihc_score_category_groupsInstance.id}">${fieldValue(bean: ihc_score_category_groupsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: ihc_score_category_groupsInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: ihc_score_category_groupsInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${ihc_score_category_groupsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
