package hello.mvc1springmvc.basic.request;

import hello.mvc1springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
public class RequsetParamController {
    @RequestMapping("/request-param-v1")
    public void requsetParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");

    }

    @RequestMapping("/request-param-v2")
    @ResponseBody // view name 호출 x
    public String requestParamV2(
            @RequestParam String username, // 대소문자 구분
            @RequestParam int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";

    }

    @RequestMapping("/request-param-v3")
    @ResponseBody // view name 호출 x
    public String requestParamV3(
            @RequestParam String username, // 대소문자 구분
            @RequestParam int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";

    }

    /**
     * String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
     * but 명확하지 않음 ..
     */
    @RequestMapping("/request-param-v4")
    @ResponseBody // view name 호출 x
    public String requestParamV4(String username, int age) { // @RequestParam 제거 가능 요청 파라미터 이름과 같을 시

        log.info("username = {}, age = {}", username, age);
        return "ok";

    }

    @RequestMapping("/request-param-required")
    @ResponseBody // view name 호출 x
    public String requestParamRequired(
            @RequestParam(required = true) String username, // 대소문자 구분
            @RequestParam(required = false) Integer age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";

    }

    @RequestMapping("/request-param-default")
    @ResponseBody // view name 호출 x
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, // 대소문자 구분
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {

        log.info("username = {}, age = {}", username, age);
        return "ok";

    }

    @RequestMapping("/request-param-map")
    @ResponseBody // view name 호출 x
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";

    }

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {

        log.info("helloData={}", helloData);

        return "ok";
    }

    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    public String modelAttributeV2(HelloData helloData) { // @ModelAttribute 생략 가능

        log.info("helloData={}", helloData);

        return "ok";
    }

}
