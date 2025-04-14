package com.qa.opencart.factory;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.constants.AppConstants;
import com.qa.opercart.exception.FrameworkException;

import io.qameta.allure.Step;



public class DriverFactory {

	WebDriver driver;

	Properties prop;

	OptionsManager optionsManager;
	

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log=LogManager.getLogger(DriverFactory.class);
	
    @Step("init the driver using property :{0}")
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

	//	System.out.println("browser name is: " + browserName);
		log.info("browser name is: " + browserName); //log4j its faster than System.out.println

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {

		case "chrome":

			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

			// driver = new ChromeDriver(optionsManager.getChromeOptions());

			break;

		case "firefox":

			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());

			break;

		case "edge":

			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));

			// driver = new EdgeDriver(optionsManager.getEdgeOptions());

			break;

		default:

			//System.out.println("Please pass valid browser name: " + browserName);
			log.error("plz pass the valid browsername.."+browserName); 
			

			throw new FrameworkException("----Invalid browser name----");

		}

		getDriver().manage().deleteAllCookies();

		getDriver().manage().window().maximize();

		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	/**
	 * 
	 * get driver using threadlocal.
	 * 
	 */

	public static WebDriver getDriver() {

		return tlDriver.get();

	}

	/**
	 * 
	 * This Method is used to init the properties from .properties file
	 * 
	 * @return
	 * 
	 */

	// supply env name using maven command line

	// mvn clean install -Denv="qa"

	// mvn clean install

	public Properties initProp() {

		String envName = System.getProperty("env");

		System.out.println("Running test suite on env: " + envName);

		FileInputStream ip = null;

		prop = new Properties();

		try {

			if (envName == null) {

				//System.out.println("no env is passed, hence running test suite on qa env..");
				log.warn("no env is passed, hence running test suite on qa env..");

				ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);

			}

			else {

				switch (envName.trim().toLowerCase()) {

				case "qa":

					ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);

					break;

				case "dev":

					ip = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);

					break;

				case "stage":

					ip = new FileInputStream(AppConstants.CONFIG_STAGE_PROP_FILE_PATH);

					break;

				case "uat":

					ip = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);

					break;

				case "prod":

					ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);

					break;

				default:
				//	System.out.println("plz pass the right env name" +envName);
					log.error("plz pass the right env name" +envName);

					throw new FrameworkException("===Invalid Environment===");

				}

			}

			prop.load(ip);

		}

		catch (FileNotFoundException e) {

			e.printStackTrace();
			log.error(e.getMessage());

		} catch (IOException e) {

			e.printStackTrace();

		}

		return prop;

	}
	/**
	 * takes screenshot taken by selenium and attache to the report by listeners 
	 */
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
	

}

/*
 * public class DriverFactory { WebDriver driver; Properties prop; // this java
 * inbuild class OptionsManager optionsManager; public static String highlight;
 * public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
 * // its class
 * 
 * public WebDriver initDriver(Properties prop) { String browserName =
 * prop.getProperty("browser"); System.out.println("browser name is :" +
 * browserName); highlight = prop.getProperty("highlight"); optionsManager = new
 * OptionsManager(prop);
 * 
 * switch (browserName.trim().toLowerCase()) { case "chrome": tldriver.set(new
 * ChromeDriver(optionsManager.getChromeOptions())); // driver = new
 * ChromeDriver(optionsManager.getChromeOptions()); // here we are // creating
 * object of chromedriver class with WebDriver(Parents // interface) with driver
 * reference variable // above constructor of Chromedriver will be called and
 * they have writen // intialize part of the chrome break; case "firefox":
 * tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions())); //
 * driver = new FirefoxDriver(optionsManager.getFirefoxOptions()); break; case
 * "edge": tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions())); //
 * driver = new EdgeDriver(optionsManager.getEdgeOptions()); break;
 * 
 * default: System.out.println("Please pass valid browser name: " +
 * browserName); throw new FrameworkException("----Invalid browser name----"); }
 * getDriver().manage().deleteAllCookies();
 * getDriver().manage().window().maximize();
 * getDriver().get(prop.getProperty("url")); return getDriver();
 * 
 * }
 * 
 * public static WebDriver getDriver() { return tldriver.get();
 * 
 * }
 * 
 *//**
	 * this method is used to init the properties from .properies fiule
	 * 
	 * @return
	 *//*
		 * //supply env name using maven command line //mvn clean install -Denv="qa"
		 * 
		 * public Properties initProp() { String envName = System.getProperty("env");
		 * System.out.println("running test suite on env:"+envName); FileInputStream ip
		 * =null;
		 * 
		 * try { if(envName==null) {
		 * System.out.println("no env is passed,hence running test suite on QA env..");
		 * ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH); }else {
		 * switch (envName.trim().toLowerCase()) { case "qa": ip = new
		 * FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH); break; case "dev": ip
		 * = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH); break; case
		 * "stage": ip = new FileInputStream(AppConstants.CONFIG_STAGE_PROP_FILE_PATH);
		 * break; case "uat": ip = new
		 * FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH); break; case "prod":
		 * ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH); break;
		 * 
		 * default: throw new FrameworkException("==INVALID ENV==");
		 * 
		 * } }
		 * 
		 * prop.load(ip); } catch(FileNotFoundException e) { e.printStackTrace(); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * return prop; }
		 * 
		 * }
		 * 
		 * 
		 * prop = new Properties(); // here object of the properies class try {
		 * FileInputStream ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);
		 * prop.load(ip); } catch (FileNotFoundException e) {
		 * 
		 * e.printStackTrace(); } // here object create with this class FileInputStream
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */