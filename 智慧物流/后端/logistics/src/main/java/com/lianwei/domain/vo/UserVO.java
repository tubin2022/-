package com.lianwei.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UserVO implements Serializable {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户登录密码",hidden = true)
    private String userPassword;
    @ApiModelProperty("用户昵称")
    private String userName;
    @ApiModelProperty("用户性别")
    private String userGender;
    @ApiModelProperty("用户手机号码")
    private String userPhone;
    @ApiModelProperty("用户出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;
    @ApiModelProperty(value = "历史头像",hidden = true)
    private List<String> passFace;
    @ApiModelProperty("用户头像")
    private String nowFace;
}
