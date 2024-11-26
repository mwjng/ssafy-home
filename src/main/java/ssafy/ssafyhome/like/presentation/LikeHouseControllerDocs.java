package ssafy.ssafyhome.like.presentation;

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
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.like.application.response.LikeHousesResponse;
import ssafy.ssafyhome.member.presentation.request.LikeHouseCreateRequest;

@Tag(name = "관심 House 컨트롤러", description = "관심 House에 대한 조회, 생성, 삭제를 처리 하는 클래스.")
@RequestMapping("/houses/like")
public interface LikeHouseControllerDocs {

    @Operation(summary = "관심 House 조회", description = "관심 House에 해당하는 House 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikeHousesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping
    ResponseEntity<LikeHousesResponse> searchAll(
            final AccessContext accessContext,
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 관심 지역 ID") Long cursorId,
            HttpServletRequest request
    );

    @Operation(summary = "관심 House 생성", description = "관심 House를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @PostMapping("/{houseId}")
    ResponseEntity<Void> create(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId
    );

    @Operation(summary = "관심 House 삭제", description = "관심 House를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @DeleteMapping("/{houseId}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId
    );
}
