package ssafy.ssafyhome.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.presentation.request.NicknameUpdateRequest;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    @UserAccess
    public ResponseEntity<MemberNicknameResponse> getMemberNickname(
        @AuthenticationPrincipal final AccessContext accessContext
    ) {
        return ResponseEntity.status(OK)
            .body(memberService.getNicknameById(accessContext.getMemberId()));
    }

    @PatchMapping("/member")
    @UserAccess
    public ResponseEntity<Void> updateNickname(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestBody final NicknameUpdateRequest request
    ) {
        memberService.changeNickname(accessContext.getMemberId(), request.nickname());
        return ResponseEntity.ok().build();
    }
}
