

<%@ page import="ca.ubc.gpec.tmadb.Patients" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'patients.label', default: 'Patients')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${patientsInstance}">
            <div class="errors">
                <g:renderErrors bean="${patientsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="patients.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${patientsInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="patient_id_txt"><g:message code="patients.patient_id_txt.label" default="Patientidtxt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'patient_id_txt', 'errors')}">
                                    <g:textField name="patient_id_txt" value="${patientsInstance?.patient_id_txt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="patient_id_txt2"><g:message code="patients.patient_id_txt2.label" default="Patientidtxt2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'patient_id_txt2', 'errors')}">
                                    <g:textField name="patient_id_txt2" value="${patientsInstance?.patient_id_txt2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="patient_source"><g:message code="patients.patient_source.label" default="Patientsource" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'patient_source', 'errors')}">
                                    <g:select name="patient_source.id" from="${ca.ubc.gpec.tmadb.Patient_sources.list()}" optionKey="id" value="${patientsInstance?.patient_source?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
