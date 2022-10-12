package com.lianwei.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Logistics implements Serializable {
    @ApiModelProperty(value = "物流id",hidden = true)
    private Integer logisticsId;
    @ApiModelProperty(value = "物流单号",hidden = true)
    private String logisticsNumber;
    @ApiModelProperty(value = "物流件的类型")
    private String logisticsType;
    @ApiModelProperty("寄件人id")
    private Integer sendUserId;
    @ApiModelProperty(value = "派件人id",hidden = true)
    private List<Integer> dispatchUserId;
    @ApiModelProperty("收件人姓名")
    private String receiveUserName;
    @ApiModelProperty("收件人手机号码")
    private String receiveUserPhone;
    @ApiModelProperty("寄件地址")
    private List<BigDecimal> sendAddress;
    @ApiModelProperty("收件地址")
    private List<BigDecimal> receiveAddress;
    @ApiModelProperty(value = "途径地址",hidden = true)
    private List<List<BigDecimal>> passAddresses;
    @ApiModelProperty(value = "物流状态",hidden = true)
    private String logisticsStatus;
    @ApiModelProperty(value = "寄件时间",hidden = true)
    private Date sendTime;
    @ApiModelProperty(value = "签收时间",hidden = true)
    private Date receiveTime;
    @ApiModelProperty("物流总重")
    private BigDecimal weight;
    @ApiModelProperty("物流费用")
    private BigDecimal cost;
    @ApiModelProperty("派件公司")
    private String dispatchCompany;
    @ApiModelProperty(value = "到达每个目的地的时间",hidden = true)
    private List<Date> passTime;
}
