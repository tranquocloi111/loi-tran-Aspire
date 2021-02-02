package logic.pages;


import framework.wdm.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class BasePage {

    public BasePage() {
        PageFactory.initElements(DriverFactory.getInstance().getDriver(), this);
    }

    //region Useful actions
    public boolean switchWindow(String title, boolean isParent) {
        if (!isParent) {
            waitForPageLoadComplete(3);
            try {
                Set<String> availableWindows = getDriver().getWindowHandles();
                if (!availableWindows.isEmpty()) {
                    for (String windowId : availableWindows) {
                        if (getDriver().switchTo().window(windowId).getTitle().equals(title)) {
                            getDriver().manage().window().maximize();
                            Thread.sleep(2000);
                            return true;
                        }
                    }
                }
            } catch (Throwable ex) {
                getDriver().switchTo().window(title);

            }
        } else {
            try {
                Set<String> availableWindows = getDriver().getWindowHandles();
                if (!availableWindows.isEmpty()) {
                    for (String windowId : availableWindows) {
                        if (getDriver().switchTo().window(windowId).getTitle().equals(title)) {
                            getDriver().manage().window().maximize();
                            Thread.sleep(2000);
                            return true;
                        }
                    }
                }
            } catch (Throwable ex) {
            }
        }
        return false;
    }

    protected void enterValueByElement(WebElement element, String val) {
        element.clear();
        element.sendKeys(val);
        waitForPageLoadComplete(5);
    }

    protected void clickElementByJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    protected void enterValueByJs(WebElement element, String val) {
        setClearInputValue(element);
        ((JavascriptExecutor) getDriver()).executeAsyncScript("arguments[0].value='" + val + "'", element);
    }

    protected void setClearInputValue(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeAsyncScript("arguments[0].value=''", element);
    }

    protected WebElement getCell(WebElement tbl, int row, int col) {
        return tbl.findElement(By.xpath("//tr[" + row + "]/td[" + col + "]"));
    }


    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public void click(WebElement element) {
        boolean staleElement = true;
        int i = 0;
        while (staleElement && i < 10000) {
            try {
                element.click();
                staleElement = false;

            } catch (StaleElementReferenceException e) {
                staleElement = true;
                i++;
            }
        }
        waitForPageLoadComplete(100);
    }

    public void submit(WebElement element) {
        element.submit();
        waitForPageLoadComplete(100);
    }

    protected void selectByVisibleText(WebElement element, String value) {
        Select drpCountry = new Select(element);
        drpCountry.selectByVisibleText(value);
    }

//    protected void waitForPageLoadComplete(int specifiedTimeout) {
//        Wait<WebDriver> wait = new WebDriverWait(getDriver(), specifiedTimeout);
//        wait.until(driver -> String
//                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
//                .equals("complete"));
//    }

    public void waitForPageLoadComplete(int specifiedTimeout) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), specifiedTimeout);
        wait.until(pageLoadCondition);
    }


    public void waitForElementDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementClickAble(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 100);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 100);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForSpecificTime(int time)  {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected WebDriver getDriver() {
        return DriverFactory.getInstance().getDriver();
    }

    public String getTextOfElement(WebElement element) {
        return element.getText();
    }

    public void hover(WebElement element) {
        Actions action = new Actions(getDriver());
        scrollToElement(element);
        action.moveToElement(element).build().perform();
    }

    protected WebElement getSpanByText(String text) {
        String xpath = String.format("//span[contains(text(),'%s')]", text);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputByType(String type) {
        String xpath = String.format("//input[@type='%s']", type);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputByValue(String type) {
        String xpath = String.format("//input[@value='%s']", type);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputByLabel(String label) {
        String xpath = String.format("//label[normalize-space(text())='%s']//following-sibling::input[1]", label);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputById(String id) {
        String xpath = String.format("//input[@id='%s']//following-sibling::input[1]", id);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputByInputId(String id) {
        String xpath = String.format("//input[@id='%s']", id);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputBySpan(String text) {
        String xpath = String.format("//span[normalize-space(text())='%s']//following-sibling::input", text);
        return getDriver().findElement(By.xpath(xpath));
    }


    protected WebElement getInputByPlaceHolder(String placeHolder) {
        String xpath = String.format("//input[@placeholder='%s']", placeHolder);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getInputByName(String name) {

        String xpath = String.format("//input[@name='%s']", name);
        return getDriver().findElement(By.xpath(xpath));
    }


    protected WebElement getSelectById(String id) {
        String xpath = String.format("//select[@id='%s']", id);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getButtonByText(String text) {
        String xpath = String.format("//button[normalize-space(text())='%s']", text);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getButtonByTextAndIndex(String text, int index) {
        String xpath = String.format("//button[normalize-space(text())='%s']", text);
        return getDriver().findElements(By.xpath(xpath)).get(index);
    }


    protected WebElement getButtonById(String text) {
        String xpath = String.format("//button[normalize-space(@id)='%s']", text);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getLinkByText(String text) {
        String xpath = String.format("//a[normalize-space(text())='%s']", text);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getLinkByClass(String className) {
        String xpath = String.format("//a[@class='%s']", className);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getLinkByHref(String href) {
        String xpath = String.format("//a[contains(@href,'%s')]", href);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement getLoadingSymbol() {
        return getDriver().findElement(By.id("ajax_loading_box"));
    }

    protected List<WebElement> getAllLinksByClass(String className) {
        String xpath = String.format("//a[@class='%s']", className);
        return getDriver().findElements(By.xpath(xpath));
    }

    protected WebElement getDivByClass(String className, Boolean allMatch) {
        String xpath;
        if (allMatch) {
            xpath = String.format("//div[@class='%s']", className);
        } else {
            xpath = String.format("//div[contains(@class,'%s')]", className);
        }
        return getDriver().findElement(By.xpath(xpath));

    }

    protected WebElement getDivById(String id, Boolean allMatch) {
        String xpath;
        if (allMatch) {
            xpath = String.format("//div[@id='%s']", id);
        } else {
            xpath = String.format("//div[contains(@id,'%s')]", id);
        }
        return getDriver().findElement(By.xpath(xpath));

    }

    protected WebElement getAByAncestorBySpan(String text) {
        String xpath = String.format("//span[normalize-space(text())='%s']//ancestor::a]", text);
        return getDriver().findElement(By.xpath(xpath));
    }

    protected List<WebElement> getDivsByClass(String className) {
        String xpath = String.format("//div[contains(@class,'%s')]", className);
        return getDriver().findElements(By.xpath(xpath));
    }

    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    protected void goBackPreviousPage() {
        getDriver().navigate().back();
    }
    //endregion
}

