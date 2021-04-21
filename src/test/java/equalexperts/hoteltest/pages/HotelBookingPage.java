package equalexperts.hoteltest.pages;

//import com.frameworkium.core.ui.annotations.Visible;
//import com.frameworkium.core.ui.pages.BasePage;
import com.frameworkium.ui.pages.PageFactory;
import com.frameworkium.ui.pages.BasePage;
import com.frameworkium.ui.annotations.Visible;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import equalexperts.hoteltest.BookerEndpoint;
import ru.yandex.qatools.htmlelements.element.Select;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class HotelBookingPage extends BasePage<HotelBookingPage> {


    @Visible
    @FindBy(id = "bookings")
    private WebElement bookingHeaders;

    @Visible private WebElement firstname;
    @Visible private WebElement lastname;
    @Visible private WebElement totalprice;
    @Visible private Select depositpaid;
    @Visible private WebElement checkin;
    @Visible private WebElement checkout;

    final String totalRowsCSS = "#bookings > .row";

    private final By tableRows = By.cssSelector(totalRowsCSS);

    @FindBy(css = totalRowsCSS)
    private List<WebElement> rows;

    @FindBy(css = "#bookings .row:last-of-type")
    private WebElement lastBookingEntryRow;

    private DatePickerComponent datePickerComponent;

    @FindBy(css = "input[value=' Save ']")
    private WebElement saveButton;


    /**
     * Open browser window and navigate to Hotel Booking Page.
     * @return Instance of HotelBookingPage page object class
     */
    public static HotelBookingPage open() {
        return PageFactory.newInstance(
                HotelBookingPage.class,
                BookerEndpoint.BASE_URI.getUrl());
    }

    /**
     *
     * @return number of rows on the Hotel Booking table including header row
     */
    public int getNumberOfRows() {
        return rows.size();
    }

    /**
     * Enter a value into the Firstname input form field.
     * @param firstname
     * @return HotelBookingPage page object class
     */
    public HotelBookingPage enterIntoFirstNameField(String firstname) {
        this.firstname.sendKeys(firstname);
        return this;
    }

    /**
     * Enter a value into the Surname input form field.
     * @param lastname
     * @return HotelBookingPage page object class
     */
    public HotelBookingPage enterIntoLastNameField(String lastname) {
        this.lastname.sendKeys(lastname);
        return this;
    }

    /**
     * Enter a value into the Price input form field.
     * @param price
     * @return HotelBookingPage page object class
     */
    public HotelBookingPage enterIntoPriceField(String price) {
        this.totalprice.sendKeys(price);
        return this;
    }

    /**
     * Select a boolean value on Deposit Select form field.
     * @param bool
     * @return HotelBookingPage page object class
     */
    public HotelBookingPage selectDepositPaidDropdown(boolean bool){
        this.depositpaid.selectByVisibleText(String.valueOf(bool));
        return this;
    }

    /**
     * Selects the Check-In field then uses the date picker to navigate to and select the input date
     * @param day
     * @param month
     * @param year
     * @return HotelBookingPage page object class
     */
    public HotelBookingPage selectCheckinDate(int day, int month, int year){
        checkin.click();
        datePickerComponent.selectYear(year);
        datePickerComponent.selectMonth(Month.of(month));
        datePickerComponent.selectDay(day);
        return this;
    }

    /**
     * Selects the Check-Out field then uses the date picker to navigate to and select the input date
     * @param day
     * @param month
     * @param year
     * @return
     */
    public HotelBookingPage selectCheckoutDate(int day, int month, int year){
        checkout.click();
        datePickerComponent.selectYear(year);
        datePickerComponent.selectMonth(Month.of(month));
        datePickerComponent.selectDay(day);
        return this;
    }


    /**
     * Clicks the Save button to submit the form booking expecting a successful creation of booking entry.
     *
     * Total number of rows is calculated before submission, after submission of the booking form an explicit wait is
     * executed until the number of rows in the booking table is increased by 1 representing a new booking entry
     * @return WebElement of the newly created row, if the new row is not created or takes too long to be created
     * a timeout exception will occur instead
     */
    public WebElement clickSaveButtonExpectingRowCreation(){
        int rowsBefore = getNumberOfRows();
        int expectedTotalRows = rowsBefore + 1;
        this.saveButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows,expectedTotalRows));
        return rows.get(expectedTotalRows -1);
    }

    //todo
    public void clickSaveButtonExpectingNoNewRow(){
        int rowsBefore = getNumberOfRows();
        int expectedTotalRows = rowsBefore + 1;
        this.saveButton.click();
        int nextRow = expectedTotalRows;
        wait.until(ExpectedConditions.invisibilityOfElementLocated((By.cssSelector("#bookings > .row:nth-of-type("+nextRow+")"))));
        wait.until(ExpectedConditions.not(ExpectedConditions.numberOfElementsToBe(tableRows,expectedTotalRows)));
    }

    /**
     * Iterates through Booking table finding all Surnames.
     * @return List of strings of all Surnames in the booking table
     */
    public List<String> getAllLastnamesInBookingTable() {
         return rows.stream()
                 .map(BookingRowCompoent::new)
                 .map(BookingRowCompoent::getSurnameField)
                 .collect(Collectors.toList());
    }

    /**
     * Get the last row in the booking table which represents the last booking to be created
     * @return BookingRowCompoent object with access methods to data of the row booking
     */
    public BookingRowCompoent getLastRowCreated() {
        return getRow(lastBookingEntryRow);
    }

    /**
     * Return a BookingRowCompoent object to access the data of a booking
     * @param row element of the row you wish to interact with
     * @return BookingRowCompoent object with access methods to data of the row booking
     */
    public BookingRowCompoent getRow(WebElement row) {
        return new BookingRowCompoent(row);
    }

    /**
     * Clicks Delete button for a booking row then explicitly waits for the row to not be visible on the booking table. If booking
     * row failed to be deleted a timeout exception would occur
     * @param row for the booking entry to be deleted
     */
    public void deleteRow(WebElement row){
        getRow(row).clickDeleteButton();
        wait.until(ExpectedConditions.invisibilityOfAllElements(row));
    }

}
