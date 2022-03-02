<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
let messageBox = function(title, message, callback){
	$("#dialog-message p").html(message);
	$("#dialog-message")
	.attr("title",title)
	.dialog({
		width:300,
		modal: true,
		buttons: {
	        "확인": function() {
	          $(this).dialog("close");
	        }
	      },
		close:callback
	});
}

let render = function(vo) {
	let html = 
			"<li data-no='" + vo.no + "'>" +
			"<strong>" + vo.name + "</strong>" +
			"<p>" + vo.message + "</p>" +
			"<strong></strong>" +
			"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
			"</li>";
			
	 return html;		
}

let startNo=-1

let fetch = function(){
	
	$.ajax({
		url: '${pageContext.request.contextPath }/api/guestbook/list?startNo=' + startNo,
		type: 'get',
		dataType: 'json',
		success: function(response) {
			console.log(response)
			if(response.result !== 'success') {
				console.error(response.message);
				return;
			}
			response.data.forEach(element=>{
				startNo=element.no;
				$("#list-guestbook").append(render(element));
			});
		}
	});
}

$(function(){
		
	// 삭제다이얼로그 객체 만들기
	let dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function() {
				let no = $("#hidden-no").val();
				let password = $("#password-delete").val();
				let url = "${pageContext.request.contextPath}/api/guestbook/" + no;
				
				$.ajax({
					url: url,
					type: 'delete',
					dataType: 'json',
					data: "password=" + password,
					success: function(response) {
						if(response.result !== 'success') {
							console.error(response.message);
							return;
						}
						
						if(response.data == -1) {
							$(".validateTips.error").show();
							$("#password-delete").val("").focus();
							return;
						}
						
						// 삭제가 된 경우
						$("#list-guestbook li[data-no='"   +  response.data + "']").remove();
						dialogDelete.dialog('close');
					}
				}); 
			},
			"취소": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			$(".validateTips.error").hide();
			$("#password-delete").val("");
			$("#hidden-no").val("");
		}
	});
	
	// 글삭제 버튼 Click 이벤트 처리(Live Event)
	$(document).on('click', "#list-guestbook li a", function(event) {
		event.preventDefault();
		let no = $(this).data("no");
		$("#hidden-no").val(no);
		dialogDelete.dialog('open');
	}); 
	
	// 최초리스트 가져오기
	fetch();
	
	$(window).scroll(function(event){
		let scrollHeight = $(window).scrollTop()+$(window).height(); //	올라간 높이+현재 보이는 높이
		let documentHeight = $(document).height(); //전체높이
		
		//30px 남아있을때 추가 
		if(scrollHeight +30> documentHeight){
			fetch();
		}
	});

	// div내에서 스크롤을 사용하는 코드
/* 	let totalHeight=0;
	$("#list-guestbook").scroll(function(event){
		let nowHeight=$("#list-guestbook").scrollTop()
		console.log($("#list-guestbook").scrollTop())
	
	//30px 남아있을때 추가 
	if(totalHeight==0){
		if(nowHeight > totalHeight + 25 ){
			fetch();
			totalHeight=nowHeight;
		}
	}
	else{
		if(nowHeight > totalHeight + 270 ){
			fetch();
			totalHeight=nowHeight;
		}
	}
});  */

	// 방명록 추가 버튼
	$("#add-form").submit(function(event) {
		event.preventDefault();
		
		let vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.message = $("#tx-content").val();
		
		// 이름 유효성 체크
		if(vo.name===""){
			messageBox("방명록","이름은 필수 항목입니다",function(){
				$("#input-name").focus();
			})
			return;
		}
		
		// 비밀번호 유효성 체크
		if(vo.password===""){
			messageBox("방명록","비밀번호는 필수 항목입니다",function(){
				$("#input-password").focus();
			})
			return;
		}
		
		// 메시지 유효성 체크
		if(vo.message===""){
			messageBox("방명록","메시지는 필수 항목입니다",function(){
				$("#tx-content").focus();
			})
			return;
		}

		$.ajax({
			url: '${pageContext.request.contextPath}/api/guestbook',
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response) {
				if(response.result !== 'success') {
					console.error(response.message);
					return;
				}
				
				let html = render(response.data);
				$("#list-guestbook").prepend(html);
			}
		});
	});
	

});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">									
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-spa"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>