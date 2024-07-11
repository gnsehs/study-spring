package hello.core.web;


import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor // final 필드 생성자 자동으로 생성됨
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger; // myLogger의 scope는 httprequest가 들어올때 생성이 된다.
    // 하지만 스프링 컨테이너가 뜨는 시점에서는 request가 없으므로 오류가 발생한다.

    @RequestMapping("log-demo")
    @ResponseBody // 문자를 바로 반환
    public String logDemo(HttpServletRequest request) {
        System.out.println("myLogger = " + myLogger.getClass());
        String requestURL = request.getRequestURL().toString();
        myLogger.setUrl(requestURL); // http 요청이 아무리 많아도 각각의 요청마다 request scope로 관리
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "ok";
    }
}
