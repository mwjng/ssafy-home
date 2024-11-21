package ssafy.ssafyhome.house.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleCreateRequest;
import ssafy.ssafyhome.article.presentation.request.ArticleSearchCondition;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AdminAccess;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealSearchCondition;
import ssafy.ssafyhome.house.application.HouseService;
import ssafy.ssafyhome.house.application.response.HouseResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.*;

@RequiredArgsConstructor
@RequestMapping("/houses")
@RestController
public class HouseController implements HouseControllerDocs{

    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<HousesResponse> getHouses(
        @Valid @ModelAttribute final HouseSearchRequest houseSearchRequest,
        final HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(houseService.searchAll(
            houseSearchRequest,
            getBaseUrl(httpServletRequest)
        ));
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseResponse> getHouse(
        @PathVariable final Long houseId,
        final HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(houseService.search(
            houseId,
            getBaseUrl(httpServletRequest)
        ));
    }

    @PostMapping
    @AdminAccess
    public ResponseEntity<Void> createHouse(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestPart final HouseRequest houseRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        houseService.createHouse(houseRequest, images);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{houseId}")
    @AdminAccess
    public ResponseEntity<Void> updateHouse(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long houseId,
        @Valid @RequestPart final HouseRequest houseRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        houseService.updateHouse(houseId, houseRequest, images);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{houseId}")
    @AdminAccess
    public ResponseEntity<Void> deleteHouse(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long houseId
    ) {
        houseService.deleteHouse(houseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{houseId}/articles")
    @UserAccess
    public ResponseEntity<ArticlesResponse> getArticles(
        @PathVariable final Long houseId,
        @ModelAttribute final ArticleSearchCondition articleSearchCondition
    ) {

        return null;
    }

    @PostMapping("/{houseId}/deals")
    @AgentAccess
    public ResponseEntity<Void> createDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long houseId,
        @RequestPart final DealCreateRequest dealCreateRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        return null;
    }

    @PostMapping("/{houseId}/articles")
    @UserAccess
    public ResponseEntity<Void> createArticle(
        @AuthenticationPrincipal final AccessContext accessContext,
        @PathVariable final Long houseId,
        @RequestPart final ArticleCreateRequest articleCreateRequest,
        @RequestPart final List<MultipartFile> images
    ) {
        return null;
    }
}
