package BookingSystem;

public class Booking {
    // Task asked for using only name instead of object, but object is stored as a pointer 
    // But Object here is a pointer to the actual room data, so more consistent and easier to look up in this case
    private Room bookedRoom;
    private Date bookingDate;

    public Booking(Room bookedRoom,  Date bookingDate) {
        this.bookedRoom = bookedRoom;
        this.bookingDate = bookingDate;
    };

    public Room getBookedRoom() {
        return this.bookedRoom;
    };

    public String getBookedRoomName() {
        return this.bookedRoom.getName();
    };

    public Date getBookingDate() {
        return this.bookingDate;
    };

    @Override
    public boolean equals(Object object)
    {
        if (object != null && object instanceof Booking)
        {
            Booking otherBooking = (Booking) object;
            return this.getBookedRoom().equals(otherBooking.getBookedRoom()) 
                && this.getBookingDate().equals(otherBooking.getBookingDate());
        }
        return false;
    }

    
    @Override
    public String toString() {
        return String.format("%s %s" ,this.bookedRoom.getName(), this.getBookingDate().toString());
    }
}
