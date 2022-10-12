package com.lianwei.service;

import com.lianwei.domain.entity.Feedback;
import com.lianwei.domain.vo.FeedbackVO;
import com.lianwei.domain.vo.ResultVO;

/**
 * 用户反馈service
 */
public interface FeedbackService {
    /**
     * 用户反馈
     * @param feedUserId 反馈用户id
     * @param logisticsId 反馈物流id
     * @param feedContext 反馈内容
     */
    public void feedback(Integer feedUserId,Integer logisticsId,String feedContext);

    /**
     * 根据条件分页查询反馈信息
     * @param feedbackVO
     * @return
     */
    public ResultVO pageQueryFeedbackInfo(FeedbackVO feedbackVO);
}
