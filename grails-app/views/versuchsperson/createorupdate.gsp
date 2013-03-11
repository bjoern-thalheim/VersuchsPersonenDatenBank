
<%@ page import="de.ist_dresden.vpdb.entity.Versuchsperson"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title><g:message code="versuchsperson.list.label.edit" /></title>
</head>
<body>
<div class="nav"><span class="menuButton"><a class="home" href="${resource(dir:'')}"><g:message code="default.list.label.home" /></a></span> <span class="menuButton"><g:link class="list" action="list">
  <g:message code="versuchsperson.list.label.list" />
</g:link></span></div>
<div class="body">
<g:if test="${versuchspersonInstance?.blacklisted}">
  <div class="warn" style="margin-left:0;" >
    <g:message code="versuchsperson.view.warning.blacklisted"/>
  </div>
</g:if> 
<h1><g:message code="versuchsperson.list.label.edit" /></h1>
<g:if test="${flash.message}">
  <div class="message">
  ${message(code:flash.message)}
  </div>
</g:if> <g:if test="${flash.error}">
  <div class="errors">
  ${message(code:flash.error)}
  </div>
</g:if> 
<g:hasErrors bean="${versuchspersonInstance}">
  <div class="errors"><g:renderErrors bean="${versuchspersonInstance}" as="list" /></div>
