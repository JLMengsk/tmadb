
<%@ page import="ca.ubc.gpec.tmadb.Image_servers" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'image_servers.label', default: 'Image_servers')}" />
  <title>Image server - ${image_serversInstance.getName()}</title>
</head>
<body>
  <div class="body">
    <h1>Image server - ${image_serversInstance.getName()}</h1>
    <g:showFlashMessage />
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name">URL:</td>
            <td valign="top" class="value">${fieldValue(bean: image_serversInstance, field: "address")}</td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">Description:</td>
            <td valign="top" class="value">${fieldValue(bean: image_serversInstance, field: "description")}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
