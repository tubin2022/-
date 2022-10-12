package com.ecjtu.ferry.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer pageNo;
    private Integer pageSize;
    private Integer uid;
    private String uname;
    private String phone;
    private String email;
}
