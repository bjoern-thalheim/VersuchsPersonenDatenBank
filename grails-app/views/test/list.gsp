
<%@ page import="de.ist_dresden.vpdb.entity.Test" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'test.label', default: 'Test')}" />
        <title><g:message code="test.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home"/></a></span>
            <span class="menuButton"><g:link class="create" action="createorupdate"><g:message code="test.list.label.new" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="crossreference" controller="studie"><g:message code="default.studie.manage"/></g:link></span>
            <span class="menuButton"><g:link class="exit" controller="files"><g:message code="default.exit"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="test.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
                <div class="errors">${flash.error}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="${message(code: 'test.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${testInstanceList}" status="i" var="testInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" onclick="window.location.href='/VPDB/test/edit/${testInstance.id}'">
                        
                            <td><g:link action="edit" id="${testInstance.id}">${fieldValue(bean: testInstance, field: "name")}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
