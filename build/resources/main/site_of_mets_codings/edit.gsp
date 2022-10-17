

<%@ page import="ca.ubc.gpec.tmadb.Site_of_mets_codings" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'site_of_mets_codings.label', default: 'Site_of_mets_codings')}" />
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
            <g:hasErrors bean="${site_of_mets_codingsInstance}">
            <div class="errors">
                <g:renderErrors bean="${site_of_mets_codingsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${site_of_mets_codingsInstance?.id}" />
                <g:hiddenField name="version" value="${site_of_mets_codingsInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="site_of_mets_codings.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: site_of_mets_codingsInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${site_of_mets_codingsInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="site_of_mets_codings.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: site_of_mets_codingsInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${site_of_mets_codingsInstance?.name}" />
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
