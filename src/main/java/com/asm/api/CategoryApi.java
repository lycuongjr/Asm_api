package com.asm.api;

import com.asm.entity.Article;
import com.asm.entity.Category;
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

public class CategoryApi extends HttpServlet {
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
                List<Category> list = ofy().load().type(Category.class).filter("status != ", 1).list();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("Save category success").setData(list).toJsonString());
            } else {
                //load detail
                Category category = ofy().load().type(Category.class).id(Long.parseLong(strId)).now();
                if (category == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("category is not found or deleted").toJsonString());
                    return;
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(new JsonData().setStatus(HttpServletResponse.SC_OK).setMessage("category detail").setData(category).toJsonString());
            }

        } catch (Exception ex) {
            String messageError = String.format("Can not create category, error %s", ex.getMessage());
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
            Category category = gson.fromJson(content, Category.class);
            ofy().save().entity(category).now();
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_CREATED, category, "Save category Success");
            resp.getWriter().println(gson.toJson(jsonData));


        } catch (Exception ex) {
            String messageError = String.format("Can not create new category, error: %s", ex.getMessage());
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
        Category existCategory = ofy().load().type(Category.class).id(Long.parseLong(strId)).now();
        if (existCategory == null || existCategory.getStatus() == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null, "category not found or deleted");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "category is not found or deleted!");
            return;
        }
        try {
            String content = StringUtil.convertInputStreamToString(req.getInputStream());
            Category categoryUpdate = gson.fromJson(content, Category.class);
            existCategory.setName(categoryUpdate.getName());
            existCategory.setDescription(existCategory.getDescription());
            existCategory.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existCategory).now();

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, existCategory, "Save article success (update)");
            resp.getWriter().println(gson.toJson(existCategory));


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
        Category existCategory = ofy().load().type(Category.class).id(Long.parseLong(strId)).now();
        if (existCategory == null || existCategory.getStatus() == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_NOT_FOUND, null, "Atube is not found or deteled!");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "category is not found or deleted");
            return;
        }
        try {
            existCategory.setStatus(0);
            existCategory.setDeletedAtMLS(Calendar.getInstance().getTimeInMillis());
            ofy().save().entity(existCategory).now();
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_OK, null, "Remove atube success!");
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, "remove category success");

        } catch (Exception ex) {
            String messageError = String.format("Can not remove atube, error %s", ex.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonData jsonData = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null, messageError);
            resp.getWriter().println(gson.toJson(jsonData));
            LOGGER.log(Level.SEVERE, messageError);

        }

    }
}

