<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <br>
    <br>
    <form action="${pageContext.request.contextPath }/client/ClientServlet?op=login" method="post">
    
		    <table border="1" width="438">
		  <tr>
		    <td>用户名：</td>
		    <td><input type="text" name="username"></td>
		  </tr>
		  <tr>
		    <td>密码：</td>
		    <td><input type="password" name="password"></td>
		  </tr>
		  <tr>
		  	<td>
		  		验证码：
		  	</td>
		  	<td nowrap="nowrap">
		  		<input type="text" name="validateCode"><img src="${pageContext.request.contextPath}/client/ClientServlet?op=getVC">
		  		<br>
		  		<font color="red">${codeError}</font>
		  	</td>
		  </tr>
		  <tr>
		  	<td colspan="2">
		  		<input type="submit" value="submit">
		  	</td>
		  </tr>
		</table>
    </form>

  </body>
</html>
