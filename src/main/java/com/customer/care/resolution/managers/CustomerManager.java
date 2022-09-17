package com.customer.care.resolution.managers;

import com.customer.care.resolution.models.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private static final CustomerManager manager = new CustomerManager();
    private final Map<String, Customer> customersByEmail = new HashMap<>();

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        return manager;
    }

    public void createCustomerTicket(String issueId, String customerEmail) {
        Customer customer = customersByEmail.get(customerEmail);
        if (customer == null) {
            customer = new Customer(customerEmail);
            customersByEmail.put(customerEmail, customer);
        }
        customer.addIssue(issueId);
    }
}
