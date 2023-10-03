package org.eu.huzpsb.crawl4j.search;

import java.util.HashMap;
import java.util.Map;

public class Tokenlizer {
    private static final Map<Character, Integer> map = new HashMap<>();

    public static int tokenlize(char c) {
        return map.computeIfAbsent(c, k -> map.size());
    }
}
