package org.eu.huzpsb.crawl4j.protocol;

import org.eu.huzpsb.crawl4j.lawful.Spider;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Fetcher {
    public static String UA = "Mozilla/5.0 (compatible; Crawl4j/1.0; +https://huzpsb.eu.org/crawl4j/)";

    public static String getPage(String url) {
        try {
            URI uri = new URI(url);
            URL urlObj = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", UA);
            con.connect();
            int responseCode = con.getResponseCode();
            InputStream inputStream;
            if (responseCode / 100 == 2) {
                inputStream = con.getInputStream();
            } else {
                inputStream = con.getErrorStream();
            }
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8);
            if (str.contains("\ufffd")) {
                str = result.toString("GBK");
            }
            return str;
        } catch (Exception e) {
            return "Could not fetch page at " + url + "\n[" + e.getMessage() + "]\n";
        }
    }

    public static String getPageLawfully(String url) {
        if (!Spider.isLawful(url)) {
            return "Could not fetch page at " + url + "\n[Not lawful]\n";
        }
        return getPage(url);
    }
}
