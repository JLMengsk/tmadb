

<%@ page import="ca.ubc.gpec.tmadb.Image_servers" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'image_servers.label', default: 'Image_servers')}" />
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
            <g:hasErrors bean="${image_serversInstance}">
            <div class="errors">
                <g:renderErrors bean="${image_serversInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="address"><g:message code="image_servers.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: image_serversInstance, field: 'address', 'errors')}">
                                    <g:textField name="address" value="${image_serversInstance?.address}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="image_servers.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: image_serversInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${image_serversInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="image_servers.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: image_serversInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${image_serversInstance?.name}" />
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
