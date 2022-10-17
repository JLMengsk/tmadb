

<%@ page import="ca.ubc.gpec.tmadb.Tma_core_images" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_core_images.label', default: 'Tma_core_images')}" />
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
            <g:hasErrors bean="${tma_core_imagesInstance}">
            <div class="errors">
                <g:renderErrors bean="${tma_core_imagesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="tma_core_images.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${tma_core_imagesInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="tma_core_images.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${tma_core_imagesInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="image_server"><g:message code="tma_core_images.image_server.label" default="Imageserver" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'image_server', 'errors')}">
                                    <g:select name="image_server.id" from="${ca.ubc.gpec.tmadb.Image_servers.list()}" optionKey="id" value="${tma_core_imagesInstance?.image_server?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="resource_name"><g:message code="tma_core_images.resource_name.label" default="Resourcename" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'resource_name', 'errors')}">
                                    <g:textField name="resource_name" value="${tma_core_imagesInstance?.resource_name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="scanning_date"><g:message code="tma_core_images.scanning_date.label" default="Scanningdate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'scanning_date', 'errors')}">
                                    <g:datePicker name="scanning_date" precision="day" value="${tma_core_imagesInstance?.scanning_date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="server_path"><g:message code="tma_core_images.server_path.label" default="Serverpath" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'server_path', 'errors')}">
                                    <g:textField name="server_path" value="${tma_core_imagesInstance?.server_path}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_core"><g:message code="tma_core_images.tma_core.label" default="Tmacore" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'tma_core', 'errors')}">
                                    <g:select name="tma_core.id" from="${ca.ubc.gpec.tmadb.Tma_cores.list()}" optionKey="id" value="${tma_core_imagesInstance?.tma_core?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tma_slice"><g:message code="tma_core_images.tma_slice.label" default="Tmaslice" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tma_core_imagesInstance, field: 'tma_slice', 'errors')}">
                                    <g:select name="tma_slice.id" from="${ca.ubc.gpec.tmadb.Tma_slices.list()}" optionKey="id" value="${tma_core_imagesInstance?.tma_slice?.id}"  />
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
