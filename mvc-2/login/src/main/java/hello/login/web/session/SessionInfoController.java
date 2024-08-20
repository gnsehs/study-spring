package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다";
        }

        /*세션 데이터 출력*/
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info(
                        "attribute name = {} value={}",name,session.getAttribute(name)
                ));

        log.info("sessionId={}", session.getId()); // JSESSIONID
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval()); // 세션의 유효시간
        log.info("getCreationTime={}", new Date(session.getCreationTime())); // 세션 생성일지
        log.info("getLastAccessedTime={}", new Date(session.getLastAccessedTime())); // 최근 서버 접근 시간
        log.info("isNew={}", session.isNew()); // 새로 생성된 세션

        return "세션출력";

    }
}
