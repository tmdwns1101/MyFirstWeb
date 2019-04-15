<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.UserDAO" %>
<%@ page import="evaluation.EvaluationDAO" %>
<%@ page import="evaluation.EvaluationDTO" %>
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
	String filter1 = "전체";
	if(request.getParameter("lectureDivide") != null){
		filter1 = (String) request.getParameter("lectureDivide");
		
	}
	String filter2 = "최신순";
	if(request.getParameter("orderBy") != null){
		filter2 = (String) request.getParameter("orderBy");
		
	}
	String search = "";
	if(request.getParameter("search") != null){
		search = (String) request.getParameter("search");
		
	}
	
	System.out.println(filter2);
	
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	ArrayList<EvaluationDTO> evaluationInfoes = new ArrayList<EvaluationDTO>();
	int result = evaluationDAO.getEvaluationInfo(filter1, filter2, search, evaluationInfoes);
	int cnt = 0;
	if(result == 1){
		cnt = evaluationInfoes.size(); 
	}
	//Test test = new Test();
	//test.main();

	
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
		<form method="get" action="index.jsp" class="form-inline mt-3"> <!-- default method="get" -->
			<select name="lectureDivide" class="form-control mx-1 mt-2">  
				<option value="전체" selected>전체</option>
				<option value="전공">전공</option>
				<option value="교양">교양</option>
				<option value="기타">기타</option>	
			</select>
			<select name="orderBy" class="form-control mx-1 mt-2">  
				<option value="최신순" selected>최신 순</option>
				<option value="추천순">추천 순</option>
			</select>
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a> 
			<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고하기</a>
		</form>
		<!-- 카드 보드 후에는 데이터 베이스에서 가져올 예정 -->
		<div class="card bg-light mt-3">
			<div class="card-header bg-light">
				<div class="row">
					<div class="col-8 text-left">알고리즘&nbsp;<small>김동성</small></div>
					<div class="col-4 text-right">
						종합&nbsp;<span style="color:red;">A</span>
					</div>
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title">
					알고리즘에 대해 많이 알 수 있었어요!!&nbsp;<small>(2018년 2학기)</small>
				</h5>
				<p class="card-text">수업 시간에 집중해서 잘 따라가면 알고리즘이라는 것을 이해 할 수 있는 좋은 수업이에요. 하지만! 수업 내용상 매우 어려워요 ㅠㅠ</p>
				<div class="row">
					<div class="col-9 text-left">
						학점 취득 난이도&nbsp;<span style="color:red;">어려움</span>
						강의력&nbsp;<samp style="color:blue;">A</samp>
						<span style="color:green;">(추천 수 : 15)</span>
					</div>
					<div class="col-3 text-right">
						<a onclick="return confirm('추천하시겠습니까?');" href="./likeAction.jsp?evaluationID=">추천</a>  <!-- 게시물 ID를 찾아 데이터베이스 접근 할 예정 -->
						<a onclick="return confirm('삭제하시겠습니까?');" href="./deleteAction.jsp?evaluationID=">삭제</a>
					</div>
				</div>
			</div>
		</div> 
		<!-- Test start-->
