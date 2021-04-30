package dao;

import java.util.List;

import models.Ticket;

public interface TicketDao {

	
	public void registerTicket(Ticket ticket);
	public void approveTicket(int ticketNum);
	public void closeTicket(int ticketNum);
	public Ticket getTicket(int ticketNum);
	public List<Ticket> getOpenTickets();
}
