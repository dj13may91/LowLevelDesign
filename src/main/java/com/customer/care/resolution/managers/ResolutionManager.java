package com.customer.care.resolution.managers;


import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.constants.Status;
import com.customer.care.resolution.models.Issue;
import com.customer.care.resolution.resolutions.*;

public class ResolutionManager {

    public static Status resolveIssue(Issue issue) {
        IssueCategory category = issue.getIssueType();
        switch (category) {
            case GOLD:
                return new GoldIssueResolver().resolve();
            case PAYMENT:
                return new PaymentIssueResolver().resolve();
            case MUTUAL_FUND:
                return new MutualFundIssueResolver().resolve();
            case OTHER:
            default:
                return new UncategorizedIssueResolver().resolve();
        }
    }
}
