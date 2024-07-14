package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletInputStream inputStream = request.getInputStream(); // 메세지 바디 내용 바이드 코드로 얻어오기
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트 문자간의 변환운 인코딩 정보를 알려줘야함

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");

    }
}
