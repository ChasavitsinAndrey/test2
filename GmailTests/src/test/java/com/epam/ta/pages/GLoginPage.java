package com.epam.ta.pages;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GLoginPage extends AbstractPage
{
    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://accounts.google.com/signin";

    @FindBy(id = "identifierId")
    private WebElement gmailLogin;

    @FindBy(id = "identifierNext")
    private WebElement loginNextButton;

    @FindBy(id = "passwordNext")
    private WebElement passNextButton;

    @FindBy(xpath = "//span[contains(text(),'Забыли пароль')]")
    private WebElement forgotPassword;

    @FindBy(xpath = "//*[@id='password']/div/div/div/input")
    private WebElement gmailPassword;

    public GLoginPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }


	public void firstLogin(String username, String password)
    {

        gmailLogin.sendKeys(username);
        highlightElement(By.id("identifierNext"));
        loginNextButton.click();
        unHighlightElement(By.id("identifierNext"));
        waitTillElementBecomeClickable(passNextButton);
        gmailPassword.sendKeys(password);
        highlightElement(By.id("passwordNext"));
        passNextButton.click();
        unHighlightElement(By.id("passwordNext"));
        logger.info("Login performed");

    }

    public boolean isLogOut()
    {
        if (!forgotPassword.isDisplayed())
        {
            return false;
        }
        return true;
    }


    @Override
    public void openPage()
    {
        driver.navigate().to(BASE_URL);
        logger.info("GLogin page opened");
    }

}