package equalexperts.hoteltest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.time.Month;
import java.util.List;
import java.util.stream.IntStream;

@FindBy(id = "ui-datepicker-div")
public class DatePickerComponent extends HtmlElement {

    @FindBy(css = ".ui-datepicker-prev")
    private WebElement previousMonthButton;

    @FindBy(css = ".ui-datepicker-next")
    private WebElement nextMonthButton;

    @FindBy(css = ".ui-datepicker-month")
    private WebElement month;

    @FindBy(css = ".ui-datepicker-year")
    private WebElement year;

    @FindBy(css = ".ui-datepicker-calendar td a")
    private List<WebElement> days;

    public int getCurrentYear(){
        return Integer.parseInt(year.getText());
    }

    public String getCurrentMonth(){
        return month.getText();
    }


    public void selectYear(int year) {
        int currentYear = getCurrentYear();
        if (year != currentYear) {
            if (year > currentYear) {
                while (year != currentYear) {
                    nextMonthButton.click();
                    currentYear = getCurrentYear();
                }
            } else {
                while (year != currentYear) {
                    previousMonthButton.click();
                    currentYear = getCurrentYear();
                }
            }
        } else {
            //Do nothing already on correct year
        }

    }

    public void selectMonth(Month targetMonth) {
        Month currentMonth = Month.valueOf(getCurrentMonth().toUpperCase());
        int monthDiff = targetMonth.getValue() - currentMonth.getValue();
        if (monthDiff > 0) {
            for (int i = 0; i < monthDiff; i++) {
                nextMonthButton.click();
            }
        } else if (monthDiff < 0){
            //todo ??
            IntStream.range(0, monthDiff).forEach(i -> previousMonthButton.click());
        }
    }

    public void selectDay(int day) {
        boolean found = false;

        for (WebElement dayElement:  days) {

            if (Integer.parseInt(dayElement.getText()) == day){
                found = true;
                dayElement.click();
                break;
            }
        }

        if (!found){
            throw new RuntimeException("day not found");
        }
    }

}
