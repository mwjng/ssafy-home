package ssafy.ssafyhome.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.MasterAccess;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.MailService;
import ssafy.ssafyhome.member.application.MemberService;
import ssafy.ssafyhome.member.application.VerificationCodeService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.presentation.request.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final VerificationCodeService verificationCodeService;
    private final MailService mailService;

    @GetMapping("/myinfo")
    @UserAccess
    public ResponseEntity<MyInfoResponse> getMyInfo(
        @AuthenticationPrincipal final AccessContext accessContext,
        HttpServletRequest request
    ) {
        final RequestUrl requestUrl = new RequestUrl(request);
        final MyInfoResponse myInfoResponse = memberService
            .getMyInfo(accessContext.getMemberId(), requestUrl.getBaseUrl());
        return ResponseEntity.ok().body(myInfoResponse);
    }

    @PatchMapping("/myinfo")
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

    @PatchMapping("/image")
    @UserAccess
    public ResponseEntity<Void> updateProfileImage(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestPart final MultipartFile file
    ) {
        memberService.updateProfileImage(accessContext.getMemberId(), file);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/id")
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

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
        @Valid @RequestBody final PasswordForgotRequest passwordForgotRequest
    ) {
        final Member member = memberService.getMemberByLoginId(passwordForgotRequest.loginId());
        final String code = verificationCodeService.saveVerificationCode(passwordForgotRequest.loginId());
        mailService.sendVerificationCode(member.getEmail(), code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-reset")
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
