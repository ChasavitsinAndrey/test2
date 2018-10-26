package com.epam.ta.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverSingleton {

    private static WebDriver driver;
    private static final Logger logger = LogManager.getRootLogger();
    // private static final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
    // private static final String GECKODRIVER_GECKODRIVER_EXE_PATH = ".\\driver\\geckodriver.exe";
    private static final String WEB_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String CHROMEFRIVER_CHROMEFRIVER_EXE_PATH = ".\\driver\\chromedriver.exe";
    private static final String REMOTE_URL = "http://127.0.0.1:4444/wd/hub";

    private DriverSingleton(){};



    public static WebDriver getDriver()
    {
        if (driver == null)
        {
            System.setProperty(WEB_CHROME_DRIVER, CHROMEFRIVER_CHROMEFRIVER_EXE_PATH);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en-US");
            driver = new ChromeDriver(options);
//            try
//            {
//                driver = new RemoteWebDriver(new URL(REMOTE_URL), DesiredCapabilities.chrome());
//            }
//            catch (MalformedURLException e)
//            {
//                e.printStackTrace();
//            }
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            logger.info("Browser started");
        }
        return driver;
    }

    public static void closeDriver()
    {
        driver.quit();
        driver = null;
    }
}
