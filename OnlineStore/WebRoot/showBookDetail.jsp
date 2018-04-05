<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
    <br>
    <br>
	    <tr>
    		<td>
    			<img alt="${book.fileName }" src="${pageContext.request.contextPath }/images/${book.path }/${book.fileName}"><br>
    			${book }<br>
    			<a href="${pageContext.request.contextPath }/client/ClientServlet?op=addCartItem&bookId=${book.id}">加入购物车</a>
    			<a href="javascript:window.history.back()">返回</a>
    		</td>
	    </tr>
    </table>
    	
  </body>
</html>
