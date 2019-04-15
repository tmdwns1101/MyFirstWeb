<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="evaluation.EvaluationDAO"%>
<%@ page import="likey.LikeyDTO"%>
<%@ page import="utill.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
	request.setCharacterEncoding("UTF-8");
	UserDAO userDAO = new UserDAO();
	String userID = null;
	if(session.getAttribute("userID") != null){  //로그인 상태인지 확인
		userID = (String) session.getAttribute("userID");
	}
	if(userID.isEmpty() == true){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요');");
		script.println("location.href = './userLogin.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	System.out.println("Email send userID is "+userID);
	
	String evaluationID = null;
	
	if(request.getParameter("evaluationID") != null){
		evaluationID = request.getParameter("evaluationID");
	}
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	
	if(userID.equals(evaluationDAO.getUserID(evaluationID))){
		int result = evaluationDAO.Delete(evaluationID);
		if(result == 1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('삭제 되었습니다.');");
			script.println("location.href = 'index.jsp'");
			script.println("</script>");
			script.close();
			return;
		}else{
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
	}else{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('자신이 쓴  게시물만 삭제 가능합니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	
%>