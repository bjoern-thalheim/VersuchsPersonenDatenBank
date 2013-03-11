
<%@ page import="de.ist_dresden.vpdb.entity.Studie" %>

<%@page import="de.ist_dresden.vpdb.data.XmlExporter"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="files.head" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.list.label.home"/></a></span>
        </div>
        <div class="body">
              <h1><g:message code="files.upload"/></h1>
              <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
              </g:if>
              
              <p>
                  <g:uploadForm name="allUpload" action="upload">
                   	<input type="file" name="versuchspersonendaten" />
                   	<br/>
                   	<g:submitButton name="upload" value="${message(code:'files.upload')}"/>
                  </g:uploadForm>
              </p>

              <h1><g:message code="files.download"/></h1>
		      <div class="warn" style="margin-left:0;" >
		        <p>
		        	<span class="emph"><g:message code="files.download.warn.title"/></span>
		        	<g:message code="files.download.warn.text"/>
		        </p>
		        <p>
		        	<g:message code="files.download.warn.detail"/>
		        <p>
		      </div>
              <g:form name="allDownload" action="download">
              	<g:submitButton name="upload" value="${message(code:'files.download')}" />
              </g:form>
        </div>            
    </body>
</html>