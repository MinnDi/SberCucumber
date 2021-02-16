package org.example.framework.managers;

import java.util.concurrent.TimeUnit;

import static org.example.framework.managers.DriverManager.getDriver;
import static org.example.framework.managers.DriverManager.quitDriver;
import static org.example.framework.utils.PropConst.*;

/**
 * Класс для инициализации фреймворка
 */
public class InitManager {

    /**
     * Менеджер пропертей
     * @see TestPropManager#getTestPropManager()
     */
    public static TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Инициализация фреймворка и запуск браузера со страницей приложения
     * @see DriverManager#getDriver()
     * @see TestPropManager#getProperty(String)
     * @see org.example.framework.utils.PropConst
     */
    public static void initFramework() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        getDriver().get(props.getProperty(APP_URL));
    }

    /**
     * Завершения работы фреймворка - гасит драйвер и закрывает сессию с браузером
     * @see DriverManager#quitDriver()
     */
    public static void quitFramework() {
        quitDriver();
    }
}
