package models;

public class User {
	
	private Integer userID;
	private String username;
	private Integer employeeID;
	
	
	public Integer getUserID() {
		return userID;
	}
	public String getUsername() {
		return username;
	}	
	
	public User(Integer id, String username, Integer empID) {
		this.userID = id;
		this.username = username;
		this.employeeID = empID;
		
	}
	public Integer getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}
	
}
