
<%@ page import="de.ist_dresden.vpdb.entity.Studie" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studie.label', default: 'Studie')}" />
        <title><g:message code="studie.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="studie.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="studie.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${studieInstance}">
            <div class="errors">
                <g:renderErrors bean="${studieInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden"
                  value="${fieldValue(bean:studieInstance,field:'id')}"
                  name="id" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="studie.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studieInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${studieInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tests"><g:message code="studie.tests.label" default="Tests" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studieInstance, field: 'tests', 'errors')}">
                                    <g:assignmentBoxes availableList="${availableTests}" selectedList="${studieInstance?.tests}" selectedParam="tests" prefix="testSelector"/>
                                    <!-- <g:select name="tests" from="${de.ist_dresden.vpdb.entity.Test.list(sort:'name')}" multiple="yes" optionKey="id" size="5" value="${studieInstance?.tests}" /> -->
                                </td>
                            </tr>
	
						      <tr class="prop">
						        <td valign="top" class="name">
						        	<label for="year"><g:message code="studie.year.label" />:</label>
						        </td>
                                <td valign="top" class="value ${hasErrors(bean: studieInstance, field: 'year', 'errors')}">
                                	<g:datePicker name="year" value="${studieInstance?.year}" precision="year"/>
              					</td>
						      </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'general.save', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="cancel" action="cancel" value="${message(code: 'general.cancel', default: 'Cancel')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'general.delete', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
