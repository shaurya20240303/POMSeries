package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class HomePage {
	private WebDriver driver; //global variable
	private ElementUtil eleUtil;
	// encapsulation how to private varib;e using public layer
	// create constructor why we create constructor becuase when LoginPage class
	// object create in different class thata time constructor will eb called and
	// passing driver (forwarded)-driverfactory to Loginpage-anyTest related page
	// classic expample is pageclass
	// martin fowler-check page
                    //local variable WebDriver driver
	public HomePage(WebDriver driver) {  //this is constructor with one  variable
		this.driver = driver;    //using this keyword define global=local
		//this keyword :using this keyword only global variable access 
		eleUtil=new ElementUtil(driver);

	}

	// 1 by locator
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content > h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// 2 public page actions
	
	
public String getHomePageTitle() {
		
		String title = eleUtil.waitForTitlecontains(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("login page title==>" + title);
		return title;
	}

	public String getHomePageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.HOME_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);

	
		System.out.println("home page title==>" + url);
		return url;
	}
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsElementDisplayed(logoutLink);

	}
	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doclick(logoutLink);
		}
		//pending
	}
	public List<String> getHeaderList() {
		List<WebElement> headersList=eleUtil.waitforElementsVisible(headers, AppConstants.SHORT_TIME_OUT);
	
		List<String> headersValList=new ArrayList<String>();
		for(WebElement e:headersList) {
			String text=e.getText();
			headersValList.add(text);
			
		}
		return headersValList;
	}
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("search key"+searchKey);
		WebElement searchEle=eleUtil.waitForElementvisible(search, AppConstants.DEFAULT_TIME_OUT);
		eleUtil.dosendkeys(searchEle, searchKey);
		
		eleUtil.doclick(searchIcon);
		
		return new SearchResultsPage(driver);
	}
	

}
