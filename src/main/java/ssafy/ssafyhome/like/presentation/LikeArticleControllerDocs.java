package ssafy.ssafyhome.like.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.ssafyhome.auth.domain.AccessContext;

@Tag(name = "관심 Article 컨트롤러", description = "관심 Article에 대한 조회, 생성, 삭제를 처리 하는 클래스.")
public interface LikeArticleControllerDocs {

    @Operation(summary = "관심 Article 생성", description = "관심 Article을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 article을 찾을 수 없습니다.")
    })
    @PostMapping("/articles/{articleId}/like")
    ResponseEntity<Void> createLikeArticle(
            final AccessContext accessContext,
            @Parameter(name = "articleId") final Long articleId
    );

    @Operation(summary = "관심 Article 삭제", description = "관심 Article을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 article을 찾을 수 없습니다.")
    })
    @DeleteMapping("/articles/{articleId}/like")
    ResponseEntity<Void> deleteLikeArticle(
            final AccessContext accessContext,
            @Parameter(name = "articleId") final Long articleId
    );
}
