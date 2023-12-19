package com.testscript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.List;


public class WebstaurantStoreTest {

    public WebDriver driver;


    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        //   options.addArguments("--headless");
        //  options.setExperimentalOption("excludeSwitches", "enable-automation");
        options.setExperimentalOption("useAutomationExtension", false);
        //  driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void webstaurantStoreTest() {
        // 1. Go to https://www.webstaurantstore.com/

        driver.get("https://www.webstaurantstore.com/");
        waitForSeconds(5);


        // 2. Search for "stainless work table"
        WebElement searchBox = driver.findElement(By.xpath("(//input[@id='searchval'])[1]"));
        searchBox.click();
        searchBox.sendKeys("stainless work table");
        searchBox.submit();


        // 3. Check the search result ensuring every product has the word 'Table' in its title.
        List<WebElement> productTitles = driver.findElements(By.cssSelector(".description"));
        for (WebElement title : productTitles) {
            Assert.assertTrue(title.getText().toLowerCase().contains("table"),
                    "Product title does not contain the word 'Table'");
        }

        waitForSeconds(5);

        WebElement lastPageButton = driver.findElement(By.xpath("(//*[@id=\"paging\"]/nav/ul/li)[last()-1]"));
        lastPageButton.click();
        waitForSeconds(5);
        // 4. Add the last of found items to Cart.
        WebElement lastItem = driver.findElement(By.xpath("(//*[@data-testid=\"itemDescription\"])[last()]"));
        lastItem.click();
        waitForSeconds(5);

        WebElement lastAddToCart = driver.findElement(By.cssSelector("input#buyButton"));
        lastAddToCart.click();
        waitForSeconds(5);

         WebElement viewCartButton = driver.findElement(By.xpath("//*[@id=\"subject\"]/div[2]/div/a[1]"));
         viewCartButton.click();
         waitForSeconds(10);
        // 5. Empty Cart.
        WebElement emptyCartButton = driver.findElement(By.cssSelector("button.emptyCartButton"));
        emptyCartButton.click();
        WebElement confirmEmptyCartButton = driver.findElement(By.xpath("(//*[text()='Empty'])[2]"));
        confirmEmptyCartButton.click();
        waitForSeconds(10);
        // Confirm that the cart is empty
        WebElement emptyCartMessage = driver.findElement(By.cssSelector(".cartEmpty"));
        Assert.assertTrue(emptyCartMessage.isDisplayed(), "Cart is not empty");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}