<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
	<head>
		<title>${pageContext.request.contextPath} <springib:message code="lblWorks" toUpperCase="true"/></title>
	</head>
	<body>
		<h1>${pageContext.request.contextPath} <springib:message code="lblWorks" toUpperCase="true"/></h1>
		<ul>
			<li><springib:message code="application.name" />: <spring:eval expression="@applicationProperties.getProperty('application.name')" /> </li>
			<li><springib:message code="application.version" />: <c:out value="${versionInfo.version}" /></li>
			<li><springib:message code="application.revision" />: <c:out value="${versionInfo.revision}" /></li>
		</ul>
		<div>
			<h2 class="page-header">Enhorabuena <sec:authentication property="name" />, el proyecto arranca</h2>
		</div>
	</body>
</html>