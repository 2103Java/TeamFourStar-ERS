package controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Ticket;
import models.User;
import org.apache.log4j.Logger;
import service.TicketService;
import service.UserService;

import java.io.IOException;
import java.util.List;


public class RequestHelper {
    final static Logger loggy = Logger.getLogger(RequestHelper.class);


    public static void process(HttpServletRequest req, HttpServletResponse res) throws IOException {


        System.out.println(req.getRequestURI());
        String endpoint = req.getRequestURI();
        String method = req.getMethod();
        String action = req.getParameter("action");
        System.out.println(action);

        //below are good spots for some log4J implementation
        switch (endpoint) {

            case "/ers/login": {
                //login controller gets called here and methods need to be sorted
                //we instead put our login logic in userService which is called by userController
                // this can only take a Post method
                if (method.equals("POST")) {

                    loggy.info("POST login request received.");
                    // more thought here... we need to handle
                    userController.getUser(req, res);

                } else {
                    loggy.info("INVALID request method received.");

                    res.setStatus(405);
                }
                break;
            }

            //this endpoint just returns all tickets for the current user
            case "/ers/tickets": {
                if (method.equals("GET")) {
                    UserService uServ = new UserService();
                    HttpSession currentUserSes = req.getSession(false);

                    if (currentUserSes != null) {
                        User currentUser = (User) currentUserSes.getAttribute("user");
                        if(currentUser.getIsAdmin()==true){
                            List<Ticket> allTickets = ticketController.getAllTickets(req,res);
                        } else {
                            List<Ticket> yourTickets = uServ.getTickets(currentUser.getUserID());
                            ObjectMapper om = new ObjectMapper();
                            res.getWriter().write(om.writeValueAsString(yourTickets));
                        }
                    }
                }
                break;
            }
            case "/ers/ticket":{
                if(method.equals("GET")){
                    loggy.info("Ticket request received: Getting Ticket Info");
                    ticketController.getTicket(req, res);
                } else{
                    loggy.warn("Invalid Method");
                    res.setStatus(405);
                }
                break;
            }
            case "/ers/registration":{
                if(method.equals("POST")){
                    loggy.info("Registration request submitted: Verifying User");
                    userController.postUser(req,res);
                } else{
                    loggy.warn("Invalid Method");
                    res.setStatus(405);
                }
                break;
            }
            case "/ers/ticket_form":
                loggy.info("Ticket form request received.");

                switch (method) {
                    //Register a new ticket?
                    case "GET":
                        ticketController.getTicket(req, res);
                        break;

                    case "POST":
                        ticketController.postTicket(req, res);
                        break;


                    default:

                        break;

                }

                break;
            case "/ers/current":
                if(method.equals("GET")){
                    loggy.info("Fetching Current User");
                    userController.getCurUser(req, res);
                } else{
                    loggy.warn("Invalid Method");
                    res.setStatus(405);
                }
                break;

            case "/ers/admin":

                switch(action){
                    case "close":
                        loggy.info("Closing Ticket");
                        ticketController.closeTicket(req, res);
                        break;
                    case "approve":
                    case "deny":
                        loggy.info("Updating Approval Status");
                        ticketController.approveTicket(req, res);
                        break;
                    default: res.setStatus(401);

                }
                break;
            case "/ers/logout":

                HttpSession currentUserSes = req.getSession(false);
                currentUserSes.invalidate();
                break;

            default:

                res.setStatus(404); //this should be a redirect(likely to the login)

        }
    }
}