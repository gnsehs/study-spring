package hello.mvc1springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // rest api -> 문자반환하면 스트링이 그대로 반환 ,뷰 작업 안함
@Slf4j
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class); -> 롬복 @Slf4j


    @GetMapping("/log-test")
    public String logTest() {
        String name = "spring";

        System.out.println("name = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log = {}", name);
        log.warn(" warn log = {}", name);
        log.error(" error log = {}", name);

        return "ok";
    }
}
