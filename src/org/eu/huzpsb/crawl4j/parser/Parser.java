package org.eu.huzpsb.crawl4j.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    public static List<String> getAllUrls(String input, String base) {
        String regex = "<a href=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(1);
            if (str.startsWith("http://") || str.startsWith("https://")) {
                list.add(str);
            } else if (input.startsWith("./")) {
                input = input.substring(2);
                list.add(base.substring(0, base.lastIndexOf("/") + 1) + input);
            } else if (input.startsWith("/")) {
                int idx = base.substring(8).indexOf("/");
                idx += 8;
                list.add(base.substring(0, idx) + input);
            }
        }
        return list;
    }
}
