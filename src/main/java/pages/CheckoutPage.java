package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(name = "streetName")
    private WebElement streetNameField;
    @FindBy(name = "streetNumber")
    private WebElement streetNumberField;
    @FindBy(name = "postalCode")
    private WebElement postCodeField;
    @FindBy(name = "city")
    private WebElement cityField;
    @FindBy(name = "fullName")
    private WebElement fullNameField;
    @FindBy(name = "email")
    private WebElement emailField;
    @FindBy(name = "phoneNumber")
    private WebElement phoneNumberField;
    @FindBy(xpath = "//div[@data-qa='multi-step-checkout-details-payment']")
    private WebElement paymentOptionsMenu;
    @FindBy(xpath = "//div[@data-qa='payment-modal-cash-element']")
    private WebElement paymentCashOption;
    @FindBy(xpath = "//footer[@data-qa='modal-footer']/button")
    private WebElement paymentConfirmation;
    @FindBy(xpath = "//button[@type='button']/span")
    private WebElement paymentButton;
    @FindBy(xpath = "//div[@data-qa='local-error-banner-delivery']")
    private WebElement errorBannerDeliveryAlert;

    @FindBy(xpath = "//div[@role='list' and @data-qa='cart-expanded-list']/div")
    private List<WebElement> itemsInBasketList;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void setStreetName(String streetName) {
        waitElementToBeClickable(streetNameField).click();
        streetNameField.clear();
        streetNameField.sendKeys(streetName);
    }

    public void setStreetNumber(String streetNumber) {
        waitElementToBeClickable(streetNumberField).click();
        streetNumberField.clear();
        streetNumberField.sendKeys(streetNumber);
    }

    public void setPostCode(String postCode) {
        waitElementToBeClickable(postCodeField).click();
        postCodeField.clear();
        postCodeField.sendKeys(postCode);
    }

    public void setCityName(String cityName) {
        waitElementToBeClickable(cityField).click();
        cityField.clear();
        cityField.sendKeys(cityName);
    }

    public void setFullName(String fullName) {
        waitElementToBeClickable(fullNameField).click();
        fullNameField.clear();
        fullNameField.sendKeys(fullName);
    }

    public void setEmail(String email) {
        waitElementToBeClickable(emailField).click();
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        waitElementToBeClickable(phoneNumberField).click();
        phoneNumberField.clear();
        phoneNumberField.sendKeys(phoneNumber);
    }

    public void applyCashPaymentOption() {
        waitElementToBeClickable(paymentOptionsMenu).click();
        waitElementToAppear(paymentCashOption).click();
        waitElementToBeClickable(paymentConfirmation).click();
    }

    public FoodTrackerPage setCreatePayment() {
        waitElementToAppear(paymentButton).click();
        return new FoodTrackerPage(driver);
    }

    public String getErrorBannerMessage() {
        return waitElementToAppear(errorBannerDeliveryAlert).getText();
    }

    @Step("Fill in delivery address")
    public void setDeliveryAddress(String streetName, String streetNumber, String postalCode, String city, String fullName, String email, String phoneNumber) {
        setStreetName(streetName);
        setStreetNumber(streetNumber);
        setPostCode(postalCode);
        setCityName(city);
        setFullName(fullName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public int countItemsInBasket() {
        return  waitUntilAllElementsAppear(itemsInBasketList).size();
    }
}
