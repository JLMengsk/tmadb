
<%@ page import="ca.ubc.gpec.tmadb.Tma_blocks" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_blocks.label', default: 'Tma_blocks')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tma_blocks.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'tma_blocks.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'tma_blocks.name.label', default: 'Name')}" />
                        
                            <th><g:message code="tma_blocks.tma_array.label" default="Tmaarray" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_blocksInstanceList}" status="i" var="tma_blocksInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tma_blocksInstance.id}">${fieldValue(bean: tma_blocksInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tma_blocksInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: tma_blocksInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: tma_blocksInstance, field: "tma_array")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_blocksInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
