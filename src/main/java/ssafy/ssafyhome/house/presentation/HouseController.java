package ssafy.ssafyhome.house.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleCreateRequest;
import ssafy.ssafyhome.article.presentation.request.ArticleSearchCondition;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealSearchCondition;
import ssafy.ssafyhome.house.application.response.HouseResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.presentation.request.HouseCreateRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchCondition;
import ssafy.ssafyhome.house.presentation.request.HouseUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController implements HouseControllerDocs{

    @Override
    public ResponseEntity<HousesResponse> searchAll(final HouseSearchCondition houseSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<HouseResponse> search(final Long id, final HouseSearchCondition houseSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<Void> create(final AccessContext accessContext, final HouseCreateRequest houseCreateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(final AccessContext accessContext, final Long id, final HouseUpdateRequest houseUpdateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(final AccessContext accessContext, final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<DealsResponse> searchDeals(final Long houseId, final DealSearchCondition dealSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createDeal(final AccessContext accessContext, final Long houseId, final DealCreateRequest dealCreateRequest, final List<MultipartFile> images) {
        return null;
    }

    @Override
    public ResponseEntity<ArticlesResponse> searchArticles(final Long houseId, final ArticleSearchCondition articleSearchCondition) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createArticle(final AccessContext accessContext, final Long houseId, final ArticleCreateRequest articleCreateRequest, final List<MultipartFile> images) {
        return null;
    }
}
