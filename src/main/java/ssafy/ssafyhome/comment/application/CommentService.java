package ssafy.ssafyhome.comment.application;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.article.domain.Article;
import ssafy.ssafyhome.article.domain.repository.ArticleRepository;
import ssafy.ssafyhome.auth.exception.AuthException;
import ssafy.ssafyhome.comment.application.response.ArticleCommentsResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.domain.Comment;
import ssafy.ssafyhome.comment.domain.repository.CommentRepository;
import ssafy.ssafyhome.common.exception.BadRequestException;
import ssafy.ssafyhome.member.domain.Member;
import ssafy.ssafyhome.member.domain.repository.MemberRepository;

import static ssafy.ssafyhome.common.exception.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public void validateCommentByMember(final Long memberId, final Long commentId) {
        if (!commentRepository.existsByMemberIdAndId(memberId, commentId)) {
            throw new AuthException(INVALID_COMMENT_WITH_MEMBER);
        }
    }

    public CommentsResponse getMemberComments(final Long memberId, final Pageable pageable) {
        return new CommentsResponse(commentRepository.findCommentsByMemberId(memberId, pageable.previousOrFirst()));
    }

    public ArticleCommentsResponse getArticleComments(final Long articleId, final Pageable pageable) {
        return new ArticleCommentsResponse(commentRepository.findCommentsByArticleId(articleId, pageable.previousOrFirst()));
    }

    @Transactional
    public void createComment(final Long memberId, final Long articleId, final String content) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_ID));
        final Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_ARTICLE_ID));

        commentRepository.save(Comment.builder()
            .article(article)
            .member(member)
            .content(content)
            .build());
    }

    @Transactional
    public void deleteComment(final Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new BadRequestException(NOT_FOUND_COMMENT_ID);
        }
        commentRepository.deleteById(commentId);
    }
}
