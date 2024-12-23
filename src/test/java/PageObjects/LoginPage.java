package PageObjects;

import CSVFile.CSVReader;
import Utility.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private static String filePath;
    private static List<String[]> fileDataList;
    private static String standUsername;
    private static String lockedOutUsername;
    private static String allUsersPwd;
    private static String lockedOutUserErrorMsg="Epic sadface: Sorry, this user has been locked out.";

    //Login Page Locators
    private final By userNameInputField=By.xpath("//*[@id=\"user-name\"]");
    private final By passwordInputField=By.xpath("//*[@id=\"password\"]");
    private final By loginBtn=By.xpath("//*[@id=\"login-button\"]");
    private final By lockedUserError=By.cssSelector("h3[data-test=error]");

    public LoginPage(WebDriver inputDriver){
        driver=inputDriver;
        filePath="src/test/resources/TestData/LoginData.csv";
        readLoginDataFromCSV(filePath);
    }
    private void readLoginDataFromCSV(String filePath){
        CSVReader csvReader=new CSVReader();
        fileDataList=csvReader.readCSV(filePath);
        if(!fileDataList.isEmpty()){
            //Accepted UserName
            standUsername=fileDataList.get(1)[0];
            lockedOutUsername=fileDataList.get(2)[0];

            //All Users Pwd
            allUsersPwd=fileDataList.get(1)[1];
        }
    }
    public void navigateToSwagLabLoginPage(){
        //Navigate to Login Page
        Hook.goToLoginPage();
    }

    //StandardUser
    public void enterStandardUserName(){
        //Enter Standard username
        driver.findElement(userNameInputField).clear();
        driver.findElement(userNameInputField).sendKeys(standUsername);
        Hook.logInfo("User Name :: "+standUsername);
    }

    //LockedOutUser
    public void enterLockedOutdUserName(){
        //Enter Standard username
        driver.findElement(userNameInputField).clear();
        driver.findElement(userNameInputField).sendKeys(lockedOutUsername);
        Hook.logInfo("User Name :: "+lockedOutUsername);
    }
    public void enterPassword(){
        //Enter password
        driver.findElement(passwordInputField).clear();
        driver.findElement(passwordInputField).sendKeys(allUsersPwd);
        Hook.logInfo("Password :: "+allUsersPwd);
    }
    public void clickLoginBtn(){
        //Click Login Button
        System.out.println("Clicking Login Button");
        driver.findElement(loginBtn).click();
    }
    public void checkLoginBtn(){
        Assert.assertTrue(driver.findElement(loginBtn).isDisplayed(),"Login button display");
    }

    //Error Message for locked out user
    public void check_lockedOutUserErrorMsg(){
        Assert.assertEquals(driver.findElement(lockedUserError).getText(),lockedOutUserErrorMsg);
        Hook.logInfo("Locked Out User Error Message :: "+lockedOutUserErrorMsg);
    }

}
