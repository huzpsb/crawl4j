package org.eu.huzpsb.crawl4j.reporter;

import org.eu.huzpsb.crawl4j.protocol.Parser;
import org.eu.huzpsb.crawl4j.protocol.Fetcher;

import java.util.List;

public class Worker implements Runnable {
    public Worker() {
    }

    @Override
    public void run() {
        while (true) {
            String now = TaskPool.getTask();
            String result = Fetcher.getPageLawfully(now);
            List<String> outages = Parser.getAllUrls(result, now);
            if (Reporter.report(now, result)) {
                for (String outage : outages) {
                    TaskPool.addTask(outage);
                }
            }
        }
    }
}
