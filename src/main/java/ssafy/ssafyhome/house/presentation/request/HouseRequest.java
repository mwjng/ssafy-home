package ssafy.ssafyhome.house.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ssafy.ssafyhome.house.domain.House;
import ssafy.ssafyhome.region.domain.Region;

public record HouseRequest(
        @NotBlank(message = "이름 입력해주세요.")
        String name,

        @NotBlank(message = "지번을 입력해주세요.")
        String jibun,

        @NotNull(message = "준공연도를 입력해주세요.")
        Long buildYear,

        @NotBlank(message = "도로명을 입력해주세요.")
        String road,

        @NotBlank(message = "본번을 입력해주세요.")
        String bonbun,

        @NotBlank(message = "부번을 입력해주세요.")
        String bubun,

        @NotBlank(message = "위도를 입력해주세요.")
        String latitude,

        @NotBlank(message = "경도를 입력해주세요.")
        String longitude,

        @NotBlank(message = "타입을 입력해주세요.")
        String houseType,

        @NotBlank(message = "시를 입력해주세요.")
        String sido,

        @NotBlank(message = "구를 입력해주세요.")
        String gugun,

        @NotBlank(message = "동을 입력해주세요.")
        String dong
) {
        public House toMember(final String imagePath, final Region region) {
                return House.builder()
                    .name(name)
                    .buildYear(buildYear)
                    .jibun(jibun)
                    .road(road)
                    .bonbun(bonbun)
                    .bubun(bubun)
                    .latitude(latitude)
                    .longitude(longitude)
                    .dirName(imagePath)
                    .region(region)
                    .build();
        }
}
