package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("rate")
@MainDiscountPolicy //
// @Qualifier("mainDiscountPolicy") -> String으로 오타가 나도 동작하므로 컴파일 타임에 오류를 잡기 힘들다.
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}

//  return price * (discountPercent / 100); -> 몫을 먼저 구해서 0을 곱해버림
