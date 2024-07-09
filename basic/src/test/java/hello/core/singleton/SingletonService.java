package hello.core.singleton;

public class SingletonService { // 싱글톤 패턴
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() { // 프라이빗 생성자로 객체 new 생성 막기
    }
}
