package ssafy.ssafyhome.notice.presentation;

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
import ssafy.ssafyhome.notice.application.response.NoticeResponse;
import ssafy.ssafyhome.notice.application.response.NoticesResponse;
import ssafy.ssafyhome.notice.presentation.request.NoticeCreateRequest;
import ssafy.ssafyhome.notice.presentation.request.NoticeUpdateRequest;

@Tag(name = "공지사항 컨트롤러", description = "공지사항에 대한 생성, 조회, 수정, 삭제를 처리 하는 클래스.")
@RequestMapping("/notices")
public interface NoticeControllerDocs {

    @Operation(summary = "모든 공지사항 조회", description = "모든 공지사항의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticesResponse.class)))
    })
    @GetMapping
    ResponseEntity<NoticesResponse> searchAll(
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 댓글 ID") Long cursorId
    );

    @Operation(summary = "공지사항 조회", description = "해당 공지사항의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeResponse.class))),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @GetMapping("/{id}")
    ResponseEntity<NoticeResponse> search(@Parameter(name = "id") final Long id);

    @Operation(summary = "공지사항 생성", description = "공지사항을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @PostMapping
    ResponseEntity<Void> create(
            final AccessContext accessContext,
            @Parameter(name = "공지사항") NoticeCreateRequest noticeCreateRequest);

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> update(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id,
            @Parameter(name = "공지사항") NoticeUpdateRequest noticeUpdateRequest
    );

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id);
}
