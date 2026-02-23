package kr.co.spring_project.header.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.header.dto.MessageDTO;
import kr.co.spring_project.header.service.MessageService;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.entity.Member;
import kr.co.spring_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UnreadMessageInterceptor implements HandlerInterceptor {

    private final MessageService    messageService;
    private final MemberRepository  memberRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mav) {

        if (mav == null || (mav.getView() == null && mav.getViewName() == null)) return;

        String viewName = mav.getViewName();
        if (viewName != null && viewName.startsWith("redirect:")) return;

        HttpSession session = request.getSession(false);
        if (session == null) return;

        // 세션에 ResloginDTO가 저장되어 있으므로 그걸로 꺼냄
        Object sessionObj = session.getAttribute("LOGIN_MEMBER");
        if (sessionObj == null) return;

        Member loginMember = null;

        if (sessionObj instanceof Member) {
            // 혹시 Member 엔티티가 직접 들어있는 경우
            loginMember = (Member) sessionObj;

        } else if (sessionObj instanceof ResloginDTO) {
            // ResloginDTO로 저장된 경우 → employeeNo로 Member 조회
            ResloginDTO dto = (ResloginDTO) sessionObj;
            loginMember = memberRepository.findById(dto.getEmployeeNo()).orElse(null);
        }

        if (loginMember == null) return;

        // 읽지 않은 쪽지 수 (헤더 뱃지)
        mav.addObject("unreadMessageCount", messageService.countUnread(loginMember));

        // 팝업용 최근 받은 쪽지 3개
        List<MessageDTO.ListResponse> recent = messageService.getRecentInbox(loginMember, 3);
        mav.addObject("recentMessages", recent);
    }
}