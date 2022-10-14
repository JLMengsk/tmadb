
<%@ page import="ca.ubc.gpec.tmadb.Bcou4620" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'bcou4620.label', default: 'Bcou4620')}" />
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
            <td valign="top" class="name"><g:message code="bcou4620.id.label" default="Id" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.studynum.label" default="Studynum" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "studynum")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.gpec_id.label" default="Gpecid" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "gpec_id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.ad.label" default="Ad" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "ad")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.age_at_diagnosis.label" default="Ageatdiagnosis" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "age_at_diagnosis")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.anyenddt.label" default="Anyenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.anyenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.anyevndt.label" default="Anyevndt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.anyevndt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.anyreldt.label" default="Anyreldt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.anyreldt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.anystat.label" default="Anystat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "anystat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.anysurv.label" default="Anysurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "anysurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.bcca_cod_desc.label" default="Bccacoddesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "bcca_cod_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.bccacod.label" default="Bccacod" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "bccacod")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.behavior.label" default="Behavior" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "behavior")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.boost.label" default="Boost" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "boost")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.brchwrt.label" default="Brchwrt" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "brchwrt")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.brdeath.label" default="Brdeath" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "brdeath")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.brdthdat.label" default="Brdthdat" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.brdthdat}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.chemflag.label" default="Chemflag" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "chemflag")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.chemtype.label" default="Chemtype" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "chemtype")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.clinical_info.label" default="Clinicalinfo" /></td>

        <td valign="top" class="value"><g:link controller="clinical_infos" action="show" id="${bcou4620Instance?.clinical_info?.id}">${bcou4620Instance?.clinical_info?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.complete.label" default="Complete" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "complete")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.death_cause_desc.label" default="Deathcausedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "death_cause_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.death_da.label" default="Deathda" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.death_da}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.death_sec_cause_desc.label" default="Deathseccausedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "death_sec_cause_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.diagnosi.label" default="Diagnosi" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.diagnosi}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.disenddt.label" default="Disenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.disenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.distdate.label" default="Distdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.distdate}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.distind.label" default="Distind" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "distind")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.distnarr.label" default="Distnarr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "distnarr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.distsite.label" default="Distsite" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "distsite")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.diststat.label" default="Diststat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "diststat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.distsurv.label" default="Distsurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "distsurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.dvsprim.label" default="Dvsprim" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "dvsprim")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.dvssec.label" default="Dvssec" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "dvssec")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.er.label" default="Er" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "er")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.er1.label" default="Er1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "er1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.er2.label" default="Er2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "er2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.er3.label" default="Er3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "er3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.erposneg.label" default="Erposneg" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "erposneg")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.erresult.label" default="Erresult" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "erresult")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.evnenddt.label" default="Evnenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.evnenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.evntstat.label" default="Evntstat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "evntstat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.evntsurv.label" default="Evntsurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "evntsurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.finrt.label" default="Finrt" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "finrt")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.finsurg.label" default="Finsurg" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "finsurg")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.grade.label" default="Grade" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "grade")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist1.label" default="Hist1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist1_desc.label" default="Hist1desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist1_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist2.label" default="Hist2" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist2")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist2_desc.label" default="Hist2desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist2_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist3.label" default="Hist3" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist3")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hist3_desc.label" default="Hist3desc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hist3_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.histcat.label" default="Histcat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "histcat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hormflag.label" default="Hormflag" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hormflag")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hormtype.label" default="Hormtype" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hormtype")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.hx_bilatca_fst_deg_rel.label" default="Hxbilatcafstdegrel" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "hx_bilatca_fst_deg_rel")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.immuno.label" default="Immuno" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "immuno")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.laterality.label" default="Laterality" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "laterality")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.localtx.label" default="Localtx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "localtx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locdate.label" default="Locdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.locdate}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locenddt.label" default="Locenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.locenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locind.label" default="Locind" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "locind")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locnarr.label" default="Locnarr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "locnarr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locregdt.label" default="Locregdt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.locregdt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locsite.label" default="Locsite" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "locsite")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locstat.label" default="Locstat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "locstat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.locsurv.label" default="Locsurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "locsurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.lrgenddt.label" default="Lrgenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.lrgenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.lrgstat.label" default="Lrgstat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "lrgstat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.lrgsurv.label" default="Lrgsurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "lrgsurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.lvnnew.label" default="Lvnnew" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "lvnnew")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.m1.label" default="M1" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "m1")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.marg_at_init_dx.label" default="Margatinitdx" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "marg_at_init_dx")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.meno_status.label" default="Menostatus" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "meno_status")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.missing_erresult.label" default="Missingerresult" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "missing_erresult")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.missing_negnodes.label" default="Missingnegnodes" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "missing_negnodes")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.missing_posnodes.label" default="Missingposnodes" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "missing_posnodes")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.negnodes.label" default="Negnodes" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "negnodes")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.nodalrt.label" default="Nodalrt" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "nodalrt")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.nodestat.label" default="Nodestat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "nodestat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.partial.label" default="Partial" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "partial")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.pat_stat.label" default="Patstat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "pat_stat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.posnodes.label" default="Posnodes" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "posnodes")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regdate.label" default="Regdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.regdate}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regenddt.label" default="Regenddt" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.regenddt}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regind.label" default="Regind" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "regind")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.registry_group.label" default="Registrygroup" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "registry_group")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regnarr.label" default="Regnarr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "regnarr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regsite.label" default="Regsite" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "regsite")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regstat.label" default="Regstat" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "regstat")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.regsurv.label" default="Regsurv" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "regsurv")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.rtintent.label" default="Rtintent" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "rtintent")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.sex.label" default="Sex" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "sex")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.site.label" default="Site" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "site")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.site_admit_date.label" default="Siteadmitdate" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.site_admit_date}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.site_desc.label" default="Sitedesc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "site_desc")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.size_lesion.label" default="Sizelesion" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "size_lesion")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.statjn04.label" default="Statjn04" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "statjn04")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.status_at_referral.label" default="Statusatreferral" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "status_at_referral")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.subbrca.label" default="Subbrca" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "subbrca")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.subbrdat.label" default="Subbrdat" /></td>

        <td valign="top" class="value"><g:formatDate date="${bcou4620Instance?.subbrdat}" /></td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.subgroup.label" default="Subgroup" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "subgroup")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.survyrs.label" default="Survyrs" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "survyrs")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.sys.label" default="Sys" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "sys")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.systemic.label" default="Systemic" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "systemic")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.testvsvalid.label" default="Testvsvalid" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "testvsvalid")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_clin_m.label" default="Tnmclinm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_clin_m")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_clin_n.label" default="Tnmclinn" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_clin_n")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_clin_t.label" default="Tnmclint" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_clin_t")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_clin_yr.label" default="Tnmclinyr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_clin_yr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_surg_m.label" default="Tnmsurgm" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_surg_m")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_surg_n.label" default="Tnmsurgn" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_surg_n")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_surg_t.label" default="Tnmsurgt" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_surg_t")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tnm_surg_yr.label" default="Tnmsurgyr" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tnm_surg_yr")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.totnodes.label" default="Totnodes" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "totnodes")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="bcou4620.tumloc.label" default="Tumloc" /></td>

        <td valign="top" class="value">${fieldValue(bean: bcou4620Instance, field: "tumloc")}</td>

        </tr>

        </tbody>
      </table>
    </div>

  </div>
</body>
</html>
