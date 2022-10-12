package com.lianwei.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SelectLogisticsVO {
    @ApiModelProperty(value = "物流id",hidden = true)
    private Integer logisticsId;
    @ApiModelProperty(value = "物流单号",hidden = true)
    private String logisticsNumber;
    @ApiModelProperty("收件人姓名")
    private String receiveUserName;
    @ApiModelProperty("收件人手机号码")
    private String receiveUserPhone;
    @ApiModelProperty("收件地址")
    private List<String> receiveAddress;
    @ApiModelProperty(value = "物流状态",hidden = true)
    private String logisticsStatus;
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
    @ApiModelProperty(value = "当前页")
    private Integer pageNum;
    @ApiModelProperty("寄件人id")
    private Integer sendUserId;
}
