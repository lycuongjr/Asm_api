package com.asm.api;

import com.asm.entity.Category;
import com.asm.entity.CrawlerSource;
import com.asm.entity.JsonData;
import com.asm.util.StringUtil;
import com.google.appengine.repackaged.com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrawlerSourceApi extends HttpServlet {
    private static Gson gson = new Gson();
    private static final Logger LOGGER = Logger.getLogger(Category.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // tra ve list or detail
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String strId = req.getParameter("id");
            if (strId == null || strId.length() == 0) {
                //load list
                List<CrawlerSource> list = ofy().load().type(CrawlerSource.class).filter("status != ", 1).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("Save atuve success").setData(list).toJsonString());
            } else {
                //load detail
                CrawlerSource crawlerSource = ofy().load().type(CrawlerSource.class).id(Long.parseLong(strId)).now();
                if (crawlerSource == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("article is not found or deleted").toJsonString());
                    return;
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("article detail").setData(crawlerSource).toJsonString());
            }

        } catch (Exception ex) {
            String messageError = String.format("Can not create article, error %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).setMessage(messageError).toJsonString());
            LOGGER.log(Level.SEVERE, messageError);

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create new
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String content = StringUtil.convertInputStreamToString(req.getInputStream());
            CrawlerSource crawlerSource = gson.fromJson(content, CrawlerSource.class);
            ofy().save().entity(crawlerSource).now();
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_CREATED, crawlerSource, "Save atube Success");
            resp.getWriter().println(gson.toJson(jsonData));


        } catch (Exception ex) {
            String messageError = String.format("Can not create new aube, error: %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, messageError, null);
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, messageError);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        //update
        // kiem tra ton tai tham so id trong paramater (cach tam thoi)
        //trong truong hop khong ton tai thi tra ve bad request
        String strId = req.getParameter("id");
        if (strId == null || strId.length() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_BAD_REQUEST, null, "bad request");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "do not ID");
            return;
        }
        // kiem tra su ton tai cua Atube trong database voi id truyen len
        // trong truong` hop` k to`n tai thi` tra ve not found
        CrawlerSource existCrawlerSource = ofy().load().type(CrawlerSource.class).id(Long.parseLong(strId)).now();
        if (existCrawlerSource == null || existCrawlerSource.getStatus() == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null, "Atube not found or deleted");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Atube is not found or deleted!");
            return;
        }
        try {
            String content = StringUtil.convertInputStreamToString(req.getInputStream());
            CrawlerSource updateCrawSource = gson.fromJson(content, CrawlerSource.class);
            existCrawlerSource.setUrl(updateCrawSource.getUrl());
            existCrawlerSource.setAuthorSelector(updateCrawSource.getAuthorSelector());
            existCrawlerSource.setContentSelector(updateCrawSource.getContentSelector());
            existCrawlerSource.setLinkSelector(updateCrawSource.getLinkSelector());
            existCrawlerSource.setTitleSelector(updateCrawSource.getTitleSelector());
            existCrawlerSource.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existCrawlerSource).now();

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, existCrawlerSource, "Save article success (update)");
            resp.getWriter().println(gson.toJson(existCrawlerSource));


        } catch (Exception ex) {
            String messageError = String.format("Can not update atube %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, messageError);
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, messageError);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //remove
        //update
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String strId = req.getParameter("id");
        if (strId == null || strId.length() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_BAD_REQUEST, null, "Bad request");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Have no id");
            return;
        }
        //kiem tra ton tai cua Atube trong database voi id truyen len
        // trong truong hop khong ton tai thi tra ve not found
        CrawlerSource existCrawlerSource = ofy().load().type(CrawlerSource.class).id(Long.parseLong(strId)).now();
        if (existCrawlerSource == null || existCrawlerSource.getStatus() == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null, "Atube is not found or deteled!");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Atube is not found or deleted");
            return;
        }
        try {
            existCrawlerSource.setStatus(0);
            existCrawlerSource.setDeleteaAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existCrawlerSource).now();
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, null, "Remove atube success!");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "remove atube success");

        } catch (Exception ex) {
            String messageError = String.format("Can not remove atube, error %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, messageError);
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, messageError);

        }

    }
}
