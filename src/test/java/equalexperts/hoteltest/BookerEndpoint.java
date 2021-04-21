package equalexperts.hoteltest;


/** The various Endpoints of Hotel Booker Site. */
public enum BookerEndpoint  {

    BASE_URI("http://hotel-test.equalexperts.io/"),
    PING("/ping"),
    BOOKING("/booking");

    private String url;

    BookerEndpoint(String url) {
        this.url = url;
    }

    /**
     * @param params Arguments referenced by the format specifiers in the url.
     * @return A formatted String representing the URL of the given constant.
     */
    public String getUrl(Object... params) {
        return String.format(url, params);
    }

}
