<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Setup TMA scoring session</title>
  <g:javascript library="prototype"/>
</head>
<body>

  <div class="body">
    <h1>Upload core images for TMA scoring session</h1>
    <g:form controller="scoring_sessions" action="uploadCoreImagesForScoring" method="post"
            enctype="multipart/form-data">
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name"><label>Score session</label></td>
              <td valign="top"><g:select optionKey="id"
                                     name="scoring_session_id" id="scoring_session_id"
                                     from="${ca.ubc.gpec.tmadb.Scoring_sessions.list()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>TMA project</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="tma_project_id" id="tma_project_id"
                                     from="${ca.ubc.gpec.tmadb.Tma_projects.list()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Biomarkers</label></td>
            <td valign="top"><g:select optionKey="id"
                                     name="biomarker_id" id="biomarker_id"
                                     from="${ca.ubc.gpec.tmadb.Biomarkers.list()}"/>
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label>Core images information file</label></td>
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
</body>
</html>