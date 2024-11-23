package ssafy.ssafyhome.like.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.article.domain.Article;
import ssafy.ssafyhome.article.domain.repository.ArticleRepository;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.like.domain.LikeArticle;
import ssafy.ssafyhome.like.domain.repository.LikeArticleRepository;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeArticleService {

    private final LikeArticleRepository likeArticleRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public void createLikeArticle(final Long memberId, final Long articleId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_ID));
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));

        if (likeArticleRepository.existsByMemberIdAndArticleId(memberId, articleId)) {
            throw new BadRequestException(DUPLICATED_LIKE_ARTICLE);
        }
        likeArticleRepository.save(new LikeArticle(member, article));
    }

    public void deleteLikeArticle(final Long memberId, final Long articleId) {
        likeArticleRepository.findByMemberIdAndArticleId(memberId, articleId)
            .ifPresent(likeArticleRepository::delete);
    }
}
