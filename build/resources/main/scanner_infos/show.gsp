
<%@ page import="ca.ubc.gpec.tmadb.Scanner_infos" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'scanner_infos.label', default: 'Scanner_infos')}" />
  <title>Scanner - ${scanner_infosInstance.getName()}</title>
</head>
<body>
  <div class="body">
    <h1>Scanner - ${scanner_infosInstance.getName()}</h1>
    <g:showFlashMessage />
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name">Magnification:</td>
            <td valign="top" class="value">${fieldValue(bean: scanner_infosInstance, field: "magnification")}</td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">Image quality:</td>
            <td valign="top" class="value">${fieldValue(bean: scanner_infosInstance, field: "image_quality")}</td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">Description:</td>
            <td valign="top" class="value">${fieldValue(bean: scanner_infosInstance, field: "description")}</td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name">Scan image type:</td>
            <td valign="top" class="value">${fieldValue(bean: scanner_infosInstance, field: "scan_image_type")}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
