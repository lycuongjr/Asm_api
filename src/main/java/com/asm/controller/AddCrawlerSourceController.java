package com.asm.api;
import com.asm.entity.CrawlerSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static com.googlecode.objectify.ObjectifyService.ofy;

public class AddCrawlerSourceController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/crawler-source/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CrawlerSource crawlerSource = new CrawlerSource();

        String url = req.getParameter("url");
        String linkSelector = req.getParameter("linkSelector");
        String titleSelector = req.getParameter("titleSelector");
        String descriptionSelector = req.getParameter("descriptionSelector");
        String contentSelector = req.getParameter("contentSelector");
        ofy().save().entity(CrawlerSource.Builder.aCrawlerSource().withUrl(url).withLinkSelector(linkSelector).withTitleSelector(titleSelector).withDescriptionSelector(descriptionSelector).withContentSelector(contentSelector).build()).now();
        resp.getWriter().println("Save success");
    }
}
