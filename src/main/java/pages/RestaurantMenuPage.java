package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RestaurantMenuPage extends BasePage {

    @FindBy(css = "h1[data-qa='restaurant-header-text']")
    private WebElement restaurantTitle;
    @FindBy(xpath = "//span[text()='Burgers']")
    private WebElement burgersMenuTab;
    @FindBy(xpath = "//div[@data-qa='menu-category-nav-categories-item']/div/span")
    private List<WebElement> menuCategoryTabList;
    @FindBy(xpath = "//section[@data-category-hash='category_burgers']/div[@role='list']/div")
    private List<WebElement> itemCategoryBurgerList;
    @FindBy(xpath = "//button[@type='button']")
    private WebElement addItemToBasketButton;
    @FindBy(xpath = "//div[@role='list' and @data-qa='cart-expanded-list']/div")
    private List<WebElement> itemsInBasketList;
    @FindBy(css = "button[data-qa='sidebar-action-checkout']")
    private WebElement checkoutButton;

    public RestaurantMenuPage(WebDriver driver) {
        super(driver);
    }

    public String getRestaurantTitle() {
        return waitElementToAppear(restaurantTitle).getText();
    }

    public void openMenuCategoryTabByName(String categoryName) {
        waitUntilAllElementsAppear(menuCategoryTabList);
        WebElement categoryTab = menuCategoryTabList.stream()
                .filter(webElement -> webElement.getText().toLowerCase().equals(categoryName))
                .findAny().orElse(burgersMenuTab);
        categoryTab.click();
    }

    @Step("Add items from category 'Burger'")
    public CheckoutPage addItemsFromBurgerCategoryToBasketAndCheckout() {
        waitUntilAllElementsAppear(itemCategoryBurgerList);
        itemCategoryBurgerList.forEach(webElement -> {
            waitElementToBeClickable(webElement).click();
            waitElementToBeClickable(addItemToBasketButton).click();
        });
            try {
                waitElementToBeClickable(checkoutButton).click();
            } catch (NoSuchElementException ignored) {
            }
        return new CheckoutPage(driver);
    }

}
