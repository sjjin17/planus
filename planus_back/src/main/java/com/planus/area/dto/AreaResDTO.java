package com.planus.area.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaResDTO {
    private long areaId;
    private String doName;
    private String siName;

    public AreaResDTO(long areaId, String doName, String siName) {
        this.areaId = areaId;
        this.doName = doName;
        this.siName = siName;
    }
}
