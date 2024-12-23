package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {
                    "src/test/java/Features/Login",
                    "src/test/java/Features/Products",
        },
        glue = {"Utility",
                "StepDefinitions",},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                  "Utility.StepDetails",},
        tags = "@TC_SL_LOGIN_02 or @TC_SL_Products_01"
)
public class RunTest extends AbstractTestNGCucumberTests {
}
