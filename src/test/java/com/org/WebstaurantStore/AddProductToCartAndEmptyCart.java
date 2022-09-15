package com.org.WebstaurantStore;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddProductToCartAndEmptyCart {
	
	
		
		public static WebDriver driver;
		
		@BeforeClass
		public void testSetup() {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
		}
		
		@Test
		public void addProductToCart() throws InterruptedException {
			
			driver.get("https://www.WebstaurantStore.com");
			Reporter.log("Browser Launched");
			Reporter.log("maximize the window");
			driver.manage().window().maximize();
				
			Reporter.log("Search for stainless work table products");
			driver.findElement(By.xpath("//input[@id='searchval']")).sendKeys("stainless work table");
			driver.findElement(By.xpath("//button[@value='Search']")).click();
				
			String pagecount = driver.findElement(By.xpath("//*[@id='paging']/nav/ul/li[7]/a")).getText();
			System.out.println(pagecount);
				
			int pageno = Integer.valueOf(pagecount);
			int total_products =0; 
				
				for (int p=1; p <= pageno; p++)
				{
					
					WebElement activePage = driver.findElement(By.xpath("//*[@id='paging']/nav/ul/li/a[text()='"+p+"']"));
					System.out.println("activepage:"+activePage.getText());
					activePage.click();
						
					List<WebElement> products= driver.findElements(By.xpath("//*[@id='ProductBoxContainer']"));
					int product_count = products.size();
					System.out.println("No.of products per page"+ product_count);
					total_products = total_products + product_count; 
						
					System.out.println ("total_products" + total_products ); 
						
					List<WebElement> titles= driver.findElements(By.xpath("//*[@id='product_listing']/div/div[2]/a"));
					int titleCount =titles.size();
					System.out.println(titleCount);
						for (WebElement title:titles)
						{
							String desc = title.getText();
							if(!desc.equals("plus") && !desc.matches(".*\\bTable\\b.*"))
							{
								System.out.println(desc);
								
							}
						}
						
				}
				WebElement lastProduct = driver.findElement(By.xpath("//*[@id='ProductBoxContainer'][last()]"));
				System.out.println(lastProduct.getText());
				driver.findElement(By.xpath("//*[@id='product_listing']/div[60]/div[4]/form/div/div/input[2]")).click();
				
				String subWindow = driver.getWindowHandle();
				System.out.println("SubWindow"+ subWindow);
				driver.switchTo().window(subWindow);
				Thread.sleep(3000);
				
				String text = driver.findElement(By.xpath("//*[@id='td']/div[11]/div/div/footer/button[2]")).getText();
				System.out.println(text);
				driver.findElement(By.xpath("//*[@id='td']/div[11]/div/div/footer/button[2]")).click();
				
				Thread.sleep(3000);
				driver.findElement(By.xpath("//*[@id='watnotif-wrapper']/div/p/div[2]/div[2]/a[1]")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//*[@id='main']/div[1]/div/div[1]/div/button")).click();
				driver.findElement(By.xpath("//*[@id='td']/div[10]/div/div/div/footer/button[1]")).click();
				
		}		
				
					
					
					
	
		

}
