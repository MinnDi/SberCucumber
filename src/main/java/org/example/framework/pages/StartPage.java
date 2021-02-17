package org.example.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.example.framework.managers.DriverManager.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StartPage extends BasePage{

    @FindBy(xpath = "//a[@role = 'button' and @class = ' kitt-top-menu__link kitt-top-menu__link_first kitt-top-menu__link_icons kitt-top-menu__link_wide']")
    List<WebElement> menuButtons;

    @FindBy(xpath = "//a[@class='kitt-top-menu__link kitt-top-menu__link_second']")
    List<WebElement> subMenuButtons;

    public StartPage checkStartPageIsOpen(){
        assertThat(getDriver().getTitle(), is("Частным клиентам — СберБанк"));
        closeCookie();
        return this;
    }

    public StartPage selectMenuButton(String menuOption){
        for (WebElement menuButton:
                menuButtons) {
            if (menuButton.getAttribute("aria-label").equals(menuOption)){
                wait.until(ExpectedConditions.elementToBeClickable(menuButton));
                scrollToElementJs(menuButton);
                action.moveToElement(menuButton).click().build().perform();
                wait.until(ExpectedConditions.visibilityOf(menuButton.findElement(By.xpath(".//following-sibling::div[@class='kitt-top-menu__dropdown kitt-top-menu__dropdown_icons']"))));
            }
        }
        return this;
    }

    private StartPage closeCookie(){
        WebElement cookie = getExistingWebElement(By.xpath("//button[@class='kitt-cookie-warning__close']"));
        if (cookie!=null){
            cookie.click();
        }
        return this;
    }

    public MortgagePage selectMortgageForFinishedBuildings(){
        for (WebElement subMenuButton :
                subMenuButtons) {
            if (subMenuButton.getAttribute("data-cga_click_top_menu").contains("Ипотека на готовое жильё")) {
                wait.until(ExpectedConditions.visibilityOf(subMenuButton));
                scrollToElementJs(subMenuButton);
                wait.until(ExpectedConditions.elementToBeClickable(subMenuButton));
                action.moveToElement(subMenuButton).click().build().perform();
                break;
            }
        }
        return app.getMortgagePage().checkIfMortgagePageOpen();
    }

}
