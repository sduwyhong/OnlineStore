package dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import util.DBCPUtil;
import dao.CustomerDao;
import domain.Customer;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addCustomer(Customer c) {
		try {
			qr.update("insert into customers (id,username,password,nickname,email,code,actived) values(?,?,?,?,?,?,?)", 
					c.getId(),c.getUsername(),c.getPassword(),c.getNickname(),c.getEmail(),c.getCode(),c.isActived());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Customer findCustomerById(String customerId) {
		Customer c;
		try {
			c = qr.query("select * from customers where id=?", new BeanHandler<Customer>(Customer.class), customerId);
			if(c==null){
				throw new RuntimeException("no such customer");
			}
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer findCustomerByUP(String username, String password) {
		try {
			Customer c;
			c = qr.query("select * from customers where username=? and password=?", new BeanHandler<Customer>(Customer.class), username,password);
			if(c==null){
				throw new RuntimeException("wrong username or password");
			}
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Customer findCustomerByIC(String id, String code) {
		try {
			Customer c = qr.query("select * from customers where id=? and code=?", new BeanHandler<Customer>(Customer.class), id,code);
			if(c!=null){
				return c; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Customer c) {
		try {
			qr.update("update customers set username=?,password=?,nickname=?,email=?,code=?,actived=? where id=?", 
					c.getUsername(),c.getPassword(),c.getNickname(),c.getEmail(),c.getCode(),c.isActived(),c.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
