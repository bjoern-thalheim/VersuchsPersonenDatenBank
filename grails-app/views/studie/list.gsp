
<%@ page import="de.ist_dresden.vpdb.entity.Studie" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studie.label', default: 'Studie')}" />
        <title><g:message code="studie.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home"/></a></span>
            <span class="menuButton"><g:link class="create" action="createorupdate"><g:message code="studie.list.label.new" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="crossreference" controller="test"><g:message code="default.test.manage"/></g:link></span>
            <span class="menuButton"><g:link class="exit" controller="files"><g:message code="default.exit"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="studie.list.label" args="[entityName]" /></h1>
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
                        
                            <g:sortableColumn property="name" title="${message(code: 'studie.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${studieInstanceList}" status="i" var="studieInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}" onclick="window.location.href='/VPDB/studie/edit/${studieInstance.id}'">
                        
                            <td><g:link action="edit" id="${studieInstance.id}">${fieldValue(bean: studieInstance, field: "name")}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
