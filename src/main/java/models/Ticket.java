package models;

public class Ticket {
	private int ticketNum;
	private int userID;
	private boolean openStatus;
	private boolean approvalStatus;
	private String description;
	private double amount;
	
	public Ticket(int userID) {
		super();
		this.userID = userID;
	}
	
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public int getUserID() {
		return userID;
	}
	public boolean isOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(boolean openStatus) {
		this.openStatus = openStatus;
	}
	public boolean isApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
