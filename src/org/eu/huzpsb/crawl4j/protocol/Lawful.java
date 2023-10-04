package org.eu.huzpsb.crawl4j.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lawful {
    private static final Map<String, Integer> cache = new ConcurrentHashMap<>();
    public static boolean unleash = false;

    public static synchronized boolean isLawful(String url) {
        String lowerCase = url.toLowerCase();
        if (lowerCase.contains("gov") || lowerCase.contains("hust") || lowerCase.contains("bingyan") || lowerCase.contains("people") || lowerCase.contains(".news.cn")) {
            // 避免不必要的麻烦
            return false;
        }
        String[] split = url.split("//");
        String domain = split[split.length == 1 ? 0 : 1].split("/")[0];
        domain = domain.split("\\?")[0];
        if (cache.containsKey(domain)) {
            int i = cache.get(domain);
            if (i > 0) {
                cache.put(domain, i - 1);
                return true;
            } else {
                return false;
            }
        }
        String spiderPath = split[0] + "//" + domain + "/robots.txt";
        String robots = Fetcher.getPage(spiderPath).toLowerCase();
        if (robots.contains("disallow: /\n") && (!unleash || robots.contains("baiduspider"))) {
            cache.put(domain, 0);
            System.out.println("依协议跳过：" + domain);
            return false;
        }
        cache.put(domain, 20);
        System.out.println("正在索引：" + domain);
        return true;
    }
}
