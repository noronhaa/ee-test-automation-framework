# Hotel Booking Form Automation Framework

This is a Test Automation Framework for writing front end UI web automated tests using Webdriver and TestNG and back end
API tests using RestAssured. It utilises 
Frameworkium-UI, a framework for writing maintainable Front End tests using Selenium Webdriver. Abstracting away the 
complexities allowing us to get up and running writing clean maintainable tests straight away without having to reinvent the wheel.


### Core Technologies used / supported
- Java
- TestNG
- Maven
- RestAssured
- Selenium Webdriver


## Pre-Requisites
The following tools will need to be instal
- Java SE Development Kit 8 (JDK 8) or higher
- [Apache Maven][mvn]
- command line terminal application

## Getting Started
### Running Tests

After setting up [apache maven][mvn], open the `ee-test-automation-examples` directory in a 
terminal/command prompt and run `mvn clean verify` to run the example tests using Firefox.

You will need the [geckodriver][geckodriver] on your path if you are using 
Firefox version 48 or above.

### Browsers

You can provide the `-Dbrowser` argument to choose a browser to run the tests in.

#### Drivers

Each browser requires a "driver".

For chrome, [ChromeDriver][chromedriver] needs to be on your path or specified
as an argument:
```
mvn clean verify -Dbrowser=chrome -Dwebdriver.chrome.driver=c:\path\to\chromedriver.exe
```

For Firefox 48 and above, [geckodriver][geckodriver] needs to be on your path or specified
as an argument:
```
mvn clean verify -Dbrowser=firefox -Dwebdriver.gecko.driver=c:\path\to\geckodriver.exe
```
## Framework Layers & Structure
This Framework uses the [PageFactory](https://github.com/SeleniumHQ/selenium/wiki/PageFactory) pattern which is an extension to the Page Object design pattern

### Page Object Model
This framework uses the [Page Object Design Pattern](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) which 
briefly means each UI web page is modeled as a class containing all services of that page. Interacting with this page in 
a test should only be done by interacting with the corrosponding page object class. 

The test class will make the decisions using the page-object to do the the actual work and interact with the system under test

## Developing Tests

### BaseTest
Test Classes for UI testing must extend `com.frameworkium.ui.tests.BaseUITest` which will take care of the set up 
and tear down of the WebDrivers 

```Have each Test Class extend extend frameworkium-ui's com.frameworkium.ui.tests.BaseUITest
import com.frameworkium.ui.tests.BaseUITest;

public class HomePageTests extends BaseUITest {

}
```

This means when your test class is executed it will also run code from BaseTest. Code here is hooked onto TestNG for example we will have code run at different points of the TestNG test lifecycle which allows us to setup a driver @BeforeMethod and close the driver @AfterMethod (a test is mapped as a method with TestNG)


### BasePage
all page-object classes must extend `com.frameworkium.ui.pages.BasePage<T>`. Where T is the type of the page you are creating e.g. HomePage
This gives access to functionality here that can run each time a new page is loaded (ie you load a new Page Object class) which will then automatically wait for the visible elements on that page to be loaded and visible



```
import com.frameworkium.ui.pages.BasePage;
 
public class HomePage extends BasePage<HomePage> {
 
 }
 ```

#### Identifying Web Elements
We identify elements on web pages using the `@FindBy` annotation in our page object classes, and passing in selector strings

For example: 

```
@FindBy(id = “fromButton”)
private Webelement fromButton
```

```
@FindBy(css = “input#fromButton”)
private Webelement fromButton
```

```
@FindBy(linkText = “Click here to view”)
private WebElement viewLink
```

If the variable name of your object is the same as the html ID you can even omit the `@FindBy` completely and it will automatically attempt to try and find an element with the id of that of your variable name 


#### Page waits and @visible annotations

Everytime you load a new page, the framework will automatically wait for that page to load, we can make more reliable by using the `@Visible` annotation above various page objects, when the new page loads the framework will wait for the visibility of all elements that have been tagged @Visible to be visible before continuing the test. This means you should not need any explicit waits

```    
    @Visible
    @FindBy(id = "bookings")
    private WebElement bookingHeaders;
```

### API Testing
For Testing HTTP API we can use the RestAssured library which is intergrated into this framework. RestAssured is a Java DSL for API testing, for further information on RestAssured see the [usage guide](https://github.com/rest-assured/rest-assured/wiki/Usage)

### Reporting

After running your tests, you can generate an [Allure][allure] test report by 
simply running:

```
mvn allure:report 
```

## Further Information

Frameworkium sets you up for other stuff too - check out the
[guidance page][guidance] for further info.

[status-svg]: https://travis-ci.org/Frameworkium/frameworkium-examples.svg?branch=master
[status]: https://travis-ci.org/Frameworkium/frameworkium-examples
[ardesco]: https://github.com/Ardesco/Selenium-Maven-Template
[bootstrapium]: https://github.com/jvanderwee/bootstrapium
[rest-assured]: http://rest-assured.io/
[frameworkium-core]: https://github.com/Frameworkium/frameworkium-core
[core-issues]: https://github.com/Frameworkium/frameworkium-core/issues
[core-releases]: https://github.com/Frameworkium/frameworkium-core/releases
[mvn]: https://maven.apache.org/download.cgi
[geckodriver]: https://github.com/mozilla/geckodriver/releases
[chromedriver]: https://sites.google.com/a/chromium.org/chromedriver/home
[guidance]: https://frameworkium.github.io
[allure]: https://docs.qameta.io/allure/
