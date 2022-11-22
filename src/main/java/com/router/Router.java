package com.router;

import java.util.HashSet;
import java.util.Set;

public class Router implements IRouter {

    public final Route root = new Route();

    @Override
    public void add(String path, String data) {
        Route crawler = root;
        String[] elements = path.split("/");
        StringBuilder sb = new StringBuilder();
        for (String ele : elements) {
            if (!crawler.getChildren().containsKey(ele)) {
                crawler.getChildren().put(ele, new Route());
            }
            crawler = crawler.getChildren().get(ele);
            sb.append("/").append(ele);
            crawler.setSelf(sb.toString());
        }
        crawler.setEndOfRoute(true);
        crawler.setValue(data);
        System.out.println(crawler);
    }

    @Override
    public Set<String> get(String path) {
        String[] elements = path.split("/");
        Set<String> resultSet = new HashSet<>();
        searchRoute(elements, 0, root, resultSet);
        return resultSet;
    }

    private void searchRoute(String[] elements, int index, Route crawler, Set<String> resultSet) {
        if (crawler == null) return;
        if (index >= elements.length) {
            if (crawler.isEndOfRoute()) {
                System.out.println("Index End for " + crawler.getSelf());
                resultSet.add(crawler.getValue());
            }
            return;
        }
        if ("*".equals(elements[index])) {
            for(Route r : crawler.getChildren().values()){
                searchRoute(elements, index + 1, r, resultSet);
            }
        } else if (crawler.getChildren().containsKey(elements[index])) {
            searchRoute(elements, index + 1, crawler.getChildren().get(elements[index]), resultSet);
        }
    }
}
