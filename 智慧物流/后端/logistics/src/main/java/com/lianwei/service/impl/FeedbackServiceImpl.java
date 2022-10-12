package com.lianwei.service.impl;

import com.lianwei.dao.FeedbackDao;
import com.lianwei.domain.entity.Feedback;
import com.lianwei.domain.vo.FeedbackVO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户反馈service实现类
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    /**
     * 用户反馈
     * @param feedUserId 反馈用户id
     * @param logisticsId 反馈物流id
     * @param feedContext 反馈内容
     */
    @Override
    public void feedback(Integer feedUserId,Integer logisticsId,String feedContext) {
        Feedback feedback = new Feedback();
        feedback.setUserId(feedUserId);
        feedback.setLogisticsId(logisticsId);
        feedback.setFeedbackContext(feedContext);
        feedback.setFeedTime(new Date());
        feedbackDao.insertFeedbackInfo(feedback);
    }

    /**
     * 根据条件分页查询反馈信息
     * @param feedbackVO
     * @return
     */
    @Override
    public ResultVO pageQueryFeedbackInfo(FeedbackVO feedbackVO) {
        List<Feedback> feedbacks = feedbackDao.pageQueryFeedbackInfo(feedbackVO);
        int count = feedbackDao.count(feedbackVO);
        Map<String,Object> map = new HashMap<>();
        map.put("count",count);
        map.put("data",feedbacks);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }
}
