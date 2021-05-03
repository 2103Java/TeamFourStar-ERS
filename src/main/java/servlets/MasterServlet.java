package servlets;

import controller.RequestHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MasterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5073783423047296831L;

/*
    * see web.xml
    * "/ers/" was added to uri
    * so servlet doesn't handle ALL traffic ie from HTML, CSS, and JS
    */
@Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    System.out.println("Request recieved.");

    RequestHelper.process(req,res);
    
}
}
