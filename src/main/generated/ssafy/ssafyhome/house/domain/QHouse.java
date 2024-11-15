package ssafy.ssafyhome.house.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouse is a Querydsl query type for House
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouse extends EntityPathBase<House> {

    private static final long serialVersionUID = -328004903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouse house = new QHouse("house");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    public final StringPath bonbun = createString("bonbun");

    public final StringPath bubun = createString("bubun");

    public final NumberPath<Long> buildYear = createNumber("buildYear", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<HouseType> houseType = createEnum("houseType", HouseType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jibum = createString("jibum");

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final ssafy.ssafyhome.region.domain.QRegion region;

    public final StringPath road = createString("road");

    public QHouse(String variable) {
        this(House.class, forVariable(variable), INITS);
    }

    public QHouse(Path<? extends House> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouse(PathMetadata metadata, PathInits inits) {
        this(House.class, metadata, inits);
    }

    public QHouse(Class<? extends House> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.region = inits.isInitialized("region") ? new ssafy.ssafyhome.region.domain.QRegion(forProperty("region")) : null;
    }

}

