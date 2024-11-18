package ssafy.ssafyhome.article.application;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleCondition {

    private int size;
    private Long cursorId;
    private Long houseId;
    private Long memberId;
    private String sortCondition;

    @Builder
    public ArticleCondition(int size, Long cursorId, Long houseId, Long memberId, String sortCondition) {
        this.size = size;
        this.cursorId = cursorId;
        this.houseId = houseId;
        this.memberId = memberId;
        this.sortCondition = sortCondition;
    }
}
