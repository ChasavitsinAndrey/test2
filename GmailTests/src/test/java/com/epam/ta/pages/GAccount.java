package com.epam.ta.pages;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GAccount extends AbstractPage
{
    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://myaccount.google.com/";

    @FindBy(xpath = "//*[@href='https://mail.google.com']" )
    private WebElement gmailButton;

    public GAccount(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public void openPage()
    {
        driver.navigate().to(BASE_URL);
        logger.info("GAccount page opened");
    }

    public void  moveOn()
    {
        gmailButton.click();
        logger.info("Browser closed");
    }



}