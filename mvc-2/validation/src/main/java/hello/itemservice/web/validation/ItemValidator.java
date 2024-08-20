package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.isAssignableFrom(aClass);
        // item == aClass
        // item == subItem -> 자식 클래스도 통과 !
    }

    @Override
    public void validate(Object o, Errors errors) {
        Item item = (Item) o;


        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName","required");
        }
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors,"itemName","required"); -> 대체가능


        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price","range",new Object[]{1000,1000000},null); // reject: object, rejectValue: field
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }

        // 특정한 필드가 하닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();

            if (resultPrice < 10000) {
                errors.reject("totalPriceMin",new Object[]{10000, resultPrice},null);
            }
        }




    }
}
