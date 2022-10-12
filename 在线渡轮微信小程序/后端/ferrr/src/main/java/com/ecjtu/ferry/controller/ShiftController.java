package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.pojo.vo.ShiftVO;
import com.ecjtu.ferry.service.Impl.ShiftServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/shift")
@Api(tags = "班次管理相关接口")
public class ShiftController {
    @Autowired
    private ShiftServiceImpl iShiftService;

    @PostMapping("/addShift")
    @ApiOperation("添加轮渡班次")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startCity",value = "出发城市",dataType = "String",required = true),
            @ApiImplicitParam(name = "endCity",value = "到达城市",dataType = "String",required = true),
            @ApiImplicitParam(name = "startTime",value = "出发时间",dataType = "String",required = true),
            @ApiImplicitParam(name = "endTime",value = "到达时间",dataType = "String",required = true)
    })
    public ResultVO addShift(String startCity,String endCity,String startTime,String endTime) throws ConditionNotException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(startTime == null || endTime == null){
            throw new ConditionNotException("添加条件不能为空");
        }else{
            Date parseStartTime = sdf.parse(startTime);
            Date parseEndTime = sdf.parse(endTime);
            Shift shift = new Shift(null,startCity,endCity,parseStartTime,parseEndTime);
            iShiftService.addShift(shift);
            ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
            return resultVO;
        }
    }

    @PostMapping("/deleteShift")
    @ApiOperation("根据班次id删除")
    @ApiImplicitParam(name = "sid",value = "班次id",dataType = "String",required = true)
    public ResultVO deleteShift(Integer sid) throws ConditionNotException, CanNotDeleteShiftException {
        iShiftService.deleteShift(sid);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @GetMapping("/pageQuery")
    @ApiOperation("分页查询班次信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
            @ApiImplicitParam(name = "sid",value = "班次id",dataType = "String"),
            @ApiImplicitParam(name = "startCity",value = "出发城市",dataType = "String"),
            @ApiImplicitParam(name = "arriveCity",value = "到达城市",dataType = "String"),
            @ApiImplicitParam(name = "startTime",value = "出发时间",dataType = "String"),
            @ApiImplicitParam(name = "arriveTime",value = "到达时间",dataType = "String"),
    })
    public ResultVO pageQueryShift(Integer pageNo,Integer pageSize,Integer sid,String startCity,String arriveCity,String startTime,String arriveTime){
        Integer index = (pageNo - 1)*pageSize;
        if(startCity != null && startCity != ""){
            startCity = "%"+startCity+"%";
        }
        if(arriveCity != null && arriveCity != ""){
            arriveCity = "%"+arriveCity+"%";
        }
        if(startTime != null && startTime != ""){
            startTime = "%"+startTime+"%";
        }
        if(arriveTime != null && arriveTime != ""){
            arriveTime = "%"+arriveTime+"%";
        }
        ShiftVO shiftVO = new ShiftVO(index,pageSize,sid,startCity,arriveCity,startTime,arriveTime);
        ResultVO resultVO = iShiftService.pageQueryShift(shiftVO);
        return  resultVO;
    }

    @PostMapping("/updateShift")
    @ApiOperation("修改班次信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid",value = "班次id",dataType = "String",required = true),
            @ApiImplicitParam(name = "startCity",value = "出发城市",dataType = "String"),
            @ApiImplicitParam(name = "arriveCity",value = "到达城市",dataType = "String"),
            @ApiImplicitParam(name = "startTime",value = "出发时间",dataType = "String"),
            @ApiImplicitParam(name = "arriveTime",value = "到达时间",dataType = "String"),
    })
    public ResultVO updateShift(Integer sid,String startCity,String arriveCity,String startTime,String arriveTime) throws ParseException, ConditionNotException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift();
        shift.setSid(sid);
        shift.setStartCity(startCity);
        shift.setEndCity(arriveCity);
        if(startTime != null){
            Date parseStartTime = sdf.parse(startTime);
            shift.setStartTime(parseStartTime);
        }
        if(arriveTime != null){
            Date parseArriveTime = sdf.parse(arriveTime);
            shift.setEndTime(parseArriveTime);
        }
        iShiftService.updateShift(shift);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }
}
