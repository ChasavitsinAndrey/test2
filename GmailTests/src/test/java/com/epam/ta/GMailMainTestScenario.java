package com.epam.ta;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.ta.steps.Steps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GMailMainTestScenario
{
	private Steps steps;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private final String USERNAME = "andrey8056Test@gmail.com";
	private final String PASSWORD = "2245720zxC";
	private final String LETTER_VALUE = "something in letter";
	private String SUBJ;



	@BeforeMethod(description = "Init browser")
	public void setUp()
	{
		steps = new Steps();
		steps.initBrowser();
		LocalDateTime now = LocalDateTime.now();
		SUBJ = "TEST "+ dtf.format(now);
	}


	@Test(enabled = true)
	public void gmailLogin()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.openGAccountOnGMailPage();
		Assert.assertTrue(steps.isLoggedIn(USERNAME));
	}

	@Test (enabled = true)
	public void saveAndCheckMailInDrafts()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.saveMailAsDraft(USERNAME, SUBJ, LETTER_VALUE);
		Assert.assertTrue(steps.isLetterInDrafts(SUBJ));

	}

	@Test (enabled = true)
	public void checkMailContent()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.saveMailAsDraft(USERNAME, SUBJ, LETTER_VALUE);
		steps.moveToDrafts();
		steps.openFromDrafts(SUBJ);
		Assert.assertTrue(steps.isAllContentOfLetterTheSame(USERNAME, SUBJ, LETTER_VALUE));
	}

	@Test(enabled = true)
	public void sendFromDrafts()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.saveMailAsDraft(USERNAME, SUBJ, LETTER_VALUE);
		steps.moveToDrafts();
		steps.openFromDrafts(SUBJ);
		steps.sendFromDrafts();
		Assert.assertTrue(steps.isntInDrafts(SUBJ));
		steps.moveToInbox();
		Assert.assertTrue(steps.isInbox(SUBJ));
	}

	@Test(enabled = true)
	public void gmailLogout()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.openGAccountOnGMailPage();
		steps.moveToDrafts();
		steps.logOut();
		Assert.assertTrue(steps.isLogoutSuccessful());

	}

	@Test(enabled = true)
	public void dragFromDraftAndDropToInbox()
	{
		steps.loginGAccount(USERNAME, PASSWORD);
		steps.moveToGMailFromGAccount();
		steps.saveMailAsDraft(USERNAME, SUBJ, LETTER_VALUE);
		steps.moveToDrafts();
		Assert.assertTrue(steps.isSuccessfullyDropped(SUBJ));
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser()
	{
		steps.closeDriver();
	}

}
