package web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RestaurantPage extends BasePage {

    @FindBy(id = "input_5")
    private WebElement searchRestaurantField;
    @FindBy(css = "a[href='/en/menu/brt-test-restaurant']")
    private WebElement restaurantCard;
    @FindBy(css = "button[data-qa='privacy-settings-action-info']")
    private WebElement acceptCookiesButton;

    public RestaurantPage(WebDriver driver) {
        super(driver);
    }

    @Step("Accept cookies")
    public RestaurantPage acceptCookies() {
        waitElementToBeClickable(acceptCookiesButton).click();
        return this;
    }

    @Step("Search for restaurant")
    public RestaurantPage searchForRestaurant(String titleName) {
        waitElementToBeClickable(searchRestaurantField).click();
        searchRestaurantField.sendKeys(titleName);
        return this;
    }

    @Step("Open restaurant page & wait page is opened")
    public RestaurantMenuPage openRestaurantPage(String pageUrl) {
        waitElementToAppear(restaurantCard);
        restaurantCard.click();
        waitIsPageUrlContain(pageUrl);
        return new RestaurantMenuPage(driver);
    }

}
