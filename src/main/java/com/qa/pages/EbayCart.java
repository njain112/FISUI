package com.qa.pages;

import com.qa.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import static javax.xml.datatype.DatatypeConstants.SECONDS;


public class EbayCart extends TestBase {
	
	//Page Factory - OR:
	@FindBy(xpath="//input[@type='text']")
	WebElement searchBox;
	
	@FindBy(xpath="//span[@class='gh-search-button__label']")
	WebElement searchButton;

	@FindBy(xpath="//div[@class='srp-river-results clearfix']/ul/li/div/div")
	List<WebElement> products;

	@FindBy(xpath="//*[@id='atcBtn_btn_1']")
	WebElement addtoCart;

	@FindBy(xpath="//*[@class='badge']")
	WebElement addtocartCount;

	@FindBy(xpath="//*[@class='gh-cart']")
	WebElement cartIcon;

	@FindBy(xpath="//*[@class='gh-minicart-body']//*[@class='gh-info__title']/span")
	WebElement cartItemName;




	//Initializing the Page Objects:
	public EbayCart(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public void validateebayCart() throws InterruptedException {
		driver.get(prop.getProperty("url"));
		wait(searchBox);
		searchBox.sendKeys("book");
		wait(searchButton);
		searchButton.click();
		String parentWindow=driver.getWindowHandle();
		wait(products.get(4));
		products.get(4).click();

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


		//scroll the page by few pixels to get to "Add To Cart" Button
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(0,450)");
        wait(addtoCart);
		addtoCart.click();
		Thread.sleep(10000);
		//driver.switchTo().defaultContent();
		driver.navigate().refresh();
		wait(addtocartCount);

		System.out.print(addtocartCount.isDisplayed()+addtocartCount.getText());

		//Validating Cart
		Assert.assertEquals("1",addtocartCount.getText());
		Assert.assertEquals(String.valueOf(addtocartCount.isDisplayed()),"true");
	}

public void wait(WebElement element) {
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
	wait.until(ExpectedConditions.elementToBeClickable(element));
}

	public void wait(WebElement element,String event) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void mouseHoverElement(WebElement element) {
		Actions action = new Actions(driver);
		Action a = action.moveToElement(element).build();
		a.perform();
	}


	

	
}

