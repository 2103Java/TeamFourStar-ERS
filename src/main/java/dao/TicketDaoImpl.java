package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Ticket;
import utilities.DAOUtility;

public class TicketDaoImpl implements TicketDao {

	Connection connection = null;
	PreparedStatement stmt = null;
	
	
	@Override
	public void registerTicket(Ticket ticket) {
		
		try {
			connection = DAOUtility.getConnection();
			String sql = "INSERT INTO tickets (related_user, is_open, is_approved, description, amount) VALUES (?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ticket.getUserID());
			stmt.setBoolean(2, ticket.isOpenStatus());
			stmt.setBoolean(3, ticket.isApprovalStatus());
			stmt.setString(4, ticket.getDescription());
			stmt.setDouble(5, ticket.getAmount());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}


	@Override
	public void approveTicket(int ticketNum) {
		
		try {
			connection = DAOUtility.getConnection();
			String sql = "UPDATE tickets SET is_approved = true WHERE ticket_id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ticketNum);
			
			stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void closeTicket(int ticketNum) {
		try {
			connection = DAOUtility.getConnection();
			String sql = "UPDATE tickets SET is_open = true WHERE ticket_id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ticketNum);
			
			stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public Ticket getTicket(int ticketNum) {
		try {
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ticketNum);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Ticket tick = new Ticket(rs.getInt("related_user"));
				tick.setTicketNum(ticketNum);
				tick.setApprovalStatus(rs.getBoolean("is_approved"));
				tick.setOpenStatus(rs.getBoolean("is_open"));
				tick.setAmount(rs.getDouble("amount"));
				tick.setDescription(rs.getString("description"));
				return tick;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<Ticket> getOpenTickets() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		try {
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM tickets WHERE is_open = true";
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Ticket tick = new Ticket(rs.getInt("related_user"));
				tick.setTicketNum(rs.getInt("ticket_id"));
				tick.setApprovalStatus(rs.getBoolean("is_approved"));
				tick.setOpenStatus(rs.getBoolean("is_open"));
				tick.setAmount(rs.getDouble("amount"));
				tick.setDescription(rs.getString("description"));
				tickets.add(tick);
			}
			return tickets;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
