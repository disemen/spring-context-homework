package com.bobocode;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/name")
public class NameServlet extends HttpServlet {
    private static final String SRPING_APP_CONTEXT = "SRPING_APP_CONTEXT";

    /*2. Manually link Spring context with Servlet context.
        a. Create an empty maven project.
        b. Add a new class NameProvider with method getName that returns your name.
        c. Make this class a Spring bean.
        d. Create a new NameServlet that handles /name URL.
        e. During servlet initialization, create a new Spring ApplicationContext that will hold a bean of NameProvider,
        store Spring context as ServletContext attribute with name SRPING_APP_CONTEXT.
        f. Inside servlet method that handles GET request, fetch Spring context, then get NameProvider  bean,
        get your name form it and print it to the response.
        g. Deploy your app to Tomcat 9.
        h. Publish your code to the GitHub and post a link to it as a response to this message.
    */

    @Override
    public void init(ServletConfig config) throws ServletException {
        var applicationContext = new AnnotationConfigApplicationContext(NameProvider.class);
        var servletContext = config.getServletContext();
        servletContext.setAttribute(SRPING_APP_CONTEXT, applicationContext);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var applicationContext = (ApplicationContext) req.getServletContext().getAttribute(SRPING_APP_CONTEXT);
        var nameProvider = applicationContext.getBean(NameProvider.class);
        var name = nameProvider.getName();

        var writer = resp.getWriter();
        writer.println(name);
        writer.flush();
    }
}
