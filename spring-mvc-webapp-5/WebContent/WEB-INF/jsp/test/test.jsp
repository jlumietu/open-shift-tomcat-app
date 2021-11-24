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
		<h2>Kubernetes integration:</h2>
		<ul>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/kubernetes/core/url.service" >core/url.service</a></li>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/kubernetes/core/response.service" >core/response.service</a></li>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/kubernetes/alt/url.service" >alt/url.service</a></li>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/kubernetes/alt/response.service" >alt/response.service</a></li>
		</ul>
		<h2>Dns Kubernetes integration</h2>
		<ul>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/dns/name.service">dns/name.service</a></li>
			<li><a href="${pageContext.request.contextPath}/services/system/cloud/dns/members.service">dns/members.service</a></li>
		</ul>
	</body>
</html>