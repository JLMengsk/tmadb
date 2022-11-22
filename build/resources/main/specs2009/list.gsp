
<%@ page import="ca.ubc.gpec.tmadb.Specs2009" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'specs2009.label', default: 'Specs2009')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'specs2009.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="adjuvant_chemo" title="${message(code: 'specs2009.adjuvant_chemo.label', default: 'Adjuvantchemo')}" />
                        
                            <g:sortableColumn property="adjuvant_hormone" title="${message(code: 'specs2009.adjuvant_hormone.label', default: 'Adjuvanthormone')}" />
                        
                            <g:sortableColumn property="age_at_dx" title="${message(code: 'specs2009.age_at_dx.label', default: 'Ageatdx')}" />
                        
                            <g:sortableColumn property="architectual_grade" title="${message(code: 'specs2009.architectual_grade.label', default: 'Architectualgrade')}" />
                        
                            <g:sortableColumn property="cancer_status" title="${message(code: 'specs2009.cancer_status.label', default: 'Cancerstatus')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${specs2009InstanceList}" status="i" var="specs2009Instance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${specs2009Instance.id}">${fieldValue(bean: specs2009Instance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: specs2009Instance, field: "adjuvant_chemo")}</td>
                        
                            <td>${fieldValue(bean: specs2009Instance, field: "adjuvant_hormone")}</td>
                        
                            <td>${fieldValue(bean: specs2009Instance, field: "age_at_dx")}</td>
                        
                            <td>${fieldValue(bean: specs2009Instance, field: "architectual_grade")}</td>
                        
                            <td>${fieldValue(bean: specs2009Instance, field: "cancer_status")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${specs2009InstanceTotal}" />
            </div>
        </div>
    </body>
</html>
