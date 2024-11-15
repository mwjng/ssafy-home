package ssafy.ssafyhome.likearticle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeArticle is a Querydsl query type for LikeArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeArticle extends EntityPathBase<LikeArticle> {

    private static final long serialVersionUID = 2019108313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeArticle likeArticle = new QLikeArticle("likeArticle");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    public final SimplePath<ssafy.ssafyhome.article.domain.Article> article = createSimple("article", ssafy.ssafyhome.article.domain.Article.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ssafy.ssafyhome.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QLikeArticle(String variable) {
        this(LikeArticle.class, forVariable(variable), INITS);
    }

    public QLikeArticle(Path<? extends LikeArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeArticle(PathMetadata metadata, PathInits inits) {
        this(LikeArticle.class, metadata, inits);
    }

    public QLikeArticle(Class<? extends LikeArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("member")) : null;
    }

}

