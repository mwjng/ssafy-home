package ssafy.ssafyhome.house.domain;

import java.util.Arrays;

import static org.springframework.util.StringUtils.hasText;

public enum HouseType {

    APT,
    VILLA,
    DETACHED;

    public static HouseType getHouseType(String houseType) {
        if (!hasText(houseType)) {
            return null;
        }
        return Arrays.stream(HouseType.values())
            .filter(type -> type.name().equalsIgnoreCase(houseType))
            .findFirst()
            .orElse(null);
    }
}
