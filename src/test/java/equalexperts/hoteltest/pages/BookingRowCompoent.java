package equalexperts.hoteltest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BookingRowCompoent {

    public static final Integer FIRST_NAME_INDEX = 0;
    public static final Integer SURNAME_INDEX = 1;
    public static final Integer PRICE_INDEX = 2;
    public static final Integer DEPOSIT_INDEX = 3;
    public static final Integer CHECKIN_INDEX = 4;
    public static final Integer CHECKOUT_INDEX = 5;

    WebElement row;
    List<WebElement> rowElements;
    private final By deleteButton = By.cssSelector("input[value=\"Delete\"");

    public BookingRowCompoent(WebElement row) {
        this.row = row;
        this.rowElements = row.findElements(By.cssSelector("div"));
    }

    public String getFirstnameField() {
        return rowElements.get(FIRST_NAME_INDEX).getText();
    }

    public String getSurnameField() {
        return rowElements.get(SURNAME_INDEX).getText();
    }

    public String getPriceField() {
        return rowElements.get(PRICE_INDEX).getText();
    }

    public Boolean getDepositField() {
        return Boolean.valueOf(rowElements.get(DEPOSIT_INDEX).getText());
    }

    public String getCheckinField() {
        return rowElements.get(CHECKIN_INDEX).getText();
    }

    public String getCheckoutField() {
        return rowElements.get(CHECKOUT_INDEX).getText();
    }

     public WebElement clickDeleteButton() {
        row.findElement(deleteButton).click();
        return row;
    }

}
