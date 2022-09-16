package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {
    public DataGenerator data = new DataGenerator();

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $$("[type=\"text\"]").first().setValue(data.generateCity("ru"));
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "A");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(data.generateDate(daysToAddForFirstMeeting));
        $$("[type=\"text\"]").last().setValue(data.generateName("ru"));
        $x("//input[@name=\"phone\"]").setValue(data.generatePhone("ru"));
        $(".checkbox").click();
        $("button.button").click();
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "A");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(data.generateDate(daysToAddForSecondMeeting));
        $("button.button").click();
        $$("button.button").last().click();
        $("[data-test-id='success-notification'] .notification__content").should(text("Встреча успешно запланирована на " + data.generateDate(daysToAddForSecondMeeting)));
    }
}
