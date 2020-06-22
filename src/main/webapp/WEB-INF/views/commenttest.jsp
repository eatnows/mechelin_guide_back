<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<title>comment test page</title>
</head>
<body>
<!-- <form action="insertcomm" method="post"> -->
<form action="getcomments" method="get">
<!-- 
	<input type="hidden" name="post_id" value="7">
	<input type="hidden" name="user_id" value="3">
	sql에 "" 그대로 넘어가도 null 처리 된다
	<input type="hidden" name="parent_comment_id" value="">
 -->
	<table>
		<tr>
			<th>post_id<th>
			<td>
				<input type="text" name="post_id" />
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
