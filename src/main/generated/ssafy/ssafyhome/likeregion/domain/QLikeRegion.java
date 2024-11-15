package ssafy.ssafyhome.likeregion.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeRegion is a Querydsl query type for LikeRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeRegion extends EntityPathBase<LikeRegion> {

    private static final long serialVersionUID = 361092679L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeRegion likeRegion = new QLikeRegion("likeRegion");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ssafy.ssafyhome.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ssafy.ssafyhome.region.domain.QRegion region;

    public QLikeRegion(String variable) {
        this(LikeRegion.class, forVariable(variable), INITS);
    }

    public QLikeRegion(Path<? extends LikeRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeRegion(PathMetadata metadata, PathInits inits) {
        this(LikeRegion.class, metadata, inits);
    }

    public QLikeRegion(Class<? extends LikeRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new ssafy.ssafyhome.region.domain.QRegion(forProperty("region")) : null;
    }

}

