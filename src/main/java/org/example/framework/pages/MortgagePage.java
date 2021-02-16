package org.example.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.example.framework.managers.DriverManager.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MortgagePage extends BasePage {

    @FindBy(xpath = "//div[@class='dc-input__input-container-4-9-1']")
    List<WebElement> inputFields;

    public MortgagePage checkIfMortgagePageOpen() {
        wait.until(ExpectedConditions.urlContains("/credits/home/buying_complete_house"));
        assertThat(getDriver().getTitle(), is("Ипотека на готовое жилье — СберБанк"));
        //assertThat(inputFields.get(0).findElement(By.xpath(".//input")).getAttribute("value"), is("Готовое жилье"));
        return this;
    }

    public MortgagePage fillField(String field, String value) {
        String fieldHeaderXPath = "./div";
        String inputValueXPath = "./input";
        for (WebElement inputField :
                inputFields) {
            if (inputField.findElement(By.xpath(fieldHeaderXPath)).getAttribute("textContent").contains(field)) {
                inputField = inputField.findElement(By.xpath(inputValueXPath));
                scrollToElementJs(inputField);
                fillInputField(inputField, Keys.chord(Keys.CONTROL, "a")+ Keys.DELETE + value);
                assertThat(inputField.getAttribute("value").replaceAll("\\D", ""), is(value.replaceAll("\\D", "")));
            }
        }
        return this;
    }

    public MortgagePage checkoutSwitch(String switchName) {
        String switchXPath = "//div[@data-test-id='controls']//span[contains(.,'%s')]";
        WebElement checkoutSwitch = getDriver().findElement(By.xpath(String.format(switchXPath, switchName)));
        checkoutSwitch = checkoutSwitch.findElement(By.xpath("./../..//input"));
        boolean switchCheckedBefore = checkoutSwitch.getAttribute("aria-checked").equals("true");
        scrollToElementJs(checkoutSwitch);
        wait.until(ExpectedConditions.elementToBeClickable(checkoutSwitch));
        checkoutSwitch.click();
        wait.until(ExpectedConditions.attributeToBe(checkoutSwitch, "aria-checked", Boolean.toString(!switchCheckedBefore)));
        return this;
    }

    public MortgagePage checkResults(String field, String value) {
        String resultXPath = "//div[@data-test-id='main-results-block']//li/span[contains(.,'%s')]";
        WebElement resultFiled = getDriver().findElement(By.xpath(String.format(resultXPath, field)));
        String fieldName = resultFiled.getAttribute("textContent");
        resultFiled = resultFiled.findElement(By.xpath("./../span/span"));
        if (fieldName.contains("Процентная ставка")) assertThat(resultFiled.getAttribute("textContent").replaceAll("[^,.0-9]+", ""), is(value.replaceAll("[^,.0-9]+", "")));
        else assertThat("Сумма поля " + field + " не соответствует введенному!", resultFiled.getAttribute("textContent").replaceAll("\\D", ""), is(value.replaceAll("\\D", "")));
        return this;
    }
}
