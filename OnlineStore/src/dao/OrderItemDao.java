package dao;

import domain.OrderItem;

public interface OrderItemDao {

	void addOrderItem(OrderItem oitem);

	OrderItem findOrderItemById(String orderItemId);

}
