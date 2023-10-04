package org.eu.huzpsb.crawl4j.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tokenlizer {
    private static final Map<String, Integer> map = new HashMap<>();

    private static int tokenlizeWord(String c) {
        return map.computeIfAbsent(c, k -> map.size());
    }

    public static int[] tokenlize(String s) {
        String[] parts = s.split(" ");
        ArrayList<Integer> list = new ArrayList<>();
        for (String part : parts) {
            String lower = part.toLowerCase();
            boolean isChinese = false;
            for (int i = 0; i < lower.length(); i++) {
                if (lower.charAt(i) >= 0x4e00 && lower.charAt(i) <= 0x9fa5) {
                    isChinese = true;
                    break;
                }
            }
            if (isChinese) {
                for (int i = 0; i < lower.length(); i++) {
                    list.add(tokenlizeWord(lower.substring(i, i + 1)));
                }
            } else {
                list.add(tokenlizeWord(lower));
            }
        }
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}
