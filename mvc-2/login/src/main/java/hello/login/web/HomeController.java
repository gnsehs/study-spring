package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "home";
        }

        // login
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원정보를 조회
        Object member = (Member)sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        // login
        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션 없으면 home
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        // 세션에 멤버 정보가 없다면 home
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "home";
        }


        // login
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(value = SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) { // 로그인 안된 상황
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     *
     * @Login 애너테이션이 있으면 직접 만든 ArgumentResolver가 작동 -> 자동으로 세션에 있는 로그인 회원을 찾아준다.
     */
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) { // 로그인 안된 상황
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}