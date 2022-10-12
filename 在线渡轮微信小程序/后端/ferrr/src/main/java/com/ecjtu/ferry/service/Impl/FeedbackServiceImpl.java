package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.dao.FeedbackDao;
import com.ecjtu.ferry.pojo.Feedback;
import com.ecjtu.ferry.pojo.vo.FeedbackVO;
import com.ecjtu.ferry.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    /**
     * 用户反馈
     * @param feedback
     */
    @Override
    public void addFeedback(Feedback feedback) throws ConditionNotException {
        Integer uid = feedback.getUid();
        if(uid == null){
            throw new ConditionNotException("反馈用户id不能为空");
        }
        feedbackDao.addFeedback(feedback);
    }

    /**
     * 分页查询反馈信息，包含条件查询
     * @param feedbackVO
     * @return List<Feedback>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findByCondition(FeedbackVO feedbackVO) {
        return feedbackDao.findByCondition(feedbackVO);
    }

    /**
     * 查询总记录数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int findAllCount(FeedbackVO feedbackVO) {
        return feedbackDao.findAllCount(feedbackVO);
    }

    /**
     * 根据反馈信息id删除反馈信息
     * @param fid
     */
    @Override
    public void deleteById(Integer fid) {
        feedbackDao.deleteById(fid);
    }

    /**
     * 根据条件删除反馈信息
     * @param feedbackVO
     */
    @Override
    public void deleteByCondition(FeedbackVO feedbackVO) throws Exception {
        Integer uid = feedbackVO.getUid();
        String context = feedbackVO.getContext();
        String fdate = feedbackVO.getFdate();
        //没有输入条件抛出异常
        if(uid ==null && context == null && fdate == null){
            throw new ConditionNotException("条件不能为空");
        }else{
            //否则根据条件进行批量删除
            feedbackDao.deleteByCondition(feedbackVO);
        }
    }
}
