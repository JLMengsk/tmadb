

<%@ page import="ca.ubc.gpec.tmadb.Coring_projects" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'coring_projects.label', default: 'Coring_projects')}" />
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
            <g:hasErrors bean="${coring_projectsInstance}">
            <div class="errors">
                <g:renderErrors bean="${coring_projectsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${coring_projectsInstance?.id}" />
                <g:hiddenField name="version" value="${coring_projectsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="coring"><g:message code="coring_projects.coring.label" default="Coring" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coring_projectsInstance, field: 'coring', 'errors')}">
                                    
<ul>
<g:each in="${coring_projectsInstance?.coring?}" var="c">
    <li><g:link controller="coring" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="coring" action="create" params="['coring_projects.id': coring_projectsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'coring.label', default: 'Coring')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="coring_projects.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coring_projectsInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${coring_projectsInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="end_date"><g:message code="coring_projects.end_date.label" default="Enddate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coring_projectsInstance, field: 'end_date', 'errors')}">
                                    <g:datePicker name="end_date" precision="day" value="${coring_projectsInstance?.end_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="coring_projects.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coring_projectsInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${coring_projectsInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="start_date"><g:message code="coring_projects.start_date.label" default="Startdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: coring_projectsInstance, field: 'start_date', 'errors')}">
                                    <g:datePicker name="start_date" precision="day" value="${coring_projectsInstance?.start_date}"  />
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
