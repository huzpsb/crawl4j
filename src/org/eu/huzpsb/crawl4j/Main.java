package org.eu.huzpsb.crawl4j;

import org.eu.huzpsb.crawl4j.reporter.Collector;
import org.eu.huzpsb.crawl4j.schedule.TaskPool;
import org.eu.huzpsb.crawl4j.worker.Worker;

public class Main {
    public static void main(String[] args) {
        TaskPool.addTask("https://www.2345.com/");
        for (int i = 0; i < 50; i++) {
            Thread.ofVirtual().name("T-" + i).start(new Worker());
        }
        try {
            Thread.sleep(60L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collector.close();
        System.exit(0);
    }
}
