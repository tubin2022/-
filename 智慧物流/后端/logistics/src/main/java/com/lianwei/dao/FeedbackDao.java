package com.lianwei.dao;

import com.lianwei.domain.entity.Feedback;
import com.lianwei.domain.vo.FeedbackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 反馈Dao
 */
@Mapper
public interface FeedbackDao {
    /**
     * 添加反馈信息
     * @param feedback
     */
    public void insertFeedbackInfo(Feedback feedback);

    /**
     * 根据条件分页查询反馈信息
     * @param feedbackVO
     * @return
     */
    public List<Feedback> pageQueryFeedbackInfo(FeedbackVO feedbackVO);

    /**
     * 根据条件分页查询反馈记录总数
     * @param feedbackVO
     * @return
     */
    public int count(FeedbackVO feedbackVO);
}
