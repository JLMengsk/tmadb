
<%@ page import="ca.ubc.gpec.tmadb.Bcou4620" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bcou4620.label', default: 'Bcou4620')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bcou4620.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="studynum" title="${message(code: 'bcou4620.studynum.label', default: 'Studynum')}" />
                        
                            <g:sortableColumn property="gpec_id" title="${message(code: 'bcou4620.gpec_id.label', default: 'Gpecid')}" />
                        
                            <g:sortableColumn property="ad" title="${message(code: 'bcou4620.ad.label', default: 'Ad')}" />
                        
                            <g:sortableColumn property="age_at_diagnosis" title="${message(code: 'bcou4620.age_at_diagnosis.label', default: 'Ageatdiagnosis')}" />
                        
                            <g:sortableColumn property="anyenddt" title="${message(code: 'bcou4620.anyenddt.label', default: 'Anyenddt')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bcou4620InstanceList}" status="i" var="bcou4620Instance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bcou4620Instance.id}">${fieldValue(bean: bcou4620Instance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bcou4620Instance, field: "studynum")}</td>
                        
                            <td>${fieldValue(bean: bcou4620Instance, field: "gpec_id")}</td>
                        
                            <td>${fieldValue(bean: bcou4620Instance, field: "ad")}</td>
                        
                            <td>${fieldValue(bean: bcou4620Instance, field: "age_at_diagnosis")}</td>
                        
                            <td><g:formatDate date="${bcou4620Instance.anyenddt}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bcou4620InstanceTotal}" />
            </div>
        </div>
    </body>
</html>
