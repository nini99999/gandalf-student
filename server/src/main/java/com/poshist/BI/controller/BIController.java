package com.poshist.BI.controller;

import com.poshist.BI.service.BIService;
import com.poshist.BI.vo.QueryVO;
import com.poshist.common.vo.BaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/BI")
public class BIController {
    @Autowired
    private BIService biService;

    /**
     * 获取在读学生总数
     * @return
     */
    @RequestMapping("/getStudentCount")
    public BaseVO getStudentCount(Authentication auth){
        String userName = (String) auth.getPrincipal();
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountStudent(userName));
        return baseVO;
    }

    /**
     * 获取校外人数
     * @return
     */
    @RequestMapping("/getCountStudentOutSchool")
    public BaseVO getCountStudentOutSchool(Authentication auth){
        String userName = (String) auth.getPrincipal();
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountStudentOutSchool(userName));
        return baseVO;
    }

    /**
     * 获取车辆外勤数
     * @return
     */
    @RequestMapping("/getCountCarControl")
    public BaseVO getCountCarControl(){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountCarControl());
        return baseVO;
    }

    /**
     * 获取车辆运营数
     * @return
     */
    @RequestMapping("/getCountCarOperation")
    public BaseVO getCountCarOperation(){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountCarOperation());
        return baseVO;
    }

    /**
     * 每日派车数量
     * @param params
     * @return
     */
    @RequestMapping("/getCountCarControlDaily")
    public BaseVO getCountCarControlDaily(@RequestBody Map params){
        QueryVO queryVO = toQueryVO(params);
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountCarControlDaily(queryVO));
        return baseVO;
    }

    /**
     * 部門拍車量
     * @param params
     * @return
     */
    @RequestMapping("/getCountCarControlDepartment")
    public BaseVO getCountCarControlDepartment(@RequestBody Map params){
        QueryVO queryVO = toQueryVO(params);
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountCarControlDepartment(queryVO));
        return baseVO;
    }

    /**
     * 每日请假
     * @param params
     * @return
     */
    @RequestMapping("/getCountStudentDaily")
    public BaseVO getCountStudentDaily(@RequestBody Map params){
        QueryVO queryVO = toQueryVO(params);
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountStudentDaily(queryVO));
        return baseVO;
    }

    /**
     * 部门请假
     * @param params
     * @return
     */
    @RequestMapping("/getCountStudentDepartment")
    public BaseVO getCountStudentDepartment(@RequestBody Map params){
        QueryVO queryVO = toQueryVO(params);
        BaseVO baseVO=new BaseVO();
        baseVO.setData(biService.getCountStudentDepartment(queryVO));
        return baseVO;
    }
    private QueryVO toQueryVO(@RequestBody Map params) {
        QueryVO queryVO = new QueryVO();
        if(null!=params.get("minTime")){
            queryVO.setMinTime(new Date(Long.valueOf(params.get("minTime").toString())));
        }
        if(null!=params.get("maxTime")) {
            queryVO.setMaxTime(new Date(Long.valueOf(params.get("maxTime").toString())));
        }
        return queryVO;
    }


}
