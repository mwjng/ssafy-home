package ssafy.ssafyhome.directmessage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectMessage is a Querydsl query type for DirectMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectMessage extends EntityPathBase<DirectMessage> {

    private static final long serialVersionUID = -532157575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectMessage directMessage = new QDirectMessage("directMessage");

    public final ssafy.ssafyhome.common.auditing.QBaseEntity _super = new ssafy.ssafyhome.common.auditing.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ssafy.ssafyhome.member.domain.QMember receiver;

    public final ssafy.ssafyhome.member.domain.QMember sender;

    public final EnumPath<MessageStatus> status = createEnum("status", MessageStatus.class);

    public QDirectMessage(String variable) {
        this(DirectMessage.class, forVariable(variable), INITS);
    }

    public QDirectMessage(Path<? extends DirectMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectMessage(PathMetadata metadata, PathInits inits) {
        this(DirectMessage.class, metadata, inits);
    }

    public QDirectMessage(Class<? extends DirectMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new ssafy.ssafyhome.member.domain.QMember(forProperty("sender")) : null;
    }

}

