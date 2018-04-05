package dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import util.DBCPUtil;
import dao.OrderItemDao;
import domain.Book;
import domain.Order;
import domain.OrderItem;

public class OrderItemDaoImpl implements OrderItemDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addOrderItem(OrderItem oitem) {
		try {
			qr.update("insert into orderitems(id,quantity,price,bookId,orderId) values(?,?,?,?,?)",
					oitem.getId(),oitem.getQuantity(),oitem.getPrice(),oitem.getBook().getId(),oitem.getOrder().getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public OrderItem findOrderItemById(String orderItemId) {
		try {
			OrderItem orderItem = qr.query("select * from orderitems where id=?", new BeanHandler<OrderItem>(OrderItem.class), orderItemId);
			Book book = qr.query("select * from books where id=(select bookId from orderitems where id=?)", new BeanHandler<Book>(Book.class),orderItemId);
			Order order = qr.query("select * from orders where id=(select orderId from orderitems where id=?)", new BeanHandler<Order>(Order.class), orderItemId);
			orderItem.setBook(book);
			orderItem.setOrder(order);
			return orderItem;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
