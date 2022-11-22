
<%@ page import="ca.ubc.gpec.tmadb.upload.tma.UploadCoreScores"%>
<%@ page import="ca.ubc.gpec.tmadb.ScoreType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Upload TMA core images</title>
  <g:javascript library="prototype"/>
</head>
<body>
  <div class="body">
    <h1>Upload TMA core scores</h1>
    <g:showFlashMessage />
    <g:form action="uploadCoreScores" method="post"
            enctype="multipart/form-data">
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name"><label>TMA project</label></td>
              <td valign="top"><g:select optionKey="id"
                                     name="tma_project_id" id="tma_project_id"
                                     from="${ca.ubc.gpec.tmadb.Tma_projects.list()}"
                                     onchange="${remoteFunction(controller:'tma_projects', action:'ajaxGetArrays', params:'\'id=\' + escape(this.value)', onComplete:'updateArrays(e)')}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA array</label></td>
            <td valign="top">
              <select name="tma_array_id"
                      id="tma_array_id" 
                      onchange="${remoteFunction(controller:'tma_arrays', action:'ajaxGetBlocks', params:'\'id=\' + escape(this.value)', onComplete:'updateBlocks(e)')}"
                      ></select></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA block</label></td>
            <td valign="top"><select name="tma_block_id"
                                     id="tma_block_id" 
                                     onchange="${remoteFunction(controller:'tma_blocks', action:'ajaxGetSlices', params:'\'id=\' + escape(this.value)', onComplete:'updateSlices(e)')}"/></select></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA slice</label></td>
            <td valign="top"><select name="tma_slice_id"
                                     id="tma_slice_id" 
                                     onchange="${remoteFunction(controller:'tma_slices', action:'ajaxGetScannerInfos', params:'\'id=\' + escape(this.value)', onComplete:'updateScannerInfos(e)')}"/></select></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Scanner info</label></td>
            <td valign="top"><select optionKey="id"
                                     name="tma_core_image_id" 
                                     id="tma_core_image_id"/></select></td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Score types</label></td>
            <td valign="top">
              <select onchange="checkIhcScoreCategoryGroups()" id="score_type" name="score_type">
                <option value="${UploadCoreScores.SCORE_TYPE_TOTAL_NUCLEI_COUNT}">${ScoreType.TOTAL_NUCLEI_COUNT}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_ESTIMATE}">${ScoreType.VISUAL_PERCENT_POSITIVE_ESTIMATE}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE}">${ScoreType.VISUAL_PERCENT_POSITIVE_NUCLEI_ESTIMATE}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE}">${ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_ESTIMATE}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE}">${ScoreType.VISUAL_PERCENT_POSITIVE_MEMBRANE_ESTIMATE }</option>
                <option value="${UploadCoreScores.SCORE_TYPE_VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE}">${ScoreType.VISUAL_PERCENT_POSITIVE_CYTOPLASMIC_AND_OR_MEMBRANE_ESTIMATE }</option>
                <option value="${UploadCoreScores.SCORE_TYPE_POSITIVE_ITIL_COUNT}">${ScoreType.POSITIVE_ITIL_COUNT}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_POSITIVE_STIL_COUNT}">${ScoreType.POSITIVE_STIL_COUNT}</option>
                <option value="${UploadCoreScores.SCORE_TYPE_IHC_SCORE_CATEGORICAL}">${ScoreType.CATEGORICAL_SCORE}</option>
              </select>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>IHC score category groups</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="ihc_score_category_group_id" id="ihc_score_category_group_id"
                                     from="${ca.ubc.gpec.tmadb.Ihc_score_category_groups.listInterpretableScoreCategoryGroups()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>IHC missing score categories</label></td>
            <td valign="top"><g:select multiple="multiple" optionKey="id"
                                     name="missing_ihc_score_category_id_arr" id="missing_ihc_score_category_id_arr"
                                     from="${ca.ubc.gpec.tmadb.Ihc_score_categories.listUninterpretableScoreCategories()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA scorer(s) - up to 3 only</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="tma_scorer1_id" id="tma_scorer1_id"  noSelection="${['null':'select zero or one...']}"
                                     from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}"/>
          </td>
          </tr>
          <tr class="prop"><td></td>
            <td valign="top"><g:select optionKey="id"
                                     name="tma_scorer2_id" id="tma_scorer2_id" noSelection="${['null':'select zero or one...']}"
                                     from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}"/>
          </td>
          </tr>
          <tr class="prop"><td></td>
            <td valign="top"><g:select optionKey="id"
                                     name="tma_scorer3_id" id="tma_scorer3_id" noSelection="${['null':'select zero or one...']}"
                                     from="${ca.ubc.gpec.tmadb.Tma_scorers.list()}"/>
          </td>
          </tr>						

          <tr class="prop">
            <td valign="top" class="name"><label>Scoring date</label>
            </td>
            <td valign="top"><g:datePicker name="scoring_date" 
                                         id="scoring_date" value="${new Date()}" precision="day"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Score received date</label>
            </td>
            <td valign="top"><g:datePicker name="received_date" 
                                         id="received_date" value="${new Date()}" precision="day"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA result name</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="tma_result_name_id" id="tma_result_name_id"
                                     from="${ca.ubc.gpec.tmadb.Tma_result_names.list()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Core scores file</label></td>
            <td valign="top"><input type="file" name="myFile" /></td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:submitButton name="upload"
                                             class="upload" value="Upload" /> </span>
      </div>
    </g:form>
  </div>

