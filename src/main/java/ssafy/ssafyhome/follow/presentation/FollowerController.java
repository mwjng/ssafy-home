package ssafy.ssafyhome.follow.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.follow.application.response.FollowersResponse;

@RestController
@RequestMapping("/followers")
public class FollowerController implements FollowerControllerDocs{

    @Override
    public ResponseEntity<FollowersResponse> searchFollower(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long memberId) {
        return null;
    }
}
