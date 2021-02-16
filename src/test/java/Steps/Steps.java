package Steps;

import io.cucumber.datatable.DataTable;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.framework.managers.PageManager;

public class Steps {
    /**
     * Менеджер страничек
     * @see PageManager#getManagerPages()
     */
    private PageManager app = PageManager.getManagerPages();

    @Когда("^Загружена стартовая страница$")
    public void getInitialPage(){
        app.getStartPage();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        app.getStartPage().checkStartPageOpen();
    }

    @Когда("^Переход в главное меню '(.*)'$")
    public void selectNameBaseMenu(String nameBaseMenu){
        app.getStartPage().selectMenuButton(nameBaseMenu);
    }

    @Когда("^Выбираем подменю '(.*)'$")
    public void selectNameSubMenu(String nameSubMenu){
        app.getStartPage().selectMortgageForFinishedBuildings(nameSubMenu);
    }

    @Тогда("^Проверка открытия страницы ипотеки$")
    public void checkMortgagePageTitle() {
        app.getMortgagePage().checkIfMortgagePageOpen();
    }


    @Когда("^Заполняем форму поле/значение$")
    public void fillFields(DataTable dataTable){
        dataTable.cells().forEach(
                raw -> {
                    app.getMortgagePage().fillField(raw.get(0), raw.get(1));
                }
        );
    }

    @Когда("^Отмечаем свичи у поля$")
    public void checkoutSwitches(DataTable dataTable){
        dataTable.cells().forEach(
                raw -> {
                    app.getMortgagePage().checkoutSwitch(raw.get(0));
                }
        );
    }

    @Тогда("^Проверяем, что у полей верные значения$")
    public void checkResultsCorrect(DataTable dataTable){
        dataTable.cells().forEach(
                raw  -> {
                    app.getMortgagePage().checkResults(raw.get(0), raw.get(1));
                }
        );
    }
}