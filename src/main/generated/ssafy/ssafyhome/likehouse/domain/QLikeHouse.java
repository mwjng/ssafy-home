package ssafy.ssafyhome.likehouse.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeHouse is a Querydsl query type for LikeHouse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeHouse extends EntityPathBase<LikeHouse> {

    private static final long serialVersionUID = -79012135L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeHouse likeHouse = new QLikeHouse("likeHouse");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ssafy.ssafyhome.house.domain.QHouse house;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ssafy.ssafyhome.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QLikeHouse(String variable) {
        this(LikeHouse.class, forVariable(variable), INITS);
    }

    public QLikeHouse(Path<? extends LikeHouse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeHouse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeHouse(PathMetadata metadata, PathInits inits) {
        this(LikeHouse.class, metadata, inits);
    }

    public QLikeHouse(Class<? extends LikeHouse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.house = inits.isInitialized("house") ? new ssafy.ssafyhome.house.domain.QHouse(forProperty("house"), inits.get("house")) : null;
        this.member = inits.isInitialized("member") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("member")) : null;
    }

}

