package com.customer.care.resolution.resolutions;

import com.customer.care.resolution.constants.Status;

public class MutualFundIssueResolver implements IssueResolver {
    @Override
    public Status resolve() {
        nextStepsOfMFResolution();
        return Status.RESOLVED;
    }

    @Override
    public void updateIssue() {

    }

    private void nextStepsOfMFResolution(){
    }
}
