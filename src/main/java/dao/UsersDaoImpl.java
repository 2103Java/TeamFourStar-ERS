package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Ticket;
import models.User;
import utilities.DAOUtility;
import utilities.PasswordHashing;

public class UsersDaoImpl implements UsersDao{
	Connection connection = null;
	PreparedStatement stmt = null;
	PasswordHashing pHash = new PasswordHashing();

	@Override
	public List<Ticket> getUserTickets(Integer userid) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		try {
			connection = DAOUtility.getConnection();
			String sql = "SELECT * FROM Tickets WHERE related_user = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userid);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Ticket tick = new Ticket(rs.getInt("ticket_id"),rs.getInt("related_user"));
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

	@Override
	public User userLogin(String username, String password) {
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
				if (pass.equals(pHash.encryptPassword(salt, password))) {
					return user;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void registerUser(String username, String password, Integer empID) {
		String salt = pHash.makeSalt();
		String hashedPass = pHash.encryptPassword(salt, password);
		
		try {
			connection = DAOUtility.getConnection();
			String sql = "INSERT INTO users (username, user_password, salt, employee_id) VALUES (?,?,(SELECT employee_id FROM employees where employee_id = ?))";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, hashedPass);
			stmt.setString(3, salt);
			stmt.setInt(4, empID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
