package hello.itemservice.vaildation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    /**
     * messageCode = required.item
     * messageCode = required
     */
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");


        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    /**
     * fieldError의 codes에 스트링 배열로 들어감
     * messageCode = required.item.itemName
     * messageCode = required.itemName
     * messageCode = required.java.lang.String -> type
     * messageCode = required
     */
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );


    }
}
