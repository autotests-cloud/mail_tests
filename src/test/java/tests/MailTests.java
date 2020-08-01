package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import utils.EmailUtils;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utils.RandomUtils.*;


public class MailTests extends TestBase {

    // Отправляем почту (UI)
    @Test
    void sendMailTest() {
        open("https://mail.yandex.ru/");
        $(byText("Войти")).parent().click();

        $(byName("login")).val("qaguru1").pressEnter();
        $(byName("passwd")).val("qaguru135").pressEnter();

//        $(".user-account__name").shouldHave(text("qaguru1"));

        $(".mail-ComposeButton").click();
//        $(".ComposePopup").shouldBe(visible); for new version of compose popup
        $(".mail-Layout-Panes_compose").shouldBe(visible);
//        $(".svgicon-mail--Compose-Beta-Off").parent().parent().click();
        $(byName("to")).click();
        $(byName("to")).val("qaguru1@yandex.ru");

        $(".cke_editable").val("HI");
        $(".js-send").click();

        $(".mail-Done-Title").shouldHave(text("Письмо отправлено."));
    }

    // Отправляем (UI) и получаем почту (UI)
    @Test
    void sendUIAndReceiveUIMailTest() {
        String randomMessage = getRandomMessage(30, 60);

        open("https://mail.yandex.ru/");
        $(byText("Войти")).parent().click();

        $(byName("login")).val("qaguru1").pressEnter();
        $(byName("passwd")).val("qaguru135").pressEnter();

        $(".mail-ComposeButton").click();
        $(".mail-Layout-Panes_compose").shouldBe(visible);
        $(byName("to")).click();
        $(byName("to")).val("qaguru2@yandex.ru");

        $(".cke_editable").val(randomMessage);
        $(".js-send").click();

        $(".mail-Done-Title").shouldHave(text("Письмо отправлено."));

        closeWebDriver();

        open("https://mail.yandex.ru/");
        $(byText("Войти")).parent().click();

        $(byName("login")).val("qaguru2").pressEnter();
        $(byName("passwd")).val("qaguru235").pressEnter();

        $(".mail-MessagesList").shouldHave(text(randomMessage));
    }


    // Отправляем (API) и получаем почту (UI)
    @Test
    void sendApiAndReceiveUIMailTest() {
        String randomMessage = getRandomMessage(30, 60);

        new EmailUtils().sendMail(firstEmail, firstPassword, secondEmail, "", randomMessage);

        open("https://mail.yandex.ru/");
        $(byText("Войти")).parent().click();

        $(byName("login")).val(secondEmail).pressEnter();
        $(byName("passwd")).val(secondPassword).pressEnter();

        $(".mail-MessagesList").shouldHave(text(randomMessage));
    }


    // Отправляем (UI) и получаем почту (API)
    @Test
    void sendUIReceiveApiMailTest() {
        String randomMessage = getRandomMessage(30, 60);

        open("https://mail.yandex.ru/");
        $(byText("Войти")).parent().click();

        $(byName("login")).val("qaguru1").pressEnter();
        $(byName("passwd")).val("qaguru135").pressEnter();

//        $(".user-account__name").shouldHave(text("qaguru1"));

        $(".mail-ComposeButton").click();
//        $(".ComposePopup").shouldBe(visible); for new version of compose popup
        $(".mail-Layout-Panes_compose").shouldBe(visible);
//        $(".svgicon-mail--Compose-Beta-Off").parent().parent().click();
        $(byName("to")).click();
        $(byName("to")).val(secondEmail);

        $(".cke_editable").val(randomMessage);
        $(".js-send").click();

        $(".mail-Done-Title").shouldHave(text("Письмо отправлено."));

        new EmailUtils().verifyMailHasMessage("qaguru2", "qaguru235", randomMessage);
    }

    // Отправляем (API) и получаем почту (API)
    @Test
    void sendApiAndReceiveApiMailTest() {
        String randomMessage = getRandomMessage(30, 60);

        new EmailUtils().sendMail(firstEmail, firstPassword, secondEmail, "", randomMessage);

        new EmailUtils().verifyMailHasMessage("qaguru2", "qaguru235", randomMessage);

    }
}