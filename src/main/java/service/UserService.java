package service;

import java.util.List;

import dao.UsersDao;
import dao.UsersDaoImpl;
import models.Ticket;
import models.User;

public class UserService {
	
	private UsersDao uDao;
	
	public UserService(UsersDao uDao) {
		this.uDao = uDao;
	}
	
	public User userLogin(String username, String password) {
		User u = uDao.userLogin(username, password);
		return u;
	}
	
	public List<Ticket> getTickets(int userID){
		List<Ticket> ticks = uDao.getUserTickets(userID);
		return ticks;
	}
	
	
	public boolean registerUser(String username, String password, int empID) {
		if (uDao.checkEmployment(empID)) {
			uDao.registerUser(username, password, empID);
			return true;
		}
		else return false;
		
	}

}
