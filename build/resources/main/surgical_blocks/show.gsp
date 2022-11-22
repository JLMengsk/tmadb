
<%@ page import="ca.ubc.gpec.tmadb.Surgical_blocks" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'surgical_blocks.label', default: 'Surgical block')}" />
  <title>Show surgical block</title>
</head>
<body>
  <div class="body">
    <div style='display: inline'>
      <g:link controller="patients" action="show" id="${surgical_blocksInstance?.showPatient()?.getId()}" title="${surgical_blocksInstance?.showPatient()?.getPatient_source()?.toString()}">${surgical_blocksInstance?.showPatient()?.encodeAsHTML()}</g:link>
      &#8594;
      <h1 style='display: inline'>${surgical_blocksInstance.toString()}</h1>
    </div>
    <g:showFlashMessage />

    <div class="dialog">
      <p></p>
      <table>
        <tbody>

        <g:if test="${surgical_blocksInstance.showAdditional_info()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Additional info:</td>
            <td valign="top" class="value">${surgical_blocksInstance.showAdditional_info()}</td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Last checked date:</td>
          <td valign="top" class="value">TODO</td>
        </tr>

        <g:if test="${surgical_blocksInstance.showComment()!=""}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Comment:</td>
            <td valign="top" class="value">${surgical_blocksInstance.showComment()}</td>
          </tr>
        </g:if>

        <g:if test="${!surgical_blocksInstance.getCorings().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}"><td>Corings</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${surgical_blocksInstance.corings}" var="c">
                  <li><g:link controller="coring" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                </g:each>
              </ul>
            </td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">In GPEC?</td>
          <td valign="top" class="value">TODO</td>
        </tr>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Specimen number:</td>
          <td valign="top" class="value">${surgical_blocksInstance.showSpecimen_number()}</td>
        </tr>

        <g:if test="${surgical_blocksInstance?.showParaffin_block_package()!=null}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Paraffin block package:</td>
            <td valign="top" class="value"><g:link controller="paraffin_block_packages" action="show" id="${surgical_blocksInstance?.showParaffin_block_package()?.getId()}">${surgical_blocksInstance?.showParaffin_block_package()?.encodeAsHTML()}</g:link></td>
          </tr>
        </g:if>

        <tr class="${oddEvenRowFlag.showFlag()}">
          <td valign="top" class="name">Tissue type:</td>
          <td valign="top" class="value"><g:link controller="tissue_types" action="show" id="${surgical_blocksInstance?.showTissue_type()?.getId()}">${surgical_blocksInstance?.showTissue_type()?.encodeAsHTML()}</g:link></td>
        </tr>

        <g:if test="${!surgical_blocksInstance.getTma_cores().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">TMA cores:</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${surgical_blocksInstance.tma_cores}" var="t">
                  <g:if test="${t.isAvailable(session.user)}">
                    <li><g:link controller="tma_cores" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
                  </g:if>
                </g:each>
              </ul>
            </td>
          </tr>
        </g:if>

        <g:if test="${!surgical_blocksInstance.showWhole_section_slices().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Whole section slices:</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${surgical_blocksInstance.showWhole_section_slices()}" var="w">
                  <li><g:link controller="whole_section_slices" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
                </g:each>
              </ul>
            </td>
          </tr>
        </g:if>

        <g:if test="${!surgical_blocksInstance.getQpcr_experiments().isEmpty()}">
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">qPCR experiments:</td>
            <td valign="top" style="text-align: left;" class="value">
              <ul>
                <g:each in="${surgical_blocksInstance.qpcr_experiments}" var="t">
                  <li><g:link controller="qpcr_experiments" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
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
