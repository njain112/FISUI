package com.qa.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.EbayCart;

public class EbayCartTest extends TestBase{
	EbayCart ebayCart;
	
	public EbayCartTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		ebayCart = new EbayCart();
	}
	
	@Test(priority=1)
	public void eBayCartTest() throws InterruptedException {
		ebayCart.validateebayCart();
	}

	@AfterMethod
	public void tearDown(){
driver.quit();
	}
	
	
	
	

}
