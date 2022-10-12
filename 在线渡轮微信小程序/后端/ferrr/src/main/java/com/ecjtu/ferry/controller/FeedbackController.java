package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.Feedback;
import com.ecjtu.ferry.pojo.vo.FeedbackVO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.FeedbackServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "反馈信息相关接口")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackServiceImpl iFeedbackService;


    @ApiOperation("添加用户反馈信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "反馈用户id",dataType = "String",required = true),
            @ApiImplicitParam(name = "context",value = "反馈内容",dataType = "String")
    })
    @PostMapping("/fed")
    public ResultVO feedback(Integer uid,String context) throws ConditionNotException {
        Date nowDate = new Date();
        Feedback feedback = new Feedback(null,uid,context,nowDate);
        iFeedbackService.addFeedback(feedback);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @ApiOperation("分页查询反馈信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
            @ApiImplicitParam(name = "uid",value = "反馈用户id",dataType = "String"),
            @ApiImplicitParam(name = "context",value = "反馈信息",dataType = "String"),
            @ApiImplicitParam(name = "fdate",value = "反馈时间",dataType = "String")
    })
    @GetMapping("pageQuery")
    public ResultVO findFeedback(Integer pageNo,Integer pageSize,Integer uid,String context,String fdate) {
        if(context != null && context != ""){
            context = "%"+context+"%";
        }
        if(fdate != null && fdate != ""){
            fdate = "%"+fdate+"%";
        }
        Integer index = (pageNo - 1)*pageSize;
        FeedbackVO feedbackVO = new FeedbackVO(index,pageSize,uid,context,fdate);
        List<Feedback> feedbacks = iFeedbackService.findByCondition(feedbackVO);
        int total = iFeedbackService.findAllCount(feedbackVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",feedbacks);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("total",total);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }


    @ApiOperation("根据反馈信息id删除反馈信息")
    @ApiImplicitParam(name = "fid",value = "反馈信息id",dataType = "String",required = true)
    @PostMapping("/deleteById")
    public ResultVO deleteById(Integer fid){
        iFeedbackService.deleteById(fid);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @ApiOperation("根据条件部分删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "反馈用户id",dataType = "String"),
            @ApiImplicitParam(name = "context",value = "反馈内容",dataType = "String"),
            @ApiImplicitParam(name = "fdate",value = "反馈时间",dataType = "String")
    })
    @PostMapping("/deleteByCondition")
    public ResultVO deleteByCondition(Integer uid,String context,String fdate) throws Exception {
        if(context != null){
            context = "%"+context+"%";
        }
        if(fdate != null){
            fdate = "%"+fdate+"%";
        }
        FeedbackVO feedbackVO = new FeedbackVO(null,null,uid,context,fdate);
        iFeedbackService.deleteByCondition(feedbackVO);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }
}
