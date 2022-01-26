<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%-- <%@ page import="com.poscoict.mysite.dao.BoardDao" %>
<%@ page import="com.poscoict.mysite.vo.BoardVo" %> --%>
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
				<select name="kwd">
				    <option value="" disabled>검색</option>
				    <option value="title">제목</option>
				    <option value="content">내용</option>
				</select>
					<input type="text" id="value" name="value" value="">
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
					
					<c:forEach items="${map.list}" var="vo" varStatus="status">
					<tr>
						<!-- limit로 5개씩 정렬된 게시글을 나타낼 때 list 사이즈에서 데이터 테이블 no 컬럼의 값의 위치를 구해 빼면 순서대로 글 번호를 출력할 수 있다.-->
						<td>${map.totalList.size()-map.totalList.indexOf(vo.no)}</td>
						<td style="text-align:left; padding-left:${(vo.depth-1)*20}px;">
						<c:if test = "${vo.depth>1}"><img src="${pageContext.servletContext.contextPath}/assets/images/reply.png">
						</c:if>
						<c:choose>
							<c:when test="${vo.title!='삭제된 글입니다.'}">
								<a href="${pageContext.servletContext.contextPath}/board/view/${vo.no}?page=${map.currentPage}&kwd=${map.kwd}&value=${map.value}">${vo.title}</a>
							</c:when>
							<c:otherwise>
								${vo.title}
							</c:otherwise>		
						</c:choose>
						</td>				
						<td>${vo.name}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<td>
						<c:if test = "${authUser.name == vo.name && vo.title != '삭제된 글입니다.'}">
						<a href="${pageContext.servletContext.contextPath}/board/delete/${vo.no}?page=${map.currentPage}&kwd=${map.kwd}&value=${map.value}" class="del" 
						style='background-image: url("${pageContext.servletContext.contextPath}/assets/images/recycle.png")'>삭제</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
									<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test = "${map.startPage!=1}">
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${map.startPage-5}&kwd=${map.kwd}&value=${map.value}&arrow=arrow">◀</a></li>
						</c:if>
						<c:forEach  begin="${map.startPage}" end="${map.endPage}"  step="1" var="page">
							<c:choose>
								<c:when test="${map.currentPage==page}">
									<li class="selected">
									<a href="${pageContext.servletContext.contextPath}/board?page=${page}&kwd=${map.kwd}&value=${map.value}">${page}</a></li>
								</c:when>
								<c:otherwise>
								<li>
								<a href="${pageContext.servletContext.contextPath}/board?&page=${page}&kwd=${map.kwd}&value=${map.value}">${page}</a></li>
								</c:otherwise>	
							</c:choose>		
						</c:forEach>
						<c:if test = "${map.endPage!=Math.ceil(map.totalList.size()/5)}">
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${map.startPage+5}&kwd=${map.kwd}&value=${map.value}&arrow=arrow">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->			
				<div class="bottom">
					<c:choose>
						<c:when test="${empty authUser}">
							<a href="${pageContext.servletContext.contextPath}/user/login">글쓰기</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath}/board/write?page=${map.currentPage}&kwd=${map.kwd}&value=${map.value}" id="new-book">글쓰기</a>
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