package com.example.bitpanda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage
{
    @FindBy(css = "[class='login']")
    public WebElement loginButton;

    @FindBy(css = "[name='email']")
    public WebElement emailTextBox;

    @FindBy(css = "[name='passwd']")
    public WebElement pwdTextBox;

    @FindBy(css = "[name='SubmitLogin']")
    public WebElement signInButton;


    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }
}
