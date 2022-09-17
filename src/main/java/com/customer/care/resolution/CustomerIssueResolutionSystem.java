package com.customer.care.resolution;

import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.constants.Status;
import com.customer.care.resolution.managers.AgentManager;
import com.customer.care.resolution.managers.IssueManager;

import java.util.Arrays;

public class CustomerIssueResolutionSystem {

    private final AgentManager agentManager = AgentManager.getInstance();
    private final IssueManager issueManager = IssueManager.getInstance();

    public static void main(String[] args) {
        CustomerIssueResolutionSystem application = new CustomerIssueResolutionSystem();
        application.createIssues();
        application.createAgents();
        application.assignIssue();
        application.issueManager.updateIssue("I3", Status.IN_PROGRESS, "Waiting for payment confirmation");
        application.agentManager.agentsWorkHistory();
        application.issueManager.resolveIssue("I3", "PaymentFailed debited amount will get reversed");
        application.issueManager.filterIssue("{\"email\": \"testUser2@test.com\"}");
    }

    public void assignIssue(){
        issueManager.assignIssue("I1");
        issueManager.assignIssue("I2");
        issueManager.assignIssue("I3");
    }
    public void createAgents() {
        agentManager.addAgent("agent1@test.com", "Agent 1", Arrays.asList(IssueCategory.PAYMENT, IssueCategory.GOLD));
        agentManager.addAgent("agent2@test.com", "Agent 2", Arrays.asList(IssueCategory.MUTUAL_FUND));
    }

    public void createIssues() {
        issueManager.createIssue("T1", IssueCategory.PAYMENT, "Payment Failed", "My payment failed but money is debited", "testUser1@test.com");
        issueManager.createIssue("T2", IssueCategory.MUTUAL_FUND, "Purchase Failed", "Unable to purchase Mutual Fund", "testUser2@test.com");
        issueManager.createIssue("T3", IssueCategory.PAYMENT, "Payment Failed", "My payment failed but money is debited", "testUser2@test.com");
    }
}
