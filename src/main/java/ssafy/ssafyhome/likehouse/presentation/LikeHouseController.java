package ssafy.ssafyhome.likehouse.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.likehouse.application.response.LikeHousesResponse;

@RestController
@RequestMapping("/houses/like")
public class LikeHouseController implements LikeHouseControllerDocs{

    @Override
    @UserAccess
    public ResponseEntity<LikeHousesResponse> searchAll(final AccessContext accessContext, final int size, final Long cursorId) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> create(final AccessContext accessContext, final Long id) {
        return null;
    }

    @Override
    @UserAccess
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }
}
