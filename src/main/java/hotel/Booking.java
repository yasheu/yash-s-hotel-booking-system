package hotel;

import java.io.Serializable;
import java.sql.Date;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    // Table specific attributes
    private int bookingID;
    private Date bookingDate;
    private String bookingStatus;
    private int numOfGuest;
    private int customerID;
    private int roomTypeID;
    private Date checkInDate;
    private Date checkOutDate;
    private String customerPhone;


    // Join specific attributes (from other tables)
    private String customerName;
    private String roomTypeName;
    private String location;
    private int roomNumber;

    public Booking() {}

    // Getters and Setters
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public int getNumOfGuest() { return numOfGuest; }
    public void setNumOfGuest(int numOfGuest) { this.numOfGuest = numOfGuest; }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public int getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
}