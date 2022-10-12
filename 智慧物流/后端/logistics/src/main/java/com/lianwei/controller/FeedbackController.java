package com.lianwei.controller;

import com.lianwei.domain.entity.Feedback;
import com.lianwei.domain.vo.FeedbackVO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户反馈controller
 */
@RestController
@RequestMapping("/feedback")
@Api(tags = "用户反馈管理接口")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation("反馈")
    @PostMapping("/feed")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedUserId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "logisticsId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "feedContext",paramType = "query",dataType = "String",required = true)
    })
    public ResultVO feedback(Integer feedUserId,Integer logisticsId,String feedContext){
        feedbackService.feedback(feedUserId,logisticsId,feedContext);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    @GetMapping("/pageQuery")
    @ApiOperation("分页查询反馈信息")
    public ResultVO pageQuery(@ModelAttribute FeedbackVO feedbackVO){
        ResultVO resultVO = feedbackService.pageQueryFeedbackInfo(feedbackVO);
        return resultVO;
    }
}
