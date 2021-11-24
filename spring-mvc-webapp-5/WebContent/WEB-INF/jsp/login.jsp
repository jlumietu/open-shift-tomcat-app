<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><springib:message code="lblApplicationName" toUpperCase="true" /></title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}${properties['static.resources.path']}${properties['bootstrap.sbadmin2.theme.path']}/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}${properties['static.resources.path']}${properties['bootstrap.sbadmin2.theme.path']}/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}${properties['static.resources.path']}${properties['bootstrap.sbadmin2.theme.path']}/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}${properties['static.resources.path']}${properties['bootstrap.sbadmin2.theme.path']}/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body >
	
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><springib:message code="lblSignIn" toUpperCase="true" /></h3>
                    </div>
                    <div class="panel-body">
						<c:if test="${not empty error}">
							<div class="errorValidacion" >
								<span>	<springib:message code="lblSignInErrors" /></span>
								<ul> 
									<li>
										${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
									</li>
								</ul>
							</div>
						</c:if>
						<div class="errorValidacion" style="display: none;">
							<span>
								<spring:message code="lblSignInErrors" /></span>
								<ul></ul>
						</div>
                        <form role="form" method="POST" action="${pageContext.request.contextPath}${properties['application.login.processing']}">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder=<spring:message code="lblUser"/> id="usrname" name="usrname" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder=<spring:message code="lblPassword"/> id='usrpwd'  name='usrpwd' type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember-me" id="remember-me" type="checkbox" value="<spring:message code="lblRememberMe"/>"><spring:message code="lblRememberMe"/>
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" class="btn btn-lg btn-success btn-block" name="submit"><spring:message code="lblLogin"/></button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
       
</body>
</html>