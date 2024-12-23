package Utility;

import Managers.PageObjectManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Hook {
    private static final ExtentReports extentReports=new ExtentReports();
    private static final String url="https://www.saucedemo.com/";
    private static WebDriver driver;
    private static ExtentTest test;
    private static int stepTestIndex;
    private static String scenarioName;

    public static WebDriver getDriver(){
        return driver;
    }
    public static void configureSparkReporter(ExtentSparkReporter sparkReporter) throws IOException {
        sparkReporter.loadXMLConfig("config/spark-config.xml");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setProtocol(Protocol.HTTPS);
        sparkReporter.config().setTimelineEnabled(true);
        sparkReporter.config().thumbnailForBase64(true);
        sparkReporter.config().setDocumentTitle("Swag Lab - Cucumber Framework");
        sparkReporter.config().setTimeStampFormat("MMMM dd, yyyy, hh:mm a");
        sparkReporter.config().setJs("Array.from(document.getElementsByClassName('badge badge-default')).forEach((el)=>{let text=el.textContent.trim();let segments=text.split(':');if(segments.length<4){el.style.display='none';}});");
    }
    public static void configureExtentReport(ExtentSparkReporter sparkReporter) {
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("User Name", "Test Automation Engineer_Su Nandar Myo");
        extentReports.setSystemInfo("Project Name", "Swag Lab E-Commerce");
        extentReports.setSystemInfo("Machine", "Mac Os");
        extentReports.setSystemInfo("Appium Java Client Version", "8.5.1");
        extentReports.setSystemInfo("Maven Version", "4.0.0");
        extentReports.setSystemInfo("Java Version", "20");
        extentReports.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
    }
    @BeforeAll
    public static void setupReport() throws IOException {
        ExtentSparkReporter extentSparkReporter=new ExtentSparkReporter("target/cucumber-reports/"+new SimpleDateFormat("ddMMMyyyy_hh.mm.ss").format(new Date())+".html");
        configureSparkReporter(extentSparkReporter);
        configureExtentReport(extentSparkReporter);
    }
    @Before
    public static void setWebDriver(Scenario scenario){
        stepTestIndex=0;
        System.setProperty("webdriver.chrome.driver","chromedriver-mac-131/chromedriver");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        goToLoginPage();

        //Maximize the browser window
        driver.manage().window().maximize();

        System.out.println("=== " + scenario.getName().toLowerCase().substring(0, 1).toUpperCase() + scenario.getName().toLowerCase().substring(1) + " ===");
        scenarioName = scenario.getUri().toString().substring(scenario.getUri().toString().lastIndexOf("/") + 1).split("\\.")[0];
        test = extentReports.createTest(scenarioName, scenario.getName()+"  "+ scenario.getSourceTagNames()).assignCategory(scenarioName.split("_")[2]);
    }
    @AfterStep
    public static void addingReportInfo(Scenario scenario) throws IOException {
        stepTestIndex += 1;
        if (!scenario.isFailed()) {
            test.createNode(StepDetails.stepName).log(Status.PASS, "");
        }
        else {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File("target/cucumber-reports/screenshots/" + new SimpleDateFormat("ddMMMyyyy").format(new Date()) + "_" + scenarioName + ".png"));
            test.createNode(StepDetails.stepName)
                    .log(Status.FAIL, StepDetails.error, MediaEntityBuilder.createScreenCaptureFromPath("./screenshots/" + new SimpleDateFormat("ddMMMyyyy").format(new Date()) + "_" + scenarioName + ".png")
                            .build());
            for (int index = stepTestIndex; index < StepDetails.testSteps.size(); index++) {
                String stepName = StepDetails.testSteps.get(index).getStep().getKeyword() + " | " + StepDetails.testSteps.get(index).getStep().getText();
                test.createNode(stepName).log(Status.SKIP, "");
            }
        }
    }
    @After
    public static void tearDown() {
        System.out.println("=== Test Finished ===");
        quitDriver();
        extentReports.flush();
    }
    public static void quitDriver(){
        if(driver!=null){
            System.out.println("Quit Driver!!");
            driver.quit();
        }
    }

    //Navigate to SwabLab URL
    public static void goToLoginPage(){
        try{
            driver.get(url);
        }catch(Exception ex){ex.printStackTrace();}
    }

    //Accept Alert
    public static void acceptAlert(){
        try{
            System.out.println("Accepting Alert");
            Alert alert=driver.switchTo().alert();
            System.out.println("Alert data : "+alert.getText());
            alert.accept();
            System.out.println("Alert accepted.");
        }catch (Exception exception){
            exception.getMessage();
        }
    }

    //Log Information
    public static void logInfo(String message){
        if(test!=null){
            test.log(Status.INFO,message);
        }
    }
    public static void logFail(String warning){
        if(test!=null){
            test.log(Status.FAIL,warning);
        }
    }

}
