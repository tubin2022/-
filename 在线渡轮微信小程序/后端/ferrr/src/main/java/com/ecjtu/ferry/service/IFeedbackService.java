package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.pojo.Feedback;
import com.ecjtu.ferry.pojo.vo.FeedbackVO;

import java.util.List;

public interface IFeedbackService {

    /**
     * 用户反馈
     * @param feedback
     */
    public void addFeedback(Feedback feedback) throws ConditionNotException;

    /**
     * 分页查询反馈信息，包含条件查询
     * @param feedbackVO
     * @return List<Feedback>
     */
    public List<Feedback> findByCondition(FeedbackVO feedbackVO);

    /**
     * 查询总记录数
     * @return
     */
    public int findAllCount(FeedbackVO feedbackVO);

    /**
     * 根据反馈信息id删除反馈信息
     * @param fid
     */
    public void deleteById(Integer fid);

    /**
     * 根据条件删除反馈信息
     * @param feedbackVO
     */
    public void deleteByCondition(FeedbackVO feedbackVO) throws Exception;
}
