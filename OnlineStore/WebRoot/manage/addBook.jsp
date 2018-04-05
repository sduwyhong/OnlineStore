<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<h2>Book adding</h2>
    <form action="${pageContext.request.contextPath }/manage/ManageServlet?op=addBook" method="post" enctype="multipart/form-data">
	    <table border="1" width="438">
		    	<tr>
		    		<td>书名</td>
		    		<td>
		    			<input type="text" name="name">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>作者</td>
		    		<td>
		    			<input type="text" name="author">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>单价</td>
		    		<td>
		    			<input type="text" name="price">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>图片</td>
		    		<td>
		    			<input type="file" name="photo">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>描述</td>
		    		<td>
		    			<textarea rows="3" cols="38" name="description"></textarea>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>所属分类</td>
		    		<td>
		    			<select name="categoryId">
		    				<c:forEach items="${categories }" var="c">
		    					<option value="${c.id }">${c.name }</option>
		    				</c:forEach>
		    			</select>
		    		</td>
		    	</tr>
		    	<tr>
			    	<td colspan="5" align="center">
			    		<input type="submit" value="submit">
			    	</td>
		    	</tr>
	    </table>
    </form>
  </body>
</html>
