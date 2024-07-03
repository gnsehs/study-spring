package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 의존관계 주입
@Configuration // 구성정보, 설정정보
public class AppConfig { // 필요한 구현객체를 생성 // 공연 기획자
    @Bean // 스프링 컨테이너에 등록
    public MemberService memberService() {
        return new MemberServiceImpl(getMemberRepository());
    }
    @Bean
    public static MemberRepository getMemberRepository() { // 이 부분 중복이었다. 이제는 리펙토링하여 한 부분만 변경가능
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }
    @Bean
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy(); // 할인 정책을 변경하려면 이 부분만 변경 하면 된다.
    }


    /*
     * 구성 영역에 있는 코드만 변경 하면 됨.
     */

}