</g:hasErrors>
<g:form action="save" method="post">
  <input type="hidden" value="${fieldValue(bean:versuchspersonInstance,field:'id')}" name="id" />
  <div class="dialog">
  <table>
    <tbody>

      <tr class="prop">
        <td valign="top" class="nachname"><label for="nachname"><g:message code="versuchsperson.view.label.nachname" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'nachname','errors')}"><input type="text" id="nachname" name="nachname" value="${fieldValue(bean:versuchspersonInstance,field:'nachname')}" /></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="vorname"><label for="vorname"><g:message code="versuchsperson.view.label.vorname" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'vorname','errors')}"><input type="text" id="vorname" name="vorname" value="${fieldValue(bean:versuchspersonInstance,field:'vorname')}" /></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="geschlecht"><g:message code="versuchsperson.view.label.geschlecht" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'geschlecht','errors')}">
          <select name="geschlecht" id="geschlecht">
            <g:each in="${de.ist_dresden.vpdb.entity.Geschlecht?.values()}">
              <g:if test="${it.equals(versuchspersonInstance?.geschlecht)}">
                <option value="${it}" selected><g:message code="versuchsperson.view.value.geschlecht.${it}" /></option>
              </g:if>
              <g:if test="${!it.equals(versuchspersonInstance?.geschlecht)}">
                <option value="${it}"><g:message code="versuchsperson.view.value.geschlecht.${it}" /></option>
              </g:if>
            </g:each>
          </select>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="geburtsdatum"><g:message code="versuchsperson.view.label.geburtsdatum" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'geburtsdatum','errors')}"><g:datePicker name="geburtsdatum" value="${versuchspersonInstance?.geburtsdatum}" precision="day"></g:datePicker></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="altersklasse"><g:message code="versuchsperson.view.label.altersklasse" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'altersklasse','errors')}">
          <select name="altersklasse" id="altersklasse">
            <g:each in="${de.ist_dresden.vpdb.entity.Altersklasse?.values()}">
              <g:if test="${it.equals(versuchspersonInstance?.altersklasse)}">
                <option value="${it}" onclick="${remoteFunction(action:'showBildung',update:'bildung', params:[personId:versuchspersonInstance?.id, selectedAltersklasse:it])}" selected><g:message code="versuchsperson.view.value.altersklasse.${it}" /></option>
              </g:if>
              <g:if test="${!it.equals(versuchspersonInstance?.altersklasse)}">
                <option value="${it}" onclick="${remoteFunction(action:'showBildung',update:'bildung', params:[personId:versuchspersonInstance?.id, selectedAltersklasse:it])}"><g:message code="versuchsperson.view.value.altersklasse.${it}" /></option>
              </g:if>
            </g:each>
          </select>
        </td>
      </tr>
      
      <tr>
        <td></td>
        <td>
          <table id="bildung"><g:render template="erwachsener"></g:render></table>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="adresse"><g:message code="versuchsperson.view.label.adresse" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'adresse','errors')}">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name"><label for="strasseUndHausnummer"><g:message code="adresse.view.label.strasse" />:</label></td>
              <td valign="top" class="value ${hasErrors(bean:adresseInstance,field:'strasseUndHausnummer','errors')}"><input type="text" id="strasseUndHausnummer" name="strasseUndHausnummer" value="${fieldValue(bean:adresseInstance,field:'strasseUndHausnummer')}" /></td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name"><label for="plz"><g:message code="adresse.view.label.plz" />:</label></td>
              <td valign="top" class="value ${hasErrors(bean:adresseInstance,field:'plz','errors')}"><input type="text" id="plz" name="plz" value="${fieldValue(bean:adresseInstance,field:'plz')}" /></td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name"><label for="ort"><g:message code="adresse.view.label.ort" />:</label></td>
              <td valign="top" class="value ${hasErrors(bean:adresseInstance,field:'ort','errors')}"><input type="text" id="ort" name="ort" value="${fieldValue(bean:adresseInstance,field:'ort')}" /></td>
            </tr>

          </tbody>
        </table>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="telefonnummer"><g:message code="versuchsperson.view.label.telefon" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'telefonnummer','errors')}"><input type="text" id="telefonnummer" name="telefonnummer" value="${fieldValue(bean:versuchspersonInstance,field:'telefonnummer')}" /></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="emailadresse"><g:message code="versuchsperson.view.label.email" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'emailadresse','errors')}"><input type="text" id="emailadresse" name="emailadresse" value="${fieldValue(bean:versuchspersonInstance,field:'emailadresse')}" /></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="besonderheiten"><g:message code="versuchsperson.view.label.besonderheiten" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'besonderheiten','errors')}">
            <textarea id="besonderheiten" name="besonderheiten">${fieldValue(bean:versuchspersonInstance,field:'besonderheiten')}</textarea>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="inStudie"><g:message code="versuchsperson.view.label.inStudie" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'inStudie','errors')}"><g:checkBox value="${versuchspersonInstance?.inStudie}" ${(versuchspersonInstance?.inStudie) ? "checked" : ""} name="inStudie"></g:checkBox></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="blacklisted"><g:message code="versuchsperson.view.label.blacklisted" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'blacklisted','errors')}"><g:checkBox value="${versuchspersonInstance?.blacklisted}" ${(versuchspersonInstance?.blacklisted) ? "checked" : ""} name="blacklisted"></g:checkBox></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="letzterKontakt"><g:message code="versuchsperson.view.label.letzterKontakt" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'letzterKontakt','errors')}"><g:datePicker name="letzterKontakt" value="${versuchspersonInstance?.letzterKontakt}" precision="month"></g:datePicker></td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name"><label for="letzterKontaktNotizen"><g:message code="versuchsperson.view.label.notizenLetzterKontakt" />:</label></td>
        <td valign="top" class="value ${hasErrors(bean:versuchspersonInstance,field:'letzterKontaktNotizen','errors')}"><input type="text" id="letzterKontaktNotizen" name="letzterKontaktNotizen" value="${fieldValue(bean:versuchspersonInstance,field:'letzterKontaktNotizen')}" /></td>
      </tr>
      
      <script>$(document).ready(function() {
  		${remoteFunction(action:'showBildung',update:'bildung', params:[personId:versuchspersonInstance?.id, selectedAltersklasse:versuchspersonInstance?.altersklasse])};
	  });</script>
      
      <tr class="prop">
        <td valign="top" class="name"><label for="studien">Studien:</label></td>
        <td valign="top" class="value ${hasErrors(bean: studieInstance, field: 'tests', 'errors')}">
          <g:assignmentBoxes availableList="${availableStudien}" selectedList="${versuchspersonInstance?.studien}" selectedParam="selectedStudien" prefix="studienSelector"/>
        </td>
      </tr>

    </tbody>
  </table>
  </div>
  <div class="buttons">
    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'general.save', default: 'Update')}" /></span>
    <span class="button"><g:actionSubmit class="cancel" action="cancel" value="${message(code: 'general.cancel', default: 'Cancel')}" /></span>
    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'general.delete', default: 'Delete')}"
      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </span>
  </div>
</g:form></div>
</body>
</html>
