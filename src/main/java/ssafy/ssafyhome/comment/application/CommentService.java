package ssafy.ssafyhome.comment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.domain.repository.CommentRepository;
import ssafy.ssafyhome.common.exception.BadRequestException;

import static ssafy.ssafyhome.common.exception.ErrorCode.NOT_FOUND_COMMENT_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentsResponse getMemberComments(final Long memberId, final Pageable pageable) {
        return new CommentsResponse(commentRepository.findCommentsByMemberId(memberId, pageable));
    }

    @Transactional
    public void deleteComment(final Long commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new BadRequestException(NOT_FOUND_COMMENT_ID);
        }
        commentRepository.deleteById(commentId);
    }
}
