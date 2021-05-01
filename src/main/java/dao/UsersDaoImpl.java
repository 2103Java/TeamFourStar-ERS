package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import models.Ticket;
import models.User;
import utilities.DAOUtility;
import utilities.PasswordHashing;

public class UsersDaoImpl implements UsersDao{
	Connection connection = null;
	PreparedStatement stmt = null;
	PasswordHashing pHash = new PasswordHashing();
	final static Logger loggy = Logger.getLogger(UsersDaoImpl.class);
	
	//Remember the CRUD.

	@Override
	public List<Ticket> getUserTickets(Integer userid) {
		loggy.info("DATABASE OPERATION for GET TICKETS for USER ID: " + userid);
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		try {
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM tickets WHERE related_user = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userid);
			
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
        	loggy.warn("FAILURE in getUserTickets method in UsersDaoImpl.");

			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public User userLogin(String username, String password) {
		loggy.info("ATTEMPTING LOGIN FOR: " + username);
		User user;
		try{
			String pass = null;
			String salt = null;
			//get the salt for our hash!
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM users WHERE username = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				salt = rs.getString("salt");
				pass = rs.getString("user_password");
				user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getInt("employee_id"));
				user.setIsAdmin(rs.getBoolean("is_admin"));
				if (pass.equals(pHash.encryptPassword(salt, password))) {
					return user;
				}
			}
		}catch(SQLException e) {
        	loggy.warn("FAILURE in userLogin method in UsersDaoImpl.");

			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void registerUser(String username, String password, Integer empID) {
		System.out.println("ATTEMPTING TO REGISTER USER.");
    	loggy.info("ATTEMPTING REGISTRATION OF USER: " + username);

		String salt = pHash.makeSalt();
		String hashedPass = pHash.encryptPassword(salt, password);
		
		try {
			connection = DAOUtility.getConnection();
			String sql = "INSERT INTO users (username, user_password, salt, employee_id, is_admin) VALUES (?,?,?,(SELECT employee_id FROM employees where employee_id = ?),(SELECT is_admin FROM employees where employee_id = ?))";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, hashedPass);
			stmt.setString(3, salt);
			stmt.setInt(4, empID);
			stmt.setInt(5, empID);
			stmt.executeUpdate();
		} catch (SQLException e) {
        	loggy.warn("FAILURE in registerUser method in UsersDaoImpl.");

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean checkEmployment(int empID) {
    	loggy.info("ATTEMPTING employment check for employee ID: " + empID);

		try {
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM employees WHERE employee_id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, empID);
			
			ResultSet rs = stmt.executeQuery();
			
			return rs.next();
		} catch (Exception e) {
        	loggy.warn("FAILURE in checkEmployment method in UsersDaoImpl.");

			e.printStackTrace();
		}
			return false;
		
	}
	
}
