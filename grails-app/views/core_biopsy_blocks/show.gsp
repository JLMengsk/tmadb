
<%@ page import="ca.ubc.gpec.tmadb.Core_biopsy_blocks" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'core_biopsy_blocks.label', default: 'Core biopsy block')}" />
  <title>Show core biopsy block</title>
</head>
<body>
  <div class="body">
    <div style='display: inline'>
      <g:link controller="patients" action="show" id="${core_biopsy_blocksInstance?.showPatient()?.getId()}" title="${core_biopsy_blocksInstance?.showPatient()?.getPatient_source()?.toString()}">${core_biopsy_blocksInstance?.showPatient()?.encodeAsHTML()}</g:link>
      &#8594;
      <h1 style='display: inline'>${core_biopsy_blocksInstance.toString()}</h1>
    </div>
    <g:showFlashMessage />

    <div class="dialog">
      <p></p>
      <table>
        <tbody>

        <g:if test="${core_biopsy_blocksInstance.showAdditional_info()!=null && core_biopsy_blocksInstance.showAdditional_info()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Additional info:</td>
            <td valign="top" class="value">${core_biopsy_blocksInstance.showAdditional_info()}</td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Last checked date:</td>
          <td valign="top" class="value">TODO</td>
        </tr>

        <g:if test="${core_biopsy_blocksInstance.showComment()!=null && core_biopsy_blocksInstance.showComment()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Comment:</td>
            <td valign="top" class="value">${core_biopsy_blocksInstance.showComment()}</td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">In GPEC?</td>
          <td valign="top" class="value">TODO</td>
        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Specimen number:</td>
          <td valign="top" class="value">${core_biopsy_blocksInstance.showSpecimen_number()}</td>
        </tr>

        <g:if test="${core_biopsy_blocksInstance?.showParaffin_block_package()!=null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Paraffin block package:</td>
            <td valign="top" class="value"><g:link controller="paraffin_block_packages" action="show" id="${core_biopsy_blocksInstance?.showParaffin_block_package()?.getId()}">${core_biopsy_blocksInstance?.showParaffin_block_package()?.encodeAsHTML()}</g:link></td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Tissue type:</td>
          <td valign="top" class="value"><g:link controller="tissue_types" action="show" id="${core_biopsy_blocksInstance?.showTissue_type()?.getId()}">${core_biopsy_blocksInstance?.showTissue_type()?.encodeAsHTML()}</g:link></td>
        </tr>

        <g:if test="${!core_biopsy_blocksInstance.showWhole_section_slices().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Whole section slices:</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${core_biopsy_blocksInstance.showWhole_section_slices()}" var="w">
                  <li><g:link controller="whole_section_slices" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
                </g:each>
              </ul>
            </td>
          </tr>
        </g:if>

        </tbody>
      </table>
    </div>


  </div>
</body>
</html>
