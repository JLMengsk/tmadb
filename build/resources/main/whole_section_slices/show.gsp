
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_slices" %>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_images" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Whole section slice</title>
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
  </head>
  <body>
    <div class="body">
      <div style='display: inline'>
        <g:link controller="patients" action="show" id="${whole_section_slicesInstance.getParaffin_block()?.getPatient()?.getId()}" title="${whole_section_slicesInstance.getParaffin_block()?.getPatient()?.getPatient_source()?.toString()}">${whole_section_slicesInstance.getParaffin_block()?.getPatient()?.encodeAsHTML()}</g:link>
        &#8594;
        <g:if test="${whole_section_slicesInstance.getParaffin_block().showIsSurgical_block()}">
          <g:link controller="surgical_blocks" action="show" id="${whole_section_slicesInstance?.paraffin_block?.surgical_block?.id}">${whole_section_slicesInstance?.paraffin_block?.surgical_block?.encodeAsHTML()}</g:link>
        </g:if>
        <g:elseif test="${whole_section_slicesInstance.getParaffin_block().showIsCore_biopsy_block()}">
          <g:link controller="core_biopsy_blocks" action="show" id="${whole_section_slicesInstance?.paraffin_block?.core_biopsy_block?.id}">${whole_section_slicesInstance?.paraffin_block?.core_biopsy_block?.encodeAsHTML()}</g:link>
        </g:elseif>
        <g:else>
          <g:link controller="paraffin_blocks" action="show" id="${whole_section_slicesInstance?.paraffin_block?.id}">${whole_section_slicesInstance?.paraffin_block?.encodeAsHTML()}</g:link>
        </g:else>
        &#8594;
        <h1 style='display: inline'>${whole_section_slicesInstance.toStringShort()}</h1>
      </div>
      <g:showFlashMessage />
      <div class="dialog">
        <p></p>
        <table>
          <tbody>
            <tr class="${oddEvenRowFlag.showFlag()}">
              <td valign="top" class="name">Cut on:</td>
              <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${whole_section_slicesInstance?.cut_date}" /></td>
          </tr>
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Description:</td>
            <td valign="top" class="value">${fieldValue(bean: whole_section_slicesInstance, field: "description")}</td>
          </tr>
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Staining detail:</td>
            <td valign="top" class="value"><g:link controller="staining_details" action="show" id="${whole_section_slicesInstance?.staining_detail?.id}">${whole_section_slicesInstance?.staining_detail?.encodeAsHTML()}</g:link></td>
          </tr>
          <tr class="prop">
            <td valign="top" style="text-align: left;" class="value" colspan="2">
              <div style="width: ${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE+50}px; height: ${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE+50}px">
                <div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;">
                  <g:each in="${whole_section_slicesInstance.whole_section_images}" var="w">
                    <div dojoType="dijit.layout.ContentPane" title="${w.toString()}">
                      <g:display_all_whole_section_preview_images id="${w.getId()}" width="${ImageViewerMethods.IMAGE_PREVIEW_WIDTH_LARGE}" mode="${Whole_section_images.WHOLE_SECTION_IMAGE_VIEW_MODE_REGULAR}"/>
                    </div>
                  </g:each>
                </div>
              </div>
            </td>
          </tr>
          <tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name">Other available whole<br>section slice(s):</td>
            <td valign="top" class="value"><select id="whole_section_slices_select_id" onchange="window.location = this.value"></select></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

  <asset:script type="text/javascript">    
    require(["dojo/_base/xhr"], function (xhr) {

    //var waitDialogObj = getWaitDialog();

    // get some data, convert to JSON
    xhr.get({
    url:"${createLink(controller:"whole_section_slices", action:"ajaxGetAvailableWhole_section_slices")}",
    handleAs:"json",
    load: function(e){
    buildWhole_section_slicesSelect(
    e,
    ${whole_section_slicesInstance.getId()},
    "whole_section_slices_select_id");
    //waitDialogObj.destroy();
    },onError:function(e){alert("${ViewConstants.UNKNOWN_SERVER_ERROR_MESSAGE}");}
    }); // xhr.get
    }); // function (xhr)
  </asset:script>
</body>
</html>
