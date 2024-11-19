package ssafy.ssafyhome.region.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssafy.ssafyhome.region.application.response.*;
import ssafy.ssafyhome.region.presentation.request.RegionSearchCondition;

@Tag(name = "지역 컨트롤러", description = "지역에 대한 조회를 처리 하는 클래스.")
@RequestMapping("/regions")
public interface RegionControllerDocs {

    @Operation(summary = "시, 도, 구, 군, 동 조회", description = "시, 도, 구, 군, 동 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping
    ResponseEntity<RegionResponse> search(@Parameter(name = "검색 조건") final RegionSearchCondition regionSearchCondition);

    @Operation(summary = "regionId 조회", description = "regionId를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionIdResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping("/id")
    ResponseEntity<RegionIdResponse> searchId(
            @Parameter(name = "sido") final String sido,
            @Parameter(name = "gugun") final String gugun,
            @Parameter(name = "dong") final String dong
    );
}
