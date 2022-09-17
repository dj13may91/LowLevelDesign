package com.customer.care.resolution.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String email;
    private final List<String> issuesReportedById = new ArrayList<>();

    public Customer(String email) {
        this.email = email;
    }

    public void addIssue(String issueId){
        this.issuesReportedById.add(issueId);
    }
}
