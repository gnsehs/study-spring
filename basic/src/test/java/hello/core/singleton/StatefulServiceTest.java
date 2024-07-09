package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자가 10000원 주문
        int userA_price = statefulService1.order("userA", 10000);
        // ThreadB: B사용자가 20000원 주문
        int userB_price = statefulService2.order("userB", 20000);

        // ThreadA: 사용자 A가 주문 금액을 조회
//        int price = statefulService1.getPrice();

        System.out.println("userA_price = " + userA_price);
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        Assertions.assertThat(userA_price).isEqualTo(10000);
    }


    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}