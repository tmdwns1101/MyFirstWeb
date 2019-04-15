<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="evaluation.EvaluationDAO"%>
<%@ page import="likey.LikeyDTO"%>
<%@ page import="likey.LikeyDAO" %>
<%@ page import="java.io.PrintWriter"%>

<!--  서블릿 정의  -->

<%!
	public static String getClientIP(HttpServletRequest request){
		String ip = request.getHeader("X-FORWARDED_FOR");
		if(ip == null || ip.length() == 0){
			ip = request.getHeader("Proxy-Client_IP");
		}
		if(ip == null || ip.length() == 0){
			ip = request.getHeader("WL-Proxy-Client_IP");
		}
		if(ip == null || ip.length() == 0){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
%>

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
	
	
	String evaluationID = null;
	String userIP = null;
	if(request.getParameter("evaluationID") != null){
		evaluationID = request.getParameter("evaluationID");
	}
	
	System.out.println("like Action evaluationID : " + evaluationID);
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	LikeyDAO likeDAO = new LikeyDAO();
	int result = likeDAO.Like(userID, evaluationID, getClientIP(request));
	if(result == 1){
		evaluationDAO.Like(evaluationID);
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'index.jsp'");
		script.println("</script>");
		script.close();
		return;
	}else if(result == 3){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 추천 하셨습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}else{
		System.out.println("데이터 베이스 오류");
	}
	
	
%>