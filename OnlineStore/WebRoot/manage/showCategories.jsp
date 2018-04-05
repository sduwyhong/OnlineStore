<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <table border="1" width="438">
    	<tr>
    		<th>选择</th>
    		<th>分类名称</th>
    		<th>分类描述</th>
    		<th>操作</th>
    	</tr>
    	<c:forEach items="${categories }" var="c" varStatus="vs">
    		<tr class="${vs.index%2==0?'odd':'even' }">
	    		<th><input type="checkbox" ></th>
	    		<th>${c.name }</th>
	    		<th>${c.description }</th>
	    		<th>
					<a href="#">修改</a>
					<a href="#">删除</a>
				</th>
    		</tr>
    	</c:forEach>
    </table>
  </body>
</html>
