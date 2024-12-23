package StepDefinitions;

import Managers.PageObjectManager;
import PageObjects.LoginPage;
import PageObjects.ProductsPage;
import Utility.Hook;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;

public class Products {
    private static LoginPage loginPage;
    private static ProductsPage productsPage;
    int desiredTotalQty;
    public Products(){
        WebDriver driver= Hook.getDriver();
        PageObjectManager pageObjectManager=new PageObjectManager(driver);
        loginPage=pageObjectManager.getLoginPage();
        productsPage=pageObjectManager.getProductsPage();
    }

    //TC_SL_Products_01_As a user, add the product to the shopping cart and then proceed to checkout successfully
    @Then("click product price container and sort the price high to low")
    public void click_product_price_container_and_sort_the_price_high_to_low() throws InterruptedException {
        Thread.sleep(1000);
        productsPage.clickProductSortContainer();
        Thread.sleep(1000);
        productsPage.sortingPriceHighToLow();
        Thread.sleep(2000);
    }
    @Then("add {int} items which has ${string} price to the shopping cart")
    public void add_items_which_has_$_price_to_the_shopping_cart(int totalItems, String price) throws InterruptedException {
        desiredTotalQty=totalItems;
        productsPage.retriveItemNameAndPrice(price);
        Thread.sleep(1000);
    }
    @Then("click shopping cart")
    public void click_shopping_cart() throws InterruptedException {
        productsPage.clickShoppingCart();
        Thread.sleep(1000);
    }
    @Then("user is able to see your cart screen with the two added items")
    public void user_is_able_to_see_your_cart_screen_with_the_two_added_items() throws InterruptedException, ParseException {
        productsPage.check_yourCartPage();
        productsPage.check_shoppingCartItems(desiredTotalQty);
        Thread.sleep(1000);
    }
    @Then("click checkout button")
    public void click_checkout_button() throws InterruptedException {
        productsPage.clickCheckOut();
        Thread.sleep(1000);
    }
    @Then("user able to see checkout your information page and need to fill the required information with {string},{string},{string}")
    public void user_able_to_see_checkout_your_information_page_and_need_to_fill_the_required_information_with(String firstName, String lastName, String zipPostal)throws InterruptedException {
       productsPage.check_yourInformationPage();
       productsPage.fill_information(firstName,lastName,zipPostal);
        Thread.sleep(2000);
    }
    @Then("click continue button")
    public void click_continue_button() {
        productsPage.click_continueBtn();
    }
    @Then("user able to see checkout overview screen with log the checkout summary")
    public void user_able_to_see_checkout_overview_screen_with_log_the_checkout_summary() throws InterruptedException {
        //check payment summary
        productsPage.check_paymentSummary();
        Thread.sleep(2000);
        productsPage.click_finishBtn();

        //check complete page
        productsPage.check_checkoutcompletePage();
        Thread.sleep(2000);
        productsPage.click_backhome();
        Thread.sleep(1000);

        //check home page
        productsPage.checkProductsPage();
        Thread.sleep(1000);

        //click Burger Menu
        productsPage.click_burgerMenu();
        Thread.sleep(2000);
        productsPage.click_logoutLink();
        Thread.sleep(2000);

        //check_loginPage
        loginPage.checkLoginBtn();
        Thread.sleep(2000);
    }
}
