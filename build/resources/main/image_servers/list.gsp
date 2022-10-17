
<%@ page import="ca.ubc.gpec.tmadb.Image_servers" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'image_servers.label', default: 'Image_servers')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'image_servers.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="address" title="${message(code: 'image_servers.address.label', default: 'Address')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'image_servers.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'image_servers.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${image_serversInstanceList}" status="i" var="image_serversInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${image_serversInstance.id}">${fieldValue(bean: image_serversInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: image_serversInstance, field: "address")}</td>
                        
                            <td>${fieldValue(bean: image_serversInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: image_serversInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${image_serversInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
