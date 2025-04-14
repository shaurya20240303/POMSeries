package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

// we will avoid as static method because parallel execution not possible so we use non static method
//web element and webdriver related method we usually cant create as static
public class ElementUtil {

	private WebDriver driver;
	private javaScriptutil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil=new javaScriptutil(driver);
	}

	private void nullcheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("==value/prop/attribute can not be null==="); // here enclapsuilation method here
		}
	}
	
	private void highlightElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		

	}

	public String doelementgettext(By locator) {
		return getElement(locator).getText();
	}

	public String doelementgettext(String locaterType, String locatorValue) {
		return getElement(getlocator(locaterType, locatorValue)).getText();
	}
	
	@Step("element {0} is diplayed ..")
	public boolean doIsElementDisplayed(By locator) { // This method is work only for single element
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is displayed");
			return false;
		}
	}
	
	public String waitForURLContains(String fractionURL, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if(wait.until(ExpectedConditions.urlContains(fractionURL))){
				return driver.getCurrentUrl();
		}	
	}
		catch(TimeoutException e) {
			System.out.println("URL is not found after " + timeOut + " seconds");
			
		}
		return null;
	}
	

	public String doGetDomAttribute(By locator, String attrName) {
		nullcheck(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}

	public String doGetProperty(By locator, String propName) {
		nullcheck(propName);
		return getElement(locator).getDomProperty(propName);
	}

	@Step("clicking on webelement using locotor:{0}")
	public void doclick(By locator) {
		getElement(locator).click();
	}

	public void doclick(String locaterType, String locatorValue) {
		getElement(getlocator(locaterType, locatorValue)).click();
	}

	public void dosendkeys(String locaterType, String locatorValue, CharSequence... value) {
		nullcheck(value);
		WebElement element = getElement(getlocator(locaterType, locatorValue));
		element.clear();
		element.sendKeys(value);
		
	}

	@Step("fill value :{1} into the webElement :{0}")
	public void dosendkeys(By locator, CharSequence... value) {
		nullcheck(value);
		WebElement element=getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	@Step("fill value :{1} into the webElement :{0}")
	public void dosendkeys(WebElement element, CharSequence... value) {
		nullcheck(value);
		element.clear();
		element.sendKeys(value);
	}

	@Step("get webelement using locator:{0}")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;

	}

	public By getlocator(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;

		default:
			System.out.println("invalid locator ,please pass right locator");
			break;
		}
		return locator;
	}

