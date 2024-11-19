package ssafy.ssafyhome.comment.application;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCondition {

    private int size;
    private Long cursorId;
    private Long memberId;
    private Long articleId;

    @Builder
    public CommentCondition(int size, Long cursorId, Long memberId, Long articleId) {
        this.size = size;
        this.cursorId = cursorId;
        this.memberId = memberId;
        this.articleId = articleId;
    }
}
