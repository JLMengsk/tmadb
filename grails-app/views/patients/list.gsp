
<%@ page import="ca.ubc.gpec.tmadb.Patients" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'patients.label', default: 'Patients')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'patients.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'patients.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="patient_id_txt" title="${message(code: 'patients.patient_id_txt.label', default: 'Patientidtxt')}" />
                        
                            <g:sortableColumn property="patient_id_txt2" title="${message(code: 'patients.patient_id_txt2.label', default: 'Patientidtxt2')}" />
                        
                            <th><g:message code="patients.patient_source.label" default="Patientsource" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${patientsInstanceList}" status="i" var="patientsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${patientsInstance.id}">${fieldValue(bean: patientsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: patientsInstance, field: "comment")}</td>
                        
                            <td>${fieldValue(bean: patientsInstance, field: "patient_id_txt")}</td>
                        
                            <td>${fieldValue(bean: patientsInstance, field: "patient_id_txt2")}</td>
                        
                            <td>${fieldValue(bean: patientsInstance, field: "patient_source")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${patientsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
