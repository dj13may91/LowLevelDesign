package com.customer.care.resolution.constants;

public enum IssueCategory {
    PAYMENT("Payment Related"),
    GOLD("Gold Related"),
    MUTUAL_FUND("Mutual Fund Related"),
    INSURANCE("Insurance Related"),
    OTHER("Miscellaneous");
    final String name;

    private IssueCategory(String name) {
        this.name = name;
    }
}
