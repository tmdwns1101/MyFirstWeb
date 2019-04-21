<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.UserDAO" %>
<%@ page import="evaluation.EvaluationDAO" %>
<%@ page import="evaluation.EvaluationDTO" %>
<%@ page import="notebox.NoteBoxDTO" %>
<%@ page import="notebox.NoteBoxDAO" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>강의평가 웹 사이트</title>
<!-- 부트스트랩 CSS 추가하기 -->
<link rel="stylesheet" href="./css/bootstrap.min.css">
<!-- 커스텀 CSS 추가하기 -->
<link rel="stylesheet" href="./css/custom.css">
<!--  구글 차트 -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<!-- 제이쿼리 자바스크립트 추가하기 -->
<script src="./js/jquery.min.js"></script>	
<!-- 파퍼 자바스크립트 추가하기 -->
<script src="./js/popper.min.js"></script>
<!-- 부트스트랩 자바스크립트 추가하기 -->
<script src="./js/bootstrap.min.js"></script>
	

	
</head>
<body>
<%
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
	}
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	System.out.println("current session : " + userID);
	boolean emailChecked = new UserDAO().getUserEmailChecked(userID);
	if(emailChecked == false){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이메일 인증을 해주세요');");
		script.println("location.href = 'emailSendConfirm.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	
	
	
	NoteBoxDAO noteBoxDAO = new NoteBoxDAO();
	ArrayList<NoteBoxDTO> noteBoxinfoes = new ArrayList<NoteBoxDTO>();
	int result = noteBoxDAO.getNoteBoxInfo(userID, noteBoxinfoes);
	int cnt = 0;
	if(result == 1){
		cnt = noteBoxinfoes.size(); 
	}
	
	
%>
	

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">학점을 부탁해(강의평가)</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="index.jsp">메인</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<%
	if(userID == null){
		
%>       					
       					<a class="dropdown-item" href="userLogin.jsp">로그인</a>
          				<a class="dropdown-item" href="userJoin.jsp">회원가입</a>
<% 
	} else{
%>	
          				<a class="dropdown-item" href="userLogOutAction.jsp">로그아웃</a>
          				<a class="dropdown-item" href="myPage.jsp">마이페이지</a>
          				<a class="dropdown-item" href="noteBox.jsp">쪽지함</a>
          				
<%
	}
%>          				
          			</div>
				</li>	
			</ul>
			<form class="form-inline my-2 my-lg-0">
  				<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
     			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    		</form>
		</div>
	</nav>

	
	<section class="container">
<% 
	if(cnt != 0) {
		for(NoteBoxDTO cur : noteBoxinfoes) {
			
			String 	fromUserID = cur.getFromUserID();
			String 	noteTitle = cur.getNoteTitle();
			String 	noteContent = cur.getNoteContent();
			int noteBoxID = cur.getNoteBoxID();
			String evaluationID = cur.getEvaluationID();
			EvaluationDAO evaluationDAO = new EvaluationDAO();
			String lectureName = evaluationDAO.getLectureName(evaluationID);
			String professorName = evaluationDAO.getProfessorName(evaluationID);
			
			
%>

	 <div class="card bg-light mt-3">
			<div class="card-header bg-light">
				<div class="row">
					<div class="col-8 text-left"><%=noteTitle%></div>
					<!-- 추후 보낸 날짜 추가 할 것임 -->
					<div class="col-4 text-right">(강의명 : <%= lectureName%>  교수명 :<%= professorName%>)</div>
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title">
					<%=noteTitle%>
				</h5>
				<p class="card-text"><%=noteContent%></p>
				<div class="row">
					<div class="col-7 text-left"></div>
					<div class="col-5 text-right">
					<!-- 추후 삭제 답장 신고 기능 구현  -->
					<a onclick="return confirm('삭제하시겠습니까?');" href="#">삭제</a>
					<button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#" 
							data-evaluationid=<%= noteBoxID%>>답장</button>
					<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#" 
							data-noteboxid=<%= noteBoxID%>>신고</button>
					</div>
				</div>
			</div>
		</div> 
<% 
	} //118
%>
				
<%
	} //116
	
%>

	</section>
	
	<!--  추후 신고 답장 모달 추가 할 것임. -->
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"> <!-- 제목 -->
					<h5 class="modal-title" id="modal">신고하기</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">  <!-- 'x' 버튼 -->
						<span aria-hidden="true">&times;</span>
					</button> 
				</div>
				<div class="modal-body">  <!--  내용 -->
					<form action="./reportAction.jsp" method="post">  <!-- 신고 페이지로 데이터를 넘기기 위함 -->
						<div class="form-group">
							<label>신고 제목</label>
							<input type="text" name="reportTitle" class="form-control" maxlength="20">
						</div>
						<div class="form-group">
							<label>신고 내용</label>
							<textarea name="reportContent" class="form-control" maxlength= "2048" style="height:180px;" placeholder="허위 사실 신고시 관리자에 의해 정지처분 됩니다!"></textarea>
						</div>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">신고하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<!--  쪽지 보내기 모달 -->
	<div class="modal fade" id="sendNoteBoxModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"> <!-- 제목 -->
					<h5 class="modal-title" id="modal">쪽지 보내기</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">  <!-- 'x' 버튼 -->
						<span aria-hidden="true">&times;</span>
					</button> 
				</div>
				<div class="modal-body">  <!--  내용 -->
					<form action="./sendNoteBoxAction.jsp" method="post"> 
						<input type="hidden" id="to" name="to" class="form-control">
						
						<div class="form-group">
							<label>쪽지 제목</label>
							<input type="text" name="noteTitle" class="form-control" maxlength="20">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea name="noteContent" class="form-control" maxlength= "2048" style="height:180px;"></textarea>
						</div>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-success">보내기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<script type="text/javascript">
	
		$('#sendNoteBoxModal').on('show.bs.modal', function (event) {
  		var button = $(event.relatedTarget);
  		console.log(button);
  		var to = button.data('evaluationid'); // Extract info from data-* attributes
  		var modal = $(this);
  		modal.find('.modal-body input[id="to"]').val(to);
  		console.log(to);		
  		
		})

	</script>
	
	
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color:#FFFFFF;">
		Copyright &copy; 2019이승준All Right Reserved.
	</footer>
	
</body>
</html>