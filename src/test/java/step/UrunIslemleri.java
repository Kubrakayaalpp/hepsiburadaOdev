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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class UrunIslemleri extends BaseClass {

    public static ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String, String>();

    @Step("<key> <key2> in degerini <mapKey2> tut")
    public void ikinciDegerTut(String key, String key2, String mapKey2){

        WebElement webElement =findElement(key);
        String value = webElement.getText();

        WebElement webElement1 = findElement(key2);
        String value2 = webElement1.getText();

        String value3= value+","+value2+" TL";
        map.put(mapKey2,value3);
        System.out.println("İkinci değer => " + value3);

    }

    @Step("<key> alanından Rastgele bir nesne seç ve kaydet <mapKey1>")
    public void randomListSecimi(String key, String mapKey1) throws InterruptedException{
        List<WebElement> allProducts = new ArrayList<>();
        allProducts.add(findElement(key));
        Random rand = new Random();
        int randomProduct = rand.nextInt(allProducts.size());
        WebElement webElement = allProducts.get(randomProduct);
        String value = webElement.getText();
        map.put(mapKey1,value);
        System.out.println("İlk değer => " + value);
        webElement.click();
    }

    @Step("<mapKey1> ile <mapKey2> urun fiyatları aynı mı?")
    public void UrunFiyatKontrolu(String mapKey1, String mapKey2){
        String expectedValue = map.get(mapKey1);
        String actualValue = map.get(mapKey2);
        System.out.println("Expected Value => " + expectedValue);
        System.out.println("Actual Value => " + actualValue);

        Assert.assertTrue("!!.",expectedValue.contains(actualValue));
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
