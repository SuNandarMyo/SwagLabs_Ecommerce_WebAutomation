package PageObjects;

import CSVFile.CSVReader;
import Utility.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    String eachPrice;
    String desiredPrice;
    String desiredQty;
    double totalItemPrice=0.00;
    String taxString;
    String taxCharges;
    double total=0.00;
    String displayTotalPrice;
    double checkoutTotalPrice=0.00;

    //Products Page Locators
    private final By productPageTitle = By.xpath("//*[@id=\"header_container\"]/div[2]/span");
    private final By productSortContainer = By.cssSelector("select.product_sort_container");
    private final By inventoryListItems = By.cssSelector("div.inventory_list");
    private final By inventoryItems = By.cssSelector("div.inventory_item");
    private final By inventoryItemDescription = By.cssSelector("div.inventory_item_description");
    private final By priciebar = By.cssSelector("div.pricebar");
    private final By inventoryItemPrice = By.cssSelector("div.inventory_item_price");
    private final By addToCartBtn = By.cssSelector("button.btn_inventory");
    private final By shoppingCart=By.cssSelector("a.shopping_cart_link");

    //Your Cart
    private final By yourCartTitle=By.cssSelector("span.title");
    private final By cartListTable=By.cssSelector("div.cart_list");
    private final By cartItem=By.cssSelector("div.cart_item");
    private final By cartItemLabel=By.cssSelector("div.cart_item_label");
    private final By itemPriceBar=By.cssSelector("div.item_pricebar");
    private final By checkOutBtn=By.id("checkout");

    //Checkout:Your Information
    private final By checkOutYourInformationTitle=By.cssSelector("span.title");
    private final By firstNameYourInformation=By.xpath("//*[@id=\"first-name\"]");
    private final By lastNameYourInformation=By.xpath("//*[@id=\"last-name\"]");
    private final By zipPostalCode=By.xpath("//*[@id=\"postal-code\"]");
    private final By continueBtn=By.xpath("//*[@id=\"continue\"]");

    //Checkout Overview
    private final By overviewTitle=By.cssSelector("span.title");
    private final By paymentInfoLabel=By.cssSelector("div[data-test=payment-info-label]");
    private final By paymentInfovalue=By.cssSelector("div[data-test=payment-info-value]");
    private final By shippingInfoLabel=By.cssSelector("div[data-test=shipping-info-label]");
    private final By shippingInfoValue=By.cssSelector("div[data-test=shipping-info-value]");
    private final By totalInfoLabel=By.cssSelector("div[data-test=total-info-label]");
    private final By subTotalLabel=By.cssSelector("div[data-test=subtotal-label]");
    private final By taxLabel=By.cssSelector("div[data-test=tax-label]");
    private final By totalLabel=By.cssSelector("div[data-test=total-label]");
    private final By finishBtn=By.xpath("//*[@id=\"finish\"]");

    //Checkout: Complete!
    private final By checkoutCompleteTitle=By.cssSelector("span.title");
    private final By thankYouOrderDescription=By.cssSelector("h2.complete-header");
    private final By backHomeBtn=By.xpath("//*[@id=\"back-to-products\"]");
    private final By burgermenu=By.xpath("//*[@id=\"react-burger-menu-btn\"]");
    private final By logout=By.linkText("Logout");
    public ProductsPage(WebDriver inputDriver) {
        driver = inputDriver;
    }
    public void checkProductsPage() {
        //click ok button in alert box
        Hook.acceptAlert();
        //check products page able to display
        Assert.assertTrue(driver.findElement(productPageTitle).isDisplayed(), "Products Page Title is able to display.");
    }

    public void clickProductSortContainer() {
        //click product Sort Container
        driver.findElement(productSortContainer).click();
    }

    public void sortingPriceHighToLow() {
        //Sorting the price High to Low
        Select priceSorting = new Select(driver.findElement(productSortContainer));
        priceSorting.selectByValue("hilo");
    }

    public void retriveItemNameAndPrice(String price) throws InterruptedException {
        desiredPrice=price;
        //Retrieve each item price
        List<WebElement> listItems = driver.findElements(inventoryListItems);
        List<WebElement> subListItems = listItems.get(0).findElements(inventoryItems);

        for(int i=0;i<subListItems.size();i++) {
            List<WebElement> subListItemsDescription = subListItems.get(i).findElements(inventoryItemDescription);
            List<WebElement> priceBarList = subListItemsDescription.get(0).findElements(priciebar);
            String itemPrice = priceBarList.get(0).findElement(inventoryItemPrice).getText();

            if (itemPrice.contains(price)) {
                //click add to cart button
                List<WebElement> addToCart=priceBarList.get(0).findElements(addToCartBtn);
                addToCart.get(0).click();
                Thread.sleep(1000);
            }
        }
    }
    public void clickShoppingCart(){
        //click Shopping Cart link button
        driver.findElement(shoppingCart).click();
    }

    //Your Cart Page
    public void check_yourCartPage(){
        Assert.assertEquals(driver.findElement(yourCartTitle).getText(),"Your Cart");
    }
    public void check_shoppingCartItems(int totalQty) throws ParseException {
        int j=0;
        List<WebElement> cartList=driver.findElements(cartListTable);
        List<WebElement> carItemList=cartList.get(0).findElements(cartItem);

        for(int d=0;d<carItemList.size();d++){
            List<WebElement> priceBar=carItemList.get(d).findElements(cartItemLabel);
            List<WebElement> inventItemPrice=priceBar.get(0).findElements(itemPriceBar);

            if(inventItemPrice.get(0).findElement(inventoryItemPrice).getText().contains(desiredPrice)){
                j++;
                eachPrice=inventItemPrice.get(0).findElement(inventoryItemPrice).getText().replace("$","").replace(",","");
                totalItemPrice=totalItemPrice+Double.valueOf(eachPrice);
            }
        }
        System.out.println("Total Items in the Cart are "+j);
        Assert.assertEquals(j,totalQty,"Total Quantity items");
        Hook.logInfo("Purchase for "+totalQty+" items display the same quantity into shopping cart total items :: "+j);

        System.out.println("Total Price : "+totalItemPrice);
    }
    public void clickCheckOut(){
        //click checkout button
        driver.findElement(checkOutBtn).click();
    }

   //Checkout: Your Information
    public void check_yourInformationPage(){
        Assert.assertEquals(driver.findElement(checkOutYourInformationTitle).getText(),"Checkout: Your Information");
    }
    public void fill_information(String firstName,String lastName,String zipPostal){
        driver.findElement(firstNameYourInformation).sendKeys(firstName);
        driver.findElement(lastNameYourInformation).sendKeys(lastName);
        driver.findElement(zipPostalCode).sendKeys(zipPostal);
    }
    public void click_continueBtn(){
        driver.findElement(continueBtn).click();
    }

    //Checkout Overview
     public void check_paymentSummary(){

        //check overview screen
         Assert.assertEquals(driver.findElement(overviewTitle).getText(),"Checkout: Overview");
        //payment information
        Assert.assertEquals(driver.findElement(paymentInfoLabel).getText(),"Payment Information:");
        Assert.assertTrue(driver.findElement(paymentInfovalue).isDisplayed(),"SauceCard Number display.");
        Hook.logInfo("Payment Information:: "+driver.findElement(paymentInfovalue).getText());

        //shipping information
        Assert.assertEquals(driver.findElement(shippingInfoLabel).getText(),"Shipping Information:");
        Assert.assertEquals(driver.findElement(shippingInfoValue).getText(),"Free Pony Express Delivery!");
        Hook.logInfo("Shipping Information:: "+driver.findElement(shippingInfoValue).getText());

        //Price Total
        Assert.assertEquals(driver.findElement(totalInfoLabel).getText(),"Price Total");
        Assert.assertTrue(driver.findElement(subTotalLabel).isDisplayed(),"Item total display.");
        Hook.logInfo(driver.findElement(subTotalLabel).getText());

        //Tax
        Assert.assertTrue(driver.findElement(taxLabel).isDisplayed(),"Tax display.");
        taxString=driver.findElement(taxLabel).getText();
        taxCharges=taxString.substring(6,taxString.length());
        System.out.println("Tax : "+taxCharges);
        Hook.logInfo(driver.findElement(taxLabel).getText());

        //Add totalItem price and tax
        total=totalItemPrice+Double.valueOf(taxCharges);
         System.out.println("Expected total price is "+total);

        //Total
        Assert.assertTrue(driver.findElement(totalLabel).isDisplayed(),"Total display.");
        Hook.logInfo(driver.findElement(totalLabel).getText());
        displayTotalPrice=driver.findElement(totalLabel).getText().substring(8,driver.findElement(totalLabel).getText().length());
        checkoutTotalPrice=Double.valueOf(displayTotalPrice);
        System.out.println("Actual total price is "+checkoutTotalPrice);

        //Check checkout Total price with expected total price
         Assert.assertEquals(checkoutTotalPrice,total,"Exepected Total is the same with actual total price");

    }
    public void click_finishBtn(){
        driver.findElement(finishBtn).click();
    }

    //Checkout: Complete!
    public void check_checkoutcompletePage(){
        Assert.assertEquals(driver.findElement(checkoutCompleteTitle).getText(),"Checkout: Complete!");
        Assert.assertEquals(driver.findElement(thankYouOrderDescription).getText(),"Thank you for your order!");
    }
    public void click_backhome(){
        driver.findElement(backHomeBtn).click();
    }
    public void click_burgerMenu(){
        driver.findElement(burgermenu).click();
    }
    public void click_logoutLink(){
        driver.findElement(logout).click();
    }

}
