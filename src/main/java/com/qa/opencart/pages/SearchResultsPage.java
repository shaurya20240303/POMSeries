package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class SearchResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	// encapsulation how to private varib;e using public layer
	// create constructor why we create constructor becuase when LoginPage class
	// object create in different class thata time constructor will eb called and
	// passing driver (forwarded)-driverfactory to Loginpage-anyTest related page
	// classic expample is pageclass
	// martin fowler-check page

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

	}
	private By productsResults=By.cssSelector("div.product-thumb");
	
	
	public int getProductResultsCount() {
		int resultCount=eleUtil.waitforElementsPresence(productsResults, AppConstants.SHORT_TIME_OUT).size();
		
		System.out.println("products results count==>"+resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("product name :"+productName);
		eleUtil.doclick(By.linkText(productName));
		return new ProductInfoPage(driver); 
		
	}
}
