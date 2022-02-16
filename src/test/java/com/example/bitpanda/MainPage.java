package com.example.bitpanda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage
{
//    @FindBy(xpath = "//div[contains(@class, 'menu-main__item') and text() = 'Developer Tools']")
//    public WebElement toolsMenu;

    @FindBy(css = "[name='submit_search']")
    public WebElement searchButton;

    @FindBy(css = "[id='search_query_top']")
    public WebElement searchField;

    @FindBy(css = "[class='product-count']")
    public WebElement productCount;

    @FindBy(css = "li a[title='T-shirts']")
    public WebElement tshirtButton;

    @FindBy(css = "[class='alert alert-warning']")
    public WebElement warningText;

    @FindBy(xpath = "//a[contains(@class, 'sf-with-ul') and text() = 'Women']")
    public WebElement womenMenuButton;

    @FindBy(css = "[ class='continue btn btn-default button exclusive-medium']")
    public WebElement continueButton;


    public MainPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }
}
