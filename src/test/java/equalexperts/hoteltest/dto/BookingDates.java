package equalexperts.hoteltest.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingDates {

    public static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ISO_LOCAL_DATE;

    public String checkin;
    public String checkout;

    public static BookingDates newInstance() {
        var dates = new BookingDates();
        dates.checkin = LocalDate.now().plusDays(1).format(FORMAT);
        dates.checkout = LocalDate.now().plusDays(10).format(FORMAT);
        return dates;
    }


}
