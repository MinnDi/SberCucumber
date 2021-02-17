package org.example.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.example.framework.managers.DriverManager.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MortgagePage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'dc-input__input-container')]")
    List<WebElement> inputFields;

    public MortgagePage checkIfMortgagePageOpen() {
        assertThat(getDriver().getTitle(), is("Ипотека на готовое жилье — СберБанк"));
        return this;
    }

    public MortgagePage fillField(String field, String value) {
        scrollToElementJs(getDriver().findElement(By.xpath("//h2[contains(. ,'Рассчитайте ипотеку')]")));
        getDriver().switchTo().frame("iFrameResizer0");
        String fieldHeaderXPath = "./div";
        String inputValueXPath = "./input";
        for (WebElement inputField :
                inputFields) {
            if (inputField.findElement(By.xpath(fieldHeaderXPath)).getAttribute("textContent").contains(field)) {
                inputField = inputField.findElement(By.xpath(inputValueXPath));
                fillInputField(inputField, value);
                assertThat(inputField.getAttribute("value").replaceAll("\\D", ""), is(value.replaceAll("\\D", "")));
            }
        }
        getDriver().switchTo().defaultContent();
        return this;
    }

    public MortgagePage checkoutSwitch(String switchName) {
        getDriver().switchTo().frame("iFrameResizer0");
        String switchXPath = "//div[@data-test-id='controls']//span[contains(.,'%s')]";
        WebElement checkoutSwitch = getDriver().findElement(By.xpath(String.format(switchXPath, switchName)));
        checkoutSwitch = checkoutSwitch.findElement(By.xpath("./../..//input"));
        boolean switchCheckedBefore = checkoutSwitch.getAttribute("aria-checked").equals("true");
        scrollToElementJs(getDriver().findElement(By.xpath("//span[contains(.,'Получить одобрение')]")));
        checkoutSwitch.click();
        wait.until(ExpectedConditions.attributeToBe(checkoutSwitch, "aria-checked", Boolean.toString(!switchCheckedBefore)));
        getDriver().switchTo().defaultContent();
        return this;
    }

    public MortgagePage checkResults(String field, String value) {
        getDriver().switchTo().frame("iFrameResizer0");
        String resultXPath = "//div[@data-test-id='main-results-block']//li/span[contains(.,'%s')]";
        WebElement resultFiled = getDriver().findElement(By.xpath(String.format(resultXPath, field)));
        String fieldName = resultFiled.getAttribute("textContent");
        resultFiled = resultFiled.findElement(By.xpath("./../span/span"));
        if (fieldName.contains("Процентная ставка")) assertThat(resultFiled.getAttribute("textContent").replaceAll("[^,.0-9]+", ""), is(value.replaceAll("[^,.0-9]+", "")));
        else assertThat("Сумма поля " + field + " не соответствует введенному!", resultFiled.getAttribute("textContent").replaceAll("\\D", ""), is(value.replaceAll("\\D", "")));
        getDriver().switchTo().defaultContent();
        return this;
    }
}

