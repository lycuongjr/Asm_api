package com.asm.taskqueue;
import com.asm.entity.CrawlerSource;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AddQueue extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddQueue.class.getSimpleName());
    private static Queue q = QueueFactory.getQueue("queue-green");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CrawlerSource crawlerSource = new CrawlerSource();
        List<CrawlerSource> crawlerSourcesList = ofy().load().type(CrawlerSource.class).filter("status", 1).list();
        if (crawlerSourcesList.size() == 0) {
            LOGGER.warning("crawlerSourcesList size: " + crawlerSourcesList.size());
            return;
        }
        for (CrawlerSource CrawlerSource :
                crawlerSourcesList) {
            Document document = Jsoup.connect(crawlerSource.getUrl()).ignoreContentType(true).get();
            Elements elements = document.select(crawlerSource.getLinkSelector());
            for (Element el : elements) {
                String link = el.attr("href");
                HashMap<String, Object> hashMapQuere = new HashMap<>();
                hashMapQuere.put("link", link);
                hashMapQuere.put("sourceId", crawlerSource.getId());
                LOGGER.info(link);
                q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(new Gson().toJson(hashMapQuere)));
            }

        }
    }
}

