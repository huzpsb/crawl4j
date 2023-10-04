package org.eu.huzpsb.c4j_web;

import nano.http.bukkit.api.BukkitServerProvider;
import nano.http.d2.console.Logger;
import nano.http.d2.consts.Mime;
import nano.http.d2.consts.Status;
import nano.http.d2.core.Response;
import org.eu.huzpsb.c4j_web.web.Consts;
import org.eu.huzpsb.crawl4j.search.Core;
import org.eu.huzpsb.crawl4j.search.PendingResult;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class Main extends BukkitServerProvider {
    @Override
    public void onEnable(String name, File dir, String uri) {
        Consts.init(dir);
        Logger.info("C4J-Web is enabled!");
    }

    @Override
    public void onDisable() {
        Logger.info("C4J-Web is disabled!");
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        String pr = parms.getProperty("question");
        if (pr == null) {
            return new Response(Status.HTTP_OK, Mime.MIME_HTML, Consts.searchHtml);
        }
        List<PendingResult> results = Core.doSearch(pr);
        return new Response(Status.HTTP_OK, Mime.MIME_HTML, Consts.buildResult(results));
    }

    @Override
    public Response fallback(String uri, String method, Properties header, Properties parms, Properties files) {
        return null;
    }
}
