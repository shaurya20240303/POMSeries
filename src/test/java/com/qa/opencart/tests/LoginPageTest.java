package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100:design login page for oprn cart")
@Story("US 101:design the various features of the cart login page")
@Feature("Feature 50:loginpage feature")
public class LoginPageTest extends BaseTest {

	
	@Description("checking login page title..")
	@Severity(SeverityLevel.MINOR)
	@Owner("shaurya Prajapati")
	@Test

	public void loginPageTitleTest() {

		String actTitle = loginPage.getLoginPageTitle();

		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}

	
	@Description("checking login page url..")
	@Severity(SeverityLevel.MINOR)
	@Test

	public void loginPageURLTest() {

		String actURL = loginPage.getLoginPageURL();

		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);

	}

	
	@Description("checking forgot pwd link..")
	@Severity(SeverityLevel.CRITICAL)
	@Test

	public void forgotPwdLinkExistTest() {

		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);

	}

	@Description("checking user is able to login with right crede..")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE)

	public void loginTest() {

		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}

	@Description("checking login page logo..")
	@Severity(SeverityLevel.CRITICAL)
	@Test

	public void logoTest() {

		Assert.assertTrue(commonsPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND_ERROR);

	}

	@DataProvider

	public Object[][] getFooterData() {

		return new Object[][] {

				{ "About Us" },

				{ "Contact Us" },

				{ "Specials" },

				{ "Order History" }

		};

	}

	@Description("checking page footer..")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData")
	public void footerTest(String footerLink) {

		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));

	}

}

/*
 * public class LoginPageTest extends BaseTest{
 * 
 * @Test public void loginPageTitleTest() { String actTitle =
 * loginPage.getLoginPageTitle(); Assert.assertEquals(actTitle,
 * AppConstants.LOGIN_PAGE_TITLE,AppError.TITLE_NOT_FOUND_ERROR);
 * 
 * }
 * 
 * @Test public void loginPageURLTest() { String actURL =
 * loginPage.getLoginPageURL();
 * Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION),
 * AppError.URL_NOT_FOUND_ERROR);
 * 
 * }
 * 
 * @Test public void forgotPwdLinkExistTest() {
 * Assert.assertTrue(loginPage.isForgotPwdLinkExist(),AppError.
 * ELEMENT_NOT_FOUND_ERROR);
 * 
 * 
 * }
 * 
 * @Test(priority=Integer.MAX_VALUE) public void loginTest() { homePage =
 * loginPage.doLogin(prop.getProperty("username"),
 * prop.getProperty("password")); //Thread.sleep(3000);
 * Assert.assertEquals(homePage.getHomePageTitle(),
 * AppConstants.HOME_PAGE_TITLE,AppError.TITLE_NOT_FOUND_ERROR);
 * 
 * 
 * }
 * 
 * @Test(description="checking logo on login page",enabled=false) //using
 * description tage we can write details about test case in plain english public
 * void logoTest() {
 * Assert.assertTrue(commonsPage.isLogoDisplayed(),AppError.LOGO_NOT_FOUND_ERROR
 * );
 * 
 * }
 * 
 * @DataProvider public Object[][] getFooterData() { return new Object [][] {
 * {"About Us"}, {"Contact Us"}, {"Specials"}, {"Order History"}
 * 
 * 
 * }; }
 * 
 * @Test(dataProvider="getFooterData",
 * description="checking important footer links on home page",enabled=false)
 * public void footerTest(String footerLink) {
 * Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
 * 
 * }
 * 
 * 
 * }
 */