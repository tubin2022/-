package com.lianwei.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User implements Serializable {
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "用户登录账号")
    private String userAccount;
    @ApiModelProperty("用户登录密码")
    private String userPassword;
    @ApiModelProperty("用户昵称")
    private String userName;
    @ApiModelProperty(hidden = true)
    private String passwordSalt;
    @ApiModelProperty("用户性别")
    private String userGender;
    @ApiModelProperty("用户手机号码")
    private String userPhone;
    @ApiModelProperty("用户出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;
    @ApiModelProperty(hidden = true)
    private Date registeTime;
    @ApiModelProperty(value = "历史头像",hidden = true)
    private List<String> passFace;
    @ApiModelProperty("用户头像")
    private String nowFace;
}
