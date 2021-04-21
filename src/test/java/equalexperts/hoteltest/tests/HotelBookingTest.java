package equalexperts.hoteltest.tests;

import com.frameworkium.ui.tests.BaseUITest;

import equalexperts.hoteltest.pages.HotelBookingPage;
import equalexperts.hoteltest.utils.BookingDateUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import equalexperts.hoteltest.dto.Booking;

import static org.testng.Assert.*;

public class HotelBookingTest extends BaseUITest {

    @Test()
    public final void createBookingTest() {

        //Create test data
        Booking bookingData = Booking.newInstance();
        BookingDateUtils checkin = new BookingDateUtils(bookingData.bookingdates.checkin);
        BookingDateUtils checkout = new BookingDateUtils(bookingData.bookingdates.checkout);

        var bookingPage = HotelBookingPage.open();

        int bookingRowsPresentBefore = bookingPage.getNumberOfRows();

        //Create new Booking
        bookingPage
                .enterIntoFirstNameField(bookingData.firstname)
                .enterIntoLastNameField(bookingData.lastname)
                .enterIntoPriceField(bookingData.totalprice)
                .selectDepositPaidDropdown(bookingData.depositpaid)
                .selectCheckinDate(
                    checkin.getDay(),
                    checkin.getMonth(),
                    checkin.getYear())
                .selectCheckoutDate(
                    checkout.getDay(),
                    checkout.getMonth(),
                    checkout.getYear())
                .clickSaveButtonExpectingRowCreation();

        int bookingRowsPresentAfter = bookingPage.getNumberOfRows();

        assertEquals((bookingRowsPresentBefore + 1), bookingRowsPresentAfter,
                "Expecting booking rows count to of increased");

        assertTrue(bookingPage.getAllLastnamesInBookingTable().contains(bookingData.lastname),
                "could not find last name from created booking in the booking table anywhere");


        //Check last created booking on the table contains expected data from booking creation
        assertEquals(bookingPage.getLastRowCreated().getFirstnameField(),bookingData.firstname);
        assertEquals(bookingPage.getLastRowCreated().getSurnameField(),bookingData.lastname);
        assertEquals(bookingPage.getLastRowCreated().getPriceField(),bookingData.totalprice);
        assertEquals(bookingPage.getLastRowCreated().getDepositField().booleanValue(),bookingData.depositpaid);
        assertEquals(bookingPage.getLastRowCreated().getCheckinField(),bookingData.bookingdates.checkin);
        assertEquals(bookingPage.getLastRowCreated().getCheckoutField(),bookingData.bookingdates.checkout);

    }

    @Test()
    public final void deleteBookingTest() {

        //Create test data
        Booking bookingData = Booking.newInstance();
        BookingDateUtils checkin = new BookingDateUtils(bookingData.bookingdates.checkin);
        BookingDateUtils checkout = new BookingDateUtils(bookingData.bookingdates.checkout);

        var bookingPage = HotelBookingPage.open();

        //Create new Booking
        WebElement createdRow =  bookingPage
                .enterIntoFirstNameField(bookingData.firstname)
                .enterIntoLastNameField(bookingData.lastname)
                .enterIntoPriceField(bookingData.totalprice)
                .selectDepositPaidDropdown(bookingData.depositpaid)
                .selectCheckinDate(
                        checkin.getDay(),
                        checkin.getMonth(),
                        checkin.getYear())
                .selectCheckoutDate(
                        checkout.getDay(),
                        checkout.getMonth(),
                        checkout.getYear())
                .then()
                .clickSaveButtonExpectingRowCreation();


        assertTrue(bookingPage.getAllLastnamesInBookingTable().contains(bookingData.lastname),
                "could not find last name from created booking in the booking table anywhere");

        int bookingRowsBeforeDelete = bookingPage.getNumberOfRows();

        bookingPage.deleteRow(createdRow);

        int expectedBookingTableRows = bookingRowsBeforeDelete -1;
        int actualBookingTableRows = bookingPage.getNumberOfRows();

        assertEquals(actualBookingTableRows, expectedBookingTableRows,
                "unexpected number of booking rows present after booking deletion");

        assertFalse(bookingPage.getAllLastnamesInBookingTable().contains(bookingData.lastname),
                String.format("entry '%s' found in booking table that was deleted",bookingData.lastname));


    }

    @Test()
    public final void bookingNotCreatedWithMissingFirstName() {

        //Create test data
        Booking bookingData = Booking.newInstance();
        bookingData.firstname = "";
        BookingDateUtils checkin = new BookingDateUtils(bookingData.bookingdates.checkin);
        BookingDateUtils checkout = new BookingDateUtils(bookingData.bookingdates.checkout);

        var bookingPage = HotelBookingPage.open();
        int rowsPresent = bookingPage.getNumberOfRows();


        //Create new Booking
        bookingPage
                .enterIntoFirstNameField(bookingData.firstname)
                .enterIntoLastNameField(bookingData.lastname)
                .enterIntoPriceField(bookingData.totalprice)
                .selectDepositPaidDropdown(bookingData.depositpaid)
                .selectCheckinDate(
                        checkin.getDay(),
                        checkin.getMonth(),
                        checkin.getYear())
                .selectCheckoutDate(
                        checkout.getDay(),
                        checkout.getMonth(),
                        checkout.getYear())
                .clickSaveButtonExpectingNoNewRow();


        int rowsAfter = bookingPage.getNumberOfRows();

        assertEquals(rowsPresent, rowsAfter);

    }


}
