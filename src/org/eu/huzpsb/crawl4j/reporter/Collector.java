package org.eu.huzpsb.crawl4j.reporter;

import org.eu.huzpsb.crawl4j.schedule.AntiDup;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Collector {
    private static final PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("c4j.db", "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized boolean Collect(String url, String content) {
        if (content.contains("$") || content.contains(";&#") || content.contains("\ufffd")) {
            return false;
        }
        if (AntiDup.isProcessed(content)) {
            return false;
        }
        if (content.length() < 2) {
            return false;
        }
        writer.println(url + "淦" + content.replace("淦", "透"));
        return true;
    }

    public static void close() {
        writer.flush();
        writer.close();
    }
}
