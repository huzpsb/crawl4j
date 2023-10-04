package org.eu.huzpsb.crawl4j.search;

public class PendingResult {
    public int index;
    public int weight;
    public String title;
    public String content;

    public PendingResult(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }
}
