package org.eu.huzpsb.crawl4j.search;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SearchCLI {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Map<Integer, Map<Integer, Integer>> tokens = new HashMap<>();
        // token -> (articleId -> weight)
        Map<Integer, String> lines = new HashMap<>();
        Map<Integer, String> titles = new HashMap<>();
        Scanner scanner = new Scanner(new File("result.txt"), StandardCharsets.UTF_8);
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
        System.out.println("索引建立完成，耗时：" + (System.currentTimeMillis() - start) + "ms");

        while (true) {
            System.out.print("请输入关键词：");
            String keyword = new Scanner(System.in).nextLine();
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
                System.out.println(" ");
            }
            System.out.println("搜索完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
