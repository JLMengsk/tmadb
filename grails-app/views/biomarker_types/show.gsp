
<%@ page import="ca.ubc.gpec.tmadb.Biomarker_types" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'biomarker_types.label', default: 'Biomarker_types')}" />
  <title>Biomarker type - ${biomarker_typesInstance.name}</title>
</head>
<body>
  <div class="body">
    <h1>Biomarker type - ${biomarker_typesInstance.getName()} (${biomarker_typesInstance.getDescription()})</h1>
    <g:showFlashMessage />
    <div class="dialog">
      <table>
        <tbody>                   
        <g:each in="${biomarker_typesInstance.biomarkers}" var="b">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" style="text-align: left;" class="value"><g:link controller="biomarkers" action="show" id="${b.id}">${b?.getName()} - ${b?.getDescription()}</g:link></td>
          </tr>                    
        </g:each>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
