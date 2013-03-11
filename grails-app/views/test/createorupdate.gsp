
<%@ page import="de.ist_dresden.vpdb.entity.Test"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'test.label', default: 'Test')}" />
<title><g:message code="test.edit.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav"><span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home" /></a></span> <span class="menuButton"><g:link class="list" action="list">
  <g:message code="test.list.label" args="[entityName]" />
</g:link></span></div>
<div class="body">
<h1><g:message code="test.edit.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
  <div class="message">
  ${flash.message}
  </div>
</g:if> <g:hasErrors bean="${testInstance}">
  <div class="errors"><g:renderErrors bean="${testInstance}" as="list" /></div>
</g:hasErrors> <g:form action="save" method="post">
  <input type="hidden" value="${fieldValue(bean:testInstance,field:'id')}" name="id" />
  <div class="dialog">
  <table>
    <tbody>

      <tr class="prop">
        <td valign="top" class="name"><label for="name"><g:message code="test.name.label" default="Name" /></label></td>
        <td valign="top" class="value ${hasErrors(bean: testInstance, field: 'name', 'errors')}"><g:textField name="name" value="${testInstance?.name}" /></td>
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
