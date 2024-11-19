package ssafy.ssafyhome.article.presentation.request;

import lombok.Builder;
import lombok.Getter;
import ssafy.ssafyhome.article.application.ArticleSortCondition;

@Getter
public class ArticleSearchCondition {

    private int size;
    private Long cursorId;
    private ArticleSortCondition articleSortCondition;

    @Builder
    public ArticleSearchCondition(int size, Long cursorId, ArticleSortCondition articleSortCondition) {
        this.size = size;
        this.cursorId = cursorId;
        this.articleSortCondition = articleSortCondition;
    }
}
