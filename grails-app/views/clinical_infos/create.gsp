

<%@ page import="ca.ubc.gpec.tmadb.Clinical_infos" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'clinical_infos.label', default: 'Clinical_infos')}" />
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
            <g:hasErrors bean="${clinical_infosInstance}">
            <div class="errors">
                <g:renderErrors bean="${clinical_infosInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="received_date"><g:message code="clinical_infos.received_date.label" default="Receiveddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'received_date', 'errors')}">
                                    <g:datePicker name="received_date" precision="day" value="${clinical_infosInstance?.received_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="clinical_infos.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'comment', 'errors')}">
                                    <g:textField name="comment" value="${clinical_infosInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="patient"><g:message code="clinical_infos.patient.label" default="Patient" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'patient', 'errors')}">
                                    <g:select name="patient.id" from="${ca.ubc.gpec.tmadb.Patients.list()}" optionKey="id" value="${clinical_infosInstance?.patient?.id}"  />
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
