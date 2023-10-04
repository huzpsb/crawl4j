package org.eu.huzpsb.crawl4j.control;

public class Reporter {
    public static boolean report(String url, String content) {
        try {
            String split = content.split("<title>")[1];
            split = split.split("</title>")[0];
            if (split.contains("\n")) {
                return false;
            }
            return Collector.Collect(url, split);
        } catch (Exception ex) {
            return false;
        }
    }
}
