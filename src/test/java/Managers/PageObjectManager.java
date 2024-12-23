package Managers;

import PageObjects.LoginPage;
import PageObjects.ProductsPage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    public WebDriver driver;
    public PageObjectManager(WebDriver inputDriver){driver=inputDriver;}
    public LoginPage getLoginPage(){return new LoginPage(driver);}
    public ProductsPage getProductsPage(){return new ProductsPage(driver);}
}
