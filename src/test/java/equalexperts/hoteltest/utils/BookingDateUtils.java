package equalexperts.hoteltest.utils;

public class BookingDateUtils {

    String bookingDate;

    public BookingDateUtils(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getMonth(){
        return Integer.parseInt(bookingDate.substring(5,7));
    }

    public int getDay(){
        return Integer.parseInt(bookingDate.substring(8,10));
    }

    public int getYear(){
        return Integer.parseInt(bookingDate.substring(0,4));
    }
}
