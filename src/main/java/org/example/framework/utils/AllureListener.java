package org.example.framework.utils;

import io.qameta.allure.Attachment;
import org.example.framework.managers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureListener {
    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] addScreenshot() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
