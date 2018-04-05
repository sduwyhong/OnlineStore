package service;

import java.util.List;

import commons.Page;

import domain.Book;
import domain.Category;
import domain.Customer;
import domain.Order;
import domain.OrderItem;

public interface BusinessService {

	void addCategory(Category c);
	void deleteCategory(String categoryId);
	Category findCategoryById(String categoryId);
	List<Category> findAllCategories();
	
	void addBook(Book b);
	void deleteBook(String bookId);
	Book findBookById(String bookId);
	List<Book> findAllBooks();
	
	Page getPage(int currentPageNum,int recordsPerPage);
	Page getCategoryPage(String categoryId, int currentPageNum, int recordsPerPage);
	
	void register(Customer c);
	Customer findCustomerById(String customerId);
	Customer login(String username,String password);
	void activateCustomer(String id,String code);
	
	void addOrder(Order o);
	Order findOrderById(String orderId);
	List<Order> findOrderByCustomerId(String customerId);
	
	void addOrderItem(OrderItem oi);
	OrderItem findOrderItemById(String orderItemId);
	void editBook(Book newBook);
}
