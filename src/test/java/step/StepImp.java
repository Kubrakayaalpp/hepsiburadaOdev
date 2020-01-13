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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class StepImp extends BaseClass {

    public static ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String, String>();

    @Step("<key>  butonuna tikla")
    public void clickButton(String key) {
        findElement(key).click();
    }


    @Step("<saniye> saniye bekle")
    public void wait(int saniye) throws InterruptedException {
        Thread.sleep(saniye * 1000);
    }

    @Step({"<key> alanını bul"})
    public void scrollToElement(String key) {
        scrollToElementToBeVisible(key);
    }

    @Step("Sayfada <key> buton var mı ? Yoksa <msg>")
    public void getButtonControl(String key, String msg){
        Assert.assertTrue(msg,findElement(key).isDisplayed());
    }

    @Step("Sayfada <key> buton var mı ? Varsa <msg>")
    public void getButtonControl2(String key, String msg){

        Assert.assertEquals(msg,"x, sepetin şu an boş.",findElement("Sepet_Bos").getText());
    }

    @Step("Urun tutarini <tutar> ve urun ismini <isim> csv dosyasına yazdirma")
    public void csvWriter(String tutar, String isim) {
        try (PrintWriter csvWriter = new PrintWriter("/Users/testinium/Desktop/CSV_FİLE/urunDetay.csv")) {
            WebElement urunAdiElement = findElement(isim);
            WebElement urunTutarElement = findElement(tutar);
            String urunAdi = urunAdiElement.getText();
            String urunTutar2 = urunTutarElement.getText();
            csvWriter.append("\n");
            csvWriter.append(String.join(";", urunAdi));
            csvWriter.append(String.join(";", urunTutar2));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public WebElement scrollToElementToBeVisible(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement webElement =findElement(key);
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
        return webElement;
    }
    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }
    private void scrollTo(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }
    private JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }


}
