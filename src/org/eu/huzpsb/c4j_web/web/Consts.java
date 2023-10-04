package org.eu.huzpsb.c4j_web.web;

import org.eu.huzpsb.crawl4j.search.PendingResult;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Consts {
    public static String searchHtml = null;
    private static String resultHtml = null;
    private static String resultItemHtml = null;

    public static void init(File folder) {
        try {
            File file = new File(folder, "search.html");
            Scanner sc = new Scanner(file, "UTF-8");
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
            searchHtml = sb.toString();
            sc.close();
            file = new File(folder, "result.html");
            sc = new Scanner(file, "UTF-8");
            sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
            resultHtml = sb.toString();
            sc.close();
            file = new File(folder, "template.html");
            sc = new Scanner(file, "UTF-8");
            sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append("\n");
            }
            resultItemHtml = sb.toString();
            sc.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildResult(List<PendingResult> result) {
        StringBuilder sb = new StringBuilder();
        for (PendingResult pr : result) {
            sb.append(resultItemHtml.replace("%title%", pr.title).replace("%link%", pr.content)).append("\n");
        }
        return resultHtml.replace("%gen%", sb.toString()).replace("%num%", String.valueOf(result.size()));
    }
}
