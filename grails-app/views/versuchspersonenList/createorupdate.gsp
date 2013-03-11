
<%@ page import="de.ist_dresden.vpdb.entity.VersuchspersonenList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'versuchspersonenList.label', default: 'Versuchspersonen-Liste')}" />
<title><g:message code="versuchspersonenList.edit.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav">
	<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home" /></a></span> 
	<span class="menuButton"><g:link class="list" action="list">
		<g:message code="versuchspersonenList.list.label" args="[entityName]" />
	</g:link></span>
</div>
<div class="body">
	<h1><g:message code="versuchspersonenList.edit.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
	  <div class="message">
	  ${flash.message}
	  </div>
	</g:if>
	<g:hasErrors bean="${versuchspersonenListInstance}">
	  <div class="errors"><g:renderErrors bean="${versuchspersonenListInstance}" as="list" /></div>
	</g:hasErrors> 
	<g:form action="save" method="post">
	  <input type="hidden" value="${fieldValue(bean:versuchspersonenListInstance,field:'id')}" name="id" />
	  <div class="dialog">
		  <table>
		    <tbody>
				<tr class="prop">
					<td valign="top" class="name"><label for="name"><g:message code="versuchspersonenList.name.label" default="Name" /></label></td>
					<td valign="top" class="value ${hasErrors(bean: versuchspersonenListInstance, field: 'name', 'errors')}"><g:textField name="name" value="${versuchspersonenListInstance?.name}" /></td>
				</tr>
				<g:if test="${versuchspersonenListInstance == null}">
					<tr class="prop">
						<td valign="top" class="name">
							<label for="name"><g:message code="versuchspersonenList.existingName.label" default="Vorhandener Name" /></label>
						</td>
						<td valign="top" class="value">
							<select name="existingName" id="existingName">
				                <option value="">...</option>
					            <g:each in="${de.ist_dresden.vpdb.entity.VersuchspersonenList?.list(sort:'name')}">
					                <option value="${it.name}">${it.name}</option>
					            </g:each>
							</select>
						</td>
					</tr>
				</g:if>
		    </tbody>
		  </table>
	  </div>
	  
	  <div class="buttons">
	    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'general.save', default: 'Update')}" /></span>
	    <span class="button"><g:actionSubmit class="cancel" action="cancel" value="${message(code: 'general.cancel', default: 'Cancel')}" /></span>
	    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'general.delete', default: 'Delete')}" 
	        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	    </span>
	    <span class="button">
	    	<g:actionSubmit class="restore" action="restore" value="${message(code: 'versuchspersonenList.restore.label', default: 'Wiederherstellen')}" />
	    </span>
	  </div>
	</g:form>
	
	<div class="list" style="margin-top:5px;background-color: black; filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50; z-index: 1;">
  		<table>
  		    <thead>
  		        <tr>
  		   	        <th><g:message code="versuchsperson.list.label.id"/></th>
  		        
  		   	        <th><g:message code="versuchsperson.view.label.nachname"/></th>
  		   	        <th><g:message code="versuchsperson.view.label.vorname"/></th>
  		        
  		   	        <th><g:message code="versuchsperson.list.label.birthday" /></th>
  		        </tr>
  		    </thead>
  		    <tbody>
  		    <g:each in="${versuchspersonInstanceList}" status="i" var="versuchspersonInstance">
  		        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
  		            <td>${fieldValue(bean:versuchspersonInstance, field:'id')}</td>
  		        
  		            <td>${fieldValue(bean:versuchspersonInstance, field:'nachname')}</td>
  		            <td>${fieldValue(bean:versuchspersonInstance, field:'vorname')}</td>
  		
  		            <td><g:dateFormat value="${versuchspersonInstance.geburtsdatum}" format="dd.MM.yyyy" /></td>
  		        </tr>
  		    </g:each>
  		    </tbody>
  		</table>
  	</div>
</div>
</body>
</html>
