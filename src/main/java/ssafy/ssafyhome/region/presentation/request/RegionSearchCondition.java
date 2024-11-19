package ssafy.ssafyhome.region.presentation.request;

import lombok.Builder;
import lombok.Getter;
import ssafy.ssafyhome.region.application.RegionField;

@Getter
public class RegionSearchCondition {
    private String sido;
    private String gugun;
    private String dong;
    private RegionField field;

    @Builder
    public RegionSearchCondition(String sido, String gugun, String dong, RegionField field) {
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
        this.field = field;
    }
}
