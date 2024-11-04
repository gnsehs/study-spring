package hello.jdbc.exception.basic;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.calThrow()).isInstanceOf(MyUncheckedException.class);
    }

    static class Service {
        Repository repository = new Repository();

        public void callCatch() {

            try { // 필요한 경우 언체크 예외도 잡아서 처리 가능
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }

        public void calThrow() { // throws 를 따로 선언하지 않아도 컴파일 오류가 발생하지 않는다.
            repository.call();
        }
    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }

    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }
}
