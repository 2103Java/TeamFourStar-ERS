package service;

import dao.TicketDao;
import dao.TicketDaoImpl;
import models.Ticket;

public class TicketService {
	
	private static TicketDao tDao = new TicketDaoImpl(); 
	
	public Ticket getTicket(int ticketNum) {
		return tDao.getTicket(ticketNum);
	}
	
	public void postTicket(Ticket tick) {
		if(tick != null) {tDao.registerTicket(tick);}
	}

}
