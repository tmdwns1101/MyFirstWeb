<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO"%>
<%@ page import = "user.UserDTO"%>
<%@ page import = "utill.Rules"%>
<%@ page import="utill.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
	}
	if(userID != null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인이 된 상태입니다.');");
		script.println("location.href = 'index.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	String userPassword = null;
	String userPasswordChecked = null;
	
	if(request.getParameter("userID") != null){
		userID = (String) request.getParameter("userID");
	}
	if(request.getParameter("userPassword") != null){
		userPassword = (String) request.getParameter("userPassword");
	}
	if(request.getParameter("userPasswordChecked") != null){
		userPasswordChecked =(String) request.getParameter("userPasswordChecked");
	}
	System.out.println("Update UserPassword page userPassword : " + userPassword);
	System.out.println("Update UserPassword page userID : " + userID);
	if(userID.isEmpty() == true || userPassword.isEmpty() == true || userPasswordChecked.isEmpty() == true){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	Rules rules = new Rules();
	int PasswordCheck = rules.PassWordRule(userPassword);
	if(PasswordCheck == -2){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호의 길이가 너무 짧습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	if(PasswordCheck == -1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호 생성 조건을 맞춰주세요.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	System.out.println(userPassword);
	System.out.println(userPasswordChecked);
	if(!userPassword.equals(userPasswordChecked)){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('확인한 비밀번호와 입력하신 비밀번호가 다릅니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	UserDAO userDAO = new UserDAO();
	int result = userDAO.UpdatePassword(userID, userPassword);
	System.out.println(result);
	if(result == 1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호가 재 설정 되었습니다.');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	if(result == -1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터 베이스 오류.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}

%>