<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>

<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.request.contextPath}/board/reply">
					<input type = "hidden" name= "no" value="${replyvo.no}">
					<input type = "hidden" name = "groupNo" value = "${replyvo.groupNo}">
					<input type = "hidden" name = "orderNo" value = "${replyvo.orderNo}">
					<input type = "hidden" name = "depth" value = "${replyvo.depth}">
					<input type = "hidden" name= "page" value="${param.page}">
					<input type = "hidden" name= "kwd" value="${param.kwd}">
					<input type = "hidden" name= "value" value="${param.value}">
		
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value = ""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?page=${param.page}&kwd=${param.kwd}&value=${param.value}">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
<c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>

</body>
</html>