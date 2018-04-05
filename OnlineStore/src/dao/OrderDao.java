package dao;

import java.util.List;

import domain.Order;

public interface OrderDao {

	void addOrder(Order o);

	Order findOrderById(String orderId);
	
	List<Order> findOrderByCustomerId(String customerId);

}
