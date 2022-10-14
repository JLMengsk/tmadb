
<%@ page import="ca.ubc.gpec.tmadb.Bcou4543" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'bcou4543.label', default: 'Bcou4543')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>

          <tr class="prop">
            <td valign="top" class="name"><g:message code="bcou4543.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.gpec_id.label" default="Gpecid" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "gpec_id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.a_o_2.label" default="Ao2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "a_o_2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.a_o_3.label" default="Ao3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "a_o_3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.age_at_diagnosis.label" default="Ageatdiagnosis" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "age_at_diagnosis")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.bcca_chemo.label" default="Bccachemo" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "bcca_chemo")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.bcca_cod.label" default="Bccacod" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "bcca_cod")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.bcca_cod_desc.label" default="Bccacoddesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "bcca_cod_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.bcca_horm.label" default="Bccahorm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "bcca_horm")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.behavior.label" default="Behavior" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "behavior")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.clinical_info.label" default="Clinicalinfo" /></td>

        <td valign="top" class="value"><g:link controller="clinical_infos" action="show" id="${bcou4543Instance?.clinical_info?.id}">${bcou4543Instance?.clinical_info?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.death_cause_orig_desc.label" default="Deathcauseorigdesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "death_cause_orig_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.death_cause_original.label" default="Deathcauseoriginal" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "death_cause_original")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.death_date.label" default="Deathdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.death_date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.death_sec_cause.label" default="Deathseccause" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "death_sec_cause")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.death_sec_cause_desc.label" default="Deathseccausedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "death_sec_cause_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.diagnosis_date.label" default="Diagnosisdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.diagnosis_date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distant_completeness.label" default="Distantcompleteness" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distant_completeness")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distcat1.label" default="Distcat1" /></td>

        <td valign="top" class="value"><g:link controller="site_of_mets_codings" action="show" id="${bcou4543Instance?.distcat1?.id}">${bcou4543Instance?.distcat1?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distcat2.label" default="Distcat2" /></td>

        <td valign="top" class="value"><g:link controller="site_of_mets_codings" action="show" id="${bcou4543Instance?.distcat2?.id}">${bcou4543Instance?.distcat2?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distcat3.label" default="Distcat3" /></td>

        <td valign="top" class="value"><g:link controller="site_of_mets_codings" action="show" id="${bcou4543Instance?.distcat3?.id}">${bcou4543Instance?.distcat3?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distcat4.label" default="Distcat4" /></td>

        <td valign="top" class="value"><g:link controller="site_of_mets_codings" action="show" id="${bcou4543Instance?.distcat4?.id}">${bcou4543Instance?.distcat4?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distcat5.label" default="Distcat5" /></td>

        <td valign="top" class="value"><g:link controller="site_of_mets_codings" action="show" id="${bcou4543Instance?.distcat5?.id}">${bcou4543Instance?.distcat5?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distdate1.label" default="Distdate1" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.distdate1}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distdate2.label" default="Distdate2" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.distdate2}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distdate3.label" default="Distdate3" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.distdate3}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distdate4.label" default="Distdate4" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.distdate4}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distdate5.label" default="Distdate5" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.distdate5}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsite1.label" default="Distsite1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsite1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsite2.label" default="Distsite2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsite2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsite3.label" default="Distsite3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsite3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsite4.label" default="Distsite4" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsite4")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsite5.label" default="Distsite5" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsite5")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsitedesc1.label" default="Distsitedesc1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsitedesc1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsitedesc2.label" default="Distsitedesc2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsitedesc2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsitedesc3.label" default="Distsitedesc3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsitedesc3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsitedesc4.label" default="Distsitedesc4" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsitedesc4")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.distsitedesc5.label" default="Distsitedesc5" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "distsitedesc5")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.er.label" default="Er" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "er")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.er_result.label" default="Erresult" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "er_result")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.erposneg.label" default="Erposneg" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "erposneg")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.grade.label" default="Grade" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist1.label" default="Hist1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist1_desc.label" default="Hist1desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist1_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist2.label" default="Hist2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist2_desc.label" default="Hist2desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist2_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist3.label" default="Hist3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.hist3_desc.label" default="Hist3desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "hist3_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.immuno_stains.label" default="Immunostains" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "immuno_stains")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.locdate.label" default="Locdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.locdate}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.locind.label" default="Locind" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "locind")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.locsite.label" default="Locsite" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "locsite")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.locsitedesc.label" default="Locsitedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "locsitedesc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.lvn.label" default="Lvn" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "lvn")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.lvnnew.label" default="Lvnnew" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "lvnnew")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.lymph.label" default="Lymph" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "lymph")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.marg_at_init_dx.label" default="Margatinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "marg_at_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.meno_status.label" default="Menostatus" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "meno_status")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.missing_er_result.label" default="Missingerresult" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "missing_er_result")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.missing_num_neg_nod_init_dx.label" default="Missingnumnegnodinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "missing_num_neg_nod_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.missing_num_pos_nod_init_dx.label" default="Missingnumposnodinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "missing_num_pos_nod_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.num_neg_nod_init_dx.label" default="Numnegnodinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "num_neg_nod_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.num_pos_nod_init_dx.label" default="Numposnodinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "num_pos_nod_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.pat_status.label" default="Patstatus" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "pat_status")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.regdate.label" default="Regdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.regdate}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.regind.label" default="Regind" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "regind")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.registry_group.label" default="Registrygroup" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "registry_group")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.regsite.label" default="Regsite" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "regsite")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.regsitedesc.label" default="Regsitedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "regsitedesc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.sex.label" default="Sex" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "sex")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.site.label" default="Site" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "site")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.site_admit_date.label" default="Siteadmitdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4543Instance?.site_admit_date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.site_desc.label" default="Sitedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "site_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.size_lesion.label" default="Sizelesion" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "size_lesion")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.status_at_referral.label" default="Statusatreferral" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "status_at_referral")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_clin_m.label" default="Tnmclinm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_clin_m")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_clin_n.label" default="Tnmclinn" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_clin_n")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_clin_t.label" default="Tnmclint" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_clin_t")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_clin_yr.label" default="Tnmclinyr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_clin_yr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_surg_m.label" default="Tnmsurgm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_surg_m")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_surg_n.label" default="Tnmsurgn" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_surg_n")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_surg_t.label" default="Tnmsurgt" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_surg_t")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.tnm_surg_yr.label" default="Tnmsurgyr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "tnm_surg_yr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.type_init_chemo.label" default="Typeinitchemo" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "type_init_chemo")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.type_init_horm.label" default="Typeinithorm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "type_init_horm")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4543.veins.label" default="Veins" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4543Instance, field: "veins")}</td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
