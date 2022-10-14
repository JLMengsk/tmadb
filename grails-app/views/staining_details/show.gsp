
<%@ page import="ca.ubc.gpec.tmadb.Staining_details" %>
<%@ page import="ca.ubc.gpec.tmadb.DisplayConstant" %>
<%@ page import="ca.ubc.gpec.tmadb.util.OddEvenRowFlag"%>
<%@ page import="ca.ubc.gpec.tmadb.util.ViewConstants"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'staining_details.label', default: 'Staining details')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

  <div class="body">
    <h1><g:link controller="biomarkers" action="show" id="${staining_detailsInstance?.biomarker?.id}">${staining_detailsInstance?.biomarker?.encodeAsHTML()}</g:link>
stained @
<g:link controller="institutions" action="show" id="${staining_detailsInstance?.staining_institution?.id}">${staining_detailsInstance?.staining_institution?.encodeAsHTML()}</g:link>
      <g:if test="${staining_detailsInstance?.staining_date != null}">
        on
        <g:formatDate format="${DisplayConstant.DATE_FORMAT}" date="${staining_detailsInstance?.staining_date}" />
      </g:if>
    </h1>
    <g:showFlashMessage />
    <g:set var="oddEvenRowFlag" value="${new OddEvenRowFlag(ViewConstants.CSS_CLASS_NAME_ROW_ODD,ViewConstants.CSS_CLASS_NAME_ROW_EVEN)}"/>
    <div class="dialog">
      <table>
        <tbody>
        <g:if test="${staining_detailsInstance.getAntibody_concentration()!=null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.antibody_concentration.label" default="Antibody concentration" /></td>  
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "antibody_concentration")}</td>    
          </tr></g:if>
        <g:if test="${staining_detailsInstance.getAntigen_retrieval_method() != null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.antigen_retrieval_method.label" default="Antigen retrieval method" /></td>   
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "antigen_retrieval_method")}</td>        
          </tr></g:if>

        <g:if test="${staining_detailsInstance.getDetection_method() != null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.detection_method.label" default="Detection method" /></td>          
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "detection_method")}</td>        
          </tr></g:if>

        <g:if test="${staining_detailsInstance.getDilution() != null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.dilution.label" default="Dilution" /></td>               
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "dilution")}</td>     
          </tr></g:if>

        <g:if test="${staining_detailsInstance.getGpec_protocol_number() != null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.gpec_protocol_number.label" default="GPEC protocol number" /></td>
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "gpec_protocol_number")}</td>    
          </tr></g:if>

        <g:if test="${staining_detailsInstance.getIncubation() != null}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.incubation.label" default="Incubation" /></td>     
          <td valign="top" class="value">${fieldValue(bean: staining_detailsInstance, field: "incubation")}</td>    
          </tr></g:if>

        <g:if test="${!availableTma_slices.isEmpty()}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.tma_slices.label" default="TMA slices" /></td>

          <td valign="top" style="text-align: left;" class="value">
            <ul>
              <g:each in="${availableTma_slices}" var="t">
                <li><g:link controller="tma_slices" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
              </g:each>
            </ul>
          </td>
          </tr></g:if>

        <g:if test="${!availableWhole_section_slices.isEmpty()}"><tr class="${oddEvenRowFlag.showFlag()}">
            <td valign="top" class="name"><g:message code="staining_details.whole_section_slices.label" default="Whole section slices" /></td>

          <td valign="top" style="text-align: left;" class="value">
            <ul>
              <g:each in="${availableWhole_section_slices}" var="w">
                <li><g:link controller="whole_section_slices" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></li>
              </g:each>
            </ul>
          </td>
          </tr></g:if>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