// ***************** select tag -dropdown utils**************

	public void doselectDropdownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doselectDropdownByText(By locator, String visibletext) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibletext);
	}

	public void doselectDropdownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doselectDropdownBycontainstext(By locator, String partialText) {
		Select select = new Select(getElement(locator));
		select.selectByContainsVisibleText(partialText);
	}

	// ******* select options related common method*****
	public int getDropdownoptionTextCount(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> countryoptionlist = select.getOptions();
		System.out.println("option sixe" + countryoptionlist.size());

		return countryoptionlist.size();
	}

	public void printDropdownoptionText(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionlist = select.getOptions();

		for (WebElement e : optionlist) {
			String text = e.getText();

		}

	}

	public List<String> getDropdownoptionTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionlist = select.getOptions();
		System.out.println("option sixe" + optionlist.size());
		List<String> optionvaluelist = new ArrayList<String>();

		for (WebElement e : optionlist) {
			String text = e.getText();
			optionvaluelist.add(text);
		}
		return optionvaluelist;

	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void dosearch(By searchfield, By suggestions, String searchkey, String actualvalue)
			throws InterruptedException {
		// driver.findElement(By.name("q")).sendKeys("selenium");
		// getElement(searchfield).sendKeys(searchkey);
		dosendkeys(searchfield, searchkey);
		Thread.sleep(5000);

		List<WebElement> sugglist = getElements(suggestions);
		// System.out.println(sugglist.size());
		System.out.println(sugglist.size());
		boolean flag = false;

		for (WebElement e : sugglist) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(actualvalue)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(actualvalue + "is available and selected");
		} else {
			System.out.println(actualvalue + "is not available");
		}
	}

	public void selectvalueFromDropdown(By locator, String value) {

		Select select = new Select((getElement(locator)));
		List<WebElement> optionsList = select.getOptions();

		boolean flag = false;
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);

			if (text.contains(value)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(value + "is available and selected");
		} else {
			System.out.println(value + "is not available");
		}
	}

	/**
	 * this method is handling single/multiple and all choices selection.please pass
	 * "all" to select all the choices selectchoice(choicedropdown, choices, "all");
	 * 
	 * @param choicedropdown
	 * @param choices
	 * @param choicevalue
	 * @throws InterruptedException
	 */
	// string... it become string array
	public void selectchoice(By choicedropdown, By choices, String... choicevalue) throws InterruptedException {

		doclick(choicedropdown);
		Thread.sleep(2000);
		List<WebElement> elCounts = getElements(choices);
		System.out.println(elCounts.size());
		if (choicevalue[0].equalsIgnoreCase("all")) {
			// select all the choice one by one
			for (WebElement e : elCounts) {
				e.click();
			}
		} else {

			for (WebElement e : elCounts) {
				String text = e.getText();
				System.out.println(text);
				for (String ch : choicevalue) {
					if (text.equals(ch)) {
						e.click();
					}
				}

			}

		}
	}

	// ********** actions util********

	public void doActionsSendkeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	/**
	 * 
	 * @param level1Menu
	 * @param level2Menu
	 * @param level3Menu
	 * @param level4Menu
	 * @throws InterruptedException
	 */
	public void levelfourmenusubmenuhandling(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		Actions act = new Actions(driver);

		driver.findElement(level1Menu).click();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		driver.findElement(level4Menu).click();
	}

	public void handletwolevelmenusubmenuhandling(By parentMenuLocator, By childMenuLocator) {

		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		getElement(childMenuLocator).click();
	}

	public void doSendkeyswithpause(By locator, String value, long pausetime) {
		Actions act = new Actions(driver);
		WebElement firstname = driver.findElement(By.id("input-firstname"));
		//
		char val[] = value.toCharArray();

		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pausetime).perform();
		}

	}

	// ******* wait utils****
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		 highlightElement(element);
		 return element;

	}
	
	public WebElement waitForElementPresence(By locator, long timeout,long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(pollingTime));
	 WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	 highlightElement(element);
		return element;


	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that isgreater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	@Step("waiting for webelement using locator:{0} within timeout:{1}")
	public WebElement waitForElementvisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		 highlightElement(element);
		 return element;
		
	}
	public WebElement waitForElementvisible(By locator, long timeout,long pollingTime ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(pollingTime));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		 highlightElement(element);
		 return element;
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * dropdown,
	 * check visible element and click this method

	 * @param locator
	 * @param timeout
	 */
	
	public void clickElementWhenReady(By locator,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring its part subpart
	 * 
	 * @param fractionTitle
	 * @param timeout
	 * @return
	 */

	@Step("wait for Title is:{0} within time out {1}")
	public String waitForTitlecontains(String fractionTitle, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle)))
				;
			{
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after+timeout" + timeout + "seconds");

		}

		return null;
	}

	/**
	 * An expectation for checking the title of a page.->exact title
	 * 
	 * 
	 * @param Title
	 * @param timeout
	 * @return
	 */

	public String waitForTitleToBe(String Title, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			if (wait.until(ExpectedConditions.titleIs(Title)))
				;
			{
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after+timeout" + timeout + "seconds");

		}

		return null;
	}

	// *************alert util******
	public Alert waitForAlert(long timeout,long pollingtime) {
	
		 Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(timeout)) .pollingEvery(Duration.ofSeconds(pollingtime))
				  .ignoring(NoSuchElementException.class)
				  .ignoring(StaleElementReferenceException.class)
				  .withMessage("---element is not found---");
		  
		return  wait.until(ExpectedConditions.alertIsPresent());
		

	}
	
	public Alert waitForAlert(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(long timeout) {
		return waitForAlert(timeout).getText();

	}

	public void acceptAlert(long timeout) {
		waitForAlert(timeout).accept();

	}

	public void dismissAlert(long timeout) {
		waitForAlert(timeout).dismiss();

	}

	public void AlertSendkeys(String text, long timeout) {
		waitForAlert(timeout).sendKeys(text);

	}
	
	//**********frame util****
	
	public Alert waitForFrameByLocatorAndSwitchItUsingFluentFeatures(long timeout,long pollingtime) {
		 Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(timeout)) .pollingEvery(Duration.ofSeconds(pollingtime))
				  .ignoring(NoSuchElementException.class)
				  .ignoring(StaleElementReferenceException.class)
				  .withMessage("---element is not found---");
		  
		return  wait.until(ExpectedConditions.alertIsPresent());

	}
	
	public void waitForFrameByLocatorAndSwitchIt(By framelocator,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));

	}
	
	public  void waitForFrameByIndexAndSwitchIt(By frameIndex,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}
	public  void waitForFrameByLocatorID0rNameAndSwitchIt(String frameIDorName,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDorName));

	}
	public  void waitForFrameByLocatorAndSwitchIt(WebElement frameElement,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}
	//*****wait for window**********
	
	public  boolean waitForWindow(int numberofWindows, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberofWindows));
		} catch (TimeoutException e) {
			System.out.println("number of windows are not matched...");
			return false;
		}

	}
	
	//********list of webelements*****
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public  List<WebElement> waitforElementsPresence(By locator,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e){
			return Collections.emptyList();
			
		}
		 
		
	}
	/**An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	//below one with webdriverwait
	
	public  List<WebElement> waitforElementsVisible(By locator,long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}catch(TimeoutException e){
		return Collections.emptyList();
		
	}
	 
		 
		
	}
	//below one with fluentwait
	public  WebElement waitforelementvisibleusingFluentfeature(By locator,long timeout,long pollingtime) {
		
		  Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(timeout)) .pollingEvery(Duration.ofSeconds(pollingtime))
				  .ignoring(NoSuchElementException.class)
				  .ignoring(StaleElementReferenceException.class)
				  .withMessage("---element is not found---");
		  
		return  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	
	//**** page is fully loaded with ready state using javscript excutor
	//interctive,complete ,loading
	
	public boolean isPageLoaded(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String isPageLoaded=	wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'")).toString();
		return Boolean.parseBoolean(isPageLoaded);
		//here boolean only return true with double quatos boolean value so we use parseboolean  its convert normal true
		
	}
	
	


	

}
