package ssafy.ssafyhome.house.presentation.request;

import lombok.Getter;
import ssafy.ssafyhome.house.domain.HouseType;

@Getter
public class HouseSearchCondition {
    private int size;
    private Long cursorId;
    private Long regionId;
    private HouseType type;
    private Long startBuildYear;
    private Long endBuildYear;
    private String name;

    public HouseSearchCondition(
            int size,
            Long cursorId,
            Long regionId,
            HouseType type,
            Long startBuildYear,
            Long endBuildYear,
            String name) {

        this.size = size;
        this.cursorId = cursorId;
        this.regionId = regionId;
        this.type = type;
        this.startBuildYear = startBuildYear;
        this.endBuildYear = endBuildYear;
        this.name = name;
    }
}
