package com.asm.api;
import com.asm.entity.Article;
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

public class ArticleApi extends HttpServlet {
    private static com.google.appengine.repackaged.com.google.gson.Gson gson = new Gson();
    private static final Logger LOGGER = Logger.getLogger(Article.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // tra ve list or detail
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try{
            String strId = req.getParameter("id");
            if (strId == null || strId.length() == 0){
                //load list
                List<Article> list = ofy().load().type(Article.class).filter("status != ", 1).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("Save Aricle success").setData(list).toJsonString());
            }else {
                //load detail
                Article article = ofy().load().type(Article.class).id(Long.parseLong(strId)).now();
                if (article == null){
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("article is not found or deleted").toJsonString());
                    return;
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("article detail").setData(article).toJsonString());
            }

        } catch (Exception ex){
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
            Article article = gson.fromJson(content, Article.class);
            ofy().save().entity(article).now();
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_CREATED, article, "Save aricle Success");
            resp.getWriter().println(gson.toJson(jsonData));
        }catch (Exception ex){
            String messageError = String.format("Can not create new article, error: %s", ex.getMessage());
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
        if (strId == null || strId.length() == 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_BAD_REQUEST, null, "bad request");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "do not ID");
            return;
        }
        // kiem tra su ton tai cua Atube trong database voi id truyen len
        // trong truong` hop` k to`n tai thi` tra ve not found
        Article existAtube = ofy().load().type(Article.class).id(Long.parseLong(strId)).now();
        if (existAtube == null || existAtube.getStatus() == 0){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null,"Atube not found or deleted");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Article is not found or deleted!");
            return;
        }
        try {
            String content = StringUtil.convertInputStreamToString(req.getInputStream());
            Article articleUpdate = gson.fromJson(content, Article.class);
            existAtube.setUrl(articleUpdate.getUrl());
            existAtube.setDescription(articleUpdate.getDescription());
            existAtube.setAuthor(articleUpdate.getAuthor());
            existAtube.setDescription(articleUpdate.getDescription());
            existAtube.setContent(articleUpdate.getContent());
            existAtube.setTitle(articleUpdate.getTitle());
            existAtube.setCategory(articleUpdate.getCategory());
            existAtube.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existAtube).now();

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, existAtube, "Save article success (update)");
            resp.getWriter().println(gson.toJson(existAtube));


        }catch (Exception ex){
            String messageError = String.format("Can not update article %s", ex.getMessage());
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
        if(strId == null || strId.length() == 0){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_BAD_REQUEST, null, "Bad request");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Have no id");
            return;
        }
        //kiem tra ton tai cua Atube trong database voi id truyen len
        // trong truong hop khong ton tai thi tra ve not found
        Article existArticle = ofy().load().type(Article.class).id(Long.parseLong(strId)).now();
        if (existArticle == null || existArticle.getStatus() == 0){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null, "Atube is not found or deteled!");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "Article is not found or deleted");
            return;
        }
        try {
            existArticle.setStatus(0);
            existArticle.setDeletedAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existArticle).now();
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, null, "Remove article success!" );
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "remove article success");

        }catch (Exception ex){
            String messageError = String.format("Can not remove article, error %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, messageError);
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, messageError);

        }

    }
}

