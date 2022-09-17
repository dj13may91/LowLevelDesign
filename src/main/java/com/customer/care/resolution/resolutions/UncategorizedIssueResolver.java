package com.customer.care.resolution.resolutions;

import com.customer.care.resolution.constants.Status;

public class UncategorizedIssueResolver implements IssueResolver {
    @Override
    public Status resolve() {
        nextStepsOfResolution();
        return Status.RESOLVED;
    }

    @Override
    public void updateIssue() {

    }

    private void nextStepsOfResolution() {
    }
}
