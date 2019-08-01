package com.asm.api;

import com.asm.entity.CrawlerSource;
import com.asm.entity.JsonData;
import com.google.appengine.repackaged.com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetCrawlerSource extends HttpServlet {
    private static Gson gson = new Gson();
    private static final Logger LOGGER = Logger.getLogger(CrawlerSource.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // tra ve list or detail
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try{
            String strId = req.getParameter("id");
            if (strId == null || strId.length() == 0){
                //load list
                List<CrawlerSource> list = ofy().load().type(CrawlerSource.class).filter("status != ", 1).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("Save atuve success").setData(list).toJsonString());
            }else {
                //load detail
                CrawlerSource crawlerSource = ofy().load().type(CrawlerSource.class).id(Long.parseLong(strId)).now();
                if (crawlerSource == null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage(" is not found or deleted").toJsonString());
                    return;
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage(" detail").setData(crawlerSource).toJsonString());
            }

        } catch (Exception ex){
            String messageError = String.format("Can not create , error %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).setMessage(messageError).toJsonString());
            LOGGER.log(Level.SEVERE, messageError);

        }


    }

}
