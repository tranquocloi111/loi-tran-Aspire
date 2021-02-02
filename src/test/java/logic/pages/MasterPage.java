package logic.pages;

import framework.wdm.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class MasterPage extends BasePage {

    private MasterPage() {

    }

    private static MasterPage instance = new MasterPage();

    public static MasterPage getInstance() {
        if (instance != null) {
            return instance;
        }
        return new MasterPage();
    }

    public WebElement getPhoneInput(){
        return getInputByName("phone");
    }

    public WebElement getLoginBtn(){
        return getDriver().findElement(By.xpath("//span[text()='Login']//ancestor::button"));
    }

    public List<WebElement> getAllOTPInput(){
        return getDriver().findElements(By.xpath("//div[contains(@class,'digit-input__input')]"));
    }

    public void registerPhone(String phone){
        waitForSpecificTime(5);
        click(getPhoneInput());
        enterValueByElement(getPhoneInput(),phone);
        click(getLoginBtn());
    }

    public void fillInOTP(){
        waitForSpecificTime(5);
        DriverFactory.getInstance().getDriver().findElement(By.xpath("//input")).sendKeys("12345");
    }

}