package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BusinessService;
import service.impl.BusinessServiceImpl;
import util.SendActivateEmail;
import util.idGenerator;
import beans.Cart;
import beans.CartItem;
import cn.dsna.util.images.ValidateCode;

import commons.Page;

import constant.Constants;
import domain.Book;
import domain.Category;
import domain.Customer;
import domain.Order;
import domain.OrderItem;

public class ClientServlet extends HttpServlet {
	private BusinessService bs = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if(op.equals("showClientBooks")){
			showClientBooks(request,response);
		}else if(op.equals("changePage")){
			changePage(request,response);
		}else if(op.equals("findBooksByCategory")){
			findBooksByCategory(request,response);
		}else if(op.equals("showBookDetail")){
			showBookDetail(request,response);
		}else if(op.equals("addCartItem")){
			addCartItem(request,response);
		}else if(op.equals("regist")){
			regist(request,response);
		}else if(op.equals("login")){
			login(request,response);
		}else if(op.equals("pay")){
			pay(request,response);
		}else if(op.equals("showOrders")){
			showOrders(request,response);
		}else if(op.equals("changeCartItemQuantity")){
			changeCartItemQuantity(request,response);
		}else if(op.equals("deleteCart")){
			deleteCart(request,response);
		}else if(op.equals("logout")){
			logout(request,response);
		}else if(op.equals("activate")){
			activate(request,response);
		}else if(op.equals("getVC")){
			getVC(request,response);
		}
	}
	String validateCode;
	private void getVC(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ValidateCode vc = new ValidateCode(100, 50, 4, 20);
		validateCode = vc.getCode();
		System.out.println(validateCode);
		 vc.write(response.getOutputStream());
	}

	private void activate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		bs.activateCustomer(id, code);
		response.getWriter().write("激活成功！");
		response.setHeader("Refresh", "2;URL="+request.getContextPath());
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(Constants.HTTPSESSION_CUSTOMER);
		response.sendRedirect(request.getContextPath());
	}

	private void deleteCart(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute(Constants.HTTPSESSION_CART);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	private void changeCartItemQuantity(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		String newQuantity = request.getParameter("newQuantity");
		
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		
		Map<String,CartItem> items = cart.getItems();
		CartItem item = items.get(bookId);
		item.setQuantity(Integer.parseInt(newQuantity));
		session.setAttribute(Constants.HTTPSESSION_CART, cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	private void showOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute(Constants.HTTPSESSION_CUSTOMER);
		if(customer==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		List<Order> orders = bs.findOrderByCustomerId(customer.getId());
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/showOrders.jsp").forward(request, response);
	}

	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//通过session获取当前用户
		HttpSession cSession = request.getSession();
		Customer customer = (Customer) cSession.getAttribute(Constants.HTTPSESSION_CUSTOMER);
		if(customer==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//取出购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		Map<String,CartItem> items = cart.getItems();
		//生成订单
		Order order = new Order();
		order.setId(idGenerator.genOrderId());
		order.setCustomer(customer);
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			//生成订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setId(idGenerator.genGUID());
			orderItem.setQuantity(item.getValue().getQuantity());
			orderItem.setPrice(item.getValue().getPrice());
			orderItem.setBook(item.getValue().getBook());
			orderItem.setOrder(order);
			//将订单项加入订单
			order.getItems().add(orderItem);
		}
		for(OrderItem item:order.getItems()){
			order.setQuantity(order.getQuantity() + item.getQuantity());
			order.setPrice(order.getPrice() + item.getPrice());
		}
		bs.addOrder(order);
		//结算后将购物车清空
		session.removeAttribute(Constants.HTTPSESSION_CART);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String vcode = request.getParameter("validateCode");
		if(!vcode.equals(validateCode)){
			request.setAttribute("codeError", "验证码有误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Customer c = bs.login(username, password);
		HttpSession session = request.getSession();
		session.setAttribute(Constants.HTTPSESSION_CUSTOMER,c);
		response.sendRedirect(request.getContextPath());
	}

	private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		Customer c = new Customer();
		c.setId(idGenerator.genGUID());
		c.setUsername(username);
		c.setPassword(password);
		c.setNickname(nickname);
		c.setEmail(email);
		c.setCode(idGenerator.genGUID());
		bs.register(c);
		
		SendActivateEmail sae = new SendActivateEmail(c,request.getContextPath());
		sae.start();
		
		
		
		response.getWriter().write("regist successfully!Please Check your email to activate your account!");
		response.setHeader("Refresh", "3;URL="+request.getContextPath()+"/login.jsp");
	}

	private void addCartItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.HTTPSESSION_CART);
		if(cart==null){
			cart = new Cart();
		}
		CartItem item = cart.getItems().get(bookId);
		if(item==null){
			item  = new CartItem();
			item.setBook(bs.findBookById(bookId));
			item.setQuantity(1);
			cart.getItems().put(bookId, item);
		}else{
			item.setQuantity(item.getQuantity()+1);
		}
//		System.out.println(cart);
//		System.out.println(item);
		session.setAttribute(Constants.HTTPSESSION_CART, cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	private void showBookDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = bs.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/showBookDetail.jsp").forward(request, response);
	}

	private void setCategoriesList(HttpServletRequest request,
			HttpServletResponse response){
		List<Category> categories = bs.findAllCategories();
		request.setAttribute("categories", categories);
	}
	
	private void findBooksByCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		if(num==null){
			num = "1";
		}
		Page page = bs.getCategoryPage(categoryId, Integer.parseInt(num), 3);
		//URL中的参数要写准，漏掉ID的话换页无数据
		page.setUrl("/client/ClientServlet?op=findBooksByCategory&categoryId="+categoryId);
		request.setAttribute("page", page);
		
		setCategoriesList(request, response);
		
		request.getRequestDispatcher("/showBooks.jsp").forward(request, response);
	}

	private void changePage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		Page page = bs.getPage(Integer.parseInt(num), 3);
		page.setUrl("/client/ClientServlet?op=changePage");
		request.setAttribute("page", page);
		
		setCategoriesList(request, response);
		
		request.getRequestDispatcher("/showBooks.jsp").forward(request, response);
	}

	private void showClientBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Page page = bs.getPage(1, 3);
		page.setUrl("/client/ClientServlet?op=changePage");
//		System.out.println(page);
		request.setAttribute("page", page);
		
		setCategoriesList(request, response);
		
		request.getRequestDispatcher("/showBooks.jsp").forward(request, response);
	}

}
