package com.epam.ta.pages;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GMailMainPage extends AbstractPage
{
    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://mail.google.com/mail/#inbox";


    @FindBy(xpath = "//*[contains(@class, 'T-I J-J5-Ji T-I-KE L3')]" )
    private WebElement newLetter;

    @FindBy(xpath = "//textarea[@name='to']" )
    private WebElement textBoxTo;

    @FindBy(xpath = "//input[@name='subjectbox']/../../input[3]" )
    private WebElement textBoxSubjToVerify;

    @FindBy(xpath = "//input[@name='subjectbox']" )
    private WebElement textBoxSubj;

    @FindBy(xpath = "//*[contains(@class, 'Am Al editable LW-avf')]" )
    private WebElement letterValue;

    @FindBy(xpath = "//*[contains(@class, 'Ha')]" )
    private WebElement closeLetter;

    @FindBy(xpath = "//*[contains(@class, 'J-J5-Ji btA')]/div[2]" )
    private WebElement sendButton;

    @FindBy(xpath = "//*[contains(@class, 'gb_Hb')]/div[2]/a" )
    private WebElement signOutButton;

    @FindBy(xpath = "//a[@href='https://mail.google.com/mail/#drafts']" )
    private WebElement draftsButton;

    @FindBy(xpath = "//a[@href='https://accounts.google.com/SignOutOptions?hl=en&continue=https://mail.google.com/mail&service=mail']" )
    private WebElement accButton;

    @FindBy(xpath = "//a[@href='https://profiles.google.com/?hl=en&tab=mX']/../div/div[2]")
    private WebElement loggedUserName;

    @FindBy(xpath = "//img[@aria-label='Minimize']//../preceding-sibling::td//h2/div[2]" )
   // @FindBy(xpath = "//input[@name='subject']" )
    private WebElement subjectFromDrafts;

    @FindBy(xpath = "//input[@name='composeid']/../div[2]/div[1]/span")
    private WebElement nameOfSavedInDraftsLetter;

    @FindBy(xpath = "//a[@href='https://mail.google.com/mail/#inbox']" )
    private WebElement inboxButton;

    @FindBy(xpath = "//div[@gh='mtb']/div/div/div/div/div/span" )
    private WebElement pickAllItemsCheckbox;

    @FindBy(xpath = "//div[@act='16']/div" )
    private WebElement discardDraftsButton;

    @FindBy(xpath = "//td[contains(text(),\"You don't have any saved drafts.\")]" )
    private WebElement allElementsWereDeleted;

    @FindBy(xpath = "//div[@title='Inbox']" )
    private WebElement inboxLabel;

    public GMailMainPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public void openPage()
    {
        driver.navigate().to(BASE_URL);
        logger.info("GMain page opened");
    }

    public void  createNewLetter()
    {

        newLetter.click();

    }


    public void fillNewLetter(String To, String Subject, String Value)
    {

        textBoxTo.sendKeys(To);
        textBoxSubj.sendKeys(Subject);
        letterValue.sendKeys(Value);
        waitTillElementBecomeClickable(closeLetter);
        closeLetter.click();


    }

    public void logOutClick()
    {
        accButton.click();
        waitTillElementBecomeClickable(signOutButton);
        signOutButton.click();

    }

    public void openLetterFromDrafts (String Subj)
    {
        String strMyXPath = "//*[contains(@class, 'zA yO')]/td[6]/div/div/div/span/span[contains(text(),'" + Subj + "')]";
        WebElement letter = driver.findElement(By.xpath(strMyXPath));
        letter.click();
        waitTillElementBecomePresent(textBoxSubjToVerify);
        waitInvisibilityOf(driver, textBoxSubjToVerify);
    }

    public void sendLetterFromDrafts ()
    {
        sendButton.click();
        waitForDocumentReady();
    }

    public void clickGAccountButton ()
    {
        waitTillElementBecomeClickable(accButton);
        accButton.click();
    }

    public void  goToDrafts()
    {
        waitElementRefresh(draftsButton);
        waitTillElementBecomeClickable(draftsButton);
        draftsButton.click();
    }

    public void  goToInbox()
    {
        waitElementRefresh(inboxButton);
        waitTillElementBecomeClickable(inboxButton);
        inboxButton.click();
    }

     public boolean isLetterInbox(String Subj)
    {
        String strMyXPath = "//*[contains(@class, 'zA zE')]/td[6]/div/div/div/span/span[contains(text(),'" + Subj + "')]";
        WebElement letter = driver.findElement(By.xpath(strMyXPath));
        return letter.isDisplayed();
    }

    public boolean isntLetterInDrafts(String Subj)
    {
        String strMyXPath = "//*[contains(@class, 'zA yO')]/td[6]/div/div/div/span/span[contains(text(),'" + Subj + "')]";
        List<WebElement> list = driver.findElements(By.xpath(strMyXPath));
        if (list.size()<1)
        {
            return true;
        }
        return false;

    }

    public boolean discardWholeDrafts ()
    {

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", pickAllItemsCheckbox);
        waitTillElementBecomePresent(discardDraftsButton);
        waitTillElementBecomeClickable(discardDraftsButton);
        discardDraftsButton.click();
        waitTillElementBecomeVisible(allElementsWereDeleted);
        return true;

    }

    public String getLoggedInUserName()
    {

    	return loggedUserName.getText();

    }
    public String getSubj(String Subj)
    {
        String strMyXPath = "//*[contains(@class, 'zA yO')]/td[6]/div/div/div/span/span[contains(text(),'" + Subj + "')]";
        WebElement letter = driver.findElement(By.xpath(strMyXPath));
        return letter.getText();

    }

    public String getToFromLetter()
    {
        WebElement LetterName = driver.findElement(By.xpath("//input[@name='composeid']/../div[2]/div[1]/span"));
        return LetterName.getAttribute("data-hovercard-id");

    }

    public String getSubjFromLetter()
    {
        new Actions(driver).pause(1000).build().perform();
        return subjectFromDrafts.getText();

    }

    public String getValueFromLetter()
    {
        return letterValue.getText();

    }


    public boolean  dragDraftAndDropToInbox(String Subj)
    {
        String strMyXPath = "//tr[contains(@class, 'zA yO') and @draggable='true']/td[6]/div/div/div/span/span[contains(text(),'" + Subj + "')]/ancestor::tr";
        WebElement letter = driver.findElement(By.xpath(strMyXPath));
        waitElementRefresh(inboxButton);
        waitTillElementBecomeClickable(inboxButton);
        new Actions(driver).dragAndDrop(letter, inboxButton).build().perform();
        new Actions(driver).pause(1000).build().perform();
        return inboxLabel.isDisplayed();
    }


}