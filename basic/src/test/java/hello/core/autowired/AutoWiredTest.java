package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void autoWiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class); // TestBean을 스프링 빈으로 등록

    }

    static class TestBean {
//
//        @Autowired
//        public void setNoBean1(Member noBean1) {
//            System.out.println("noBean1 = " + noBean1);
//        } -> 예외발생, Member가 스프링 빈으로 등록되어있지 않음.

        @Autowired(required = false) // 의존 관계가없으므로 메서드 자체가 호출되지 않음. (자동 주입할 대상이 없다.)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired // 호출은 되지만 null로 들어옴
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired // 자바 8 부터 지원 스프링 빈이 없으면 Optional.empty로 들어옴
        public void setNoBean3(Optional<Member> noBean3) {

            System.out.println("noBean3 = " + noBean3);
        }

    }
}
