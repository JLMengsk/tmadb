

<%@ page import="ca.ubc.gpec.tmadb.Tma_result_names" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_result_names.label', default: 'Tma_result_names')}" />
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
            <g:hasErrors bean="${tma_result_namesInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_result_namesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${tma_result_namesInstance?.id}" />
                <g:hiddenField name="version" value="${tma_result_namesInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="tma_result_names.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_result_namesInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tma_result_namesInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="tma_result_names.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_result_namesInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${tma_result_namesInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tma_results"><g:message code="tma_result_names.tma_results.label" default="Tmaresults" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_result_namesInstance, field: 'tma_results', 'errors')}">
                                    
<ul>
<g:each in="${tma_result_namesInstance?.tma_results?}" var="t">
    <li><g:link controller="tma_results" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tma_results" action="create" params="['tma_result_names.id': tma_result_namesInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tma_results.label', default: 'Tma_results')])}</g:link>

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
