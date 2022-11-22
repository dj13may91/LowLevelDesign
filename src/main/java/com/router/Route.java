package com.router;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Route {
    private final Map<String, Route> children;
    private boolean endOfRoute;
    private String value;
    private String self;

    public Route() {
        children = new HashMap<>();
        value = "";
        endOfRoute = false;
    }

    @Override
    public String toString() {
        return "{" +
                "c=" + children +
                ", end=" + endOfRoute +
                ", value='" + value + '\'' +
                ", self='" + self + '\'' +
                '}';
    }
}
