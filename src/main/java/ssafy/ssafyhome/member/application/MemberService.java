package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.exception.UserNotFoundException;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_USER_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberNicknameResponse getNicknameById(final Long memberId) {
        return memberRepository.findNicknameById(memberId)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));
    }
}
