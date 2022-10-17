
<%@ page import="ca.ubc.gpec.tmadb.Tma_core_images" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_core_images.label', default: 'Tma_core_images')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'tma_core_images.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'tma_core_images.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'tma_core_images.description.label', default: 'Description')}" />
                        
                            <th><g:message code="tma_core_images.image_server.label" default="Imageserver" /></th>
                        
                            <g:sortableColumn property="resource_name" title="${message(code: 'tma_core_images.resource_name.label', default: 'Resourcename')}" />
                        
                            <g:sortableColumn property="scanning_date" title="${message(code: 'tma_core_images.scanning_date.label', default: 'Scanningdate')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_core_imagesInstanceList}" status="i" var="tma_core_imagesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tma_core_imagesInstance.id}">${fieldValue(bean: tma_core_imagesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tma_core_imagesInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: tma_core_imagesInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: tma_core_imagesInstance, field: "image_server")}</td>
                        
                            <td>${fieldValue(bean: tma_core_imagesInstance, field: "resource_name")}</td>
                        
                            <td><g:formatDate date="${tma_core_imagesInstance.scanning_date}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_core_imagesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
