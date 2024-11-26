package ssafy.ssafyhome.member.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.image.application.ImageService;
import ssafy.ssafyhome.image.domain.ImageEvent;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MembersResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.UserNotFoundException;
import ssafy.ssafyhome.member.presentation.request.*;

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

    public MembersResponse getAllMembers(final Pageable pageable, final String baseUrl) {
        final List<MyInfoResponse> myInfoResponses = memberRepository.findAllBy(pageable).stream()
            .map(member -> getMemberResponse(member, baseUrl))
            .toList();
        return new MembersResponse(myInfoResponses);
    }

    public MyInfoResponse getMyInfo(final Long memberId, final String baseUrl) {
        final Member member = findMember(memberId);
        return getMemberResponse(member, baseUrl);
    }

    private MyInfoResponse getMemberResponse(final Member member, String baseUrl) {
        final List<String> imageUrlList = getProfileImageUrlList(baseUrl, member);
        return MyInfoResponse.of(
            member,
            imageUrlList.stream().findFirst().orElse(null));
    }

    private List<String> getProfileImageUrlList(final String baseUrl, final Member member) {
        final List<String> imageFileNames = imageService.getImageFileNames(member.getDirName(), PROFILE.getDirectory());
        return imageService.getImageUrlList(baseUrl, PROFILE.getDirectory(), imageFileNames, member.getDirName());
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
        changeNickname(nickname, member);
    }

    private void changeNickname(final String nickname, final Member member) {
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
    public void updateMyInfos(final Long memberId, final MemberUpdateAllRequest request, final MultipartFile image) {
        final Member member = findMember(memberId);
        checkSocialType(member);
        updateImage(image, member);
        changeLoginId(request.loginId(), member);
        changePassword(request.currentPassword(), request.newPassword(), member);
        changeNickname(request.nickname(), member);
        member.changeEmail(request.email());
    }

    @Transactional
    public void updateLoginId(final Long memberId, final String loginId) {
        final Member member = findMember(memberId);
        checkSocialType(member);
        changeLoginId(loginId, member);
    }

    private void changeLoginId(final String loginId, final Member member) {
        if (member.isChangedLoginId(loginId)) {
            checkDuplicatedLoginId(loginId);
        }
        member.changeLoginId(loginId);
    }

    @Transactional
    public void updatePassword(final Long memberId, final PasswordUpdateRequest request) {
        final Member member = findMember(memberId);
        checkSocialType(member);
        changePassword(request.currentPassword(), request.newPassword(), member);
    }

    private void changePassword(final String currentPassword, final String newPassword, final Member member) {
        if(!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new BadRequestException(INVALID_PASSWORD);
        }
        member.changePassword(passwordEncoder.encode(newPassword));
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
        updateImage(image, member);
    }

    private void updateImage(final MultipartFile image, final Member member) {
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

    @Transactional
    public void updateStatus(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        member.changeStatus();
    }
}
