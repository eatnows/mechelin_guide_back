<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<title>Insert title here</title>
</head>
<body>
<form action="testurl" method="post">
	<table>
		<tr>
			<th>이름<th>
			<td>
				<input type="text" name="name" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="submit">전송</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
