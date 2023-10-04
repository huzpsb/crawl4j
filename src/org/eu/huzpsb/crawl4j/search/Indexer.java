package org.eu.huzpsb.crawl4j.search;

import org.eu.huzpsb.crawl4j.Main;
import org.eu.huzpsb.crawl4j.protocol.Lawful;
import org.eu.huzpsb.crawl4j.protocol.Fetcher;
import org.eu.huzpsb.crawl4j.reporter.Collector;
import org.eu.huzpsb.crawl4j.reporter.TaskPool;
import org.eu.huzpsb.crawl4j.reporter.Worker;

import java.util.Scanner;

public class Indexer {
    public static void doIndex() {
        System.out.println("请输入一个网址，我们将从这个网址开始爬取。例如（https://www.2345.com/）：");
        System.out.println("提示！选择与你所需要的信息最接近的网址，可以提高爬取效率。");
        System.out.println("提示！输入一个导航网址，可以使你的爬虫更贴近日常使用。");
        System.out.print(">");
        TaskPool.addTask(Main.sc.nextLine());
        System.out.print("请输入一个时间（分钟），爬虫将在这段时间内爬取网页：");
        System.out.println("提示！在这段时间内关闭程序，半成品索引将被丢弃。");
        System.out.println("提示！太长的时间（一个小时以上）可能导致资源不足，程序崩溃。");
        System.out.print(">");
        long time = Long.parseLong(Main.sc.nextLine());
        if (time < 0) {
            time = -time;
            Lawful.unleash = true;
            Fetcher.UA = "Mozilla/5.0 (compatible; Like Baiduspider; Crawl4j/1.0; +https://huzpsb.eu.org/crawl4j/)";
        }
        for (int i = 0; i < 50; i++) {
            new Thread(new Worker()).start();
        }
        try {
            Thread.sleep(time * 60L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collector.close();
    }
}
