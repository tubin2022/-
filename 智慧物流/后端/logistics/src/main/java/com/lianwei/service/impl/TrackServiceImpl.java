package com.lianwei.service.impl;

import com.lianwei.domain.entity.Track;
import com.lianwei.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 埋点数据service实现类
 */
@Service
public class TrackServiceImpl implements TrackService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * 将track写入到kafka
     * @param track
     */
    @Override
    public void saveTrack(Track track) {
        String sendTime = null;
        String dispatchedTime = null;
        String signTime = null;
        String backTime = null;

        if(track.getSendTime() != null){
            String sendTime1 = track.getSendTime();
            if(sendTime1.split("-").length > 1){
                sendTime = sendTime1;
            }else{
                Date sendDate = new Date(Long.valueOf(sendTime1));
                sendTime = sdf.format(sendDate);
            }
        }

        if(track.getDispatchedTime() != null){
            Long dispatchedTime1 = track.getDispatchedTime();
            Date dispatchedDate = new Date(dispatchedTime1);
            dispatchedTime = sdf.format(dispatchedDate);
        }

        if(track.getSignTime() != null){
            Long signTime1 = track.getSignTime();
            Date signDate = new Date(signTime1);
            signTime = sdf.format(signDate);
        }

        if(track.getBackTime() != null){
            Long backTime1 = track.getBackTime();
            Date backDate = new Date(backTime1);
            backTime = sdf.format(backDate);
        }

        String trackEvent = track.getLogisticsId()+"_"+track.getSendProvince()+"_"+track.getSendCity()+"_"+
        track.getReceiveProvince()+"_"+track.getReceiveCity()+"_"+sendTime+"_"+track.getStartProvince()+"_"+
        track.getStartCity()+"_"+track.getDispatchedProvince()+"_"+
        track.getDispatchedCity()+"_"+dispatchedTime+"_"+track.getDispatchedLost()+"_"+signTime+"_"+backTime+"_"+
        track.getCompany()+"_"+track.getLogisticsStatus();

        System.out.println(trackEvent);

        kafkaTemplate.send("logistics",trackEvent);
    }
}
