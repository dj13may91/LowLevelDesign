package com.router;

import java.util.Set;

public class Application {
    public static void main(String[] args) {
        Router rm = new Router();
        rm.add("jira/test", "foo1");
        rm.add("jira/test/xyz", "foo2");
        rm.add("jira/test/abc", "foo3");
        rm.add("jira/test/abc/xyz", "foo4");
        rm.add("jira/test/ghi/xyz", "foo5");
        Set<String> v = rm.get("jira/test/*/xyz");
        Set<String> w = rm.get("jira/test");
        Set<String> x = rm.get("jira/test/abc");
        Set<String> y = rm.get("jira/test/*");
        Set<String> z = rm.get("jira/*/xyz");
        System.out.println(v);
        System.out.println(w);
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }
}
