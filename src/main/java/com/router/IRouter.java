package com.router;

import java.util.Set;

public interface IRouter {
    void add(String path, String data);
    Set<String> get(String path);
}
