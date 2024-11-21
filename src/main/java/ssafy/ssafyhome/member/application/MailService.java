package ssafy.ssafyhome.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ssafy.ssafyhome.member.infrastructure.MailSendClient;

@RequiredArgsConstructor
@Service
public class MailService {

    private static final String VERIFICATION_CODE_SUBJECT = "SSAFY-HOME: 인증 코드 유효기간은 3분 입니다.";
    private static final String TEMPORARY_PASSWORD_SUBJECT = "SSAFY-HOME: 임시 비밀번호 입니다.";

    private final MailSendClient mailSendClient;

    @Async
    public void sendVerificationCode(final String to, final String code) {
        mailSendClient.sendMail(to, VERIFICATION_CODE_SUBJECT, code);
    }

    @Async
    public void sendTemporaryPassword(final String to, final String temporaryPassword) {
        mailSendClient.sendMail(to, TEMPORARY_PASSWORD_SUBJECT, temporaryPassword);
    }
}
