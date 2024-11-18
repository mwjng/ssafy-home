package ssafy.ssafyhome.follow.presentation;

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
import ssafy.ssafyhome.follow.application.response.FollowersResponse;

@Tag(name = "Follower 컨트롤러", description = "follower에 대한 조회, 삭제를 처리 하는 클래스.")
@RequestMapping("/followers")
public interface FollowerControllerDocs {

    @Operation(summary = "follower 조회", description = "follower에 해당하는 member 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FollowersResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping
    ResponseEntity<FollowersResponse> searchFollower(
            final AccessContext accessContext,
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 관심 지역 ID") Long cursorId
    );

    @Operation(summary = "follower 삭제", description = "follower를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @DeleteMapping("/{memberId}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "memberId") final Long memberId
    );
}
