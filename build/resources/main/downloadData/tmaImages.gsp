<!--
  Download tma images ...
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Download TMA images</title>
  <g:javascript library="prototype"/>
</head>
<body>
  <div class="body">
    <g:form action="downloadTmaCoreImageSelectionCopyScript" method="post"
            enctype="multipart/form-data">
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name"><label>TMA project</label>
              </td>
              <td valign="top"><g:select optionKey="id"
                                     name="tma_project_id" id="tma_project_id"
                                     from="${ca.ubc.gpec.tmadb.Tma_projects.list()}"
                                     onchange="${remoteFunction(controller:'tma_projects', action:'ajaxGetStainingDetails', params:'\'id=\' + escape(this.value)', onComplete:'updateStainingDetails(e)')}" />
          </td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name"><label>Biomarker</label>
            </td>
            <td valign="top"><g:select name="staining_details_id" id="staining_details_id"/>
          </td>
          </tr>
          <tr class="prop">
            <td valign="top" class="name"><label><abbr title="core ID's separated by comma (,)">Core ID names</abbr></label></td>
            <td valign="top"><textarea name="core_ids"></textarea></td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:submitButton name="download"
                                             class="download" value="Download copy script" /> </span>
      </div>
    </g:form>

    <g:javascript>

      function updateStainingDetails(e) {
      // The response comes back as a bunch-o-JSON
      var staining_details;
      if (e==null) {
      staining_details = eval("([])")	// evaluate JSON
      } else {
      staining_details = eval("(" + e.responseText + ")")	// evaluate JSON
      }

      if (staining_details) {

      var rselect = document.getElementById('staining_details_id')

      // Clear all previous options
      var l = rselect.length

      while (l > 0) {
      l--
      rselect.remove(l)
      }

      // Rebuild the select
      for (var i=0; i < staining_details.length; i++) {
      var staining_detail = staining_details[i]
      var opt = document.createElement('option');

      opt.text = staining_detail[1];

      opt.value = staining_detail[0]; // this is stainig_detail id !!!

      try {
      rselect.add(opt, null) // standards compliant; doesn't work in IE
      }
      catch(ex) {
      rselect.add(opt) // IE only
      }
      }
      }

      }


      ///////////////////////////////////////////////////////////////////////////
      // This is called when the page loads to initialize tma_array
      ///////////////////////////////////////////////////////////////////////////
      var zselect = document.getElementById('tma_project_id');
      var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_projects", action:"ajaxGetStainingDetails", params:"'id=' + zopt.value", onComplete:"updateStainingDetails(e)")}

    </g:javascript>

  </div>
</body>
</html>
