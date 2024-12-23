package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OrderPage {
    private final WebDriver webDriver;
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]"); // Форма
    private final By nameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]"); // Поле "Имя"
    private final By surnameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]"); // Поле "Фамилия"
    private final By addressInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]"); // Поле "Адрес"
    private final By metroInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]"); // Поле "Станция метро"
    private final By phoneInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]"); // Поле "Телефон"
    private final By commentInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий')]"); // Поле "Комментарий"
    private final By metroList = By.className("select-search__select"); // Выпадающий список станций метро
    private final By metroListItems = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]"); // Список доступных станций. Order_Text... нашел с помощью комбинации Ctrl+Shift+C
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button"); // Кнопка "Далее"
    private final By dateSelected = By.className("react-datepicker__day--selected"); // Выбранная дата календаря
    private final By dateInput = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input"); // Ввод даты календаря
    private final By termDropdownRoot = By.className("Dropdown-root"); // Выпадающий список аренды
    private final By termDropdownOption = By.className("Dropdown-option"); // Доступные варианты аренды
    private final By colorLabels = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label"); // Список цветов
    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]"); // Кнопка "Заказать"
    private final By acceptOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]"); // Кнопка "Да" во всплывающем окне
    private final By newOrderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]"); // Успешное оформление заказа

    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void waitForLoadForm() {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(this.orderForm)));
    }

    private void waitForElementLoad(By elementToLoad) {
        new WebDriverWait(this.webDriver, 3)
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(elementToLoad)));

    }

    public void setName(String name) {
        this.webDriver.findElement(this.nameInput).sendKeys(name);
    }

    public void setSurname(String surname) {
        this.webDriver.findElement(this.surnameInput).sendKeys(surname);
    }

    public void setAddress(String address) {
        this.webDriver.findElement(this.addressInput).sendKeys(address);
    }

    public void setMetro(String metro) {
        this.webDriver.findElement(this.metroInput).sendKeys(metro);
        this.waitForElementLoad(this.metroList);
        this.chooseElementFromDropdown(this.metroListItems, metro);
    }

    public void setPhone(String phone) {
        this.webDriver.findElement(this.phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        this.webDriver.findElement(this.nextButton).click();
    }

    public void setDate(String date) {
        this.webDriver.findElement(this.dateInput).sendKeys(date);
        this.waitForElementLoad(this.dateSelected);
        this.clickDateSelected();
    }

    public void setTerm(String termToChoose) {
        this.clickTermDropdown();
        this.chooseElementFromDropdown(this.termDropdownOption, termToChoose);
    }

    public void setColor(String colorToChoose) {
        this.chooseElementFromDropdown(this.colorLabels, colorToChoose);
    }

    public void setComment(String comment) {
        this.webDriver.findElement(this.commentInput).sendKeys(comment);
    }

    public void makeOrder() {
        this.clickOrderButton();
        this.waitForElementLoad(this.acceptOrderButton);
        this.clickAcceptOrderButton();
    }

    public String getNewOrderSuccessMessage() {
        return this.webDriver.findElement(this.newOrderSuccessMessage).getText();
    }

    private void clickOrderButton() {
        this.webDriver.findElement(this.orderButton).click();
    }

    private void clickAcceptOrderButton() {
        this.webDriver.findElement(this.acceptOrderButton).click();
    }

    private void chooseElementFromDropdown(By dropdownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = this.webDriver.findElements(dropdownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    private void clickDateSelected() {
        this.webDriver.findElement(this.dateSelected).click();
    }

    private void clickTermDropdown() {
        this.webDriver.findElement(this.termDropdownRoot).click();
    }
}