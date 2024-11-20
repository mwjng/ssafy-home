package ssafy.ssafyhome.follow.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.follow.application.response.FollowingsResponse;

@RestController
@RequestMapping("/followings")
public class FollowingController implements FollowingControllerDocs{

    @Override
    @UserAccess
    public ResponseEntity<FollowingsResponse> searchFollower(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> create(final AccessContext accessContext, final Long memberId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long memberId) {
        return null;
    }
}
