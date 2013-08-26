package heartbeat.project.frontend.filters;

import heartbeat.project.frontend.beans.session.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
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

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

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
