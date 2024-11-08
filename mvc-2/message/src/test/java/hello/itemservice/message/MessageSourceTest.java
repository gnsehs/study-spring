package hello.itemservice.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    /**
     * wsl에서 default locale을 한글로 바꾸기 위함
     */
    @BeforeAll
    static void setDefaultLocale() {
        Locale.setDefault(Locale.KOREA);
    }

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    /**
     * NoSuchMessageException
     */
    @Test
    void noFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void noFoundMessageCodeDefaultMessage() {
        String defaultMsg = "기본 메세지";
        String message = ms.getMessage("no_code", null, defaultMsg, null);
        assertThat(message).isEqualTo(defaultMsg);
    }

    @Test
    void argumentMessage() {
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);

        assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
