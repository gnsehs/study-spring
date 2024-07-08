package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

//    @Autowired -> 인스터스 필드에 바로 적용하기
//    private MemberRepository memberRepository;
//    @Autowired
//    private DiscountPolicy discountPolicy;

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    /*
    수정자 의존관계 주입
     */
//    @Autowired(required = false) -> 필수값이 아니다. 라고 명시
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//
//        this.memberRepository = memberRepository;
//    }
//
    @Autowired // 자동으로 의존 관계 주입 Appcontext이용
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) { // -> @RequiredArgsConstructor
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
