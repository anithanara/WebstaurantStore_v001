// Go to https://www.webstaurantstore.com/

//Search for 'stainless work table'.

//Check the search result ensuring every product has the word 'Table' in its title.

//Add the last of found items to Cart.

//Empty Cart.

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
			
			Reporter.log("Checking products through pagination");
			String pagecount = driver.findElement(By.xpath("//*[@id='paging']/nav/ul/li[7]/a")).getText();
			Reporter.log("Total number pages - " + pagecount);
			Reporter.log("Product titles that does not contain Table");	
			int pageno = Integer.valueOf(pagecount);
			int total_products =0; 
			
				for (int p=1; p <= pageno; p++)
				{
					
					WebElement activePage = driver.findElement(By.xpath("//*[@id='paging']/nav/ul/li/a[text()='"+p+"']"));
					activePage.click();
					
					List<WebElement> products= driver.findElements(By.xpath("//*[@id='ProductBoxContainer']"));
					int product_count = products.size();
					total_products = total_products + product_count; 
						
					List<WebElement> titles= driver.findElements(By.xpath("//*[@id='product_listing']/div/div[2]/a"));
						for (WebElement title:titles)
						{
							String desc = title.getText();
							if(!desc.equals("plus") && !desc.matches(".*\\bTable\\b.*"))
							{
								Reporter.log(desc);
								
							}
						}
						
				}
				WebElement lastProduct = driver.findElement(By.xpath("//*[@id='ProductBoxContainer'][last()]"));
				System.out.println("last product in last page:"+lastProduct.getText());
				
				//click Add to Cart of the last product from the search page
				driver.findElement(By.xpath("//*[@id='product_listing']/div[60]/div[4]/form/div/div/input[2]")).click();
				
				Reporter.log("Product added to the cart:"+lastProduct.getText());
				
				String subWindow = driver.getWindowHandle();
				System.out.println("SubWindow"+ subWindow);
								
				driver.switchTo().window(subWindow);
				Thread.sleep(1000);
				
				
					
				//String text = driver.findElement(By.xpath("//button[contains(text(),'Add To Cart')]")).getText();
				//System.out.println(text);
				
				System.out.println("About to enter to the if condition");
				Boolean viewCartElementExists = driver.findElements(By.xpath("//*[@id='watnotif-wrapper']/div/p/div[2]/div[2]/a[1]")).size() > 0;
				Boolean addToCartElementExists = driver.findElements(By.xpath("//*[@id='td']/div[11]/div/div/footer/button[2]")).size() > 0;
				System.out.println(viewCartElementExists);
				System.out.println(addToCartElementExists);
				if (viewCartElementExists)
				{
					if((driver.findElement(By.xpath("//*[@id='watnotif-wrapper']/div/p/div[2]/div[2]/a[1]")).getText()).equals("View Cart"))
					{
						
						System.out.println("View cart loop");
						driver.findElement(By.xpath("//*[@id='watnotif-wrapper']/div/p/div[2]/div[2]/a[1]")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//*[@id='main']/div[1]/div/div[1]/div/button")).click();
						driver.findElement(By.xpath("//*[@id='td']/div[10]/div/div/div/footer/button[1]")).click();
						Reporter.log("Product removed from the cart");
					}

				}
				else if (addToCartElementExists)
				{
					if ((driver.findElement(By.xpath("//button[contains(text(),'Add To Cart')]")).getText()).equals("Add To Cart"))
					
					{
						System.out.println("Add to cart loop");
						
						driver.findElement(By.xpath("//button[contains(text(),'Add To Cart')]")).click();//Add To Cart
						
						Thread.sleep(3000);
						driver.findElement(By.xpath("//*[@id='watnotif-wrapper']/div/p/div[2]/div[2]/a[1]")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//*[@id='main']/div[1]/div/div[1]/div/button")).click();
						driver.findElement(By.xpath("//*[@id='td']/div[10]/div/div/div/footer/button[1]")).click();
						
					}
				}
				
			
		}		
				
					
		
		@AfterClass
		public void tearDown() {
			driver.quit();
		}	
			
	
		

}
