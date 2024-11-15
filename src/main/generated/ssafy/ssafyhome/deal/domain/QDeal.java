package ssafy.ssafyhome.deal.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeal is a Querydsl query type for Deal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeal extends EntityPathBase<Deal> {

    private static final long serialVersionUID = -1780050007L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeal deal = new QDeal("deal");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> deposit = createNumber("deposit", Integer.class);

    public final NumberPath<java.math.BigDecimal> exclusiveArea = createNumber("exclusiveArea", java.math.BigDecimal.class);

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final ssafy.ssafyhome.house.domain.QHouse house;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ssafy.ssafyhome.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> monthlyRentPrice = createNumber("monthlyRentPrice", Integer.class);

    public final NumberPath<Integer> rentPrice = createNumber("rentPrice", Integer.class);

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final EnumPath<DealStatus> status = createEnum("status", DealStatus.class);

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QDeal(String variable) {
        this(Deal.class, forVariable(variable), INITS);
    }

    public QDeal(Path<? extends Deal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeal(PathMetadata metadata, PathInits inits) {
        this(Deal.class, metadata, inits);
    }

    public QDeal(Class<? extends Deal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.house = inits.isInitialized("house") ? new ssafy.ssafyhome.house.domain.QHouse(forProperty("house"), inits.get("house")) : null;
        this.member = inits.isInitialized("member") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("member")) : null;
    }

}

