package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printAllParams(request);
        printOneParams(request);
        printSameNamesParams(request); // 컨트롤 + 알트 + 쉬프트 + T 리팩토링 단축키
        // 컨트롤 쉬프트 위, 아래 방향키 -> 라인 위치 변경

        response.getWriter().write("ok");


    }

    private void printSameNamesParams(HttpServletRequest request) {
        System.out.println("[이름이 같은 복수 파라미터 조회] - start");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }
        System.out.println("[이름이 같은 복수 파라미터 조회] - end");
    }

    private void printOneParams(HttpServletRequest request) {
        System.out.println("[단일 파라미터 조회] - start");

        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("age = " + age);
        System.out.println("username = " + username);

        System.out.println("[단일 파라미터 조회] - end");
    }

    private void printAllParams(HttpServletRequest request) {
        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - end");

    }


}
