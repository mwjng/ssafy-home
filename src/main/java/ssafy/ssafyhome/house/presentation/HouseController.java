package ssafy.ssafyhome.house.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AdminAccess;
import ssafy.ssafyhome.auth.presentation.AgentAccess;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.deal.application.DealService;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.house.application.HouseService;
import ssafy.ssafyhome.house.application.response.HouseDetailsResponse;
import ssafy.ssafyhome.house.application.response.HouseNamesResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;
import ssafy.ssafyhome.house.presentation.request.SearchHouseNameRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static ssafy.ssafyhome.common.util.UrlUtil.*;

@RequiredArgsConstructor
@RequestMapping("/houses")
@RestController
public class HouseController implements HouseControllerDocs{

    private final HouseService houseService;
    private final DealService dealService;

    @GetMapping("/name")
    public ResponseEntity<HouseNamesResponse> getHouseNames(
            @Valid @ModelAttribute final SearchHouseNameRequest searchHouseNameRequest){

        HouseNamesResponse response = houseService
                .searchByHouseName(searchHouseNameRequest.toHouseNameSearchCondition());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<HousesResponse> getHouses(
        @Valid @ModelAttribute final HouseSearchRequest houseSearchRequest,
        final Pageable pageable,
        final HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(houseService.searchAll(
            null,
            houseSearchRequest,
            pageable,
            getBaseUrl(httpServletRequest)
        ));
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDetailsResponse> getHouse(
        @PathVariable final Long houseId,
        final HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(houseService.search(
               null,
                houseId,
                getBaseUrl(httpServletRequest)
        ));
    }

    @UserAccess
    @GetMapping("/like-check")
    public ResponseEntity<HousesResponse> getHousesLike(
            @AuthenticationPrincipal AccessContext accessContext,
            @Valid @ModelAttribute final HouseSearchRequest houseSearchRequest,
            final Pageable pageable,
            final HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(houseService.searchAll(
                accessContext.getMemberId(),
                houseSearchRequest,
                pageable,
                getBaseUrl(httpServletRequest)
        ));
    }

    @UserAccess
    @GetMapping("/like-check/{houseId}")
    public ResponseEntity<HouseDetailsResponse> getHouseLike(
            @AuthenticationPrincipal AccessContext accessContext,
            @PathVariable final Long houseId,
            final HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(houseService.search(
                accessContext.getMemberId(),
                houseId,
                getBaseUrl(httpServletRequest)
        ));
    }

    @PostMapping
    @AdminAccess
    public ResponseEntity<Void> createHouse(
        @AuthenticationPrincipal final AccessContext accessContext,
        @Valid @RequestPart final HouseRequest houseRequest,
        @RequestPart(required = false) final List<MultipartFile> images
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
        @RequestPart(required = false) final List<MultipartFile> images
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

    @PostMapping("/deals")
    @AgentAccess
    public ResponseEntity<Void> createDeal(
        @AuthenticationPrincipal final AccessContext accessContext,
        @RequestPart final DealCreateRequest dealCreateRequest,
        @RequestPart(required = false) final List<MultipartFile> images
    ) {
        dealService.createDeal(accessContext.getMemberId(), dealCreateRequest, images);
        return ResponseEntity.status(CREATED).build();
    }
}
