<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<title>user sign up test</title>
</head>
<body>
<!-- <form action="signup" method="post"> -->
<form action="signupcheck/email" method="get">
	<table>
		<tr>
			<th>메일주소<th>
			<td>
				<input type="text" name="email" />
			</td>
		</tr>
		<!-- <tr>
			<th>닉네임<th>
			<td>
				<input type="text" name="nickname" />
			</td>
		</tr>
		<tr>
			<th>비밀번호<th>
			<td>
				<input type="password" name="password" />
			</td>
		</tr> -->
		<tr>
			<td colspan="2">
				<button type="submit">전송</button>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
