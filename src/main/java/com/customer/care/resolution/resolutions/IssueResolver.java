package com.customer.care.resolution.resolutions;


import com.customer.care.resolution.constants.Status;

public interface IssueResolver {
    Status resolve();
    void updateIssue();
}
