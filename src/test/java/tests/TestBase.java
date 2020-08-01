package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class TestBase {

    final static String
            firstEmail = "qaguru1@yandex.ru",
            firstPassword = "qaguru135",
            secondEmail = "qaguru2@yandex.ru",
            secondPassword = "qaguru235";

    @BeforeAll
    public static void setUp() {
        Configuration.startMaximized = true;
        Configuration.timeout = 20000;

//        Configuration.headless = true;
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterEach
    public void closeBrowser(){
        closeWebDriver();
    }
}
