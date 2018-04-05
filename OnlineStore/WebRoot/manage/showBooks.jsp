<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <table border="1" width="100%">
    	<tr>
    		<th>序号</th>
    		<th>图片</th>
    		<th>书名</th>
    		<th>作者</th>
    		<th>单价</th>
    		<th>描述</th>
    		<th>所属分类</th>
    		<th>操作</th>
    	</tr>
    	<c:forEach items="${books }" var="b" varStatus="vs">
    		<tr class="${vs.index%2==0?'odd':'even' }">
    		<!-- vs.index(start from 0) vs.count(start from 1) -->
	    		<td>${vs.count }</td>
	    		<td>
	    			<img src="${pageContext.request.contextPath }/images/${b.path }/${b.fileName}" alt="${b.fileName}"/>
	    		</td>
	    		<td>${b.name }</td>
	    		<td>${b.author }</td>
	    		<td>${b.price }</td>
	    		<td>${b.description }</td>
	    		<td>${b.category.name}</td>
	    		<td>
					<a href="${pageContext.request.contextPath}/manage/ManageServlet?op=editBookUI&id=${b.id}">修改</a>
					<a href="${pageContext.request.contextPath}/manage/ManageServlet?op=deleteBook&id=${b.id}">删除</a>
				</td>
    		</tr>
    	</c:forEach>
    </table>
  </body>
</html>
