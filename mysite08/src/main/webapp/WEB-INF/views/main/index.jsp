<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page = "/WEB-INF/views/includes/header.jsp"></jsp:include>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" src="${pageContext.request.contextPath}${site.profile}" style="width:200px">
					<h2>${site.welcome}</h2>
					<p>
						${site.descrpition}<br><br>
						<a href="${pageContext.request.contextPath}/guestbook">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<jsp:include page = "/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page = "/WEB-INF/views/includes/footer.jsp"/>	
	</div>
</body>
</html>