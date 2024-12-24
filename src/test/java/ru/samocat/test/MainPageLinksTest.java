package ru.samocat.test;

import ru.page.objects.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MainPageLinksTest {
    private WebDriver webDriver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String yandexUrl = "https://ya.ru/";
    private final String scooterUrl = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @After
    public void tearDown() {
        this.webDriver.quit();
    }

    @Test
    public void checkYandexLinkIsCorrectTest() {
        MainPage mainPage = new MainPage(this.webDriver);

        assertTrue(
                "Лого Яндекс не ведет на " + this.yandexUrl,
                mainPage.getYandexLogoLink().contains(this.yandexUrl)
        );

        assertTrue(
                "Лого Яндекс не открывает новую вкладку",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    @Test
    public void checkScooterLinkIsCorrectTest() {
        MainPage mainPage = new MainPage(this.webDriver);

        assertTrue(
                "Самокат не ведет на " + this.scooterUrl,
                mainPage.getScooterLogoLink().contains(this.scooterUrl)
        );
    }
}
