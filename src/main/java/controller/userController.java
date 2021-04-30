package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import service.UserService;

public class userController {
	
	private static UserService uServ = new UserService();
	
    public static void getUser(HttpServletRequest req, HttpServletResponse res) {
    	String uname = (String) req.getParameter("username");
    	String upass = (String) req.getParameter("password");
    	
    	ObjectMapper om = new ObjectMapper();
    	
    	User use = uServ.userLogin(uname, upass);
    	if (use != null) {
    	try {
			res.getWriter().write(om.writeValueAsString(use));
			res.getWriter().write(om.writeValueAsString(uServ.getTickets(use.getUserID())));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	
    }

    public static void postUser(HttpServletRequest req, HttpServletResponse res) {
    	String uname = req.getParameter("username");
    	String upass = req.getParameter("password");
    	Integer empID = Integer.parseInt(req.getParameter("employeeid"));
    	
    	//System.out.println(req);
    	System.out.println(uname + upass + empID);
    	uServ.registerUser(uname, upass, empID);
    }

    public static void putUser(HttpServletRequest req, HttpServletResponse res) {
    	
    }

    public static void deleteUser(HttpServletRequest req, HttpServletResponse res) {
    }
}
