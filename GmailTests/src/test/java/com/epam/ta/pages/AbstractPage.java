package com.epam.ta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static sun.plugin2.message.HeartbeatMessage.DEFAULT_TIMEOUT;

public abstract class AbstractPage
{
	protected WebDriver driver;
	private static final int TIMER = 15;

	public abstract void openPage();

	public AbstractPage(WebDriver driver)
	{
		this.driver = driver;
	}

	public void waitInvisibilityOf(WebDriver driver, WebElement webElement){
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(webElement));
	}

	protected void waitTillElementBecomeVisible (WebElement locator)
	{
		new WebDriverWait(driver, TIMER).until(ExpectedConditions.visibilityOf(locator));
	}

	protected void waitTillElementBecomePresent (WebElement locator)
	{
		//new WebDriverWait(driver, TIMER).until(ExpectedConditions.pre);
	}

	protected void waitTillElementBecomeClickable (WebElement locator)
	{
		new WebDriverWait(driver, TIMER).until(ExpectedConditions.elementToBeClickable(locator));
	}

	protected void waitElementRefresh(WebElement element) {
		new WebDriverWait(driver, 5)
				.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}

	protected void waitForDocumentReady()
	{
		new WebDriverWait(driver, 10).until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	}

	protected void highlightElement(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid green'", element);
	}

	protected void unHighlightElement(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
	}

	private ExpectedCondition<Boolean> isAjaxFinished() {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
			}
		};
	}

}
