import BookingSystem.BookingSystemController;
import BookingSystem.Room;
import BookingSystem.Booking;
import BookingSystem.Date;

class Main {
    public static void main(String[] args) {
        Room testRoom = new Room(
            "isDropIn",
            true,
            false,
            9.0F
        );
        Room testRoom2 = new Room(
            "isNotDropin",
            false,
            false,
            12.0F
        );
        Room testRoom3 = new Room(
            "isNotDropin2",
            false,
            false,
            18.0F
        );
        Room testRoom4= new Room(
            "isDropin2",
            true,
            false,
            14.0F
        );

        Room testRoom5= new Room(
            "isNotDropin3",
            false,
            true,
            14.0F
        );
        BookingSystemController controller = new BookingSystemController();

        controller.addRoom(testRoom);
        controller.addRoom(testRoom2);
        controller.addRoom(testRoom3);
        controller.addRoom(testRoom4);
        controller.addRoom(testRoom5);

        System.out.println("All rooms 5=" + String.valueOf(controller.getAllRooms().size()));
        System.out.println("Total drop in rooms 2=" + String.valueOf(controller.getAllDropInRooms().size()));
        System.out.println("Total bookable today 3=" + String.valueOf(controller.getBookableRooms(Date.currentDate()).size()));

        controller.addBooking(new Booking(testRoom2, Date.currentDate()));
        controller.addBooking(new Booking(testRoom5, new Date(22, 4, 2024)));
        System.out.println("Total bookable today after booking 2=" + String.valueOf(controller.getBookableRooms(Date.currentDate()).size()));
        controller.addBooking(new Booking(testRoom5, Date.currentDate()));
        System.out.println("Total bookable today after secound booking 1=" + String.valueOf(controller.getBookableRooms(Date.currentDate()).size()));
        

        System.out.println(testRoom4.getTemperature(14.0F)); // has window

        System.out.println(testRoom5.getTemperature(14.0F)); // no window

        System.out.println(Date.currentDate().getWeekDayInAmountOfDays(7)); // monday
        System.out.println(Date.currentDate().getWeekDayInAmountOfDays(1)); // tuesday
        System.out.println(Date.currentDate().getWeekDayInAmountOfDays(314)); // monday
        System.out.println(Date.currentDate().getWeekDayInAmountOfDays(107)); // Wednesday

        System.out.println(controller.bookVarmestRoomInDays(14.0f, 4)); //2
        System.out.println(controller.bookVarmestRoomInDays(14.0f,4)); // 0
        System.out.println(controller.bookVarmestRoomInDays(14.0f,4)); // 3
        // Should not work
        System.out.println(controller.bookVarmestRoomInDays(4.0f,4));
        System.out.println(controller.bookVarmestRoomInDays(4.0f,4));
    }
}