package com.epam.ta;

import com.epam.ta.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestWithExecutor
{
    private Steps steps;
    private final String USERNAME = "andrey8056Test@gmail.com";
    private final String PASSWORD = "2245720zxC";
    private final String SUBJ = "SIMPLE SUBJECT";
    private final String LETTER_VALUE = "Something here";

    @BeforeMethod(description = "Init browser")
    public void setUp()
    {
        steps = new Steps();
        steps.initBrowser();
    }

    @Test(enabled = true)
    public void deleteWholeDrafts()
    {
        steps.loginGAccount(USERNAME, PASSWORD);
        steps.moveToGMailFromGAccount();
        steps.openGAccountOnGMailPage();
        steps.saveMailAsDraft(USERNAME, SUBJ, LETTER_VALUE);
        Assert.assertTrue(steps.areAllLettersDeleted());
    }



    @AfterMethod(description = "Stop Browser")
    public void stopBrowser()
    {
        steps.closeDriver();
    }
}
