package com.asm.filter;
import com.asm.entity.Article;
import com.asm.entity.Category;
import com.asm.entity.CrawlerSource;
import com.googlecode.objectify.ObjectifyService;
import javax.servlet.*;
import java.io.IOException;

public class ObjectifyRegisterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ObjectifyService.register(Article.class);
        ObjectifyService.register(Category.class);
        ObjectifyService.register(CrawlerSource.class);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
