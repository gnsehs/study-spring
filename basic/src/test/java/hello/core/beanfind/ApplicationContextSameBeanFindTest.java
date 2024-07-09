package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시에 같은 타입이 둘 이상 있으면 중복 오류")
    void findBeanByTypeDuplicate() {
//        MemberRepository bean = ac.getBean(MemberRepository.class);

        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,() -> ac.getBean(MemberRepository.class));
    }
    @Test
    @DisplayName("타입으로 조회시에 같은 타입이 둘 이상 있으면, 빈 이름을 지정")
    void findBeanByName() {
        MemberRepository bean = ac.getBean("memberRepository1",MemberRepository.class);

        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(MemberRepository.class);

    }

    @Test
    @DisplayName("타입으로 조회시에 같은 타입이 둘 이상 있으면, 빈 이름을 지정")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }

        System.out.println("beansOfType = " + beansOfType);

        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);

    }
        @Configuration
    static class SameBeanConfig { // 내부 정적 클래스
        @Bean
        MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }

    }
}
