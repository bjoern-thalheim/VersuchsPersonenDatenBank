
<%@ page import="de.ist_dresden.vpdb.entity.Versuchsperson" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title><g:message code="versuchsperson.list.label.list"/></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}"><g:message code="default.list.label.home"/></a></span>
            <span class="menuButton"><g:link class="search" action="cancel"><g:message code="search.execute"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="versuchsperson.list.label.list"/></h1>
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
                   	        <g:sortableColumn property="id" title="Id" titleKey="versuchsperson.list.label.id" />
                   	        
                            <g:sortableColumn property="nachname" title="Name" titleKey="versuchsperson.list.label.nachname" />
                            <g:sortableColumn property="vorname" title="Vorname" titleKey="versuchsperson.list.label.vorname" />
                            <g:sortableColumn property="geburtsdatum" title="Geburtsdatum" titleKey="versuchsperson.list.label.birthday" />
                        
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
        
        <export:formats formats="['csv', 'pdf', 'xml']" />
    </body>
</html>
