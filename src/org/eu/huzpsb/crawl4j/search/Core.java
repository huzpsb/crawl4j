package org.eu.huzpsb.crawl4j.search;

import java.io.File;
import java.util.*;

public class Core {
    private static final Map<Integer, Map<Integer, Integer>> tokens = new HashMap<>();
    // token -> (articleId -> weight)
    private static final Map<Integer, String> lines = new HashMap<>();
    private static final Map<Integer, String> titles = new HashMap<>();

    static {
        try {
            long start = System.currentTimeMillis();
            Scanner scanner = new Scanner(new File("c4j.db"), "UTF-8");
            int idx = 1000000;
            while (true) {
                try {
                    idx++;
                    String[] parts = scanner.nextLine().split("淦");
                    if (parts.length != 2)
                        break;
                    lines.put(idx, parts[0]);
                    titles.put(idx, parts[1]);
                    for (Character c : parts[1].replace(" ", "").toCharArray()) {
                        int token = Tokenlizer.tokenlize(c);
                        Map<Integer, Integer> map = tokens.computeIfAbsent(token, k -> new HashMap<>());
                        map.put(idx, map.getOrDefault(idx, 0) + 1);
                    }
                } catch (Exception e) {
                    break;
                }
            }
            if (lines.size() <= 1) {
                System.out.println("异常！数据库可能已经损坏或为空，请手动删除数据库后重新运行本程序。");
                System.exit(-1);
            }
            System.out.println("索引建立完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常！数据库可能已经损坏或无法访问，请手动删除数据库后重新运行本程序。");
            System.exit(-2);
        }
    }

    public static List<PendingResult> doSearch(String keyword) {
        Map<Integer, Integer> resultByToken = new HashMap<>();
        // articleId -> weight
        for (Character c : keyword.toCharArray()) {
            int token = Tokenlizer.tokenlize(c);
            Map<Integer, Integer> map = tokens.get(token);
            if (map == null)
                continue;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int articleId = entry.getKey();
                int weight = entry.getValue();
                resultByToken.put(articleId, resultByToken.getOrDefault(articleId, 0) + weight);
            }
        }
        List<PendingResult> list = new ArrayList<>();
        resultByToken.forEach((articleId, weight) -> list.add(new PendingResult(articleId, weight)));
        list.sort((o1, o2) -> o2.weight - o1.weight);
        int size = Math.min(15, list.size());
        List<PendingResult> resultWithK = list.subList(0, size);
        for (PendingResult pendingResult : resultWithK) {
            String title = titles.get(pendingResult.index);
            pendingResult.title = title;
            if (title.contains(keyword)) {
                pendingResult.weight += 100;
            }
            if (title.length() > 100) {
                pendingResult.weight = 1;
            }
        }
        resultWithK.sort((o1, o2) -> o2.weight - o1.weight);
        int size2 = Math.min(5, resultWithK.size());
        List<PendingResult> resultWithKL = resultWithK.subList(0, size2);
        for (PendingResult pendingResult : resultWithKL) {
            pendingResult.content = lines.get(pendingResult.index);
        }
        return resultWithKL;
    }
}
