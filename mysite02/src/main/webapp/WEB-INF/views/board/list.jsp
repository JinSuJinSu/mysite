<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.poscoict.mysite.dao.BoardDao" %>
<%@ page import="com.poscoict.mysite.vo.BoardVo" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1 id="size" style="display:none">${list.size()}</h1>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board" method="post">
				<select name="condition">
				    <option value="" disabled>검색</option>
				    <option value="title">제목</option>
				    <option value="content">내용</option>
				</select>
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:forEach items="${data}" var="vo">
					<tr>
						<td>${vo.no}</td>
						<td style="text-align:left; padding-left:${(vo.depth-1)*20}px;">
						<c:if test = "${vo.depth>1}"><img src="${pageContext.servletContext.contextPath}/assets/images/reply.png">
						</c:if>
						<c:choose>
							<c:when test="${vo.title!='삭제된 글입니다.'}">
								<a href="${pageContext.servletContext.contextPath}/board?a=view&no=${vo.no}">${vo.title}</a>
							</c:when>
							<c:otherwise>
								${vo.title}
							</c:otherwise>		
						</c:choose>
						</td>
						
						<td>${vo.userName}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<td>
						<c:if test = "${authUser.name == vo.userName && vo.title != '삭제된 글입니다.'}">
						<a href="${pageContext.servletContext.contextPath}/board?a=delete&no=${vo.no}" class="del" 
						style='background-image: url("${pageContext.servletContext.contextPath}/assets/images/recycle.png")'>삭제</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
<!-- 					paging[0]= startpage, paging[1]= endpage
					search[0]= search_condition(title,content), search[1]=search_keyword -->
					<ul>
						<c:if test = "${paging[0]!=1}">
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${paging[0]-5}&condition=${search[0]}&kwd=${search[1]}">◀</a></li>
						</c:if>
						<c:forEach  begin="${paging[0]}" end="${paging[1]}"  step="1" var="page">
							<li class="selected">
							<a href="${pageContext.servletContext.contextPath}/board?page=${page}&condition=${search[0]}&kwd=${search[1]}">${page}</a></li>
						</c:forEach>
						<c:if test = "${paging[1]!=Math.ceil(list.size()/5)}">
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${paging[0]+5}&condition=${search[0]}&kwd=${search[1]}">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->		
				<div class="bottom">
					<c:choose>
						<c:when test="${empty authUser}">
							<a href="${pageContext.servletContext.contextPath}/user?a=loginform">글쓰기</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
						</c:otherwise>		
					</c:choose>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>