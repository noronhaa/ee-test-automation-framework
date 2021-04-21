package equalexperts.hoteltest.api;

import io.restassured.RestAssured;
import equalexperts.hoteltest.BookerEndpoint;

public class PingService  {

    public String ping() {
        return RestAssured.given()
                .baseUri(BookerEndpoint.BASE_URI.getUrl())
                .get(BookerEndpoint.PING.getUrl())
                .body()
                .asString();
    }

}
