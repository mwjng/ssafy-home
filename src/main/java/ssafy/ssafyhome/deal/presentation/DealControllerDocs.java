package ssafy.ssafyhome.deal.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.deal.application.request.DealCondition;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealSearchCondition;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;
import ssafy.ssafyhome.house.application.response.HousesResponse;

import java.util.List;

import static ssafy.ssafyhome.common.util.UrlUtil.getBaseUrl;

@Tag(name = "Deal 컨트롤러", description = "deal에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public interface DealControllerDocs {

    @Operation(summary = "deal 조회", description = "houseId에 해당하는 deal을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealsResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    ResponseEntity<DealsResponse> getDeals(
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "검색 조건") DealSearchCondition dealSearchCondition,
            @Parameter(name = "한번에 가져올 갯수") int size,
            @Parameter(name = "cursorId") Long cursorId,
            final HttpServletRequest request
    );

    @Operation(summary = "Login한 사용자가 deal 조회", description = "Login한 사용자가 houseId에 해당하는 deal을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealsResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    ResponseEntity<DealsResponse> getDealsOnLogin(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "검색 조건") DealSearchCondition dealSearchCondition,
            @Parameter(name = "한번에 가져올 갯수") int size,
            @Parameter(name = "cursorId") Long cursorId,
            final HttpServletRequest request
    );

    @Operation(summary = "deal 수정", description = "deal을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 매물을 찾을 수 없습니다.")
    })
    ResponseEntity<Void> updateDeal(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long dealId,
            @Parameter(name = "deal") final DealUpdateRequest dealUpdateRequest,
            @Parameter(name = "image") List<MultipartFile> images
    );

    @Operation(summary = "deal 삭제", description = "deal을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 매물을 찾을 수 없습니다.")
    })
    ResponseEntity<Void> deleteDeal(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long dealId
    );
}
