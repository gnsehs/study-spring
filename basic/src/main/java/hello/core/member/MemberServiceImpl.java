package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // <- 구현체를 의존, 추상화 구체화 모두 의존

    @Autowired // 의존관계 주입 자동화 -> 기존 @Bean에서는 의존관계 주입을 명시 했지만 AutoAppConfig에서는 다르다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 클래스 안에서 의존관계 주입을 해결해야함.
// ac.getBean(MemberRepository.class)
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
