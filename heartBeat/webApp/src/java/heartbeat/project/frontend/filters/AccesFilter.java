package heartbeat.project.frontend.filters;

import heartbeat.project.frontend.beans.session.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
public class AccesFilter implements Filter {

    @Autowired
    SessionBean sessionBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        ServletContext servletContext = filterConfig.getServletContext();
//        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//
//        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
//
//        autowireCapableBeanFactory.configureBean(sessionBean, "sessionBean");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //todo
        System.out.println();
        ((HttpServletResponse) servletResponse).sendRedirect( ((HttpServletRequest)servletRequest).getContextPath() +"/login.xhtml");
//        if( !sessionBean.isLoggedIn() ){
//            // User is not logged in, so redirect to index.
//            HttpServletResponse res = (HttpServletResponse) servletResponse;
//            res.sendRedirect("/login.xhtml");
//        }

    }

    @Override
    public void destroy() {

    }
}
