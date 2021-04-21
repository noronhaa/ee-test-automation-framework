package equalexperts.hoteltest.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import equalexperts.hoteltest.BookerEndpoint;
import equalexperts.hoteltest.dto.Booking;

public class BookingAPITests {

    private static final String APPLICATION_JSON = "application/json";

    @BeforeClass
    public void ensure_site_is_up_by_using_ping_service() {
        Assert.assertEquals(new PingService().ping(),"Created");
    }


    protected RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri(BookerEndpoint.BASE_URI.getUrl())
                .contentType(APPLICATION_JSON);
    }

    @Test public void
    successfully_submit_booking_with_valid_data() {

        Booking booking = Booking.newInstance();

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusCode());

    }

    @Test public void
    submit_booking_with_blank_firstname_gives_error() {

        Booking booking = Booking.newInstance();
        booking.firstname = "";

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test public void
    submit_booking_with_blank_lastname_gives_error() {

        Booking booking = Booking.newInstance();
        booking.lastname = "";

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test public void
    submit_booking_with_blank_price_gives_error() {

        Booking booking = Booking.newInstance();
        booking.totalprice = "";

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test public void
    submit_booking_with_blank_checkin_gives_error() {

        Booking booking = Booking.newInstance();
        booking.bookingdates.checkin = "";

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test public void
    submit_booking_with_blank_checkout_gives_error() {

        Booking booking = Booking.newInstance();
        booking.bookingdates.checkout = "";

        Response response = getRequestSpec()
                .body(booking)
                .post(BookerEndpoint.BOOKING.getUrl());

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}
