package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // <- 구현체를 의존, 추상화 구체화 모두 의존

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
