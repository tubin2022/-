package com.lianwei.domain.dto;

import com.lianwei.domain.entity.User;
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
public class UserLogisticsDTO implements Serializable {
    private Integer logisticsId;
    private String logisticsNumber;
    private String logisticsType;
    private Integer sendUserId;
    private List<Integer> dispatchUserId;
    private String receiveUserName;
    private String receiveUserPhone;
    private List<BigDecimal> sendAddress;
    private List<BigDecimal> receiveAddress;
    private List<List<BigDecimal>> passAddresses;
    private String logisticsStatus;
    private Date sendTime;
    private Date receiveTime;
    private BigDecimal weight;
    private BigDecimal cost;
    private String dispatchCompany;
    private List<Date> passTime;
    private User sendUser;
}
