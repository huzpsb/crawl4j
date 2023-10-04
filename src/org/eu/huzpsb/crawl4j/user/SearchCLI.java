package org.eu.huzpsb.crawl4j.user;

import org.eu.huzpsb.crawl4j.Main;
import org.eu.huzpsb.crawl4j.search.Core;
import org.eu.huzpsb.crawl4j.search.PendingResult;

import java.io.File;
import java.util.*;

public class SearchCLI {
    public static void startCLI() {
        File db = new File("c4j.db");
        if (!db.exists()) {
            System.out.println("异常！看上去您是第一次使用Cr4wl4j，我们需要先为您建立索引。");
            System.out.println("请跟随提示完成操作。");
            Indexer.doIndex();
            System.out.println("索引建立完成，现在您可以使用Cr4wl4j进行搜索了。");
            System.out.println("请重新运行本程序。");
            System.exit(0);
        }

        while (true) {
            System.out.print("请输入关键词：");
            String keyword = Main.sc.nextLine();
            long start = System.currentTimeMillis();
            System.out.println("关键词 [" + keyword + "] 的搜索结果如下：");
            List<PendingResult> results = Core.doSearch(keyword);
            int idx = 0;
            for (PendingResult result : results) {
                idx++;
                System.out.println("[" + idx + "] " + result.title);
                System.out.println("[↑] " + result.content);
            }
            System.out.println("搜索完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println(" ");
        }
    }
}
