

<%@ page import="ca.ubc.gpec.tmadb.Clinical_infos" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'clinical_infos.label', default: 'Clinical_infos')}" />
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
            <g:hasErrors bean="${clinical_infosInstance}">
            <div class="errors">
                <g:renderErrors bean="${clinical_infosInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${clinical_infosInstance?.id}" />
                <g:hiddenField name="version" value="${clinical_infosInstance?.version}" />
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
                                  <label for="bcou4543"><g:message code="clinical_infos.bcou4543.label" default="Bcou4543" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'bcou4543', 'errors')}">
                                    
<ul>
<g:each in="${clinical_infosInstance?.bcou4543?}" var="b">
    <li><g:link controller="bcou4543" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="bcou4543" action="create" params="['clinical_infos.id': clinical_infosInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bcou4543.label', default: 'Bcou4543')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bcou4620"><g:message code="clinical_infos.bcou4620.label" default="Bcou4620" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'bcou4620', 'errors')}">
                                    
<ul>
<g:each in="${clinical_infosInstance?.bcou4620?}" var="b">
    <li><g:link controller="bcou4620" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="bcou4620" action="create" params="['clinical_infos.id': clinical_infosInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bcou4620.label', default: 'Bcou4620')])}</g:link>

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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specs2009"><g:message code="clinical_infos.specs2009.label" default="Specs2009" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: clinical_infosInstance, field: 'specs2009', 'errors')}">
                                    
<ul>
<g:each in="${clinical_infosInstance?.specs2009?}" var="s">
    <li><g:link controller="specs2009" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="specs2009" action="create" params="['clinical_infos.id': clinical_infosInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'specs2009.label', default: 'Specs2009')])}</g:link>

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
