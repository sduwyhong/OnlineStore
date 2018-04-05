<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<h2>Book editing</h2>
    <form action="${pageContext.request.contextPath }/manage/ManageServlet?op=editBook&id=${b.id }" method="post" enctype="multipart/form-data">
	    <table border="1" width="438">
		    	<tr>
		    		<td>书名</td>
		    		<td>
		    			<input type="text" name="name" value="${b.name }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>作者</td>
		    		<td>
		    			<input type="text" name="author" value="${b.author }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>单价</td>
		    		<td>
		    			<input type="text" name="price" value="${b.price }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>图片</td>
		    		<td>
		    			<input type="button" value="更改图片" id="changeImage">
			    		<div hidden="hidden" id="uploadUnit">
				    		<input type="file" name="photo">
			    		</div>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>描述</td>
		    		<td>
		    			<textarea rows="3" cols="38" name="description" >${b.description }</textarea>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>所属分类</td>
		    		<td>
		    			<select name="categoryId">
		    				<c:forEach items="${categories }" var="c">
		    					<c:if test="${c.id==b.category.id }">
		    						<option value="${c.id }" selected="selected">${c.name }</option>
		    					</c:if>
		    					<c:if test="${c.id!=b.category.id }">
		    						<option value="${c.id }" >${c.name }</option>
		    					</c:if>
		    				</c:forEach>
		    			</select>
		    		</td>
		    	</tr>
		    	<tr>
			    	<td colspan="5" align="center">
			    		<input type="submit" value="修改">
			    	</td>
		    	</tr>
	    </table>
    </form>
  </body>
</html>
