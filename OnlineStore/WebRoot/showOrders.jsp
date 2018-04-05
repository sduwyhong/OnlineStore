<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<c:if test="${empty orders }">
	<h2>您还没有任何订单</h2>
</c:if>
<c:if test="${!empty orders }">
    <h2>您的订单如下</h2>
    <table border="1" width="600">
    	<tr>
    		<th>订单编号</th>
    		<th>商品数量</th>
    		<th>商品总价</th>
    		<th>订单状态</th>
    	</tr>
    	<c:forEach items="${orders }" var="order" varStatus="vs">
    		<tr class="${vs.index%2==0?'odd':'even' }">
    			<td>${order.id }</td>
    			<td>${order.quantity }</td>
    			<td>${order.price }</td>
    			<td>${order.status==0?'未付款':'已付款' }</td>
    		</tr>
    	</c:forEach>
    </table>
</c:if>
  </body>
</html>
