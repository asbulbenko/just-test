import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CheckoutPage;
import pages.FoodTrackerPage;
import pages.RestaurantMenuPage;
import pages.RestaurantPage;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Base tests for JET webpage")
public class BaseTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private RestaurantPage restaurantPage;
    private RestaurantMenuPage restaurantMenuPage;
    private FoodTrackerPage trackerPage;
    private CheckoutPage checkoutPage;
    private static final String BASE_URI = "https://www.thuisbezorgd.nl/en/delivery/food/breezanddijk-8766?showTestRestaurants=true";
    private static final String testRestaurant = "BRT Cypress Test Restaurant";
    private static final String restaurantUrl = "/en/menu/brt-test-restaurant";
    private static final String menuCategoryTab = "burgers";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        // set headless mode
        options.setHeadless(false);
        driver.set(new ChromeDriver(options));
        driver.get().get(BASE_URI);
        restaurantPage = new RestaurantPage(driver.get());
        restaurantPage.acceptCookies();
    }

    @AfterEach
    void teardown() {
        if (driver.get() != null) {
            driver.get().quit();
        }
    }

    @Test
    @DisplayName("user is able to open all restaurants page")
    public void testRestListOpen() {
        Assertions.assertTrue(restaurantPage.waitIsPageUrlContain(BASE_URI));
    }

    @Test
    @DisplayName("user is able to search BRT restaurant and open it's page")
    public void testOpenBRTRestaurantPage() {
        restaurantPage.searchForRestaurant(testRestaurant);
        restaurantMenuPage = restaurantPage.openRestaurantPage(restaurantUrl);

        Assertions.assertTrue(restaurantMenuPage.waitIsPageUrlContain(restaurantUrl));
        assertThat(restaurantMenuPage.getRestaurantTitle()).contains(testRestaurant);
    }

    @Test
    @Tag("BURGER CATEGORY")
    @DisplayName("user is able to add items from burgers category to basket")
    public void testAddBurgersToBasket() {
        checkoutPage = openBRTRestaurantPageAndAddBurgersToBasket();

        // Verify that basket has items
        assertThat(checkoutPage.countItemsInBasket()).isGreaterThan(0);
    }

    @Test
    @DisplayName("order cannot be confirmed if user is not in the same city")
    public void testUnableCreateBurgerOrder() {
        String errorBannerMessage = testRestaurant + " does not deliver in the delivery area";
        checkoutPage = openBRTRestaurantPageAndAddBurgersToBasket();

        // Set all delivery address fields and apply cash payment
        checkoutPage.setDeliveryAddress("main street", "2415", "8888AA", "Enschede",
                "TestUSer", "testuser@test.test", "1234567890");
        checkoutPage.applyCashPaymentOption();

        // Unable to create order, error banner message alert appeared
        checkoutPage.setCreatePayment();
        String str = checkoutPage.getErrorBannerMessage();
        assertThat(str).contains(errorBannerMessage);
    }

    @Test
    @DisplayName("user is able to create order with correct delivery address")
    public void testSuccessfullyCreateBurgerOrder() {
        checkoutPage = openBRTRestaurantPageAndAddBurgersToBasket();

        checkoutPage.setDeliveryAddress("Afsluitdijk", "3A", "8766TS", "Breezanddijk",
                "TestUSer", "testuser@test.test", "1234567890");
        checkoutPage.applyCashPaymentOption();
        trackerPage = checkoutPage.setCreatePayment();

        // verify tracker page opened and order is created successfully
        Assertions.assertTrue(trackerPage.waitIsPageUrlContain("foodtracker?trackingid="));
        assertThat(trackerPage.getOrderReceivedTitle()).isEqualTo("Thank you, we received your order!");
        assertThat(trackerPage.getOrderTitle()).isEqualTo("We’ve got your order");
        assertThat(trackerPage.getOrderReferenceId().length()).isEqualTo(6);
    }

    private CheckoutPage openBRTRestaurantPageAndAddBurgersToBasket() {
        restaurantPage.searchForRestaurant(testRestaurant);
        restaurantMenuPage = restaurantPage.openRestaurantPage(restaurantUrl);

        // Add all items from burger category to basket
        restaurantMenuPage.openMenuCategoryTabByName(menuCategoryTab);
        checkoutPage = restaurantMenuPage.addItemsFromBurgerCategoryToBasketAndCheckout();
        return checkoutPage;
    }
}

