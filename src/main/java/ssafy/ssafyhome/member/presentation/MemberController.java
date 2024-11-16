package ssafy.ssafyhome.member.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.MasterAccess;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.presentation.request.AdminCreateRequest;
import ssafy.ssafyhome.member.presentation.request.MemberCreateRequest;
import ssafy.ssafyhome.member.presentation.request.NicknameUpdateRequest;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/myinfo")
    @UserAccess
    public ResponseEntity<MyInfoResponse> getMyInfo(
        @AuthenticationPrincipal final AccessContext accessContext
    ) {
        final MyInfoResponse myInfoResponse = memberService.getMyInfo(accessContext.getMemberId());
        return ResponseEntity.ok().body(myInfoResponse);
    }

    @GetMapping("/nickname")
    @UserAccess
    public ResponseEntity<MemberNicknameResponse> getMemberNickname(
        @AuthenticationPrincipal final AccessContext accessContext
    ) {
        return ResponseEntity.ok(memberService.getNicknameById(accessContext.getMemberId()));
    }

    @PostMapping
    public ResponseEntity<Void> createMember(
        @Valid @RequestBody final MemberCreateRequest memberCreateRequest
    ) {
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/admin")
    @MasterAccess
    public ResponseEntity<Void> createAdmin(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestBody final AdminCreateRequest adminCreateRequest
    ) {
        memberService.createAdmin(adminCreateRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @PatchMapping("/nickname")
    @UserAccess
    public ResponseEntity<Void> updateNickname(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestBody final NicknameUpdateRequest request
    ) {
        memberService.changeNickname(accessContext.getMemberId(), request.nickname());
        return ResponseEntity.noContent().build();
    }
}
