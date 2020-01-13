package step;

import WebAutomationBase.helper.ElementHelper;
import WebAutomationBase.helper.StoreHelper;
import WebAutomationBase.model.ElementInfo;
import base.BaseClass;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SepetIslemleri extends BaseClass {


    @Step("<key> değeri <text> ile aynı mı?")
    public void assertEquals(String key,String text) {
        int keyAsInt=Integer.parseInt(findElement(key).getAttribute("value"));
        System.out.println(keyAsInt);
        int textAsInt=Integer.parseInt(findElement(text).getText());
        System.out.println(textAsInt);
        Assert.assertEquals("Ürün adedi ile sepetteki tutar eşit değil."
                ,textAsInt,keyAsInt);
    }



    private WebElement findElement(String key){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }
}
