<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Create TMA cores</title>
  <g:javascript library="prototype"/>
</head>
<body>
  <div class="body">
    <h1>Create TMA cores</h1>
    <g:showFlashMessage />
    <g:form action="createCores" method="post"
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
            <td valign="top" class="name"><label>TMA array</label>
            </td>
            <td valign="top"><select name="tma_array_id"
                                     id="tma_array_id" 
                                     onchange="${remoteFunction(controller:'tma_arrays', action:'ajaxGetBlocks', params:'\'id=\' + escape(this.value)', onComplete:'updateBlocks(e)')}"/>
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA block</label>
            </td>
            <td valign="top"><select name="tma_block_id"
                                     id="tma_block_id" 
                                     onchange="${remoteFunction(controller:'tma_blocks', action:'ajaxGetSlices', params:'\'id=\' + escape(this.value)', onComplete:'updateSlices(e)')}"/>
            </td>
          </tr>			

          <tr class="prop">
            <td valign="top" class="name"><label>Patient source</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="patient_source_id" id="patient_source_id"
                                     from="${ca.ubc.gpec.tmadb.Patient_sources.list()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA core diameter (mm)</label></td>
            <td valign="top"><g:select
            name="diameter" id="diameter"
            from="${ca.ubc.gpec.tmadb.Tma_cores.allowableDiameters()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Cores
                information file</label></td>
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

</g:javascript>

</body>
</html>
