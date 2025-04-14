package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class ProductinfoPageTest extends BaseTest {
	@BeforeClass
	public void productInfoSetup() {
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {

				{ "macbook", "MacBook Pro" },

				{ "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" },

		};
	}

	@Test(dataProvider = "getProductData")
	public void productSearchHeaderTest(String searchKey, String productName) {
		searchResultsPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actualProductHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualProductHeader, productName);
	}

	/*
	 * @DataProvider public Object[][] getProductImageData() { return new Object[][]
	 * {
	 * 
	 * { "macbook", "MacBook Pro", 4 }, { "macbook", "MacBook Air", 4 }, { "imac",
	 * "iMac", 3 }, { "samsung", "Samsung SyncMaster 941BW", 1 }, { "samsung",
	 * "Samsung Galaxy Tab 10.1", 7 },
	 * 
	 * }; }
	 */
	@DataProvider
	public Object[][] getProductImageSheetData() {
	Object productData[][]=	ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		 return productData;
				 
	}

	@Test(dataProvider = "getProductImageSheetData")
	public void productImagesCountTest(String searchKey, String productName, String expectedImagesCount) {
		searchResultsPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actualProductImagesCount = productInfoPage.getProductsImagesCount();
		Assert.assertEquals(actualProductImagesCount, Integer.parseInt(expectedImagesCount));
	}
	
	@Test
	public void productInfoTest() {
		searchResultsPage = homePage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.GetProductInfo();

		productInfoMap.forEach((k,v)->System.out.println(k+":"+v));
		//when we want to muptiple assert in test then we use softassertion
		//first create object
		//if any assert will fail program will not terminate and next assertion will execute here
		//in hard assert any assertion failed immediately program terminate
		//for single check in test we use hard assert =Assert
		
		SoftAssert sofassert=new SoftAssert();
		
		sofassert.assertEquals(productInfoMap.get("header"), "MacBook Pro");
		
		sofassert.assertEquals(productInfoMap.get("Brand"), "Apple");
		sofassert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		sofassert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		sofassert.assertEquals(productInfoMap.get("Reward Points"), "800");
		
		sofassert.assertEquals(productInfoMap.get("price"), "$2,000.00");
		sofassert.assertEquals(productInfoMap.get("extax"), "$2,000.00");
		sofassert.assertAll(); //this line give all detaisl about failed assertion
		
	}
}
