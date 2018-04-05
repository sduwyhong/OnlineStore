package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import util.DBCPUtil;
import dao.OrderDao;
import domain.Customer;
import domain.Order;
import domain.OrderItem;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addOrder(Order o) {
		try {
			//保存订单
			qr.update("insert into orders (id,quantity,price,status,customerId) values(?,?,?,?,?)", 
					o.getId(),o.getQuantity(),o.getPrice(),o.getStatus(),o.getCustomer().getId());
			//保存订单项
			List<OrderItem> items = o.getItems();
			for(OrderItem item:items){
				qr.update("insert into orderitems(id,quantity,price,bookId,orderId) values(?,?,?,?,?)",
						item.getId(),item.getQuantity(),item.getPrice(),item.getBook().getId(),item.getOrder().getId());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Order findOrderById(String orderId) {
		try {
			Order order = qr.query("select * from orders where id=?", new BeanHandler<Order>(Order.class), orderId);
			Customer c = qr.query("select * from customer where id=(select customerId from orders where id=?)", new BeanHandler<Customer>(Customer.class),orderId);
			order.setCustomer(c);
			return order;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> findOrderByCustomerId(String customerId) {
		try {
			List<Order> orders = new ArrayList<Order>();
			orders = qr.query("select * from orders where customerId=?", new BeanListHandler<Order>(Order.class), customerId);
			if(orders==null){
				return null;
			}
			Customer c = qr.query("select * from customers where id=?", new BeanHandler<Customer>(Customer.class),customerId);
			for(Order order:orders){
				order.setCustomer(c);
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
