

<%@ page import="ca.ubc.gpec.tmadb.Tma_projects" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_projects.label', default: 'Tma_projects')}" />
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
            <g:hasErrors bean="${tma_projectsInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_projectsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${tma_projectsInstance?.id}" />
                <g:hiddenField name="version" value="${tma_projectsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="tma_projects.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_projectsInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${tma_projectsInstance?.name}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="core_id_name"><g:message code="tma_projects.core_id_name.label" default="Core ID name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_projectsInstance, field: 'core_id_name', 'errors')}">
                                    <g:textField name="core_id_name" value="${tma_projectsInstance?.core_id_name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="built_by"><g:message code="tma_projects.built_by.label" default="Builtby" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_projectsInstance, field: 'built_by', 'errors')}">
                                    <g:textField name="built_by" value="${tma_projectsInstance?.built_by}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="tma_projects.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_projectsInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tma_projectsInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tma_arrays"><g:message code="tma_projects.tma_arrays.label" default="Tmaarrays" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_projectsInstance, field: 'tma_arrays', 'errors')}">
                                    
<ul>
<g:each in="${tma_projectsInstance?.tma_arrays?}" var="t">
    <li><g:link controller="tma_arrays" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tma_arrays" action="create" params="['tma_projects.id': tma_projectsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tma_arrays.label', default: 'Tma_arrays')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <!-- <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span> -->
                </div>
            </g:form>
        </div>
    </body>
</html>
