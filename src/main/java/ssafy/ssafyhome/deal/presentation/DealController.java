package ssafy.ssafyhome.deal.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.deal.application.response.DealResponse;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/deals")
public class DealController implements DealControllerDocs{

    @Override
    public ResponseEntity<DealsResponse> searchMyDeals(final AccessContext accessContext) {
        return null;
    }

    @Override
    public ResponseEntity<DealResponse> search(final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final DealCreateRequest dealCreateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final Long id, final DealUpdateRequest dealUpdateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }
}
