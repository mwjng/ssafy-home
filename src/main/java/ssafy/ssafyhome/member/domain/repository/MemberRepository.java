package ssafy.ssafyhome.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.ssafyhome.member.application.response.MemberNicknameResponse;
import ssafy.ssafyhome.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.socialLoginId = :socialLoginId and m.status != 'DELETED'")
    Optional<Member> findBySocialLoginId(final Long socialLoginId);

    @Query("select new ssafy.ssafyhome.member.application.response.MemberNicknameResponse" +
        "(m.nickname) from Member m where m.id = :memberId and m.status = 'ACTIVE'")
    Optional<MemberNicknameResponse> findNicknameById(final Long memberId);
}
