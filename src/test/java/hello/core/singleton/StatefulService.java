package hello.core.singleton;

public class StatefulService {
//    private int price; // 상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price; // 지역 변수를 리턴 하도록 변경 => stateless하게 설계
//        this.price = price; // 여기가 문제!

    }

//    public int getPrice() {
////        return price;
//    }
}
