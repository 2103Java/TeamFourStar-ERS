package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    		HttpSession session = req.getSession();
    		session.setAttribute("user", use);

    		//prob don't need
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
		boolean check = uServ.registerUser(uname, upass, empID);
    	System.out.println(check);
    	if(check==true){
			ObjectMapper om = new ObjectMapper();

			try {
				res.getWriter().write(om.writeValueAsString(true));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.setStatus(201);
		} else{
    		res.setStatus(401);
		}


    }
	// password updater
    public static void putUser(HttpServletRequest req, HttpServletResponse res) {
    	
    }

    public static void deleteUser(HttpServletRequest req, HttpServletResponse res) {
    }

	public static void getCurUser(HttpServletRequest req, HttpServletResponse res) {

    	HttpSession currentUserSes = req.getSession(false);

		if (currentUserSes != null) {
			try{
			User currentUser = (User) currentUserSes.getAttribute("user");
				ObjectMapper om = new ObjectMapper();
				//prob don't need
				res.getWriter().write(om.writeValueAsString(currentUser));

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
