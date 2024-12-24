package ru.samocat.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.page.objects.MainPage;
import ru.page.objects.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPageTests {
    private WebDriver webDriver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String name, surname, address, metro, phone, date, term, color, comment;
    private final String expectedOrderSuccessText = "Заказ оформлен";

    public OrderPageTests(
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String term,
            String color,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Оформление заказа. Позитивный сценарий. Пользователь: {0} {1}")
    public static Object[][] setDataForOrder() {
        return new Object[][]{
                {"Никита", "Абрамов", "Москва, Мажоров переулок, д. 14А", "Семёновская", "89653248965", "23.12.2024", "пятеро суток", "чёрный жемчуг", "Мой комментарий"},
                {"Алла ", "Матросова", "Москва, ул. Маргелова, д. 3к2", "Беговая", "89425638782", "25.12.2024", "трое суток", "серая безысходность", "Побыстрее"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.get(mainPageUrl);
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }

    @Test
    public void orderWithHeaderButtonWhenSuccessTest() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonHeader();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Проблема с созданием заказа",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    @Test
    public void orderWithBodyButtonWhenSuccessTest() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonBody();
        makeOrder(orderPage);

        MatcherAssert.assertThat(
                "Проблема с созданием заказа",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    private void makeOrder(OrderPage orderPage) {
        orderPage.waitForLoadForm();

        orderPage.setName(this.name);
        orderPage.setSurname(this.surname);
        orderPage.setAddress(this.address);
        orderPage.setMetro(this.metro);
        orderPage.setPhone(this.phone);

        orderPage.clickNextButton();

        orderPage.setDate(this.date);
        orderPage.setTerm(this.term);
        orderPage.setColor(this.color);
        orderPage.setComment(this.comment);

        orderPage.makeOrder();
    }
}
