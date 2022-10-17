
<%@ page import="ca.ubc.gpec.tmadb.Surgical_blocks" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surgical_blocks.label', default: 'Surgical block')}" />
        <title>Add surgical blocks to ${patient.toString()}</title>
        <r:require modules="edit"/>
    </head>
    <body>
        <div class="body">
            <div style='display: inline'>
                <g:link controller="patients" action="show" id="${patient.getId()}" title="${patient.toString()}">${patient.encodeAsHTML()}</g:link>
                &#8594;
                <h1 style='display: inline'>Adding surgical block</h1>
            </div>
            <g:showFlashMessage />

            <div dojoType='dijit.form.Form' action="${createLink(base:grailsApplication.config.grails.serverSecureURL, controller:"surgical_blocks", action:"save")}" method='POST' id="${ViewConstants.HTML_FORM_NAME_EDIT_SURG_BLOCKS}">
                <div class="dialog">
                    <table>
                        <tr class="odd"><td style="text-align:right">Specimen number:</td>
                            <td><input id='${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_SPECIMEN_NUMBER}' 
                                       type='text' 
                                       data-dojo-type="dijit.form.ValidationTextBox" 
                                       data-dojo-props="regExp:'^(?!\s*$).+', invalidMessage:'Specimen number cannot be empty.'"
                                       style='width: 30em;'/></td>
                        </tr>
                        <tr class="even"><td style="text-align:right">Additional information:</td>
                            <td><input id='${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_ADDITIONAL_INFO}' 
                                       type='text' 
                                       dojoType='dijit.form.TextBox' 
                                       style='width: 30em;'/></td>
                        </tr>
                        <tr class="even"><td style="text-align:right">Tissue type:</td>
                            <td><select optionKey="id" 
                                name="${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_TISSUE_TYPE_ID}" 
                                id="${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_TISSUE_TYPE_ID}"/></td>
                        </tr>
                        <tr class="even"><td style="text-align:right">Comment:</td>
                            <td><textarea id='${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_COMMENT}' 
                                          name='${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_COMMENT}' 
                                          dojoType='dijit.form.Textarea' 
                                          style='width:30em;'></textarea></td>
                        </tr>
                    </table>
                    <div class="buttons_large_font">
                        <span class="button">
                            <div style="display: inline-block; float: right">
                                <button 
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                value="save" 
                                onclick="checkFormFieldsAndSubmit(); return false">save</button>
                                <button
                                dojoType='dijit.form.Button' 
                                type="submit" 
                                onclick="reset(); return false;"
                                >reset</button>
                            </div>
                        </span>
                    </div>
                </div> <!-- dialog -->
            </div> <!-- dijit form-->
        </div> <!-- body -->
    <r:script>

        function reset() {
        resetFormFields(
        ["${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_SPECIMEN_NUMBER}",
        "${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_ADDITIONAL_INFO}",
        "${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_COMMENT}"]
        );
        }

        // require non-empty specimen number
        // warn if paraffin block with same specimen number exist in database already
        function checkFormFields() {
            if (dojo.byId("${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_SPECIMEN_NUMBER}").value=="") {
                return false;
            }
            
        }

        require(["dojo/_base/xhr"], function (xhr) {           
        var waitDialogObj = getWaitDialog();
        xhr.get({
            url:'${createLink(controller:"tissue_types", action:"ajaxGetAvailableTissue_types", id:tma_slicesInstance?.id)}',
            handleAs: "json",
            load: function(e){
                updateTissue_typesInfos(e,"${ViewConstants.HTML_PARAM_NAME_EDIT_SURG_BLOCKS_INPUT_NAME_TISSUE_TYPE_ID}");
                waitDialogObj.destroy();
        }}); // xhr.get
        }); // function(xhr)
        
        
    </r:script>
</body>
</html>
