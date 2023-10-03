package org.eu.huzpsb.crawl4j.search;

import java.io.File;
import java.util.*;

public class SearchCLI {
    public static void main(String[] args) throws Exception {
        File db = new File("c4j.db");
        if (!db.exists()) {
            System.out.println("异常！看上去您是第一次使用Cr4wl4j，我们需要先为您建立索引。");
            System.out.println("请跟随提示完成操作。");
            Indexer.doIndex();
            System.out.println("索引建立完成，现在您可以使用Cr4wl4j进行搜索了。");
            System.out.println("请重新运行本程序。");
            System.exit(0);
        }
        long start = System.currentTimeMillis();
        Map<Integer, Map<Integer, Integer>> tokens = new HashMap<>();
        // token -> (articleId -> weight)
        Map<Integer, String> lines = new HashMap<>();
        Map<Integer, String> titles = new HashMap<>();
        Scanner scanner = new Scanner(db, "UTF-8");
        int idx = 1000000;
        while (true) {
            try {
                idx++;
                String[] parts = scanner.nextLine().split("淦");
                if (parts.length != 2)
                    break;
                lines.put(idx, parts[0]);
                titles.put(idx, parts[1]);
                for (Character c : parts[1].toCharArray()) {
                    int token = Tokenlizer.tokenlize(c);
                    Map<Integer, Integer> map = tokens.computeIfAbsent(token, k -> new HashMap<>());
                    map.put(idx, map.getOrDefault(idx, 0) + 1);
                }
            } catch (Exception e) {
                break;
            }
        }
        if (lines.size() <= 1) {
            System.out.println("异常！数据库可能已经损坏，请手动删除数据库后重新运行本程序。");
            System.exit(0);
        }
        System.out.println("索引建立完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("请输入关键词：");
            String keyword = sc.nextLine();
            System.out.println("关键词 [" + keyword + "] 的搜索结果如下：");
            start = System.currentTimeMillis();
            Map<Integer, Integer> result = new HashMap<>();
            // articleId -> weight
            for (Character c : keyword.toCharArray()) {
                int token = Tokenlizer.tokenlize(c);
                Map<Integer, Integer> map = tokens.get(token);
                if (map == null)
                    continue;
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    int articleId = entry.getKey();
                    int weight = entry.getValue();
                    result.put(articleId, result.getOrDefault(articleId, 0) + weight);
                }
            }
            List<PendingResult> list = new ArrayList<>();
            result.forEach((articleId, weight) -> list.add(new PendingResult(articleId, weight)));
            list.sort((o1, o2) -> o2.weight - o1.weight);
            int size = Math.min(5, list.size());
            for (int i = 0; i < size; i++) {
                PendingResult pendingResult = list.get(i);
                int id = pendingResult.index;
                System.out.println("[" + (i + 1) + "] 文章标题：" + titles.get(id));
                System.out.println("[+] 文章内容：" + lines.get(id));
            }
            System.out.println("搜索完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println(" ");
        }
    }
}
