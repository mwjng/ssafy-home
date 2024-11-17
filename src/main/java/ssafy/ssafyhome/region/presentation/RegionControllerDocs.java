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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "지역 컨트롤러", description = "지역에 대한 조회를 처리 하는 클래스.")
@RequestMapping("/region")
public interface RegionControllerDocs {

    @Operation(summary = "시,도 조회", description = "모든 시, 도 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping("/sido")
    ResponseEntity<List<String>> searchSido();

    @Operation(summary = "구,군 조회", description = "시, 도에 해당하는 모든 구, 군 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping("/gugun")
    ResponseEntity<List<String>> searchGugun(@Parameter(name = "시,도") @RequestParam final String sido);

    @Operation(summary = "동 조회", description = "시, 도, 구, 군에 해당하는 모든 동 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping("/dong")
    ResponseEntity<List<String>> searchDong(
            @Parameter(name = "시,도") @RequestParam final String sido,
            @Parameter(name = "구,군") @RequestParam final String gugun
    );

    @Operation(summary = "regionId 조회", description = "시, 도, 구, 군, 동에 해당하는 regionId를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping
    ResponseEntity<Long> searchRegionId(
            @Parameter(name = "시,도") @RequestParam final String sido,
            @Parameter(name = "구,군") @RequestParam final String gugun,
            @Parameter(name = "동") @RequestParam final String dong
    );
}
