package ssafy.ssafyhome.comment.presentation.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentSearchCondition {
    private int size;
    private Long cursorId;

    @Builder
    public CommentSearchCondition(int size, Long cursorId) {
        this.size = size;
        this.cursorId = cursorId;
    }
}
