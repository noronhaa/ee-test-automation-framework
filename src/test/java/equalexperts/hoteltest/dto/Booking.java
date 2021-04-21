package equalexperts.hoteltest.dto;


import java.util.concurrent.ThreadLocalRandom;

public class Booking {
    public String firstname;
    public String lastname;
    public String totalprice;
    public boolean depositpaid;
    public BookingDates bookingdates;


    public static Booking newInstance() {
        var random = ThreadLocalRandom.current();
        int randInt = random.nextInt();
        var booking = new Booking();
        booking.firstname = "firstname" + randInt;
        booking.lastname = "lastname" + randInt;
        booking.totalprice = String.valueOf(randInt);
        booking.depositpaid = random.nextBoolean();
        booking.bookingdates = BookingDates.newInstance();
        return booking;
    }
}

