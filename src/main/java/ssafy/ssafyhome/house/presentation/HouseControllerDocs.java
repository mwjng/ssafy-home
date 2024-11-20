package ssafy.ssafyhome.house.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
import ssafy.ssafyhome.house.presentation.request.HouseRegistRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;
import ssafy.ssafyhome.house.presentation.request.HouseUpdateRequest;

import java.util.List;

@Tag(name = "House 컨트롤러", description = "house에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public interface HouseControllerDocs {

    @Operation(summary = "모든 house 조회", description = "모든 house의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HousesResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<HousesResponse> searchAll(
        @Parameter(name = "검색 조건") final HouseSearchRequest houseSearchRequest, final HttpServletRequest request);

    @Operation(summary = "house 조회", description = "house의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HouseResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<HouseResponse> search(
            @Parameter(name = "id") final Long id,
            @Parameter(name = "검색 조건") final HouseSearchRequest houseSearchRequest
    );

    @Operation(summary = "house 생성", description = "house를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<Void> create(
            final AccessContext accessContext,
            @Parameter(name = "house") final HouseRegistRequest houseRegistRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );

    @Operation(summary = "house 수정", description = "house를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<Void> update(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id,
            @Parameter(name = "house") final HouseUpdateRequest houseUpdateRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );

    @Operation(summary = "house 삭제", description = "house를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id
    );

    @Operation(summary = "house에 해당하는 모든 deal 조회", description = "house에 해당하는 모든 deal의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealsResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<DealsResponse> searchDeals(
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "검색 조건") final DealSearchCondition dealSearchCondition
    );

    @Operation(summary = "house에 해당하는 deal을 생성", description = "house에 해당하는 deal을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<Void> createDeal(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "deal") final DealCreateRequest dealCreateRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );

    @Operation(summary = "house에 해당하는 모든 article 조회", description = "house에 해당하는 모든 article의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticlesResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<ArticlesResponse> searchArticles(
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "검색 조건") final ArticleSearchCondition articleSearchCondition
    );

    @Operation(summary = "house에 해당하는 article을 생성", description = "house에 해당하는 article을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<Void> createArticle(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "article") final ArticleCreateRequest articleCreateRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );
}
