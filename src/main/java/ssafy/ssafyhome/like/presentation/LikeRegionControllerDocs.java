package ssafy.ssafyhome.like.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.like.application.response.LikeRegionsResponse;
import ssafy.ssafyhome.like.presentation.request.LikeRegionCreateRequest;

@Tag(name = "관심 지역 컨트롤러", description = "관심 지역에 대한 조회, 생성 삭제를 처리 하는 클래스.")
@RequestMapping("/regions/like")
public interface LikeRegionControllerDocs {

    @Operation(summary = "관심 지역 조회", description = "관심 지역에 해당하는 지역 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikeRegionsResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping
    ResponseEntity<LikeRegionsResponse> searchAll(
            final AccessContext accessContext,
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 관심 지역 ID") Long cursorId
    );

    @Operation(summary = "관심 지역 생성", description = "관심 지역을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @PostMapping
    ResponseEntity<Void> create(
            final AccessContext accessContext,
            @Parameter(name = "region") LikeRegionCreateRequest likeRegionCreateRequest
    );

    @Operation(summary = "관심 지역 삭제", description = "관심 지역을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @DeleteMapping("/{likeRegionId}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "likeRegionId") final Long likeRegionId
    );
}
