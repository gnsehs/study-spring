package hello.exception.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}] [{}] [{}]", uuid, httpRequest.getDispatcherType(), requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.info("LOG Filter ERROR = {}", e.getMessage());
            throw e;
        } finally {
            log.info("RESPONSE [{}] [{}] [{}]", uuid, httpRequest.getDispatcherType(), requestURI);

        }


    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
