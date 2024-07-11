package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS) // http 요청당 하나씩 생성(스프링 컨테이너 요청)되고, HTTP 요청이 끝나는 시점에 소멸
public class MyLogger {
    private String url;
    private String uuid;

    public void setUrl(String url) {
        this.url = url;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + url + "] " + message);
    }

    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create " + this);

    }

    @PreDestroy
    public void destroy() {
        System.out.println("[" + uuid + "] request scope bean close " + this);

    }

}
