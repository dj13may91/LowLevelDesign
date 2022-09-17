package com.customer.care.resolution.resolutions;

import com.customer.care.resolution.constants.Status;

public class InsuranceIssueResolver implements IssueResolver {
    @Override
    public Status resolve() {
        nextStepsOfInsuranceResolution();
        return Status.RESOLVED;
    }

    @Override
    public void updateIssue() {

    }

    private void nextStepsOfInsuranceResolution(){
    }
}
