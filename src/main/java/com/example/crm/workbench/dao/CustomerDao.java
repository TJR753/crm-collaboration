package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByCompany(String company);

    int save(Customer customer);

    List<String> getCustomer(String name);

    String getCustomerId(String customerName);
}
