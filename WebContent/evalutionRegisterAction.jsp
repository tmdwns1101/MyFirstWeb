<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "evaluation.EvaluationDAO"%>
<%@ page import = "evaluation.EvaluationDTO"%>
<%@ page import="java.io.PrintWriter"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
	}
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
		return;	
	}
	
	String lectureName = null;
	String professorName = null;
	int lectureYear = 2018;
	String semesterDivide = null;
	String lectureDivide = null;
	String evaluationTitle = null;
	String evaluationContent = null;
	String totalScore = null;
	String comfortableScore = null;
	String lectureScore = null;
	
	
	if(request.getParameter("lectureName") != null){
		lectureName = (String) request.getParameter("lectureName");
	}
	if(request.getParameter("professorName") != null){
		professorName = (String) request.getParameter("professorName");
	}
	if(request.getParameter("lectureYear") != null){
		try{
			lectureYear = Integer.parseInt(request.getParameter("lectureYear"));
		}catch(Exception e){
			System.out.println("강의연도 데이터 오류");
		}
	}
	if(request.getParameter("semesterDivide") != null){
		semesterDivide = (String) request.getParameter("semesterDivide");
	}
	if(request.getParameter("lectureDivide") != null){
		lectureDivide = (String) request.getParameter("lectureDivide");
	}
	if(request.getParameter("evaluationTitle") != null){
		evaluationTitle = (String) request.getParameter("evaluationTitle");
	}
	if(request.getParameter("evaluationContent") != null){
		evaluationContent = (String) request.getParameter("evaluationContent");
	}
	if(request.getParameter("totalScore") != null){
		totalScore = (String) request.getParameter("totalScore");
	}
	if(request.getParameter("comfortableScore") != null){
		comfortableScore = (String) request.getParameter("comfortableScore");
	}
	if(request.getParameter("lectureScore") != null){
		lectureScore = (String) request.getParameter("lectureScore");
	}
	
	
	/*if(lectureName.isEmpty() == true || professorName.isEmpty() == true || evaluationTitle.isEmpty() == true || evaluationContent.isEmpty() == true){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}*/
	System.out.println(lectureName);
	System.out.println(professorName);
	System.out.println(evaluationTitle);
	System.out.println(evaluationContent);
	
	if(lectureName == null || professorName == null || evaluationTitle == null || evaluationContent == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	EvaluationDAO evaluationDAO = new EvaluationDAO();
	int result = evaluationDAO.write(new EvaluationDTO(0, userID, lectureName
			,professorName
			,lectureYear
			,semesterDivide
			,lectureDivide
			,evaluationTitle
			,evaluationContent
			,totalScore
			,comfortableScore
			,lectureScore
			,0));
	
	
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
		script.println("alert('강의평가 등록 실패하였습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}

%>