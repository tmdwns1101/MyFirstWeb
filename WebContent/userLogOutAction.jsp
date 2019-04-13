<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.io.PrintWriter"%>

<%
	session.invalidate(); //클라이언트의 모든 세션 파기
	
%>

<script>
	location.href = "./index.jsp";
</script>