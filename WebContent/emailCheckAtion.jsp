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
	System.out.println("EmailCheckedAtion userID is "+userID);
	
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요');");
		script.println("location.href = './userLogin.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	if(request.getParameter("code") != null){
		code = (String) request.getParameter("code");
	}
	System.out.println("Email send code is "+ code);
	String userEmail = userDAO.getUserEmail(userID);
	System.out.println("user Email  is "+ userEmail);
	boolean isRight = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;
	
	if(isRight == true){
		userDAO.setUserEmailChecked(userID);
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('인증을 성공하셨습니다.');");
		script.println("location.href = 'index.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	else{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 코드입니다.');");
		script.println("location.href = 'index.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	
	
%>