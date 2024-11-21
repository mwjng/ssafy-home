package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;
import ssafy.ssafyhome.member.domain.VerificationCode;
import ssafy.ssafyhome.member.domain.repository.VerificationCodeRepository;

import static ssafy.ssafyhome.common.exception.ErrorCode.INVALID_VERIFICATION_CODE;
import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_VERIFICATION_CODE;

@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final MemberRepository memberRepository;

    public String saveVerificationCode(final String loginId) {
        final VerificationCode verificationCode = verificationCodeRepository.save(new VerificationCode(loginId));
        return verificationCode.getCode();
    }

    public void checkVerificationCode(final String loginId, final String code) {
        final VerificationCode verificationCode = verificationCodeRepository.findById(loginId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_VERIFICATION_CODE));

        if(!verificationCode.isValid(code)) {
            throw new BadRequestException(INVALID_VERIFICATION_CODE);
        }
    }
}
