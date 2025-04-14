package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class CommonsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	// encapsulation how to private varib;e using public layer
	// create constructor why we create constructor becuase when LoginPage class
	// object create in different class thata time constructor will eb called and
	// passing driver (forwarded)-driverfactory to Loginpage-anyTest related page
	// classic expample is pageclass
	// martin fowler-check page

	public CommonsPage(WebDriver driver) {
		this.driver = driver;  ////const..is helping to initialize the class instance variable to the local variable using this keyword
         eleUtil=new ElementUtil(driver);
	}
	
	
	private By logo=By.className("img-responsive");
	private By footer=By.xpath("//footer//a");
	
	
	
	public boolean isLogoDisplayed() {
		return eleUtil.doIsElementDisplayed(logo);
	}
	
	public List<String> getFootersList() {
		List<WebElement> footerList = eleUtil.waitforElementsPresence(footer, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Total number of footers:"+footerList);
		
		ArrayList<String> footers = new ArrayList<String>();
		
		for(WebElement e:footerList) {
			String text = e.getText();
			footers.add(text);
		}
		return footers;
	}
	
	public boolean checkFooterLink(String footerLink) {
		return getFootersList().contains(footerLink);
	}


}
