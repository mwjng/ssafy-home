package ssafy.ssafyhome.deal.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.deal.application.response.DealResponse;
import ssafy.ssafyhome.deal.application.response.DealsResponse;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.deal.presentation.request.DealUpdateRequest;

import java.util.List;

@Tag(name = "Deal 컨트롤러", description = "deal에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
@RequestMapping("/deals")
public interface DealControllerDocs {

    @Operation(summary = "내가 작성한 모든 deal 조회", description = "내가 작성한 모든 deal의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealsResponse.class)))
    })
    @GetMapping
    ResponseEntity<DealsResponse> searchMyDeals(final AccessContext accessContext);

    @Operation(summary = "deal 조회", description = "deal의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DealResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 deal을 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    ResponseEntity<DealResponse> search(@Parameter(name = "id") Long id);

    @Operation(summary = "deal 생성", description = "deal을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @PostMapping
    ResponseEntity<Void> create(
            final AccessContext accessContext,
            @Parameter(name = "deal") final DealCreateRequest dealCreateRequest,
            @Parameter(name = "image") List<MultipartFile> images
    );

    @Operation(summary = "deal 수정", description = "deal을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> update(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id,
            @Parameter(name = "deal") final DealUpdateRequest dealUpdateRequest,
            @Parameter(name = "image") List<MultipartFile> images
    );

    @Operation(summary = "deal 삭제", description = "deal을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id
    );
}
