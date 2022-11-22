
<%@ page import="ca.ubc.gpec.tmadb.Tma_cores" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tma_cores.label', default: 'Tma_cores')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tma_cores.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="row" title="${message(code: 'tma_cores.row.label', default: 'Row')}" />
                        
                            <g:sortableColumn property="col" title="${message(code: 'tma_cores.col.label', default: 'Col')}" />
                        
                            <g:sortableColumn property="core_id" title="${message(code: 'tma_cores.core_id.label', default: 'Coreid')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'tma_cores.description.label', default: 'Description')}" />
                        
                            <th><g:message code="tma_cores.surgical_block.label" default="Surgicalblock" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tma_coresInstanceList}" status="i" var="tma_coresInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tma_coresInstance.id}">${fieldValue(bean: tma_coresInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tma_coresInstance, field: "row")}</td>
                        
                            <td>${fieldValue(bean: tma_coresInstance, field: "col")}</td>
                        
                            <td>${fieldValue(bean: tma_coresInstance, field: "core_id")}</td>
                        
                            <td>${fieldValue(bean: tma_coresInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: tma_coresInstance, field: "surgical_block")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tma_coresInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
