package ssafy.ssafyhome.article.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.article.application.response.ArticlesResponse;
import ssafy.ssafyhome.article.presentation.request.ArticleCreateRequest;
import ssafy.ssafyhome.article.presentation.request.ArticleUpdateRequest;
import ssafy.ssafyhome.auth.domain.AccessContext;

import java.util.List;

@Tag(name = "article 컨트롤러", description = "article에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public interface ArticleControllerDocs {

    @Operation(summary = "내가 작성한 모든 article 조회", description = "내가 작성한 모든 article의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticlesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    ResponseEntity<ArticlesResponse> getMyArticles(
            final AccessContext accessContext,
            final Pageable pageable,
            final HttpServletRequest request
    );

    @Operation(summary = "내가 관심 등록한 모든 article 조회", description = "내가 관심 등록한 모든 article의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticlesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    ResponseEntity<ArticlesResponse> getLikeArticles(
            final AccessContext accessContext,
            final Pageable pageable,
            final HttpServletRequest request
    );

    @Operation(summary = "houseId에 해당하는 모든 article 조회", description = "houseId에 해당하는 모든 article의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticlesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    @GetMapping("/houses/{houseId}/articles")
    ResponseEntity<ArticlesResponse> getArticles(
            @Parameter(name = "houseId") final Long houseId,
            final Pageable pageable,
            final HttpServletRequest request
    );

    @Operation(summary = "houseId에 해당하는 article 생성", description = "houseId에 해당하는 article을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    @PostMapping("/houses/{houseId}/articles")
    ResponseEntity<Void> createArticle(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "content") ArticleCreateRequest articleCreateRequest,
            @Parameter(name = "images") final List<MultipartFile> images
    );

    @Operation(summary = "article 수정", description = "해당하는 article을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다.")
    })
    ResponseEntity<Void> updateArticle(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long articleId,
            @Parameter(name = "article") final ArticleUpdateRequest articleUpdateRequest,
            @Parameter(name = "image") List<MultipartFile> images
    );

    @Operation(summary = "article 삭제", description = "해당하는 article을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다.")
    })
    ResponseEntity<Void> deleteArticle(
            final AccessContext accessContext,
            @Parameter(name = "id") final Long articleId
    );
}
