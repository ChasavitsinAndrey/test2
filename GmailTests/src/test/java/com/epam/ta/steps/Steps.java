package com.epam.ta.steps;

import java.util.concurrent.TimeUnit;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Steps
{

	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void initBrowser()
	{
		driver = DriverSingleton.getDriver();
	}

	public void closeDriver()
	{
		DriverSingleton.closeDriver();
	}

	public  void  loginGAccount (String username, String password)
	{

		GLoginPage loginPage = new GLoginPage(driver);
		loginPage.openPage();
		loginPage.firstLogin(username, password);
        logger.info("login successfull");
	}

	public void  moveToGMailFromGAccount ()
    {
        GAccount gAccount = new GAccount(driver);
        gAccount.moveOn();
        logger.info("moving to GMail from GAccount");
    }



	public void saveMailAsDraft(String To, String Subject, String Value)
	{
		GMailMainPage gMailMainPage= new GMailMainPage(driver);
		gMailMainPage.createNewLetter();
		gMailMainPage.fillNewLetter(To, Subject, Value);
        logger.info("mail saved in draft");
	}

    public boolean isAllContentOfLetterTheSame(String To, String Subject, String Value)
    {

        GMailMainPage gMailMainPage = new GMailMainPage(driver);
        String subj = gMailMainPage.getSubjFromLetter();
        String value = gMailMainPage.getValueFromLetter();
        String adresee = gMailMainPage.getToFromLetter();
        if (adresee.equalsIgnoreCase(To)&&subj.contains(Subject)&&value.equalsIgnoreCase(Value))
        {
            logger.info("content matched");
            return true;
        }
        logger.info("content isn't matched");
        return false;
    }

	public void moveToDrafts ()
    {

        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.goToDrafts();
        logger.info("moving drafts");
    }

    public void moveToInbox ()
    {

        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.goToInbox();
        logger.info("moving to inbox");
    }

    public void sendFromDrafts ()
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.sendLetterFromDrafts();
        logger.info("sending from drafts");

    }

    public void openFromDrafts (String Subj)
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.openLetterFromDrafts(Subj);
        logger.info("opening letter from drafts");
    }


    public void openGAccountOnGMailPage ()
    {

        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.goToDrafts();
        gMailMainPage.clickGAccountButton();
        logger.info("clicking account button");

    }
    public boolean isLoggedIn(String username)
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        String actualName = gMailMainPage.getLoggedInUserName();
        gMailMainPage.clickGAccountButton();
        logger.info("user is logged in");
        return actualName.equalsIgnoreCase(username);
    }

    public void logOut()
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.logOutClick();
        logger.info("logging out");
    }


    public boolean isLetterInDrafts (String letterSubj)
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        gMailMainPage.goToDrafts();
        String subj = gMailMainPage.getSubj(letterSubj);
        logger.info("letter was found");
        return subj.equalsIgnoreCase(letterSubj);
    }

    public boolean isntInDrafts (String letterSubj)
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        if (gMailMainPage.isntLetterInDrafts(letterSubj)==false)
        {
            logger.info("lettet still in drafts");
            return false;
        }
        logger.info("letter isn't is drafts");
        return true;
    }
    public boolean isInbox (String letterSubj)
    {
        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        if (gMailMainPage.isLetterInbox(letterSubj))
        {
            logger.info("letter inbox");
            return true;
        }
        logger.info("letter isn't inbox");
        return false;
    }

    public boolean isLogoutSuccessful ()
    {
        GLoginPage gLoginPage = new GLoginPage(driver);
        if (gLoginPage.isLogOut())
        {
            logger.info("logout successful");
            return true;
        }
        logger.info("logout isn't successful");
        return false;
    }


    public boolean areAllLettersDeleted ()
    {
        GMailMainPage gMailMainPage = new GMailMainPage(driver);
        gMailMainPage.discardWholeDrafts();
        logger.info("all letters were deleted");
        return true;
    }

    public boolean isSuccessfullyDropped(String Subj)
    {

        GMailMainPage gMailMainPage= new GMailMainPage(driver);
        logger.info("drag and drop was successful");
        return gMailMainPage.dragDraftAndDropToInbox(Subj);

    }


}
