package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {
    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonTest_1 = ac.getBean(SingletonBean.class); // init이 한번만 호출됨
        SingletonBean singletonTest_2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonTest_1 = " + singletonTest_1);
        System.out.println("singletonTest_2 = " + singletonTest_2);

        Assertions.assertThat(singletonTest_1).isSameAs(singletonTest_2);

        ac.close(); // 이떄 PreDestory 호출
        System.out.println("SingletonTest.singletonBeanFind"); // PreDestory 이후 출력됨.
    }

    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
