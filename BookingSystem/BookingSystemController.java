package BookingSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BookingSystemController {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    // Empty init, due to beeing a controller
    public BookingSystemController() {};

    // Simple fetch
    public ArrayList<Room> getAllRooms() {
        return this.rooms;
    };


    // Fetch all bookings for a date
    private ArrayList<Booking> getBookingsForDate(Date date) {
        // Private due to one should probably not want to or be able to view bookings
        //  uses lambda for more simpler filtering
        return new ArrayList<>(
            this.bookings.stream()
            .filter((Booking b) -> b.getBookingDate().equals(date))
            .collect(Collectors.toList())
        );
    };

    public boolean addRoom(Room newRoom) {
        // Test if room has unique name
        if(this.getAllRooms().contains(newRoom)) {
            return false;
        }
        this.getAllRooms().add(newRoom);
        return true;
    };

    public Booking addBooking(Booking newBooking) {
        if (this.bookings.contains(newBooking)) {
           // dont allow for bookings to be booked for same room and date
           return null;
        }
        if (newBooking.getBookedRoom().getIsDropIn()) {
            // Dont book drop in rooms?
            return null;
         }
        this.bookings.add(newBooking);
        return newBooking;
     };

    public ArrayList<Room> getAllDropInRooms() {
        return new ArrayList<>(
            this.getAllRooms().stream()
            .filter((Room r) -> r.getIsDropIn())
            .collect(Collectors.toList())
        );
    }

    public ArrayList<Room> getAllNonDropInRooms() {
        return new ArrayList<>(
            this.getAllRooms().stream()
            .filter((Room r) -> !r.getIsDropIn())
            .collect(Collectors.toList())
        );
    }

    public ArrayList<Room> getBookableRooms(Date date) {
        // Fetch all non drop in rooms
        ArrayList<Room> bookableRooms = new ArrayList<>(this.getAllNonDropInRooms());
        // fetch all bookings for a date
        ArrayList<Booking> bookingsForDate = this.getBookingsForDate(date);
        // Remove all from smaller booking list, should be less to often go through
        for (Booking b : bookingsForDate) {
            bookableRooms.remove(b.getBookedRoom());
        }
        return bookableRooms;
    }

    public Room getTheVarmestRoom(float outDoorTemperature) {
        // fetch all rooms, if no rooms return
        ArrayList<Room> allRooms = this.getAllRooms();
        if(allRooms.isEmpty()) {
            return null;
        }
        // Fetch and set hottest to first room
        Room varmestRoom = allRooms.get(0);
        float hottest = varmestRoom.getTemperature(outDoorTemperature); // avoid calculating each time
        for (int i = 1; i < allRooms.size(); i++) {
            Room room = allRooms.get(i);
            float currentRoomTemperature = room.getTemperature(outDoorTemperature);
            if (currentRoomTemperature > hottest) {
                hottest = currentRoomTemperature;
                varmestRoom = room;
            }
        }
        return varmestRoom;
    }

    public Room getTheVarmestRoom(float outDoorTemperature, ArrayList<Room> allRooms) {
        if(allRooms.isEmpty()) {
            return null;
        }
        // Fetch and set hottest to first room
        Room varmestRoom = allRooms.get(0);
        float hottest = varmestRoom.getTemperature(outDoorTemperature); // avoid calculating each time
        for (int i = 1; i < allRooms.size(); i++) {
            Room room = allRooms.get(i);
            float currentRoomTemperature = room.getTemperature(outDoorTemperature);
            if (currentRoomTemperature > hottest) {
                hottest = currentRoomTemperature;
                varmestRoom = room;
            }
        }
        return varmestRoom;
    }

    public Booking bookVarmestRoomInDays(float outDoorTemperature, int days) {
        Date date = Date.currentDate().getDateInAmountOfDays(days);
        ArrayList<Room> bookableRoomsForDate = getBookableRooms(date);
        if (bookableRoomsForDate.isEmpty()) {
            return null;
        }
        Room hottestBookableRoom = getTheVarmestRoom(outDoorTemperature, bookableRoomsForDate);
        return addBooking(new Booking(hottestBookableRoom , date));
    }
}
