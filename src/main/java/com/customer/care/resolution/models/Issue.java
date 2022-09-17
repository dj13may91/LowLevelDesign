package com.customer.care.resolution.models;

import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.constants.Status;

public class Issue {
    private final String transactionId;
    private final IssueCategory issueType;
    private final String subject;
    private final String description;
    private final String email;
    private final String issueId;
    private String resolution;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    private Status status = Status.OPEN;

    private String agentId;
    public Issue(String transactionId, IssueCategory issueType, String subject, String description, String email, String issueId) {
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.subject = subject;
        this.description = description;
        this.email = email;
        this.issueId = issueId;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "transactionId='" + transactionId + '\'' +
                ", issueType=" + issueType +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", issueId='" + issueId + '\'' +
                '}';
    }

    public String getTransactionId() {
        return transactionId;
    }

    public IssueCategory getIssueType() {
        return issueType;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getIssueId() {
        return issueId;
    }

    public void assignAgent(String agentId){
        this.agentId = agentId;
        System.out.println("Issue " + this.getIssueId() + " assigned to agent " + agentId);
    }
}
