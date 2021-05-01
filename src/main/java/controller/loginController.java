package controller;

import org.apache.log4j.Logger;

import models.User;

public class loginController {
    private User u; //model
	final static Logger loggy = Logger.getLogger(loginController.class);

    
    
    public String login(){
    	loggy.info("Login request recieved by controller.");
    	
        return "/login";
    }
}
