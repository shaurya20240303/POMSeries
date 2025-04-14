package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;
	// encapsulation how to private varib;e using public layer
	// create constructor why we create constructor becuase when LoginPage class
	// object create in different class thata time constructor will eb called and
	// passing driver (forwarded)-driverfactory to Loginpage-anyTest related page
	// classic expample is pageclass
	// martin fowler-check page

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

	}
	private By productHeader=By.tagName("h1");
	private By productImages=By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	public String getProductHeader() {
		String header=eleUtil.doelementgettext(productHeader);
		
		System.out.println("product header:===>"+header);
		return header;
	}
	
	public int getProductsImagesCount() {
		int imagesCount = eleUtil.waitforElementsPresence(productImages, AppConstants.SHORT_TIME_OUT).size();
		System.out.println(getProductHeader() +":Image Count="+imagesCount);
		return imagesCount;
	}
	/**
	 * get the full product information:header,inages count,meta data,price adat
	 * @return
	 */
	public Map<String, String> GetProductInfo() {
		productMap=new HashMap<String,String>();
		//productMap=new LinkedHashMap<String,String>();
		//productMap=new TreeMap<String,String>();
		productMap.put("header", getProductHeader());
		productMap.put("imagescount", getProductsImagesCount()+"");
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitforElementsPresence(productMetaData, AppConstants.DEFAULT_TIME_OUT);
		
		for (WebElement e:metaList) {
			String metaText = e.getText();
			String meta[] = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	private void getProductPriceData() {
		List<WebElement> priceList=	eleUtil.waitforElementsPresence(productPriceData, AppConstants.DEFAULT_TIME_OUT);
		String Productprice = priceList.get(0).getText().trim();
		String productExTax = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("price", Productprice);
		productMap.put("extax", productExTax);
	}
}
