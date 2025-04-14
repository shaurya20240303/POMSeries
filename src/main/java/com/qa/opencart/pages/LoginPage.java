package com.qa.opencart.pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	// encapsulation how to private varib;e using public layer
	// create constructor why we create constructor becuase when LoginPage class
	// object create in different class thata time constructor will eb called and
	// passing driver (forwarded)-driverfactory to Loginpage-anyTest related page
	// classic expample is pageclass
	// martin fowler-check page

	public LoginPage(WebDriver driver) {
		this.driver = driver;  ////const..is helping to initialize the class instance variable to the local variable using this keyword
         eleUtil=new ElementUtil(driver);
	}

	// BY locator:page objects:OR--here followed encapsulation concept
	// all page locaters are private

	private By emailID = By.id("input-email");
	private By password = By.id("input-password");
	private By loginbtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");

	// 2 public page actions-method(features or functinality)
	// all method by in public

	@Step("getLoginPageTitle")
	public String getLoginPageTitle() {
		
		String title = eleUtil.waitForTitlecontains(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("login page title==>" + title);
		ChainTestListener.log("login page title==>" + title);
		return title;
	}

	@Step("getLoginPageURL")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);

	
		System.out.println("login page title==>" + url);
		return url;
	}

	@Step("checking forgot plinl is displayed")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsElementDisplayed(forgotPwdLink);
	}

	@Step("login with username:{0} and pssword :{1}")
	public HomePage doLogin(String username, String pwd) {
		System.out.println("App cred are:" + username + ":" + pwd);
		eleUtil.waitForElementvisible(emailID, AppConstants.SHORT_TIME_OUT).sendKeys(username);
		eleUtil.dosendkeys(password, pwd);
		eleUtil.doclick(loginbtn);
		return new HomePage(driver);
	}

}
