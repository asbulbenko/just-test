package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FoodTrackerPage extends BasePage {

    @FindBy(css = ".css-1e127df")
    private WebElement orderReceivedTitle;
    @FindBy(css = ".css-c1ispf")
    private WebElement orderCreatedTitle;
    @FindBy(xpath = "//div[@class='order-reference']")
    private WebElement orderReference;
    @FindBy(xpath = "//div[@class='order-reference']/span")
    private WebElement orderReferenceId;

    public FoodTrackerPage(WebDriver driver) {
        super(driver);
    }

    public Boolean getOrderTitle(String text ) {
        return waitTextOnElementToAppear(orderCreatedTitle, text);
//        return waitElementToAppear(orderCreatedTitle).getText();
    }

    public Boolean getOrderReceivedTitle(String text) {
        return waitTextOnElementToAppear(orderReceivedTitle, text);
//        return waitElementToAppear(orderReceivedTitle).getText();
    }

    public String getOrderReferenceId() {
        return waitElementToAppear(orderReferenceId).getText();
    }
}
