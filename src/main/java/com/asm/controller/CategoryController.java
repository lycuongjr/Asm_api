package com.asm.controller;

import com.asm.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CategoryController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/category/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        ofy().save().entity(Category.Builder.aCategory()
                .withName(name)
                .withDescription(description)
                .build()).now();
        resp.getWriter().println("Okie");
        // redirect to list category.
    }
}
