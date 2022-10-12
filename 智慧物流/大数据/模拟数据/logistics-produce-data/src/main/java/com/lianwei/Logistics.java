package com.lianwei;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Logistics {
    private Integer logisticsId;
    private String sendProvince;
    private String sendCity;
    private String receiveProvince;
    private String receiveCity;
    private String sendTime;
    private String dispatchProvince;
    private String dispatchCity;
    private String dispatchedProvince;
    private String dispatchedCity;
    private String dispatchedTime;
    private Integer dispatchedLost;
    private String signTime;
    private String backTime;
    private String company;
    private String status;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(logisticsId+"_");
        sb.append(sendProvince+"_");
        sb.append(sendCity+"_");
        sb.append(receiveProvince+"_");
        sb.append(receiveCity+"_");
        sb.append(sendTime+"_");
        sb.append(dispatchProvince+"_");
        sb.append(dispatchCity+"_");
        sb.append(dispatchedProvince+"_");
        sb.append(dispatchedCity+"_");
        sb.append(dispatchedTime+"_");
        sb.append(dispatchedLost+"_");
        sb.append(signTime+"_");
        sb.append(backTime+"_");
        sb.append(company+"_");
        sb.append(status);
        return sb.toString();
    }
}
