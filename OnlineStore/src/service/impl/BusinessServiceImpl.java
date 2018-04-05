package service.impl;

import java.util.List;
import java.util.UUID;

import service.BusinessService;
import util.idGenerator;

import commons.Page;

import dao.BookDao;
import dao.CategoryDao;
import dao.CustomerDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.impl.BookDaoImpl;
import dao.impl.CategoryDaoImpl;
import dao.impl.CustomerDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import domain.Book;
import domain.Category;
import domain.Customer;
import domain.Order;
import domain.OrderItem;

public class BusinessServiceImpl implements BusinessService {
	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private OrderDao orderDao = new OrderDaoImpl();
	private OrderItemDao orderItemDao = new OrderItemDaoImpl();
	@Override
	public void addCategory(Category c) {
		c.setId(UUID.randomUUID().toString());
		categoryDao.addCategory(c);
	}

	@Override
	public void deleteCategory(String categoryId) {
		//TODO 删除分类
	}

	@Override
	public Category findCategoryById(String categoryId) {
		return categoryDao.findById(categoryId);
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

	@Override
	public void addBook(Book b) {
		b.setId(idGenerator.genGUID());
		bookDao.addBook(b);
	}

	@Override
	public void deleteBook(String bookId) {
		//TODO 删除书本
		bookDao.deleteBook(bookId);
	}

	@Override
	public Book findBookById(String bookId) {
		return bookDao.findById(bookId);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookDao.findAll();
	}

	@Override
	public Page getPage(int currentPageNum,int recordsPerPage) {
		int num = 1;
		if(currentPageNum>1){
			num = currentPageNum;
		}
		int totalRecordsNum = bookDao.getTotalRecordsNum();
		return bookDao.getPage(num,totalRecordsNum,recordsPerPage);
	}

	@Override
	public Page getCategoryPage(String categoryId, int currentPageNum,
			int recordsPerPage) {
		int num = 1;
		if(currentPageNum>1){
			num = currentPageNum;
		}
		int totalRecordsNum = bookDao.getTotalRecordsNum(categoryId);
		return bookDao.getPage(categoryId,num,totalRecordsNum,recordsPerPage);
	}

	

	@Override
	public Customer findCustomerById(String customerId) {
		return customerDao.findCustomerById(customerId);
	}

	@Override
	public Customer login(String username, String password) {
		Customer c = customerDao.findCustomerByUP(username,password);
		if(c==null){
			return null;
		}
		if(!c.isActived()){//未被激活
			return null;
		}
		return c;
	}

	@Override
	public void activateCustomer(String id, String code) {//激活用户，在用户点击邮件中激活按键跳转到控制器时调用
		Customer c = customerDao.findCustomerByIC(id, code);
		if(c!=null){
			c.setActived(true);
			System.out.println("c.actived:"+c.isActived());
			customerDao.update(c);
		}
	}
	
	@Override
	public void addOrder(Order o) {
		orderDao.addOrder(o);
	}

	@Override
	public Order findOrderById(String orderId) {
		return orderDao.findOrderById(orderId);
	}

	@Override
	public void addOrderItem(OrderItem oitem) {
		orderItemDao.addOrderItem(oitem);
	}

	@Override
	public OrderItem findOrderItemById(String orderItemId) {
		return orderItemDao.findOrderItemById(orderItemId);
	}

	@Override
	public List<Order> findOrderByCustomerId(String customerId) {
		return orderDao.findOrderByCustomerId(customerId);
	}

	@Override
	public void register(Customer c) {
		customerDao.addCustomer(c);
	}

	@Override
	public void editBook(Book newBook) {
		// TODO Auto-generated method stub
		bookDao.updateBook(newBook);
	}

	
	
}
