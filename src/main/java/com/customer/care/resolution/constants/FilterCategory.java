package com.customer.care.resolution.constants;

import java.util.Objects;

public enum FilterCategory {
    EMAIL("email"),
    TYPE("type"),
    OTHER("Miscellaneous");
    final String name;

    private FilterCategory(String name) {
        this.name = name;
    }

    public static FilterCategory getNameByValue(String category) {
        for (FilterCategory e : FilterCategory.values()) {
            if (Objects.equals(category, e.name)) return e;
        }
        return OTHER;
    }
}