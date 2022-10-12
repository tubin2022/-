package com.lianwei.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UpdateLogisticsVO {
    @ApiModelProperty(value = "物流id")
    private Integer logisticsId;
    @ApiModelProperty(value = "派件人id")
    private Integer dispatchUserId;
    @ApiModelProperty(value = "途径地址")
    private List<BigDecimal> passAddress;
    @ApiModelProperty(value = "物流状态")
    private String logisticsStatus;
    @ApiModelProperty(value = "签收时间")
    private Date receiveTime;
    @ApiModelProperty(value = "到达时间")
    private List<Date> passTime;
}
