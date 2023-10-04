package org.eu.huzpsb.crawl4j.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tokenizer {
    private static final Map<String, Integer> map = new HashMap<>();

    private static int tokenizeWord(String c) {
        return map.computeIfAbsent(c, k -> map.size());
    }

    public static int[] tokenize(String s) {
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            String currentCharStr = String.valueOf(currentChar);

            if (Character.UnicodeScript.of(currentChar) == Character.UnicodeScript.HAN) {
                if (currentToken.length() > 0) {
                    list.add(tokenizeWord(currentToken.toString().toLowerCase()));
                    currentToken.setLength(0);
                }
                list.add(tokenizeWord(currentCharStr.toLowerCase()));
            } else if (Character.isWhitespace(currentChar)) {
                if (currentToken.length() > 0) {
                    list.add(tokenizeWord(currentToken.toString().toLowerCase()));
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(currentChar);
            }
        }
        if (currentToken.length() > 0) {
            list.add(tokenizeWord(currentToken.toString().toLowerCase()));
        }
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}
