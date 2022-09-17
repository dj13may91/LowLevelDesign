package com.customer.care.resolution.models;

import com.customer.care.resolution.constants.IssueCategory;

import java.util.List;

public class Agent {
    private final String name;
    private final String email;
    private final List<IssueCategory> agentExpertise;

    public boolean isWorkingOnIssue() {
        return isWorkingOnIssue;
    }

    public void setWorkingOnIssue(boolean workingOnIssue) {
        isWorkingOnIssue = workingOnIssue;
    }

    private boolean isWorkingOnIssue = false;
    public Agent(String name, String email, List<IssueCategory> agentExpertise) {
        this.name = name;
        this.email = email;
        this.agentExpertise = agentExpertise;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", agentExpertise=" + agentExpertise +
                '}';
    }
}
