package ssafy.ssafyhome.member.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.auth.infrastructure.PasswordEncoder;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.application.response.MyInfoResponse;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.UserNotFoundException;
import ssafy.ssafyhome.member.presentation.request.AdminCreateRequest;
import ssafy.ssafyhome.member.presentation.request.MemberCreateRequest;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberNicknameResponse getNicknameById(final Long memberId) {
        return memberRepository.findNicknameById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }

    public MyInfoResponse getMyInfo(final Long memberId) {
        final Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        return MyInfoResponse.from(member);
    }

    @Transactional
    public void createMember(final MemberCreateRequest request) {
        validateMemberRole(request);
        checkDuplicatedNickname(request.nickname());
        memberRepository.save(request.toMember(passwordEncoder));
    }

    @Transactional
    public void createAdmin(final AdminCreateRequest request) {
        checkDuplicatedNickname(request.nickname());
        memberRepository.save(request.toMember(passwordEncoder));
    }

    @Transactional
    public void changeNickname(final Long memberId, final String nickname) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
        if(member.isChangedNickname(nickname)) {
            checkDuplicatedNickname(nickname);
        }
        member.changeNickname(nickname);
    }

    private void validateMemberRole(final MemberCreateRequest request) {
        if(request.isAdmin()) {
            throw new AuthException(INVALID_MEMBER_ROLE);
        }
    }

    private void checkDuplicatedNickname(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BadRequestException(DUPLICATED_USER_NICKNAME);
        }
    }
}
