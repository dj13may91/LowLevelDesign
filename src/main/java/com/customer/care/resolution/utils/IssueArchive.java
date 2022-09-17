package com.customer.care.resolution.utils;

import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.models.Issue;

import java.util.HashMap;
import java.util.Map;

public class IssueArchive {

    private Map<IssueCategory, Issue> closedIssues = new HashMap<>();
    private Map<IssueCategory, Issue> openIssues = new HashMap<>();

}
