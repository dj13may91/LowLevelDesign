package com.customer.care.resolution.managers;

import com.customer.care.resolution.constants.IssueCategory;
import com.customer.care.resolution.models.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentManager {
    private static final String NO_FREE_AGENT = "none";
    private static final AgentManager manager = new AgentManager();
    private final Map<String, Agent> agentByMailMap = new HashMap<>();
    private final Map<IssueCategory, List<String>> agentsByExpertise = new HashMap<>();
    private final Map<String, List<String>> agentWorkHistory = new HashMap<>();

    private AgentManager() {
    }

    synchronized public Agent getFreeAgent(IssueCategory category){
        String freeAgentId = agentsByExpertise.get(category)
                .stream()
                .filter(id -> !agentByMailMap.get(id).isWorkingOnIssue())
                .findFirst().orElse(NO_FREE_AGENT);
        return agentByMailMap.get(freeAgentId);
    }

    public static AgentManager getInstance(){
        return manager;
    }
    public void addAgent(String email, String name, List<IssueCategory> expertise) {
        // addAgent(“agent1@test.com”, “Agent 1”, Arrays.asList("Payment Related", "Gold Related"));
        Agent agent = new Agent(name, email, expertise);

        agentByMailMap.put(email, agent);

        for (IssueCategory category : expertise) {
            List<String> agentIds = agentsByExpertise.get(category);
            if (agentIds == null) {
                agentIds = new ArrayList<>();
            }
            agentIds.add(email);
            agentsByExpertise.put(category, agentIds);
        }
        System.out.println("Agent created: " + agent);
    }

    public Map<String, List<String>> getAgentWorkHistory() {
        return agentWorkHistory;
    }

    public void agentsWorkHistory(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("work history : " + agentWorkHistory);
    }

}
