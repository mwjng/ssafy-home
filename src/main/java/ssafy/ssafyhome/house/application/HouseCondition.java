package ssafy.ssafyhome.house.application;

import lombok.Getter;
import ssafy.ssafyhome.house.domain.HouseType;

@Getter
public class HouseCondition {

    private int size;
    private Long cursorId;
    private String sido; // 서울특별시
    private String gugun;
    private String dong;
//    private Long regionId;
    private HouseType type;
    private Long startBuildYear;
    private Long endBuildYear;
    private String name;
    private Long houseId;
}
