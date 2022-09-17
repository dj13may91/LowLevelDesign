package com.customer.care.resolution.resolutions;

import com.customer.care.resolution.constants.Status;

public class PaymentIssueResolver implements IssueResolver {
    @Override
    public Status resolve() {
        nextStepsOfPaymentResolution();
        return Status.RESOLVED;
    }

    @Override
    public void updateIssue() {

    }

    private void nextStepsOfPaymentResolution(){
    }
}
