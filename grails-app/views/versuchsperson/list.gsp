<export:resource />

<%@ page import="de.ist_dresden.vpdb.entity.Versuchsperson" %>

<%@page import="de.ist_dresden.vpdb.data.XmlExporter"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title><g:message code="versuchsperson.list.label.list"/></title>
        <g:javascript>
        jQuery(document).ready(function() {
            jQuery('#toggleCheckbox').click(function() {
                jQuery('input[name=selectedIds]').each(function(index) {
                    if( jQuery(this).attr('checked') == true) {
                        jQuery(this).attr('checked', false);
                    } else {
                        jQuery(this).attr('checked', true);
                    }
                });
            });
        });
        </g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}"><g:message code="default.list.label.home"/></a></span>
            <span class="menuButton"><g:link class="create" action="createorupdate"><g:message code="versuchsperson.list.label.new"/></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" controller="search"><g:message code="search.form.title"/></g:link></span>
            <span class="menuButton"><g:link class="save" action="createorupdate" controller="versuchspersonenList"><g:message code="versuchspersonenList.save"/></g:link></span>
            <span class="menuButton"><g:link class="exit" controller="files"><g:message code="default.exit"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="versuchsperson.list.label.list"/></h1>
        	<g:form method="post" >
            	<span class="button"><g:actionSubmit class="search" controller="versuchsperson" action="loadAll" value="${message(code: 'versuchsperson.list.label.loadall', default: 'Alle laden')}" /></span>
            </g:form>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
            <div class="errors">${flash.error}</div>
            </g:if>
            <div class="list">
            <g:form action="listSpecific">
                <table>
                    <thead>
                        <tr>
                            <th><img  id="toggleCheckbox" src="../images/toggle.jpg" width="16px" height="16px" /></th>
                   	        <g:sortableColumn property="id" title="Id" titleKey="versuchsperson.list.label.id" />
                        
                   	        <g:sortableColumn property="nachname" title="Nachname" titleKey="versuchsperson.list.label.nachname" />
                   	        <g:sortableColumn property="vorname" title="Vorname" titleKey="versuchsperson.list.label.vorname" />
                        
                   	        <g:sortableColumn property="geschlecht" title="Geschlecht" titleKey="versuchsperson.view.label.geschlecht" />
                   	        <g:sortableColumn property="geburtsdatum" title="Geburtsdatum" titleKey="versuchsperson.list.label.birthday" />
                   	        <g:sortableColumn property="telefonnummer" title="Telefon" titleKey="versuchsperson.view.label.telefon" />
                   	        <g:sortableColumn property="emailadresse" title="Email" titleKey="versuchsperson.view.label.email" />
                   	        <g:sortableColumn property="studien" title="Studien" titleKey="versuchsperson.view.label.studien" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${versuchspersonInstanceList}" status="i" var="versuchspersonInstance">
                        <tr class="${versuchspersonInstance.blacklisted ? 'blacklisted' : ((i % 2) == 0 ? 'odd' : 'even')}">
                            <td><input type="checkbox" name="selectedIds" value="${versuchspersonInstance.id}"/></td>
                            
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'id')}</td>
                        
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'nachname')}</td>
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'vorname')}</td>

                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'"><g:message code="versuchsperson.view.value.geschlecht.${fieldValue(bean:versuchspersonInstance, field:'geschlecht')}" /></td>
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'"><g:dateFormat value="${versuchspersonInstance.geburtsdatum}" format="dd.MM.yyyy" /></td>
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'telefonnummer')}</td>
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'emailadresse')}</td>
                            <td onclick="window.location.href='/VPDB/versuchsperson/edit/${versuchspersonInstance.id}'">${fieldValue(bean:versuchspersonInstance, field:'studien')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <g:submitButton value="Auswaehlen" name="Auswaehlen"/>
            </div>

			<div class="dialog">
			<table>
				<tbody>
					<tr class="prop">
						<td valign="top">
							<g:checkBox name="setInStudie"></g:checkBox>
						</td>
						<td valign="top" class="name"><label for="inStudie"><g:message
							code="versuchsperson.view.label.inStudie" />:</label></td>
						<td valign="top"
							class="value ${hasErrors(bean:versuchspersonInstance,field:'inStudie','errors')}">
							<g:checkBox name="inStudie"></g:checkBox>
						</td>
					</tr>
					<tr class="prop">
						<td valign="top">
							<g:checkBox name="setLetzterKontakt"></g:checkBox>
						</td>
						<td valign="top" class="name"><label for="letzterKontakt"><g:message
							code="versuchsperson.view.label.letzterKontakt" />:</label></td>
						<td valign="top"
							class="value ${hasErrors(bean:versuchspersonInstance,field:'letzterKontakt','errors')}"><g:datePicker
							name="letzterKontakt"
							value="${versuchspersonInstance?.letzterKontakt}" precision="month"></g:datePicker></td>
					</tr>
			
					<tr class="prop">
						<td valign="top">
							<g:checkBox name="setLetzterKontaktNotizen"></g:checkBox>
						</td>
						<td valign="top" class="name"><label for="letzterKontaktNotizen"><g:message
							code="versuchsperson.view.label.notizenLetzterKontakt" />:</label></td>
						<td valign="top"
							class="value ${hasErrors(bean:versuchspersonInstance,field:'letzterKontaktNotizen','errors')}"><input
							type="text" id="letzterKontaktNotizen" name="letzterKontaktNotizen"
							value="${fieldValue(bean:versuchspersonInstance,field:'letzterKontaktNotizen')}" /></td>
					</tr>
				      
				      <tr class="prop">
						<td valign="top">
							<g:checkBox name="setStudien"></g:checkBox>
						</td>
				        <td valign="top" class="name"><label for="studien">Studien:</label></td>
				        <td valign="top" class="value ${hasErrors(bean: studieInstance, field: 'tests', 'errors')}">
				          <g:assignmentBoxes availableList="${availableStudien}" selectedList="" selectedParam="selectedStudien" prefix="studienSelector"/>
				        </td>
				      </tr>
				</tbody>
			</table>
            <g:submitButton value="Attribute setzen" name="setAttributes" action="updateBatch"/>
			</div>
            </g:form>

			<div class="export">
				<table>
					<tbody>
						<tr>
							<td>Normaler Export</td>
							<td>Voller Export</td>
		        		</tr>
		        		<tr>
		        			<td><export:formats formats="[ 'pdf', 'csv' ]" /></td>
		        			<td><export:formats formats="[ 'pdf', 'csv' ]" params="[exportAll:'true']"/></td>
		        		</tr>
		        	</tbody>
		        </table>
	        </div>
        </div>
        
    </body>
</html>
