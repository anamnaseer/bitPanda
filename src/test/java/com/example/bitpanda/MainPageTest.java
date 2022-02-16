package com.example.bitpanda;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class MainPageTest
{
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeEach
    public void setUp()
    {
        final String rootDir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver",
            rootDir + "\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/index.php?controller=my-account");

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    /** Checking valid login by passing valid email id and password
     * check - we are asserting after successful login we see welcome message
     */
    public void login()
    {
        loginPage.loginButton.click();
        loginPage.emailTextBox.sendKeys("anam@gmail.com");
        loginPage.pwdTextBox.sendKeys("12345");
        loginPage.signInButton.click();
        WebElement welcomeNote = driver.findElement(By.className("info-account"));
        Assert.assertEquals("Welcome to your account. Here you can manage all of your personal information and orders.", welcomeNote.getText());

    }

    @Test
    /** Checking search functionality by providing different search strings
     * case1: We are passing full string that matches - and we assert that it shows search result
     * case2: We are passing partial search string - and we assert that it shows search result
     * case3: We are passing invalid string, by passing some special character- we are asserting that it shows no results
     */
    public void search()
    {
        String searchCountText;

        //Search Criteria case 1: sending full match string
        mainPage.searchField.sendKeys("t-shirts");
        mainPage.searchButton.click();
        searchCountText = mainPage.productCount.getText();
        Assert.assertTrue(searchCountText.contains("Showing 1"));
        mainPage.searchField.clear();

        //Search Criteria case 2: sending half match string
        mainPage.searchField.sendKeys("cas");
        mainPage.searchButton.click();
        searchCountText = mainPage.productCount.getText();
        Assert.assertTrue(searchCountText.contains("Showing 1"));
        mainPage.searchField.clear();

        //Search Criteria case 3: sending invalid string
        mainPage.searchField.sendKeys("1234");
        mainPage.searchButton.click();
        searchCountText = mainPage.warningText.getText();
        Assert.assertTrue(searchCountText.contains("No results were found"));

    }

    @Test
    /** This test checks we are able to add items in cart and the counter in shopping cart increments with each added item
     * We are looping against the number of items that we have and adding alternate items in cart
     * After adding each item we assert the counter on cart
     */
    public void addToCart() throws InterruptedException
    {
        login();
        int j = 0;
        mainPage.womenMenuButton.click();
        List<WebElement> buttons = driver.findElements(By.xpath("//ul[@class='product_list grid row']//li[contains(@class,'ajax')]"));
        for (int i = 1; i < buttons.size(); )
       {
            Thread.sleep(2000);

            WebElement ele = buttons.get(i-1);
            Actions action = new Actions(driver);

            //Performing the mouse hover action on the target element.
            action.moveToElement(ele).perform();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@itemprop='offers']/following::*[@class='button-container']//*[@data-id-product='"+i+"']")).click();
            mainPage.continueButton.click();
            Thread.sleep(5000);

            j = j + 1;
            i = i + 2;
        }
        driver.findElement(By.xpath("//*[@class='shopping_cart']//a[@title=\"View my shopping cart\"]")).click();
        Thread.sleep(5000);
        Assert.assertEquals(j+" Products", driver.findElement(By.xpath("//*[@id=\"summary_products_quantity\"]")).getText());

    }
}
