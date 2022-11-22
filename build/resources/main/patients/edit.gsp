

<%@ page import="ca.ubc.gpec.tmadb.Patients" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'patients.label', default: 'Patients')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${patientsInstance}">
            <div class="errors">
                <g:renderErrors bean="${patientsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${patientsInstance?.id}" />
                <g:hiddenField name="version" value="${patientsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clinical_infos"><g:message code="patients.clinical_infos.label" default="Clinicalinfos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'clinical_infos', 'errors')}">
                                    
<ul>
<g:each in="${patientsInstance?.clinical_infos?}" var="c">
    <li><g:link controller="clinical_infos" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="clinical_infos" action="create" params="['patients.id': patientsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'clinical_infos.label', default: 'Clinical_infos')])}</g:link>

                                </td>
                            </tr>
                        
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgical_blocks"><g:message code="patients.surgical_blocks.label" default="Surgicalblocks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: patientsInstance, field: 'surgical_blocks', 'errors')}">
                                    
<ul>
<g:each in="${patientsInstance?.surgical_blocks?}" var="s">
    <li><g:link controller="surgical_blocks" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="surgical_blocks" action="create" params="['patients.id': patientsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'surgical_blocks.label', default: 'Surgical_blocks')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
