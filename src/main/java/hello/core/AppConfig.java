package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 의존관계 주입

// call AppConfig.memberService
//call AppConfig.getMemberRepository <- 이론상 3번 호출되어야되는데 한번만 호출된다.
//call AppConfig.orderService

@Configuration // 구성정보, 설정정보 -> 빼면 싱글톤을 보장하지 않는다. -> component
public class AppConfig { // 필요한 구현객체를 생성 // 공연 기획자
//    @Autowired MemberRepository memberRepository; -> 의존관계 자동주입

    @Bean // 스프링 컨테이너에 등록
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean
    public MemberRepository getMemberRepository() { // 이 부분 중복이었다. 이제는 리펙토링하여 한 부분만 변경가능
        System.out.println("call AppConfig.getMemberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
        }
    @Bean
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy(); // 할인 정책을 변경하려면 이 부분만 변경 하면 된다.
    }


    /*
     * 구성 영역에 있는 코드만 변경 하면 됨.
     */

    // 싱글톤 관점에서 getMemberRepository()가 두 번 호출되므로 싱글톤 깨지는 것이 아닐까?

}
