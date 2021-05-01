package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Ticket;
import service.TicketService;

public class ticketController {

	private static TicketService tServ = new TicketService();
	
    public static void getTicket(HttpServletRequest req, HttpServletResponse res) {
    	res.setContentType("json/application");
    	
    	Integer ticketNum = Integer.parseInt(req.getParameter("ticketnumber"));
    	
    	Ticket tick = tServ.getTicket(ticketNum);
    	
    	ObjectMapper om = new ObjectMapper();
    	
    	try {
			res.getWriter().write(om.writeValueAsString(tick));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void postTicket(HttpServletRequest req, HttpServletResponse res) {
    	Integer userID = Integer.parseInt(req.getParameter("userid"));
    	Ticket ti = new Ticket(userID);
    	
    	ti.setDescription(req.getParameter("description"));
    	ti.setAmount(Double.parseDouble(req.getParameter("amount")));
    	
    	tServ.postTicket(ti);
    }
}
