package ssafy.ssafyhome.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.request.LoginMember;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.presentation.request.NicknameUpdateRequest;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/member")
    public ResponseEntity<Void> updateNickname(
        @AuthenticationPrincipal final LoginMember loginMember,
        @RequestBody final NicknameUpdateRequest request
    ) {
        memberService.changeNickname(loginMember.memberId(), request.nickname());
        return ResponseEntity.ok().build();
    }
}
