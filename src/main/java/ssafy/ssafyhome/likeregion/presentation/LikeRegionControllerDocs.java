package ssafy.ssafyhome.likeregion.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.likeregion.application.response.LikeRegionsResponse;

@Tag(name = "관심 지역 컨트롤러", description = "관심 지역에 대한 조회를 처리 하는 클래스.")
@RequestMapping("/like/region")
public interface LikeRegionControllerDocs {

    @Operation(summary = "관심 지역 조회", description = "관심 지역에 해당하는 지역 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LikeRegionsResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping
    ResponseEntity<LikeRegionsResponse> search(@AuthenticationPrincipal final AccessContext accessContext);
}
