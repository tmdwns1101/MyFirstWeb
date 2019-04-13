<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="utill.Gmail" %>
<%@ page import="utill.SHA256"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="javax.mail.Transport" %>
<%@ page import="javax.mail.Message" %>
<%@ page import="javax.mail.Address" %>
<%@ page import="javax.mail.Session" %>
<%@ page import="javax.mail.Authenticator" %>
<%@ page import="javax.mail.internet.InternetAddress" %>
<%@ page import="javax.mail.internet.MimeMessage" %>
<%@ page import="java.util.Properties" %>
<%
	request.setCharacterEncoding("UTF-8");
	UserDAO userDAO = new UserDAO();
	String userEmail = null;
	String userID =null;
	if(request.getParameter("userEmail") != null){  
		userEmail = (String) request.getParameter("userEmail");
	}
	
	if(request.getParameter("userID") != null){  
		userID = (String) request.getParameter("userID");
	}
	if(userEmail.isEmpty() == true || userID.isEmpty() == true){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력되지 않은 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	
	if(userDAO.SearchCol(userID) == 0){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('가입되지 않은 아이디 입니다');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}else if(userDAO.SearchCol(userID) == -1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터 베이스 오류');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}

	String host = "http://localhost:8080/Lecture_Evaluation/";
	String from = "tmdwns11011@gmail.com";
	String to = userEmail;
	String subject = "비밀번호 찾기 이메일 입니다.";  //이메일 제목
	String content = "다음 링크에 접속하여  비밀번호를 재설정 하세요" + 
		"<a href='" + host + "UserPasswordEmailCheckAtion.jsp?code=" + new SHA256().getSHA256(to) + "&userID="+userID + "'>이메일 인증하기</a>";
	
	Properties p = new Properties();
	p.put("mail.smtp.user",from);
	p.put("mail.smtp.host", "smtp.googlemail.com");
	p.put("mail.smtp.port", "465");
	p.put("mail.smtp.starttls.enable", "true");
	p.put("mail.smtp.auth", "true");
	p.put("mail.smtp.debug", "true");
	p.put("mail.smtp.socketFactory.post", "465");
	p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	p.put("mail.smtp.socketFactory.fallback", "false");
	
	try{
		Authenticator auth = new Gmail();
		Session ses = Session.getInstance(p, auth);
		ses.setDebug(true);
		MimeMessage msg = new MimeMessage(ses);
		msg.setSubject(subject);
		Address fromAddr = new InternetAddress(from);
		msg.setFrom(fromAddr);
		Address toAddr = new InternetAddress(to);
		msg.addRecipient(Message.RecipientType.TO, toAddr);
		msg.setContent(content,"text/html;charset=UTF8");
		Transport.send(msg);
		
	}catch(Exception e){
		e.printStackTrace();
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('오류가 발생하였습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}

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
				<li class="nav-item">
					<a class="nav-link" href="index.jsp">메인</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
       					<a class="dropdown-item" href="userLogin.jsp">로그인</a>
          				<a class="dropdown-item" href="userLogout.jsp">로그아웃</a>
          				<a class="dropdown-item active" href="userJoin.jsp">회원가입</a>
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
		<div class="alert alert-success mt-4" role="alert">
			이메일 주소 인증 메일이 전송되었습니다. 입력한 이메일에 들어가셔서 인증해주세요.(동일한 브라우저로 접속해주세요.)
		</div>
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