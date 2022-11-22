

<%@ page import="ca.ubc.gpec.tmadb.Tma_blocks" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_blocks.label', default: 'Tma_blocks')}" />
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
            <g:hasErrors bean="${tma_blocksInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_blocksInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="tma_blocks.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_blocksInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tma_blocksInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="tma_blocks.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_blocksInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${tma_blocksInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_array"><g:message code="tma_blocks.tma_array.label" default="Tmaarray" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_blocksInstance, field: 'tma_array', 'errors')}">
                                    <g:select name="tma_array.id" from="${ca.ubc.gpec.tmadb.Tma_arrays.list()}" optionKey="id" value="${tma_blocksInstance?.tma_array?.id}"  />
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
