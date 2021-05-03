package service;

import dao.TicketDao;
import dao.TicketDaoImpl;
import models.Ticket;

import java.util.List;

public class TicketService {

    private static TicketDao tDao = new TicketDaoImpl();

    public Ticket getTicket(int ticketNum) {
        return tDao.getTicket(ticketNum);
    }

    public List<Ticket> getAllTickets() {
        return tDao.getOpenTickets();
    }

    public void postTicket(Ticket tick) {
        if (tick != null) {
            tDao.registerTicket(tick);
        }
    }

    public void approveTicket(int num, boolean bool) {
        tDao.approveTicket(num, bool);
        tDao.closeTicket(num, false);
    }

    public void closeTicket(int num, boolean bool) {
        tDao.closeTicket(num, bool);
    }

}
