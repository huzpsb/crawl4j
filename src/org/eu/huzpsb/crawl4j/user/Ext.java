package org.eu.huzpsb.crawl4j.user;

import org.eu.huzpsb.crawl4j.protocol.Fetcher;

public class Ext {
    public static void main(String[] args) {
        String s = Fetcher.getPage("https://hostloc.com/").toLowerCase();
        System.out.println(trimPage(s));
    }

    public static String trimPage(String s) {
        s = s.replaceAll("\\n", " ");
        s = s.replaceAll("<script[^>]*>.*?</script>", "");
        s = s.replaceAll("<style[^>]*>.*?</style>", "");
        s = s.replaceAll("<[^>]*>", "");
        s = s.replaceAll("&[^;]*;", "");
        s = s.replaceAll("\\s+", " ");
        return s;
    }
}
