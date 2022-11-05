
<%@ page import="ca.ubc.gpec.tmadb.ImageViewerMethods"%>
<%@ page import="ca.ubc.gpec.tmadb.Whole_section_images"%>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Whole section slice image</title>
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
        <g:link controller="whole_section_slices" action="show" id="${whole_section_slicesInstance?.id}">${whole_section_slicesInstance.toStringShort()}</g:link>
        &#8594;
        <h1 style='display: inline'>${whole_section_imagesInstance.toString()}</h1>
      </div>
      <g:showFlashMessage />
      <div class="dialog">

        <div style="width: ${ImageViewerMethods.IMAGE_VIEWER_WIDTH+50}px; height: ${ImageViewerMethods.IMAGE_VIEWER_HEIGHT+100}px">
          <div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;">
            <div dojoType="dijit.layout.ContentPane" title="Whole section image">
              <div class="dialog">
                <table>
                  <tbody>
                    <tr class="prop">
                      <td valign="top" class="name">${raw(whole_section_imagesInstance.showImageHtml(createLink(uri: '/').toString(), ImageViewerMethods.IMAGE_VIEWER_WIDTH, ImageViewerMethods.IMAGE_VIEWER_HEIGHT))}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div dojoType="dijit.layout.ContentPane" title="Details">
              <div class="dialog">
                <table>
                  <tbody>
                    <tr class="${oddEvenRowFlag.showFlag()}">
                      <td valign="top" class="name">Name: </td>
                      <td valign="top" class="value">${whole_section_imagesInstance.getName()}</td>
                    </tr>

                    <tr class="${oddEvenRowFlag.showFlag()}">
                      <td valign="top" class="name">Scanner: </td>
                      <td valign="top" class="value"><g:link controller="scanner_infos" action="show" id="${whole_section_imagesInstance?.scanner_info?.id}">${whole_section_imagesInstance?.scanner_info?.encodeAsHTML()}</g:link></td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Description: </td>
                    <td valign="top" class="value">${whole_section_imagesInstance.getDescription()}</td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Image server: </td>
                    <td valign="top" class="value"><g:link controller="image_servers" action="show" id="${whole_section_imagesInstance?.image_server?.id}">${whole_section_imagesInstance?.image_server?.encodeAsHTML()}</g:link></td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Resource name: </td>
                    <td valign="top" class="value">${whole_section_imagesInstance.getResource_name()}</td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Scanning date: </td>
                    <td valign="top" class="value"><g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${whole_section_imagesInstance?.scanning_date}" /></td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Server path: </td>
                    <td valign="top" class="value">${whole_section_imagesInstance.getServer_path()}</td>
                  </tr>

                  <tr class="${oddEvenRowFlag.showFlag()}">
                    <td valign="top" class="name">Whole section results: </td>
                    <td valign="top" style="text-align: left;" class="value">
                      <ul>
                        <g:each in="${whole_section_imagesInstance.whole_section_results}" var="w">
                          <li><g:link controller="whole_section_results" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
                        </g:each>
                      </ul>
                    </td>
                  </tr>

                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>

      </div>
  </body>
</html>