<g:javascript>

  function updateScannerInfos(e) {
  // The response comes back as a bunch-o-JSON
  var scanner_infos_scanning_dates;
  if (e==null) {
  scanner_infos_scanning_dates = eval("([])")	// evaluate JSON
  } else {
  scanner_infos_scanning_dates = eval("(" + e.responseText + ")")	// evaluate JSON
  }

  if (scanner_infos_scanning_dates) {
  var rselect = document.getElementById('tma_core_image_id')

  // Clear all previous options
  var l = rselect.length

  while (l > 0) {
  l--
  rselect.remove(l)
  }

  // Rebuild the select
  for (var i=0; i < scanner_infos_scanning_dates.length; i++) {
  var scanner_info_scanning_date = scanner_infos_scanning_dates[i]
  var opt = document.createElement('option');
  opt.text = scanner_info_scanning_date[1].description + " (scanned on " + scanner_info_scanning_date[2] + ")"

  // IMPORTANT NOTE: scanner_info_scanning_date[0] is Tma_core_images.id !!!
  opt.value = scanner_info_scanning_date[0]
  try {
  rselect.add(opt, null) // standards compliant; doesn't work in IE
  }
  catch(ex) {
  rselect.add(opt) // IE only
  }
  }
  }
  }


  function updateSlices(e) {
  // The response comes back as a bunch-o-JSON
  var tma_slices;
  if (e==null) {
  tma_slices = eval("([])")	// evaluate JSON
  } else {
  tma_slices = eval("(" + e.responseText + ")")	// evaluate JSON
  }

  if (tma_slices) {
  var rselect = document.getElementById('tma_slice_id')

  // Clear all previous options
  var l = rselect.length

  while (l > 0) {
  l--
  rselect.remove(l)
  }

  // Rebuild the select
  for (var i=0; i < tma_slices.length; i++) {
  var tma_slice = tma_slices[i]
  var opt = document.createElement('option');
  opt.text = tma_slice.name + " ("+ tma_slice.description + ")"
  opt.value = tma_slice.id
  try {
  rselect.add(opt, null) // standards compliant; doesn't work in IE
  }
  catch(ex) {
  rselect.add(opt) // IE only
  }
  }
  }

  zselect = document.getElementById('tma_slice_id');
  if (zselect.options.length < 1) {
  updateScannerInfos(null)
  } else {	
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_slices", action:"ajaxGetScannerInfos", params:"'id=' + zopt.value", onComplete:"updateScannerInfos(e)")}
  }
  }

  function updateBlocks(e) {
  // The response comes back as a bunch-o-JSON
  var tma_blocks;
  if (e==null) {
  tma_blocks = eval("([])")	// evaluate JSON
  } else {
  tma_blocks = eval("(" + e.responseText + ")")	// evaluate JSON
  }

  if (tma_blocks) {
  var rselect = document.getElementById('tma_block_id')

  // Clear all previous options
  var l = rselect.length

  while (l > 0) {
  l--
  rselect.remove(l)
  }

  // Rebuild the select
  for (var i=0; i < tma_blocks.length; i++) {
  var tma_block = tma_blocks[i]
  var opt = document.createElement('option');
  opt.text = tma_block.name
  opt.value = tma_block.id
  try {
  rselect.add(opt, null) // standards compliant; doesn't work in IE
  }
  catch(ex) {
  rselect.add(opt) // IE only
  }
  }
  }

  var zselect = document.getElementById('tma_block_id');
  if (zselect.options.length < 1) {
  updateSlices(null)
  } else {		
  var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_blocks", action:"ajaxGetSlices", params:"'id=' + zopt.value", onComplete:"updateSlices(e)")}
  }

  zselect = document.getElementById('tma_slice_id');
  if (zselect.options.length < 1) {
  updateScannerInfos(null)
  } else {	
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_slices", action:"ajaxGetScannerInfos", params:"'id=' + zopt.value", onComplete:"updateScannerInfos(e)")}
  }
  }

  function updateArrays(e) {
  // The response comes back as a bunch-o-JSON
  var tma_arrays;
  if (e==null) {
  tma_arrays = eval("([])")	// evaluate JSON
  } else {
  tma_arrays = eval("(" + e.responseText + ")")	// evaluate JSON
  }

  if (tma_arrays) {
  var rselect = document.getElementById('tma_array_id')

  // Clear all previous options
  var l = rselect.length

  while (l > 0) {
  l--
  rselect.remove(l)
  }

  // Rebuild the select
  for (var i=0; i < tma_arrays.length; i++) {
  var tma_array = tma_arrays[i]
  var opt = document.createElement('option');
  opt.text = tma_array.description
  opt.value = tma_array.id
  try {
  rselect.add(opt, null) // standards compliant; doesn't work in IE
  }
  catch(ex) {
  rselect.add(opt) // IE only
  }
  }
  }

  var zselect = document.getElementById('tma_array_id');
  if (zselect.options.length < 1) {
  updateBlocks(null)
  } else {
  var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_arrays", action:"ajaxGetBlocks", params:"'id=' + zopt.value", onComplete:"updateBlocks(e)")}
  }

  zselect = document.getElementById('tma_block_id');
  if (zselect.options.length < 1) {
  updateSlices(null)
  } else {
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_blocks", action:"ajaxGetSlices", params:"'id=' + zopt.value", onComplete:"updateSlices(e)")}
  }

  zselect = document.getElementById('tma_slice_id');
  if (zselect.options.length < 1) {
  updateScannerInfos(null)
  } else {	
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_slices", action:"ajaxGetScannerInfos", params:"'id=' + zopt.value", onComplete:"updateScannerInfos(e)")}
  }
  }

  function checkIhcScoreCategoryGroups() {
  // check to see if ihc_score_categorical is selected
  var selectedIndex = document.getElementById('score_type').selectedIndex;
  var selectedOptionValue = document.getElementById('score_type').options[selectedIndex].value;
  if (selectedOptionValue == "${UploadCoreScores.SCORE_TYPE_IHC_SCORE_CATEGORICAL}") {
  document.getElementById('ihc_score_category_group_id').disabled = false;
  } else {
  document.getElementById('ihc_score_category_group_id').disabled = true;
  }
  }

  // This is called when the page loads to initialize tma_array
  var zselect = document.getElementById('tma_project_id');
  var zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_projects", action:"ajaxGetArrays", params:"'id=' + zopt.value", onComplete:"updateArrays(e)")}

  zselect = document.getElementById('tma_array_id');
  zopt = zselect.options[zselect.selectedIndex];
  if (zselect.options.length < 1) {
  updateBlocks(null)
  } else {
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_arrays", action:"ajaxGetBlocks", params:"'id=' + zopt.value", onComplete:"updateBlocks(e)")}
  }

  zselect = document.getElementById('tma_block_id');
  zopt = zselect.options[zselect.selectedIndex];
  if (zselect.options.length < 1) {
  updateSlices(null)
  } else {	
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_blocks", action:"ajaxGetSlices", params:"'id=' + zopt.value", onComplete:"updateSlices(e)")}
  }

  zselect = document.getElementById('tma_slice_id');
  zopt = zselect.options[zselect.selectedIndex];
  if (zselect.options.length < 1) {
  updateScannerInfos(null)
  } else {	
  zopt = zselect.options[zselect.selectedIndex];
${remoteFunction(controller:"tma_slices", action:"ajaxGetScannerInfos", params:"'id=' + zopt.value", onComplete:"updateScannerInfos(e)")}
  }

  // disable ihc score category first
  document.getElementById('ihc_score_category_group_id').disabled = true;
</g:javascript>

</body>
</html>
