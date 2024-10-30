package pagesobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//import pageobjects.AccountLogOutPage;

public class AccountSuccessPage extends BasePage{
	
	public AccountSuccessPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(linkText = "Logout")
	private WebElement logoutOption ;
	
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountOption;
	
	@FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[text()='Logout']")
	private WebElement logOutoptionUnderMyAccount;

	@FindBy(how=How.XPATH, using =" //div[@class='alert alert-success alert-dismissible']")
	public WebElement passwordUpdatedSuccessMsg;
	
	@FindBy(xpath="//a[text()='Change your password']")
	private WebElement changePasswordLink;
	
	
	public boolean displayLogoutOptionStatus() {
		clickElement(myAccountOption);
		return isElementVisible(logoutOption);
	}
	
	public void clickOnMyAccountOption() {
		clickElement(myAccountOption);
		
	}
     public AccountLogoutPage SelectlogOutoptionUnderMyAccount() {
    	 clickElement(myAccountOption);
    	 clickElement(logOutoptionUnderMyAccount);
    	return new AccountLogoutPage(driver);
     }
     
     public boolean displaypasswordUpdatedSuccessMsg() {
 		return isElementVisible(passwordUpdatedSuccessMsg);
 	}
     
     public ChangePasswordPage clickOnchangePasswordLink() {
    	 clickElement(changePasswordLink);
  		return new ChangePasswordPage(driver);
	
}
}