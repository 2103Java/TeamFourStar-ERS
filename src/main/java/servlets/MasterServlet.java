package servlets;

import controller.RequestHelper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MasterServlet extends HttpServlet {
    /*
    * see web.xml
    * "/ers/" was added to uri
    * so servlet doesn't handle ALL traffic ie from HTML, CSS, and JS
    */
@Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    RequestHelper.process(req,res);
}
}
