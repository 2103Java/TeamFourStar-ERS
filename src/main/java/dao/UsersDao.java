package dao;

import java.util.List;

import models.Ticket;
import models.User;

public interface UsersDao {
	
	List<Ticket> getUserTickets(Integer userID);
	User userLogin(String username, String password);
	void registerUser(String username, String password, Integer empID);
	boolean checkEmployment(int empID);
}
