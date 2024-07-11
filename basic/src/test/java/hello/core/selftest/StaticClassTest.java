package hello.core.selftest;

import org.junit.jupiter.api.Test;

/**
 * 스태틱 클래스는 다형성이 적용되지 않는다.
 * static 메서드는 overriding이 아닌 hiding개념
 * static 메서드는 runtime에 결정되는 것이 아닌 컴파일 타임에 결정된다.
 *  static 메소드는 Compile Time에 메모리에 올라가기 때문에 클래스에 종속적이기 때문에 상속되지 않는다.
 *  객체가 생성되기 전부터 이미 메모리에 할당되어있기 때문이다.
 */
public class StaticClassTest {
    @Test
    void test1() {
        Rabbit rabbit = new Rabbit();
        Animal animal = new Rabbit(); // 다형성
        rabbit.speak(); // Rabbit
        animal.speak(); // Animal
    }
}


class Animal {
    public static void speak() {
        System.out.println("Animal");
    }
}

class Rabbit extends Animal {
    public static void speak() {
        System.out.println("Rabbit"); // 실제로는 overriding 된 것이 아님
    }
}