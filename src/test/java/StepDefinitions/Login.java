package StepDefinitions;

import Managers.PageObjectManager;
import PageObjects.LoginPage;
import PageObjects.ProductsPage;
import Utility.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class Login {
    private static LoginPage loginPage;
    private static ProductsPage productsPage;
    public Login(){
        WebDriver driver= Hook.getDriver();
        PageObjectManager pageObjectManager=new PageObjectManager(driver);
        loginPage=pageObjectManager.getLoginPage();
        productsPage=pageObjectManager.getProductsPage();
    }

    //TC_SL_LOGIN_01_Verify login with valid credential
    @Given("navigate to Swag Lab url")
    public void navigate_to_swag_lab_url() {
        System.out.println("Navigating to Swag Lab URL");
        loginPage.navigateToSwagLabLoginPage();
    }
    @When("user is on login page")
    public void user_is_on_login_page() {
        loginPage.checkLoginBtn();
    }
    @When("enter valid user name and valid password")
    public void enter_valid_user_name_and_valid_password() {
        loginPage.enterStandardUserName();
        loginPage.enterPassword();
    }
    @When("click login button")
    public void click_login_button() throws InterruptedException {
        Thread.sleep(3000);
        loginPage.clickLoginBtn();
    }
    @Then("user able to see products screen")
    public void user_able_to_see_products_screen() throws InterruptedException {
        productsPage.checkProductsPage();
    }

    //TC_SL_LOGIN_02_Verify that login to Swag Labs with locked out user
    @When("enter locked out user name and password")
    public void enter_locked_out_user_name_and_password() {
        loginPage.enterLockedOutdUserName();
        loginPage.enterPassword();
    }
    @Then("user able to see this user has been locked out error")
    public void user_able_to_see_this_user_has_been_locked_out_error() throws InterruptedException {
        loginPage.check_lockedOutUserErrorMsg();
        Thread.sleep(3000);
    }
}
