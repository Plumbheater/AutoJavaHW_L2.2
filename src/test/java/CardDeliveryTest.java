import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    String date;
    String month;
    String day;


    @BeforeEach
    void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        date = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
        month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
        day = Integer.toString(calendar.get(Calendar.DATE));

    }

    @Test
    void testForTest1() {

        open("http://localhost:9999/");
        $("span[data-test-id = city] input").setValue("Ка");
        $$("div.popup__content div").find(exactText("Казань")).click();

        $("span[data-test-id = date] button").click();
        while (!$("div.calendar__name").getText().equals(month)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }
        $$("table.calendar__layout td").find(text(day)).click();

        $("span[data-test-id = name] input").setValue("Иванов - Иван");
        $("span[data-test-id = phone] input").setValue("+79038039988");
        $("label[data-test-id = agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(exactText("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }

    @Test
    void testForTest2() {

        open("http://localhost:9999/");
        $("span[data-test-id = city] input").setValue("Казань");
        $("span[data-test-id = date] input").sendKeys(Keys.CONTROL+"A");
        $("span[data-test-id = date] input").sendKeys(Keys.BACK_SPACE);
        $("span[data-test-id = date] input").setValue(date);
        $("span[data-test-id = name] input").setValue("Иванов - Иван");
        $("span[data-test-id = phone] input").setValue("+79038039988");
        $("label[data-test-id = agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(exactText("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }
}
