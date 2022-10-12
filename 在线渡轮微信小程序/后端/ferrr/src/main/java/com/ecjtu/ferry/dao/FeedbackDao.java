package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Feedback;
import com.ecjtu.ferry.pojo.vo.FeedbackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackDao {

    /**
     * 添加反馈信息
     * @param feedback    反馈信息实体
     */
    public void addFeedback(Feedback feedback);

    /**
     * 根据条件分页查询反馈信息
     * @param feedbackVO  封装的条件实体
     * @return
     */
    public List<Feedback> findByCondition(FeedbackVO feedbackVO);

    /**
     * 查询总记录数
     * @return
     */
    public int findAllCount(FeedbackVO feedbackVO);

    /**
     * 根据反馈id删除反馈信息
     * @param fid  反馈信息主键id
     */
    public void deleteById(Integer fid);

    /**
     * 根据条件删除反馈信息
     * @param feedbackVO    封装的条件实体
     */
    public void deleteByCondition(FeedbackVO feedbackVO);
}
