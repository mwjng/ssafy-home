package ssafy.ssafyhome.comment.presentation;

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
import ssafy.ssafyhome.auth.presentation.AuthenticationPrincipal;
import ssafy.ssafyhome.comment.application.response.CommentResponse;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentRequest;

@Tag(name = "댓글 컨트롤러", description = "댓글에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
@RequestMapping("/comment")
public interface CommentControllerDocs {

    @Operation(summary = "모든 댓글 조회", description = "모든 댓글의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @GetMapping("/{articleId}")
    ResponseEntity<CommentsResponse> searchAll(
            @Parameter(name = "게시글 아이디") @PathVariable final Long articleId,
            @Parameter(name = "페이징 개수") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(name = "마지막 댓글 ID") @RequestParam(required = false, defaultValue = "0") Long cursorId
    );

    @Operation(summary = "내가 작성한 모든 댓글 조회", description = "내가 작성한 모든 댓글의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @GetMapping
    ResponseEntity<CommentsResponse> searchMyComment(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Parameter(name = "페이징 개수") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(name = "마지막 댓글 ID") @RequestParam(required = false, defaultValue = "0") Long cursorId
    );

    @Operation(summary = "댓글 조회", description = "해당 댓글의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    @GetMapping("/{commentId}")
    ResponseEntity<CommentResponse> search(@Parameter(name = "댓글 아이디") @PathVariable final Long commentId);

    @Operation(summary = "댓글 생성", description = "게시글에 해당하는 댓글을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @PostMapping("/{articleId}")
    ResponseEntity<Void> create(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Parameter(name = "게시글 아이디") @PathVariable final Long articleId,
            @Parameter(name = "댓글") @RequestBody final CommentRequest commentRequest
    );

    @Operation(summary = "댓글 수정", description = "해당하는 댓글을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    @PatchMapping("/{commentId}")
    ResponseEntity<Void> update(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Parameter(name = "게시글 아이디") @PathVariable final Long commentId,
            @Parameter(name = "댓글") @RequestBody final CommentRequest commentRequest
    );

    @Operation(summary = "댓글 삭제", description = "해당하는 댓글을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> delete(
            @AuthenticationPrincipal final AccessContext accessContext,
            @Parameter(name = "게시글 아이디") @PathVariable final Long commentId
    );
}
