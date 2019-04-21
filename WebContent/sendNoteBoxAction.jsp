<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="evaluation.EvaluationDAO" %>
<%@ page import="notebox.NoteBoxDTO" %>
<%@ page import="notebox.NoteBoxDAO" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.Properties" %>
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
	
	
	String noteTitle = null;
	String noteContent = null;
	String evaluationID = null;
	
	
	if(request.getParameter("to") != null){
		evaluationID = request.getParameter("to");
	}
	
	if(request.getParameter("noteTitle") != null){
		noteTitle = request.getParameter("noteTitle");
	}
	if(request.getParameter("noteContent") != null){
		noteContent = request.getParameter("noteContent");
	}
	
	if(noteTitle.isEmpty() == true || noteContent.isEmpty() == true){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	
	String toUserID = evaluationDAO.getUserID(evaluationID);
	
	NoteBoxDAO noteBoxDAO = new NoteBoxDAO();
	
	int result = noteBoxDAO.write(new NoteBoxDTO(toUserID, userID, noteTitle, noteContent, 0, evaluationID));
	
	if(result == 1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'index.jsp';");
		script.println("</script>");
		script.close();
		return;
	}else if(result == -1){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('쪽지 보내기 실패하였습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	
	

%>