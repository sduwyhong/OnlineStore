package dao;

import domain.Customer;

public interface CustomerDao {

	void addCustomer(Customer c);

	Customer findCustomerById(String customerId);

	Customer findCustomerByUP(String username, String password);

	Customer findCustomerByIC(String id, String code);

	void update(Customer c);

}
