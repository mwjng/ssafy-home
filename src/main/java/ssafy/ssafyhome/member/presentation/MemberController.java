package ssafy.ssafyhome.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.MasterAccess;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.deal.application.DealService;
import ssafy.ssafyhome.member.application.MailService;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.application.VerificationCodeService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.presentation.request.*;
import ssafy.ssafyhome.member.presentation.response.MyDealsResponse;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;
import static ssafy.ssafyhome.deal.domain.DealStatus.getDealStatus;
import static ssafy.ssafyhome.deal.domain.DealType.getDealType;
import static ssafy.ssafyhome.house.domain.HouseType.*;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final DealService dealService;
    private final VerificationCodeService verificationCodeService;
    private final MailService mailService;

    @GetMapping("/me")
    @UserAccess
    public ResponseEntity<MyInfoResponse> getMyInfo(
        @AuthenticationPrincipal final AccessContext accessContext,
        final HttpServletRequest request
    ) {
        final MyInfoResponse myInfoResponse = memberService
            .getMyInfo(accessContext.getMemberId(), getBaseUrl(request));
        return ResponseEntity.ok().body(myInfoResponse);
    }

    @GetMapping("/me/deals")
    @AgentAccess
    public ResponseEntity<MyDealsResponse> getMyDeals(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false) Long cursorId,
        final HttpServletRequest request
    ) {
        final MyDealsResponse myDealResponse = dealService.getDealsByMemberId(
                accessContext.getMemberId(),
                getBaseUrl(request),
                size,
                cursorId);
        return ResponseEntity.ok().body(myDealResponse);
    }

    @PatchMapping("/me")
    @UserAccess
    public ResponseEntity<Void> updateMyInfo(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestBody final MemberUpdateRequest memberUpdateRequest
    ) {
        memberService.updateMyInfo(accessContext.getMemberId(), memberUpdateRequest);
        return ResponseEntity.noContent().build();
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
        memberService.updateNickname(accessContext.getMemberId(), request.nickname());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/profile-image")
    @UserAccess
    public ResponseEntity<Void> updateProfileImage(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestPart final MultipartFile file
    ) {
        memberService.updateProfileImage(accessContext.getMemberId(), file);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/login-id")
    @UserAccess
    public ResponseEntity<Void> updateLoginId(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestBody final LoginIdUpdateRequest request
    ) {
        memberService.updateLoginId(accessContext.getMemberId(), request.loginId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    @UserAccess
    public ResponseEntity<Void> updatePassword(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestBody final PasswordUpdateRequest passwordUpdateRequest
    ) {
        memberService.updatePassword(accessContext.getMemberId(), passwordUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<Void> requestResetPassword(
        @Valid @RequestBody final PasswordForgotRequest passwordForgotRequest
    ) {
        final Member member = memberService.getMemberByLoginId(passwordForgotRequest.loginId());
        final String code = verificationCodeService.saveVerificationCode(passwordForgotRequest.loginId());
        mailService.sendVerificationCode(member.getEmail(), code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/verify")
    public ResponseEntity<Void> verifyAndResetPassword(
        @Valid @RequestBody final VerificationRequest verificationRequest
    ) {
        verificationCodeService.checkVerificationCode(verificationRequest.loginId(), verificationRequest.code());
        final Member member = memberService.getMemberByLoginId(verificationRequest.loginId());
        final String temporaryPassword = memberService.createTemporaryPassword(verificationRequest.loginId());
        mailService.sendTemporaryPassword(member.getEmail(), temporaryPassword);
        return ResponseEntity.ok().build();
    }
}
