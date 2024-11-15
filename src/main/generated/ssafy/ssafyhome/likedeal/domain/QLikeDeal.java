package ssafy.ssafyhome.likedeal.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeDeal is a Querydsl query type for LikeDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeDeal extends EntityPathBase<LikeDeal> {

    private static final long serialVersionUID = -205517577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeDeal likeDeal = new QLikeDeal("likeDeal");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final SimplePath<ssafy.ssafyhome.deal.domain.Deal> deal = createSimple("deal", ssafy.ssafyhome.deal.domain.Deal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ssafy.ssafyhome.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QLikeDeal(String variable) {
        this(LikeDeal.class, forVariable(variable), INITS);
    }

    public QLikeDeal(Path<? extends LikeDeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeDeal(PathMetadata metadata, PathInits inits) {
        this(LikeDeal.class, metadata, inits);
    }

    public QLikeDeal(Class<? extends LikeDeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("member")) : null;
    }

}

