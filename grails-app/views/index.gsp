<html>
    <head>
        <title>Welcome to Grails</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;"><g:message code="default.vpdb"/></h1>
        <div class="dialog" style="margin-left:20px;width:60%;">
              
            <ol>
              <li><g:link controller="files"><g:message code="files.upload"/></g:link></li>
              
              <li>
                <ul>
                  <li><g:link controller="search"><g:message code="default.vp.search"/></g:link></li>
                  <li><g:link controller="versuchsperson"><g:message code="default.vp.manage"/></g:link></li>
                  <li><g:link controller="versuchspersonenList"><g:message code="default.vp.list.manage"/></g:link></li><br/>
                  
                  <li><g:link controller="studie"><g:message code="default.studie.manage"/></g:link></li>
                  <li><g:link controller="test"><g:message code="default.test.manage"/></g:link></li>
                </ul>
              </li><br/>
              
              <li><g:link controller="files"><g:message code="files.download"/></g:link></li>
            </ol>
        </div>
        
	      <div class="warn" >
	        <p>
	        	<span class="emph"><g:message code="files.download.warn.title"/></span>
	        	<g:message code="files.download.warn.text"/>
	        </p>
	        <p>
	        	<g:message code="files.download.warn.detail"/>
	        <p>
	      </div>
    </body>
</html>