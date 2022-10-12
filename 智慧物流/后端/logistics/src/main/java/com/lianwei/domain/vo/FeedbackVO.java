package com.lianwei.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class FeedbackVO {
    @ApiModelProperty(value = "反馈id")
    private Integer feedbackId;
    @ApiModelProperty(value = "反馈用户id")
    private Integer userId;
    @ApiModelProperty(value = "反馈物流id")
    private Integer logisticsId;
    @ApiModelProperty(value = "反馈内容")
    private String feedbackContext;
    @ApiModelProperty(value = "是否处理",notes = "0代表未处理,1代表已处理")
    private Integer isHandle;
    @ApiModelProperty(value = "处理人id")
    private Integer handleUserId;
    @ApiModelProperty(value = "反馈时间")
    private Date feedTime;
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
    @ApiModelProperty(value = "当前页")
    private Integer pageNum;
}