<% 
	if(cnt != 0) {
		for(EvaluationDTO cur : evaluationInfoes) {
			int evaluationID = cur.getEvaluationID();
			String  evalUserID = cur.getUserID();
			String 	lectureName = cur.getLectureName();
			String 	professorName = cur.getProfessorName();
			int 	lectureYear = cur.getLectureYear();
			String 	semesterDivide = cur.getSemesterDivide();
			String 	lectureDivide = cur.getLectureDivide();
			String 	evaluationTitle = cur.getEvaluationTitle();
			String 	evaluationContent = cur.getEvaluationContent();
			String 	totalScore = cur.getTotalScore();
			String 	comfortableScore = cur.getComfortableScore();
			String 	lectureScore = cur.getLectureScore();
			int 	likeCount = cur.getLikeCount();
			System.out.println(evaluationContent);
%>
	 <div class="card bg-light mt-3">
			<div class="card-header bg-light">
				<div class="row">
					<div class="col-8 text-left"><%=lectureName%>&nbsp;<small><%=professorName%></small></div>
					<div class="col-4 text-right">
						종합&nbsp;<span style="color:red;"><%=totalScore%></span>
					</div>
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title">
					<%=evaluationTitle%>&nbsp;<small>(<%=lectureYear%>년 <%=semesterDivide%>)</small>
				</h5>
				<p class="card-text"><%=evaluationContent%></p>
				<div class="row">
					<div class="col-9 text-left">
						학점 취득 난이도&nbsp;<span style="color:red;"><%=comfortableScore%></span>
						강의력&nbsp;<samp style="color:blue;"><%=lectureScore%></samp>
						<span style="color:green;">(추천 수 : <%=likeCount%>)</span>
					</div>
					<div class="col-3 text-right">
						<a onclick="return confirm('추천하시겠습니까?');" href="likeAction.jsp?evaluationID=<%= evaluationID %>">추천</a>  <!-- 게시물 ID를 찾아 데이터베이스 접근 할 예정 -->
<%
	if(userID.equals(evalUserID)) {
%>
						<a onclick="return confirm('삭제하시겠습니까?');" href="deleteAction.jsp?evaluationID=<%= evaluationID %>">삭제</a>
<% 
	}
%>
					</div>
				</div>
			</div>
		</div> 
<%
		}
	}
%>	
	<!-- Test end -->	 
	</section>
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"> <!-- 제목 -->
					<h5 class="modal-title" id="modal">평가 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">  <!-- 'x' 버튼 -->
						<span aria-hidden="true">&times;</span>
					</button> 
				</div>
				<div class="modal-body">  <!--  내용 -->
					<form action="evalutionRegisterAction.jsp" method="post">  <!-- 등록 페이지로 데이터를 넘기기 위함 -->
						<div class="form-row"> <!-- 하나 의 행을 여러가지 열로 나눌 때-->
							<div class="form-group col-sm-6">  <!-- 보통 하나의 행에  11개의 열이 들어가야함 --> <!-- 6개 열 사용  즉. 반 사용-->
								<label>강의명</label>
								<input type="text" name="lectureName" class="form-control" maxlength="20"> 
							</div>
							<div class="form-group col-sm-6">
								<label>교수명</label>
								<input type="text" name="professorName" class="form-control" maxlength="20"> 
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>수강연도</label>
								<select name="lectureYear" class="form-control" >
									<option value="2014">2014</option>
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017">2017</option>
									<option value="2018" selected>2018</option>
									<option value="2019">2019</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>수강학기</label>
								<select name="semesterDivide" class="form-control">
									<option value="1학기" selected>1학기</option>
									<option value="여름학기">여름학기</option>
									<option value="2학기">2학기</option>
									<option value="겨울학기">겨울학기</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>강의 구분</label>
								<select name="lectureDivide" class="form-control">
									<option value="전공" selected>전공</option>
									<option value="교양">교양</option>
									<option value="기타">기타</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input type="text" name="evaluationTitle" class="form-control" maxlength="20">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea name="evaluationContent" class="form-control" maxlength= "2048" style="height:180px;" placeholder="비방글,관련없는 글은 관리자에 의해 삭제 및 게시자는 정지처분 됩니다!"></textarea>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-3">
								<label>종합</label>
								<select name="totalScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>									
								</select>
							</div>
							<div class="form-group col-sm-5 ">
								<label>학점 취득 난이도</label>
								<select name="comfortableScore" class="form-control">
									<option value="A" selected>매우 쉬움</option>
									<option value="B">쉬움</option>
									<option value="C">어려움</option>
									<option value="D">매우 어려움</option>									
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>강의력</label>
								<select name="lectureScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B">B</option>
									<option value="C">C</option>
									<option value="D">D</option>									
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-primary">등록하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
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
	<footer class="bg-dark mt-4 p-5 text-center" style="color:#FFFFF;">
		Copyright &copy; 2019이승준All Right Reserved.
	</footer>
	<!-- 제이쿼리 자바스크립트 추가하기 -->
	<script src="./js/jquery.min.js"></script>	
	<!-- 파퍼 자바스크립트 추가하기 -->
	<script src="./js/popper.min.js"></script>
	<!-- 부트스트랩 자바스크립트 추가하기 -->
	<script src="./js/bootstrap.min.js"></script>
</body>
</html>