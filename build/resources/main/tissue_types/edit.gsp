

<%@ page import="ca.ubc.gpec.tmadb.Tissue_types" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tissue_types.label', default: 'Tissue_types')}" />
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
            <g:hasErrors bean="${tissue_typesInstance}">
            <div class="errors">
                <g:renderErrors bean="${tissue_typesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${tissue_typesInstance?.id}" />
                <g:hiddenField name="version" value="${tissue_typesInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="tissue_types.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tissue_typesInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tissue_typesInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="tissue_types.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tissue_typesInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${tissue_typesInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="surgical_blocks"><g:message code="tissue_types.surgical_blocks.label" default="Surgicalblocks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tissue_typesInstance, field: 'surgical_blocks', 'errors')}">
                                    
<ul>
<g:each in="${tissue_typesInstance?.surgical_blocks?}" var="s">
    <li><g:link controller="surgical_blocks" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="surgical_blocks" action="create" params="['tissue_types.id': tissue_typesInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'surgical_blocks.label', default: 'Surgical_blocks')])}</g:link>

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
