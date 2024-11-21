package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
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
import java.util.UUID;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;
import static ssafy.ssafyhome.image.application.ImageDirectory.PROFILE;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${file.image.dir}")
    private String imageDirPath;

    public MemberNicknameResponse getNicknameById(final Long memberId) {
        return memberRepository.findNicknameById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }

    public MyInfoResponse getMyInfo(final Long memberId, final String baseUrl) {
        final Member member = findMember(memberId);
        final List<String> imageFileNames = imageService.getImageFileNames(member.getDirName(), PROFILE.getDirectory());
        final List<String> imageUrlList = imageService.getImageUrlList(
            baseUrl, PROFILE.getDirectory(), imageFileNames, member.getDirName());
        return MyInfoResponse.of(
            member,
            imageUrlList.stream().findFirst().orElse(null));
    }

    public Member getMemberByLoginId(final String loginId) {
        return memberRepository.findBySocialLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_LOGIN_ID));
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
        final Member member = findMember(memberId);
        if(member.isChangedNickname(nickname)) {
            checkDuplicatedNickname(nickname);
        }
        member.changeNickname(nickname);
    }

    @Transactional
    public void updateMyInfo(final Long memberId, final MemberUpdateRequest request) {
        final Member member = findMember(memberId);
        if(member.isChangedNickname(request.nickname())) {
            checkDuplicatedNickname(request.nickname());
        }
        member.changeMemberInfo(request.nickname(), request.name(), request.email());
    }

    @Transactional
    public void updateLoginId(final Long memberId, final String loginId) {
        final Member member = findMember(memberId);
        checkSocialType(member);
        if (member.isChangedLoginId(loginId)) {
            checkDuplicatedLoginId(loginId);
        }
        member.changeLoginId(loginId);
    }

    @Transactional
    public void updatePassword(final Long memberId, final PasswordUpdateRequest request) {
        final Member member = findMember(memberId);
        checkSocialType(member);
        if(!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new BadRequestException(INVALID_PASSWORD);
        }
        member.changePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public String createTemporaryPassword(final String loginId) {
        final Member member = getMemberByLoginId(loginId);
        checkSocialType(member);
        final String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);
        member.changePassword(passwordEncoder.encode(temporaryPassword));
        return temporaryPassword;
    }

    @Transactional
    public void updateProfileImage(final Long memberId, final MultipartFile image) {
        final Member member = findMember(memberId);
        final String imagePath = imageService.save(List.of(image), PROFILE.getDirectory());
        final List<String> imgFilePaths = imageService.getImageFilePaths(member.getDirName(), PROFILE.getDirectory());
        final String imageFileDirPath = imageService.getImageFileDirPath(member.getDirName(), PROFILE.getDirectory());
        eventPublisher.publishEvent(new ImageEvent(imageFileDirPath, imgFilePaths));
        member.changeProfileImageUrl(imagePath);
    }

    private void checkMemberRole(final MemberCreateRequest request) {
        if(request.isAdmin()) {
            throw new BadRequestException(INVALID_MEMBER_ROLE);
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

    private Member findMember(final Long memberId) {
        return memberRepository
            .findMemberById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }
}
