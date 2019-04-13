<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO"%>
<%@ page import="utill.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = null;
	String code = null;
	UserDAO userDAO = new UserDAO();

	if(session.getAttribute("userID") != null){  //로그인 상태인지 확인
		userID = (String) session.getAttribute("userID");
	}
	
	if(userID != null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인 상태입니다.');");
		script.println("location.href = './userLogin.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	if(request.getParameter("code") != null){
		code = (String) request.getParameter("code");
	}
	System.out.println("Email send code is "+ code);
	String findUserID = null;
	findUserID = userDAO.getUserIDbyHashCode(code);

%>	
<%
	if(findUserID != null){
%>
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
	
	<section class="container mt-3" style="max-width: 560px;">
			<div class="form-group">
				<label>
					찾은 이메일 : &nbsp;<%=findUserID  %>
				</label>
			</div>
			<button onclick="location='userInfoFind.jsp'" class="btn btn-primary">돌아가기</button>		
	</section>
	
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color:#FFFFFF;">
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
<% 		
	}
	else{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('가입 정보가 없습니다.');");
		script.println("location.href = 'userInfoFind.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	
	
%>