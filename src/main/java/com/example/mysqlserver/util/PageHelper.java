package com.example.mysqlserver.util;

import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

public class PageHelper {

    public static String orderBy(Sort sort) {
        return sort
                .stream()
                .map(order -> order.getProperty() + " " + order.getDirection())
                .collect(Collectors.joining(", "));
    }

}
