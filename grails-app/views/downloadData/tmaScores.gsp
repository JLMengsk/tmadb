
<%@ page import="ca.ubc.gpec.tmadb.download.DownloadUtils"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Download TMA data</title>
  <g:javascript library="prototype"/>
</head>
<body>
  <div class="body">
    <g:showFlashMessage />
    <g:form action="downloadSingleTmaScores" method="post"
            enctype="multipart/form-data">
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name"><label>TMA project</label></td>
              <td valign="top"><g:select optionKey="id"
                                     name="tma_project_id" id="tma_project_id"
                                     from="${ca.ubc.gpec.tmadb.Tma_projects.list()}"
                                     onchange="${remoteFunction(controller:'tma_projects', action:'ajaxGetStainingDetails', params:'\'id=\' + escape(this.value)', onComplete:'updateStainingDetails(e)')}" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Biomarker</label></td>
            <td valign="top"><select name="staining_details_id" 
                                     id="staining_details_id"
                                     onchange="${remoteFunction(controller:'staining_details', action:'ajaxGetAvailableScorers', params:'\'id=\' + escape(this.value)', onComplete:'updateTmaScorersInfos(e)')}"></select>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Scores</label></td>
            <td valign="top"><select name="tma_results_id" id="tma_results_id"></select>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Data table format</label></td>
            <td valign="top"><select name="table_format" id="table_format">
                <g:each in="${DownloadUtils.TABLE_FORMATS}">
                  <option value="${it}">${it}</option>
                </g:each>
              </select>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Export file format</label>
            </td>
            <td valign="top"><select name="file_format" id="file_format">
                <g:each in="${DownloadUtils.FILE_FORMATS}">
                  <option value="${it}">${it}</option>
                </g:each>
              </select>
            </td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:submitButton name="download"
                                             class="download" value="Download" /> </span>
      </div>
    </g:form>
  </div>

<g:javascript>

  function updateTmaScorersInfos(e) {
  // The response comes back as a bunch-o-JSON
  var tma_scorers_infos;
  if (e==null) {
  tma_scorers_infos = eval("([])")	// evaluate JSON
  } else {
  tma_scorers_infos = eval("(" + e.responseText + ")")	// evaluate JSON
  }

  if (tma_scorers_infos) {
  var rselect = document.getElementById('tma_results_id')

  // Clear all previous options
  var l = rselect.length

  while (l > 0) {
  l--
  rselect.remove(l)
  }

  // Rebuild the select
  for (var i=0; i < tma_scorers_infos.length; i++) {
  var tma_scorers_info = tma_scorers_infos[i];
  var opt = document.createElement('option');
  var tma_scorer1_name = ""; if (tma_scorers_info[1]!=null) {tma_scorer1_name=tma_scorers_info[1].name;}
  var tma_scorer2_name = ""; if (tma_scorers_info[2]!=null) {tma_scorer2_name=tma_scorers_info[2].name;}
  var tma_scorer3_name = ""; if (tma_scorers_info[3]!=null) {tma_scorer3_name=tma_scorers_info[3].name;}
  var score_type = tma_scorers_info[5]
  if (!(typeof(score_type)=="string")) {
  score_type = tma_scorers_info[5].name;
  }

  opt.text = "";
  if (tma_scorers_info[1] != null) {opt.text = opt.text + tma_scorer1_name;}
  if (tma_scorers_info[2] != null) {opt.text = opt.text + " / " + tma_scorer2_name;}
  if (tma_scorers_info[3] != null) {opt.text = opt.text + " / " + tma_scorer3_name;}
  opt.text = opt.text +" (received " + tma_scorers_info[6] + "; using " + score_type + ") ... "+tma_scorers_info[7].name;

  opt.value = tma_scorers_info[0]; // this is tma_results id !!!

  try {
  rselect.add(opt, null) // standards compliant; doesn't work in IE
  }
  catch(ex) {
  rselect.add(opt) // IE only
  }
  }
  }
  }


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

  var zselect = document.getElementById('staining_details_id');
  if (zselect.options.length < 1) {
  updateTmaScorersInfos(null)
  } else {		
  var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"staining_details", action:"ajaxGetAvailableScorers", params:"'id=' + zopt.value", onComplete:"updateTmaScorersInfos(e)")}
  }


  }


  ///////////////////////////////////////////////////////////////////////////
  // This is called when the page loads to initialize tma_array
  ///////////////////////////////////////////////////////////////////////////
  var zselect = document.getElementById('tma_project_id');
  var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_projects", action:"ajaxGetStainingDetails", params:"'id=' + zopt.value", onComplete:"updateStainingDetails(e)")}
  zselect = document.getElementById('staining_details_id');
  if (zselect.options.length < 1) {
  updateTmaScorersInfos(null)
  } else {		
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"staining_details", action:"ajaxGetAvailableScorers", params:"'id=' + zopt.value", onComplete:"updateTmaScorersInfos(e)")}
  }

</g:javascript>
</div>
</body>
</html>