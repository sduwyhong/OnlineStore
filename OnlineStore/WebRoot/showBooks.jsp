<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <br/>
    <a href="${pageContext.request.contextPath }">所有分类:</a>
    <c:forEach items="${categories }" var="c">
	    <a href="${pageContext.request.contextPath }/client/ClientServlet?op=findBooksByCategory&categoryId=${c.id}">${c.name }</a>
    </c:forEach>
    <table>
    <br>
    <br>
	    	<tr>
    	<c:forEach items="${page.records }" var="b">
	    		<td align="center">
	    			<img alt="${b.fileName }" src="${pageContext.request.contextPath }/images/${b.path }/${b.fileName}"><br>
	    			书名:《${b.name }》<br>
	    			作者：${b.author }<br>
	    			单价：￥${b.price }<br>
	    			<a href="${pageContext.request.contextPath }/client/ClientServlet?op=showBookDetail&bookId=${b.id}">详情</a>
	    		</td>
    	</c:forEach>
	    	</tr>
    	<tr>
    		<td colspan="4" align="center">
    			<%@ include file="/common/page.jsp" %>
    		</td>
    	</tr>
    </table>
    	
  </body>
</html>
