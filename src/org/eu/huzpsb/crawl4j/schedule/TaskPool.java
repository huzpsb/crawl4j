package org.eu.huzpsb.crawl4j.schedule;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskPool {
    private static BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();

    public static String getTask() {
        try {
            return taskQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public static void addTask(String url) {
        if (AntiDup.isProcessed(url)) {
            return;
        }
        if (taskQueue.size() > 10000) {
            return;
        }
        taskQueue.add(url);
    }
}
