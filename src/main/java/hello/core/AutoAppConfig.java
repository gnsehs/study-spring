package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", // 어디서 부터 찾을지 지정 가능
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, // Configuration은 뺄거야
                classes = Configuration.class)
) // @ComponentScan은 이 애너테이션이 붙은 클래스를 스캔하여 스프링 빈으로 등록한다.
public class AutoAppConfig {


    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
