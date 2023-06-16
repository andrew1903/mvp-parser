package com.codetest.mostvaluableperson.model;

import java.util.Map;

public interface Player<T extends Player<T>> extends Comparable<T> {
    Map<String, Object> toMap();
    Integer getScore();
}
