package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebFilter("/index.jsp")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("paths.properties");
        prop.load(input);

        if (session.getAttribute("auth") == null) {
            session.setAttribute("auth", false);
            chain.doFilter(req, res);
        } else if (session.getAttribute("auth").equals(true)) {
            req.getRequestDispatcher(prop.getProperty("chats")).forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
    //TODO clear localStorage when auth=true
}
