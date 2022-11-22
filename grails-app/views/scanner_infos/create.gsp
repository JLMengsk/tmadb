

<%@ page import="ca.ubc.gpec.tmadb.Scanner_infos" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scanner_infos.label', default: 'Scanner_infos')}" />
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
            <g:hasErrors bean="${scanner_infosInstance}">
            <div class="errors">
                <g:renderErrors bean="${scanner_infosInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="scanner_infos.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scanner_infosInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${scanner_infosInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="magnification"><g:message code="scanner_infos.magnification.label" default="Magnification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scanner_infosInstance, field: 'magnification', 'errors')}">
                                    <g:textField name="magnification" value="${fieldValue(bean: scanner_infosInstance, field: 'magnification')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="image_quality"><g:message code="scanner_infos.image_quality.label" default="Imagequality" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scanner_infosInstance, field: 'image_quality', 'errors')}">
                                    <g:textField name="image_quality" value="${fieldValue(bean: scanner_infosInstance, field: 'image_quality')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="scanner_infos.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scanner_infosInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${scanner_infosInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="scan_image_type"><g:message code="scanner_infos.scan_image_type.label" default="Scanimagetype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: scanner_infosInstance, field: 'scan_image_type', 'errors')}">
                                    <g:textField name="scan_image_type" value="${scanner_infosInstance?.scan_image_type}" />
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
