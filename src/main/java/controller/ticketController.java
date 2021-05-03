package controller;

import java.io.IOException;
import java.util.List;

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
	public static List<Ticket> getAllTickets(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("json/application");



		List<Ticket> ticks = tServ.getAllTickets();

		ObjectMapper om = new ObjectMapper();

		try {
			res.getWriter().write(om.writeValueAsString(ticks));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticks;
	}

    public static void postTicket(HttpServletRequest req, HttpServletResponse res) {
    	int userID = Integer.parseInt(req.getParameter("userid"));
    	Ticket ti = new Ticket(userID);
    	
    	ti.setDescription(req.getParameter("description"));
    	ti.setAmount(Double.parseDouble(req.getParameter("amount")));
    	
    	tServ.postTicket(ti);
    }

	public static void closeTicket(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("json/application");

		int ticketNum = Integer.parseInt(req.getParameter("ticketnumber"));
		boolean bool = Boolean.parseBoolean(req.getParameter("boolean"));
		tServ.closeTicket(ticketNum, bool);

	}

	public static void approveTicket(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("json/application");

		int ticketNum = Integer.parseInt(req.getParameter("ticketnumber"));
		boolean bool = Boolean.valueOf(req.getParameter("action").equals("approve"));

		tServ.approveTicket(ticketNum, bool);

	}

}
