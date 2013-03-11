
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title><g:message code="search.form.title" /></title>
</head>
<body>
      <div class="nav">
          <span class="menuButton"><a class="home" href="${resource(dir:'')}"><g:message code="default.list.label.home"/></a></span>
          <span class="menuButton"><g:link class="exit" controller="files"><g:message code="default.exit"/></g:link></span>
      </div>
      <div class="body">
        <h1><g:message code="search.form.title" /></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:form method="post" >
          <div class="dialog">
            <table>
              <tbody>   
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.tests.whitelist.label" default="whitelist-tests" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="whitelisttest" from="${de.ist_dresden.vpdb.entity.Test.list(sort:'name')}" multiple="yes" optionKey="id" size="5" />
                    </td>
                </tr>   
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.tests.blacklist.label" default="blacklist-tests" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="blacklisttest" from="${de.ist_dresden.vpdb.entity.Test.list(sort:'name')}" multiple="yes" optionKey="id" size="5" />
                    </td>
                </tr>   
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.studien.whitelist.label" default="whitelist-studien" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="whiteliststudien" from="${de.ist_dresden.vpdb.entity.Studie.list(sort:'name')}" multiple="yes" optionKey="id" size="5" />
                    </td>
                </tr>   
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.studien.blacklist.label" default="blacklist-studien" /></label>
                    </td>
                    <td valign="top" class="value">
                        <g:select name="blackliststudien" from="${de.ist_dresden.vpdb.entity.Studie.list(sort:'name')}" multiple="yes" optionKey="id" size="5" />
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.tests.sex.label" default="geschlecht" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="geschlecht" id="geschlecht">
			            <option value="BOTH" selected><g:message code="search.both"/></option>
			            <g:each in="${de.ist_dresden.vpdb.entity.Geschlecht?.values()}">
			                <option value="${it}"><g:message code="versuchsperson.view.value.geschlecht.${it}" /></option>
			            </g:each>
                    </td>
                </tr>

		        <tr class="prop">
		        	<td valign="top" class="name"><label for="geburtsdatumuntergrenze"><g:message code="search.geburtsdatum.lower.label" /></label></td>
		        	<td valign="top" class="value"><g:datePicker name="geburtsdatumuntergrenze" value="${geburtsdatumuntergrenze}" precision="day"></g:datePicker></td>
		      	</tr>

		        <tr class="prop">
		        	<td valign="top" class="name"><label for="geburtsdatumobergrenze"><g:message code="search.geburtsdatum.upper.label" /></label></td>
		        	<td valign="top" class="value"><g:datePicker name="geburtsdatumobergrenze" precision="day"></g:datePicker></td>
		      	</tr>
		      	
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.inStudie.label" default="inStudie" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="inStudie" id="inStudie">
			            <option value="BOTH" selected><g:message code="search.both"/></option>
			            <option value="TRUE"><g:message code="search.inStudie.yes"/></option>
			            <option value="FALSE"><g:message code="search.inStudie.no"/></option>
                    </td>
                </tr>
		      	
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="search.blacklisted.label" default="blacklisted" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="blacklisted" id="blacklisted">
			            <option value="BOTH" selected><g:message code="search.both"/></option>
			            <option value="TRUE"><g:message code="search.blacklisted.yes"/></option>
			            <option value="FALSE"><g:message code="search.blacklisted.no"/></option>
                    </td>
                </tr>

		        <tr class="prop">
		        	<td valign="top" class="name"><label for="letzterkontaktuntergrenze"><g:message code="search.letzterkontakt.lower.label" /></label></td>
		        	<td valign="top" class="value"><g:datePicker name="letzterkontaktuntergrenze" value="${letzterkontaktuntergrenze}" precision="month"></g:datePicker></td>
		      	</tr>

		        <tr class="prop">
		        	<td valign="top" class="name"><label for="letzterkontaktobergrenze"><g:message code="search.letzterkontakt.upper.label" /></label></td>
		        	<td valign="top" class="value"><g:datePicker name="letzterkontaktobergrenze" precision="month"></g:datePicker></td>
		      	</tr>
		      	
                <tr class="prop">
                    <td valign="top" class="name">
                      <label for="name"><g:message code="versuchsperson.view.label.altersklasse" default="altersklasse" /></label>
                    </td>
                    <td valign="top" class="value">
			          <select name="altersklasse" id="altersklasse">
			            <option value="BOTH" selected onclick="${remoteFunction(action:'showBildung',update:'bildung')}">
			            	<g:message code="search.both"/>
			            </option>
            			<g:each in="${de.ist_dresden.vpdb.entity.Altersklasse?.values()}">
		                	<option value="${it}" onclick="${remoteFunction(action:'showBildung',update:'bildung', params:[selectedAltersklasse:it])}">
		                		<g:message code="versuchsperson.view.value.altersklasse.${it}" />
		                	</option>
		                </g:each>
                    </td>
                </tr>
			      
			      <tr>
			        <td></td>
			        <td>
			          <table id="bildung"></table>
			        </td>
			      </tr>
		      	
              </tbody>
            </table>
          </div>
          <div class="buttons">
              <span class="button"><g:actionSubmit class="search" action="execute" value="${message(code: 'search.execute', default: 'Suchen')}" /></span>
              <span class="button"><g:actionSubmit class="cancel" action="cancel" value="${message(code: 'general.cancel', default: 'Abbrechen')}" /></span>
          </div>
        </g:form>
      </div>
</body>
</html>