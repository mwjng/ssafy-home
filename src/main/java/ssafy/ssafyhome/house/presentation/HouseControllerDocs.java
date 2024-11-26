package ssafy.ssafyhome.house.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ssafy.ssafyhome.auth.domain.AccessContext;
import ssafy.ssafyhome.deal.presentation.request.DealCreateRequest;
import ssafy.ssafyhome.house.application.response.HouseAllResponse;
import ssafy.ssafyhome.house.application.response.HouseDetailsResponse;
import ssafy.ssafyhome.house.application.response.HouseNamesResponse;
import ssafy.ssafyhome.house.application.response.HousesResponse;
import ssafy.ssafyhome.house.presentation.request.HouseRequest;
import ssafy.ssafyhome.house.presentation.request.HouseSearchRequest;
import ssafy.ssafyhome.house.presentation.request.SearchHouseNameRequest;

import java.util.List;

@Tag(name = "House 컨트롤러", description = "house에 대한 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
public interface HouseControllerDocs {

    @Operation(summary = "name에 해당하는 house name 조회", description = "name에 해당하는 house name의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HouseNamesResponse.class)))
    })
    @GetMapping("/name")
     ResponseEntity<HouseNamesResponse> getHouseNames(
            @ModelAttribute final SearchHouseNameRequest searchHouseNameRequest);

    @Operation(summary = "시,구,동에 해당하는 house 조회", description = "시,구,동에 해당하는 house의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HousesResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    ResponseEntity<HousesResponse> getHouses(
            @Parameter(name = "검색 조건") final HouseSearchRequest houseSearchRequest,
            final Pageable pageable,
            final HttpServletRequest request);

    @Operation(summary = "houseId에 해당하는 house 조회", description = "houseId에 해당하는 house의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청을 성공적으로 처리하였다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HouseDetailsResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    @GetMapping("/{houseId}")
    ResponseEntity<HouseDetailsResponse> getHouse(
            @PathVariable final Long houseId,
            final HttpServletRequest httpServletRequest
    );

    @Operation(summary = "house 생성", description = "house를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    ResponseEntity<Void> createHouse(
            final AccessContext accessContext,
            @Parameter(name = "house") final HouseRequest houseRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );

    @Operation(summary = "house 수정", description = "house를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    ResponseEntity<Void> updateHouse(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId,
            @Parameter(name = "house") final HouseRequest houseRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );

    @Operation(summary = "house 삭제", description = "house를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    ResponseEntity<Void> deleteHouse(
            final AccessContext accessContext,
            @Parameter(name = "houseId") final Long houseId
    );

    @Operation(summary = "house에 해당하는 deal을 생성", description = "house에 해당하는 deal을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청이 처리되어서 새로운 리소스가 생성되었다."),
            @ApiResponse(responseCode = "403", description = "해당 리소스에 접근할 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 house를 찾을 수 없습니다.")
    })
    ResponseEntity<Void> createDeal(
            final AccessContext accessContext,
            @Parameter(name = "deal") final DealCreateRequest dealCreateRequest,
            @Parameter(name = "image") final List<MultipartFile> images
    );
}
