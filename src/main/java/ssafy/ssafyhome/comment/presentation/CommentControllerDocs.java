package ssafy.ssafyhome.comment.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;

@Tag(name = "Comment 컨트롤러", description = "댓글에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public interface CommentControllerDocs {

    @Operation(summary = "내가 작성한 모든 댓글 조회", description = "내가 작성한 모든 댓글의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    ResponseEntity<CommentsResponse> getMyComments(
            final AccessContext accessContext,
            final Pageable pageable
    );

    @Operation(summary = "댓글 삭제", description = "해당하는 댓글을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    ResponseEntity<Void> deleteComment(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long commentId
    );
}
