package com.sakurapuare.boatmanagement.pojo.vo;

import com.sakurapuare.boatmanagement.pojo.vo.base.BaseBoatsVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BoatVO extends BaseBoatsVO {
    private String typeName;
}
