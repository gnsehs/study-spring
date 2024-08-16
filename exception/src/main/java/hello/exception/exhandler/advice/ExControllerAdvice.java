package hello.exception.exhandler.advice;

import hello.exception.api.ApiExceptionV3Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = ApiExceptionV3Controller.class)
public class ExControllerAdvice {

    /**
     * 지정한 예외와 그 자식 예외까지 잡을 수 있다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle]", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    /**
     * d
     * ExceptionHandler에 class 생략하면 파라미터의 타입이 자동으로 지정된다.
     */
    @ExceptionHandler // .class 생략 가능
    public ResponseEntity<ErrorResult> userHandle(UserException e) {
        log.error("[exceptionHandle]", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult("USER-EX", e.getMessage()));
    }

    /**
     *
     * RuntimeException은 여기에서 잡힌다.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle]", e);
        return new ErrorResult("EX", "내부 오류");
    }


}
