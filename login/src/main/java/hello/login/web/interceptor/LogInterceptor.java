package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    /**
     * 컨트롤러 호출 전
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid); // 싱글톤 빈으로 생성되므로 멤버 변수를 사용하는 것은 위험

        // @RequestMapping: HandlerMethod
        // 정적 리소스: ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보 포함
        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);
        return true; // 다음 단계로 진행


    }

    /**
     * 컨트롤러 어댑터 호출 후 
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView); // posthandle은 ModleAndView를 반환한다.
    }

    /**
     * 뷰 랜더링 후
     * 예외가 발생해도 호출이 되는 것을 보장한다.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}]", uuid, requestURI);

        if (ex != null) {
            log.info("afterCompletion error!", ex); // 에러의 경우 이런식으로 사용 가능
        }



    }
}
