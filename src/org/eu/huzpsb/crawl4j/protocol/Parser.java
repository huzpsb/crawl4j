package org.eu.huzpsb.crawl4j.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static void main(String[] args) {
        List<String> cases = getAllUrls("<a href=\"./a.html\">1</a>\n" +
                "<a href=\"/b.html\">2</a>\n" +
                "<a href=\"c.html\">3</a>", "https://www.example.com/w/axe?8888");
        for (String s : cases) {
            System.out.println(s);
        }
    }

    public static List<String> getAllUrls(String input, String baseP) {
        String regex = "<a href=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(1);
            if (str.startsWith("http://") || str.startsWith("https://")) {
                list.add(str);
            } else {
                if (str.contains("javascript:")) continue;
                String base = baseP;
                int firstQuestionMarkIndex = base.indexOf("?");
                if (firstQuestionMarkIndex != -1) {
                    base = base.substring(0, firstQuestionMarkIndex);
                }

                if (str.startsWith("./")) {
                    str = str.substring(2);
                }
                if (str.startsWith("/")) {
                    str = str.substring(1);
                    int firstSlashIndex = base.substring(8).indexOf("/") + 8;
                    if (firstSlashIndex != 8 - 1) {
                        base = base.substring(0, firstSlashIndex);
                    }
                } else {
                    int lastSlashIndex = base.lastIndexOf("/");
                    if (lastSlashIndex > 8) {
                        base = base.substring(0, lastSlashIndex);
                    }
                }
                list.add(base + "/" + str);
            }
        }
        return list;
    }
}
