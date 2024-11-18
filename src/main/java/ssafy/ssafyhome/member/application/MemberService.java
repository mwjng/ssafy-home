package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.UserNotFoundException;
import ssafy.ssafyhome.member.presentation.request.AdminCreateRequest;
import ssafy.ssafyhome.member.presentation.request.MemberCreateRequest;
import ssafy.ssafyhome.member.presentation.request.MemberUpdateRequest;
import ssafy.ssafyhome.member.presentation.request.PasswordUpdateRequest;

import java.util.List;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private static final String PROFILE_IMG_DIR = "profile/profile_img";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    public MemberNicknameResponse getNicknameById(final Long memberId) {
        return memberRepository.findNicknameById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }

    public MyInfoResponse getMyInfo(final Long memberId) {
        final Member member = getMember(memberId);
        return MyInfoResponse.from(member);
    }

    @Transactional
    public void createMember(final MemberCreateRequest request) {
        checkMemberRole(request);
        checkDuplicatedLoginId(request.loginId());
        checkDuplicatedNickname(request.nickname());
        memberRepository.save(request.toMember(passwordEncoder));
    }

    @Transactional
    public void createAdmin(final AdminCreateRequest request) {
        checkDuplicatedNickname(request.nickname());
        memberRepository.save(request.toMember(passwordEncoder));
    }

    @Transactional
    public void updateNickname(final Long memberId, final String nickname) {
        final Member member = getMember(memberId);
        if(member.isChangedNickname(nickname)) {
            checkDuplicatedNickname(nickname);
        }
        member.changeNickname(nickname);
    }

    @Transactional
    public void updateMyInfo(final Long memberId, final MemberUpdateRequest request) {
        final Member member = getMember(memberId);
        if(member.isChangedNickname(request.nickname())) {
            checkDuplicatedNickname(request.nickname());
        }
        member.changeMemberInfo(request.nickname(), request.name(), request.email());
    }

    @Transactional
    public void updateLoginId(final Long memberId, final String loginId) {
        final Member member = getMember(memberId);
        checkSocialType(member);
        if (member.isChangedLoginId(loginId)) {
            checkDuplicatedLoginId(loginId);
        }
        member.changeLoginId(loginId);
    }

    @Transactional
    public void updatePassword(final Long memberId, final PasswordUpdateRequest request) {
        final Member member = getMember(memberId);
        if(!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new AuthException(INVALID_PASSWORD);
        }
        member.changePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public void updateProfileImage(final Long memberId, final MultipartFile image) {
        final Member member = getMember(memberId);
        String imagePath = imageService.save(List.of(image), PROFILE_IMG_DIR).getFirst();
        member.changeProfileImageUrl(imagePath);
    }

    private void checkMemberRole(final MemberCreateRequest request) {
        if(request.isAdmin()) {
            throw new AuthException(INVALID_MEMBER_ROLE);
        }
    }

    private void checkDuplicatedNickname(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BadRequestException(DUPLICATED_USER_NICKNAME);
        }
    }

    private void checkDuplicatedLoginId(final String loginId) {
        if(memberRepository.existsBySocialLoginId(loginId)) {
            throw new BadRequestException(DUPLICATED_USER_LOGIN_ID);
        }
    }

    private void checkSocialType(final Member member) {
        if(member.isOAuthLogin()) {
            throw new BadRequestException(INVALID_REQUEST);
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository
            .findMemberById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }
}
