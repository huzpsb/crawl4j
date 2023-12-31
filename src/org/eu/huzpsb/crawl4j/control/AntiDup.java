package org.eu.huzpsb.crawl4j.control;

import java.util.HashSet;
import java.util.Set;

public class AntiDup {
    private static final Set<String> processedUrlSet = new HashSet<>();

    public static synchronized boolean isProcessed(String url) {
        if (processedUrlSet.contains(url)) {
            return true;
        } else {
            processedUrlSet.add(url);
            return false;
        }
    }
}
