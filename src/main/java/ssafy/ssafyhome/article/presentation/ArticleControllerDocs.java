package ssafy.ssafyhome.article.presentation;

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
import ssafy.ssafyhome.article.presentation.request.ArticleSearchCondition;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.comment.application.response.CommentsResponse;
import ssafy.ssafyhome.comment.presentation.request.CommentCreateRequest;
import ssafy.ssafyhome.comment.presentation.request.CommentSearchCondition;

import java.util.List;

@Tag(name = "article 컨트롤러", description = "article에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
@RequestMapping("/articles")
public interface ArticleControllerDocs {

    @Operation(summary = "내가 작성한 모든 article 조회", description = "내가 작성한 모든 article의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticlesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @GetMapping
    ResponseEntity<ArticlesResponse> searchMyArticles(
            final AccessContext accessContext,
            @Parameter(name = "검색 조건") final ArticleSearchCondition articleSearchCondition
    );

    @Operation(summary = "article 수정", description = "해당하는 article을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> update(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long articleId,
            @Parameter(name = "article") final ArticleUpdateRequest articleUpdateRequest,
            @Parameter(name = "image") List<MultipartFile> images
    );

    @Operation(summary = "article 삭제", description = "해당하는 article을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없다.")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long id
    );

    @Operation(summary = "article에 해당하는 모든 comment 조회", description = "article에 해당하는 모든 comment 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @GetMapping("/{articleId}/comments")
    ResponseEntity<CommentsResponse> searchComments(
            @Parameter(name = "id") final Long articleId,
            @Parameter(name = "검색 조건") final CommentSearchCondition commentSearchCondition
    );

    @Operation(summary = "article에 해당하는 comment 생성", description = "article에 해당하는 comment를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없다.")
    })
    @PostMapping("/{articleId}/comments")
    ResponseEntity<Void> createComment(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long articleId,
            @Parameter(name = "댓글") final CommentCreateRequest commentCreateRequest,
            @Parameter(name = "image") MultipartFile image
    );
}
