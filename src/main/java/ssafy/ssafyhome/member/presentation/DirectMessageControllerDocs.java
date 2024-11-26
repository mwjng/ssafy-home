package ssafy.ssafyhome.member.presentation;

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
import ssafy.ssafyhome.auth.presentation.UserAccess;
import ssafy.ssafyhome.member.application.response.*;
import ssafy.ssafyhome.member.presentation.request.SendMessageRequest;

@Tag(name = "DirectMessage 컨트롤러", description = "DirectMessage에 대한 등록, 수정, 삭제, 조회를 처리 하는 클래스.")
@RequestMapping("/direct-messages")
public interface DirectMessageControllerDocs {

    @Operation(summary = "받은 DirectMessage 전체 조회", description = "받은 전체 DirectMessage 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReceivedMessagesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping("/received")
    ResponseEntity<ReceivedMessagesResponse> searchReceivedMessages(
            final AccessContext accessContext,
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 관심 지역 ID") Long cursorId
    );

    @Operation(summary = "보낸 DirectMessage 전체 조회", description = "보낸 전체 DirectMessage 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SentMessagesResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping("/sent")
    ResponseEntity<SentMessagesResponse> searchSentMessages(
            final AccessContext accessContext,
            @Parameter(name = "페이징 개수") int size,
            @Parameter(name = "마지막 관심 지역 ID") Long cursorId
    );

    @Operation(summary = "보낸 DirectMessage 상세 조회", description = "보낸 DirectMessage 상세 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SentMessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @UserAccess
    @GetMapping("/sent/{directMessageId}")
    ResponseEntity<SentMessageResponse> searchSentMessage(
            final AccessContext accessContext,
            @Parameter(name = "directMessageId") final Long directMessageId);

    @Operation(summary = "받은 DirectMessage 상세 조회", description = "받은 DirectMessage 상세 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReceivedMessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @UserAccess
    @GetMapping("/received/{directMessageId}")
    ResponseEntity<ReceivedMessageResponse> searchReceivedMessage(
            final AccessContext accessContext,
            @Parameter(name = "directMessageId") final Long directMessageId);

    @Operation(summary = "안읽은 DirectMessage 조회", description = "안읽은 DirectMessage가 있는지 확인하여 결과를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnreadMessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다.")
    })
    @GetMapping("/search-unread")
    ResponseEntity<UnreadMessageResponse> searchUnRead(final AccessContext accessContext);

    @Operation(summary = "DirectMessage 전송", description = "DirectMessage를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })

    @PostMapping("/{receiverId}")
    ResponseEntity<Void> send(
            final AccessContext accessContext,
            @Parameter(name = "receiverId") Long receiverId,
            @Parameter(name = "message") SendMessageRequest sendMessageRequest
    );

    @Operation(summary = "DirectMessage 삭제", description = "DirectMessage를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "검색 결과가 없습니다.")
    })
    @DeleteMapping("/{directMessageId}")
    ResponseEntity<Void> delete(
            final AccessContext accessContext,
            @Parameter(name = "directMessageId") Long directMessageId
    );
}
