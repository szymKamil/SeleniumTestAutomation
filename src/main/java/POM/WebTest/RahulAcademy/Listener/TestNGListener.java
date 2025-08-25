package POM.WebTest.RahulAcademy.Listener;

import Base.BaseTest.DriverFactoryV1;
import Base.Utils.Screenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class TestNGListener implements ITestListener {

    Logger logger = LoggerFactory.getLogger("Logger");



    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test wykonał się z sukcesem ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Screenshot.takeScreenshot(DriverFactoryV1.getDriver());
    }
}
