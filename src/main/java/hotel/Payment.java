package hotel;

import java.sql.Date;


public class Payment {
    private int paymentID;
    private double amount;
    private Date paymentDate;
    private String paymentMethod;
    private int bookingID;
    
    private String customerName;
    private Date checkInDate;
    private Date checkOutDate;
    private int totalStay;
    
    
    
    public Payment(int id, double amount2, Date date, String method, int bookingID2) {
		// TODO Auto-generated constructor stub
	}
	public int getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public int getTotalStay() {
		return totalStay;
	}
	public void setTotalStay(int totalStay) {
		this.totalStay = totalStay;
	}
	

}
	
    
