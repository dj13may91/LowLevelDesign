package com.customer.care.resolution.resolutions;


import com.customer.care.resolution.constants.Status;

public class GoldIssueResolver implements IssueResolver {
    @Override
    public Status resolve() {
        nextStepsOfGoldResolution();
        return Status.RESOLVED;
    }

    @Override
    public void updateIssue() {

    }

    private void nextStepsOfGoldResolution(){
    }
}
