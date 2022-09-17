package com.customer.care.resolution.managers;

import com.customer.care.resolution.constants.FilterCategory;
import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.constants.Status;
import com.customer.care.resolution.models.Agent;
import com.customer.care.resolution.models.Issue;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class IssueManager {
    private final Map<String, Issue> openIssuesByIssueId = new HashMap<>();
    private final Map<IssueCategory, List<String>> issuesByCategory = new HashMap<>();
    private final Map<String, List<String>> issuesByEmail = new HashMap<>();

    private final CustomerManager customerManager = CustomerManager.getInstance();
    private static final IssueManager manager = new IssueManager();
    private AgentManager agentManager = AgentManager.getInstance();
    private int id = 1;

    private IssueManager() {
    }

    public static IssueManager getInstance() {
        return manager;
    }

    public void createIssue(String id, IssueCategory category, String reason, String description, String customerEmail) {
        //createIssue("T1", "Payment Related", "Payment Failed", "My payment failed but money is debited", “testUser1@test.com”);
        Issue issue = new Issue(id, category, reason, description, customerEmail, getRandomIssueId());
        openIssuesByIssueId.put(issue.getIssueId(), issue);
        System.out.println("Issue " + issue.getIssueId() + " created against transaction " + issue.getTransactionId());

        customerManager.createCustomerTicket(issue.getIssueId(), customerEmail);
        issuesByCategory.computeIfAbsent(category, k -> new ArrayList<>());
        issuesByCategory.get(category).add(issue.getIssueId());
        issuesByEmail.computeIfAbsent(customerEmail, k -> new ArrayList<>());
        issuesByEmail.get(customerEmail).add(issue.getIssueId());
    }

    public void assignIssue(String issueId) {
        Issue issue = openIssuesByIssueId.get(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue " + issueId + " not found");
        }
        if (Status.RESOLVED.equals(issue.getStatus())) {
            System.out.println("Issue " + issueId + " already closed by agent " + issue.getAgentId());
            return;
        }
        Agent agent = agentManager.getFreeAgent(openIssuesByIssueId.get(issueId).getIssueType());
        System.out.println(agent);
        if (agent == null) {
            System.err.println("N0 FREE AGENT FOR ISSUE " + issueId);
            return;
        }
        agent.setWorkingOnIssue(true);
        issue.assignAgent(agent.getName());
        Map<String, List<String>> history = agentManager.getAgentWorkHistory();
        history.computeIfAbsent(agent.getName(), k -> new ArrayList<>());
        history.get(agent.getName()).add(issueId);
        ResolutionManager.resolveIssue(issue);
        agent.setWorkingOnIssue(false);
    }

    public void updateIssue(String issueId, Status status, String resolution) {
        Issue issue = openIssuesByIssueId.get(issueId);
        issue.setStatus(status);
        issue.setResolution(resolution);
        System.out.println(issueId + " updated to status " + status);
    }

    public void resolveIssue(String issueId, String resolution) {
        Issue issue = openIssuesByIssueId.get(issueId);
        issue.setStatus(Status.RESOLVED);
        issue.setResolution(resolution);
        System.out.println(issueId + " marked " + issue.getStatus());
    }

    public void filterIssue(String filter) {
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String, Object> filters = jsonParser.parseMap(filter);
        List<Issue> issueList = new ArrayList<>(openIssuesByIssueId.values());
        for (String type : filters.keySet()) {
            String value = (String) filters.get(type);
            FilterCategory category = FilterCategory.getNameByValue(type);
            System.out.println(type + " : " + value);
            switch (category) {
                case EMAIL:
                    issueList.removeAll(issueList.stream()
                            .filter(issue -> !issue.getEmail().equals(value))
                            .collect(Collectors.toList()));
                    break;
                case TYPE:
                    issueList.removeAll(openIssuesByIssueId.values().stream()
                            .filter(issue -> !issue.getIssueType().equals(Enum.valueOf(IssueCategory.class, value)))
                            .collect(Collectors.toList()));
                    break;
                case OTHER:
                    System.out.println("Filter : " + category);
                    throw new RuntimeException("Filter not defined");
            }
        }
        System.out.println(" ---> Filter : " + filter);

        for (Issue issue : issueList){
            System.out.println("\t\t" + issue);
        }
    }

    private String getRandomIssueId() {
        return "I" + id++;
    }
}
