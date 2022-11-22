
<%@ page import="ca.ubc.gpec.tmadb.Scanner_infos" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scanner_infos.label', default: 'Scanner_infos')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'scanner_infos.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'scanner_infos.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="magnification" title="${message(code: 'scanner_infos.magnification.label', default: 'Magnification')}" />
                        
                            <g:sortableColumn property="image_quality" title="${message(code: 'scanner_infos.image_quality.label', default: 'Imagequality')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'scanner_infos.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="scan_image_type" title="${message(code: 'scanner_infos.scan_image_type.label', default: 'Scanimagetype')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${scanner_infosInstanceList}" status="i" var="scanner_infosInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${scanner_infosInstance.id}">${fieldValue(bean: scanner_infosInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: scanner_infosInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: scanner_infosInstance, field: "magnification")}</td>
                        
                            <td>${fieldValue(bean: scanner_infosInstance, field: "image_quality")}</td>
                        
                            <td>${fieldValue(bean: scanner_infosInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: scanner_infosInstance, field: "scan_image_type")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${scanner_infosInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
