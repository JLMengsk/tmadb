
<%@ page import="ca.ubc.gpec.tmadb.Bcou4543" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bcou4543.label', default: 'Bcou4543')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'bcou4543.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="gpec_id" title="${message(code: 'bcou4543.gpec_id.label', default: 'Gpecid')}" />
                        
                            <g:sortableColumn property="a_o_2" title="${message(code: 'bcou4543.a_o_2.label', default: 'Ao2')}" />
                        
                            <g:sortableColumn property="a_o_3" title="${message(code: 'bcou4543.a_o_3.label', default: 'Ao3')}" />
                        
                            <g:sortableColumn property="age_at_diagnosis" title="${message(code: 'bcou4543.age_at_diagnosis.label', default: 'Ageatdiagnosis')}" />
                        
                            <g:sortableColumn property="bcca_chemo" title="${message(code: 'bcou4543.bcca_chemo.label', default: 'Bccachemo')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bcou4543InstanceList}" status="i" var="bcou4543Instance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bcou4543Instance.id}">${fieldValue(bean: bcou4543Instance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bcou4543Instance, field: "gpec_id")}</td>
                        
                            <td>${fieldValue(bean: bcou4543Instance, field: "a_o_2")}</td>
                        
                            <td>${fieldValue(bean: bcou4543Instance, field: "a_o_3")}</td>
                        
                            <td>${fieldValue(bean: bcou4543Instance, field: "age_at_diagnosis")}</td>
                        
                            <td>${fieldValue(bean: bcou4543Instance, field: "bcca_chemo")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bcou4543InstanceTotal}" />
            </div>
        </div>
    </body>
</html>
