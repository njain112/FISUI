package com.qa.pages;

import com.qa.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class EbayCart extends TestBase {
	
	//Page Factory - OR:
	@FindBy(xpath="//input[@type='text']")
	WebElement searchBox;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement searchButton;
	
	@FindBy(xpath="//div[@class='srp-river-results clearfix']/ul/li/div/div")
	List<WebElement> listofProducts;

	@FindBy(xpath="//*[@id='atcBtn_btn_1']")
	WebElement addtoCart;

	@FindBy(xpath="//*[@id='gh-top']//ul[@id='gh-eb']/li[5]//a/i[@id='gh-cart-n']")
	WebElement addtocartCount;
	//Initializing the Page Objects:
	public EbayCart(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public void validateebayCart() throws InterruptedException {
		driver.get(prop.getProperty("url"));
		wait(searchBox);
		searchBox.sendKeys("books");
		wait(searchButton);
		searchButton.click();
		String parentWindow=driver.getWindowHandle();
		wait(listofProducts.get(0));
		listofProducts.get(0).click();

		//Handling multiple windows
		Set<String> s=driver.getWindowHandles();
		Iterator<String> I1= s.iterator();
		while(I1.hasNext()) {
			String child_window = I1.next();
            if (!parentWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
				System.out.println(driver.switchTo().window(child_window).getCurrentUrl());
			}
		}

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(0,450)");
        wait(addtoCart);
		addtoCart.click();
		Thread.sleep(10000);
		driver.switchTo().window(parentWindow);
		driver.navigate().refresh();
		wait(addtocartCount,"visible");

		System.out.print(addtocartCount.isDisplayed()+addtocartCount.getText());

		//Validating Cart
		Assert.assertEquals("1",addtocartCount.getText());
		Assert.assertEquals(String.valueOf(addtocartCount.isDisplayed()),"true");
	}

public void wait(WebElement element) {
	WebDriverWait wait = new WebDriverWait(driver,30);
	wait.until(ExpectedConditions.elementToBeClickable(element));
}

	public void wait(WebElement element,String event) {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}


	

	
}

