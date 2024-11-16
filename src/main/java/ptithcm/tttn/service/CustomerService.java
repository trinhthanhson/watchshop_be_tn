package ptithcm.tttn.service;

import ptithcm.tttn.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findByUserId(Long user_id) throws Exception;

    List<Customer> findAll() throws Exception;

    Customer updateCustomer(Customer customer, String jwt) throws Exception;

    boolean checkEmailExist(String email);
}
