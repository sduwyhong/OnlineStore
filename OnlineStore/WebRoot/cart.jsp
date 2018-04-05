<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
	<c:if test="${!empty sessionScope.cart }">
		<h2>您购买的商品如下</h2>
		<table border="1" width="565">
			<tr>
				<th>书名</th>
				<th>数量</th>
				<th>单价</th>
				<th>小计</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${sessionScope.cart.items }" var="item" varStatus="vs">
				<tr class="${vs.index%2==0?'odd':'even' }">
					<td>${item.value.book.name }</td>
					<!-- 超链接中调用js方法要添加javascript：，普通标签的onchange、onclick直接写方法名 -->
					<td><input type="text" id="quantity" value="${item.value.quantity }" onchange="changeQuantity(this,'${item.value.book.id }',${item.value.quantity })"></td>
					<td>${item.value.book.price }</td>
					<td>${item.value.price}</td>
					<td>
						<a href="#">操作</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right">
					共${sessionScope.cart.quantity }件/总金额：${sessionScope.cart.price }
						<a href="${pageContext.request.contextPath }/client/ClientServlet?op=pay">去结算</a>
						<a href="${pageContext.request.contextPath }/client/ClientServlet?op=deleteCart">清空购物车</a>
				</td>
			</tr>
			<tr>
				<a href="javascript:window.history.back()">返回</a>
			</tr>
		</table>
	</c:if>
	<c:if test="${empty sessionScope.cart }">
		<h2>您还未购买任何商品！</h2>
	</c:if>
  </body>
  <script type="text/javascript">
  	function changeQuantity(inputObj,bookId,originalQuantity){
		var confirm = window.confirm("确认修改数量吗？");
		if(confirm){
			var newQuantity = inputObj.value;
			if(!/^[1-9][0-9]*$/.test(newQuantity)){
				alert("输入不合法");
				return;
			}
			window.location.href="${pageContext.request.contextPath}/client/ClientServlet?op=changeCartItemQuantity&bookId="+bookId+"&newQuantity="+newQuantity;
		}else{
			inputObj.value = originalQuantity;
		}  
  	}
  </script>
</html>
