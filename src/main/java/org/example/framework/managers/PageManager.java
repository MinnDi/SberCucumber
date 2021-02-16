package org.example.framework.managers;

import org.example.framework.pages.MortgagePage;
import org.example.framework.pages.StartPage;

/**
 * @author Arkadiy_Alaverdyan
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager managerPages;

    /**
     * Стартовая страничка
     */
    StartPage startPage;

    /**
     * Страничка ипотека на готовое жилье
     */
    MortgagePage mortgagePage;

    /**
     * Конструктор специально запривейтили (синглтон)
     * @see PageManager#getManagerPages()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация ManagerPages
     *
     * @return ManagerPages
     */
    public static PageManager getManagerPages() {
        if (managerPages == null) {
            managerPages = new PageManager();
        }
        return managerPages;
    }

    /**
     * Ленивая инициализация {@link org.example.framework.pages.StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    /**
     * Ленивая инициализация страницы ипотеки на готовое жилье{@link org.example.framework.pages.MortgagePage}
     *
     * @return MortgagePage
     */
    public MortgagePage getMortgagePage() {
        if (mortgagePage == null) {
            mortgagePage = new MortgagePage();
        }
        return mortgagePage;
    }
}