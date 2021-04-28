package controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class RequestHelper {
	final static Logger loggy = Logger.getLogger(RequestHelper.class);

	
    public static void process(HttpServletRequest req, HttpServletResponse res) {

    	
    	
        System.out.println(req.getRequestURI());
        String endpoint = req.getRequestURI();
        //below are good spots for some log4J implementation
        switch (endpoint){
            case "/TeamFourStar-ERS/ers/login":{
                    //login controller gets called here and methods need to be sorted
                String method = req.getMethod();
        switch (method) {
            case "GET" :
            	loggy.info("GET login request recieved.");
                // more thought here... we need to handle
               userController.getUser(req, res);
                break;

            case "POST" :
            	loggy.info("POST login request recieved.");

                //this is more like a register function... so it wont go in login
                userController.postUser(req, res);
                break;

                // PUT and DELETE also should not be possible methods for the login endpoint

            default:
            	loggy.info("INVALID request recieved.");

                res.setStatus(405);

    }
                break;}
            case "/TeamFourStar-ERS/ers/home":

                break;
            case "/TeamFourStar-ERS/ers/ticket_form":

                break;
            case "/TeamFourStar-ERS/ers/submissions":

                break;
            default:
                res.setStatus(404); //this should be a redirect(likely to the login)

        }}
    }