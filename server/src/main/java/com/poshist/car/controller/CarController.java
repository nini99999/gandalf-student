package com.poshist.car.controller;

import com.poshist.car.entity.CarApplicant;
import com.poshist.car.service.CarService;
import com.poshist.car.vo.*;
import com.poshist.common.ExceptionEnum;
import com.poshist.common.RunTimeException;
import com.poshist.common.vo.BaseVO;
import com.poshist.common.vo.PageVO;
import com.poshist.sys.entity.User;
import com.poshist.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @RequestMapping("/getRepairCar")
    public BaseVO getRepairCar() {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getRepairCar());
        return baseVO;
    }

    @RequestMapping("/getMaintainCar")
    public BaseVO getMaintainCar() {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getMaintainCar());
        return baseVO;
    }
    @RequestMapping("/getNowStandByCar")
    public BaseVO getNowStandByCar() {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getNowStandByCar());
        return baseVO;
    }

    @RequestMapping("/getNowControlCar")
    public BaseVO getNowControlCar() {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getNowControlCar());
        return baseVO;
    }

    @RequestMapping("/cancelCarApplicant")
    public BaseVO cancelCarApplicant(@RequestBody CarApplicant carApplicant) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.cancelCarApplicant(carApplicant.getId()));
        return baseVO;
    }


    @RequestMapping("/getCar")
    public BaseVO getCar(@RequestBody CarVO carVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getCarById(carVO.getId()));
        return baseVO;
    }

    @RequestMapping("/addCarOperation")
    public BaseVO addCarOperation(@RequestBody CarOperationVO carOperationVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.saveCarOperation(carOperationVO));
        return baseVO;
    }

    @RequestMapping("/carReturn")
    public BaseVO carReturn(@RequestBody CarControlVO carControlVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.carReturn(carControlVO));
        return baseVO;
    }

    @RequestMapping("/getCarOperation")
    public BaseVO getCarOperation(@RequestBody CarOperationVO carOperationVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getCarOperationById(carOperationVO.getId()));
        return baseVO;
    }

    /**
     * 分页查询运营单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCarOperationList")
    public BaseVO getCarOperationList(@RequestBody Map paramMap) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        CarOperationVO carOperationVO = new CarOperationVO();
        carOperationVO.setCarNO((String) paramMap.get("carNO"));
        if (null != paramMap.get("typeId")) {
            carOperationVO.setTypeId(Long.valueOf(paramMap.get("typeId").toString()));
        }
        baseVO.setData(carService.getCarOperationList(carOperationVO, pageVO));
        return baseVO;
    }

    /**
     * 分页查询派车单列表
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCarControlList")
    public BaseVO getCarControlList(@RequestBody Map paramMap) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        CarControlVO carControlVO = new CarControlVO();
        carControlVO.setCarNO((String) paramMap.get("carNO"));
        if (null != paramMap.get("useTypeId")) {
            carControlVO.setUseTypeId(Long.valueOf(paramMap.get("useTypeId").toString()));
        }
        if (null != paramMap.get("startMaxTime")) {
            carControlVO.setStartMaxTime(Long.valueOf(paramMap.get("startMaxTime").toString()));
        }
        if (null != paramMap.get("startMinTime")) {
            carControlVO.setStartMinTime(Long.valueOf(paramMap.get("startMinTime").toString()));

        }
        if (null != paramMap.get("endMaxTime")) {
            carControlVO.setEndMaxTime(Long.valueOf(paramMap.get("endMaxTime").toString()));
        }
        if (null != paramMap.get("endMinTime")) {
            carControlVO.setEndMinTime(Long.valueOf(paramMap.get("endMinTime").toString()));
        }
        if (null != paramMap.get("carApplicantId")) {
            carControlVO.setCarApplicantId(Long.valueOf(paramMap.get("carApplicantId").toString()));
        }
        carControlVO.setDriverName((String) paramMap.get("driverName"));
        baseVO.setData(carService.getCarControlList(carControlVO, pageVO));
        return baseVO;
    }

    /**
     * 导出派车单列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getCarControlListExcel")
    public void getCarControlListExcel(@RequestBody CarControlVO carControlVO, HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("carLog.xls".getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        carService.getCarControlListExcel(carControlVO, out);
        out.flush();
        out.close();
    }

    /**
     * 获取派车单
     *
     * @param carControlVO
     * @return
     */
    @RequestMapping("/getCarControl")
    public BaseVO getCarControl(@RequestBody CarControlVO carControlVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getCarControlById(carControlVO.getId()));
        return baseVO;
    }

    /**
     * 查询司机列表
     */
    @RequestMapping("/getDriverList")
    public BaseVO getDriverList(@RequestBody CarApplicantVO carApplicantVO) {
        BaseVO baseVO = new BaseVO();
//        List<UserVO> driverList = carService.getDriverList(carApplicantVO);
//        baseVO.setData(driverList);
        return baseVO;
    }

    /**
     * 新增车辆
     *
     * @param carVO
     * @return
     */
    @RequestMapping("/addCar")
    public BaseVO addCar(@RequestBody CarVO carVO) {
        BaseVO baseVO = new BaseVO();
        carVO = carService.saveCar(carVO);
        baseVO.setData(carVO);
        return baseVO;
    }

    /**
     * 修改车辆信息
     *
     * @param carVO
     * @return
     */
    @RequestMapping("/updateCar")
    public BaseVO updateCar(@RequestBody CarVO carVO) {
        BaseVO baseVO = new BaseVO();
        carVO = carService.saveCar(carVO);
        baseVO.setData(carVO);
        return baseVO;
    }

    /**
     * 获取车辆列表
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCarList")
    public BaseVO getCarList(@RequestBody Map paramMap) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        CarVO carVO = new CarVO();
        carVO.setCarNO((String) paramMap.get("carNO"));
        if (null != paramMap.get("statusId")) {
            carVO.setStatusId(Long.valueOf(paramMap.get("statusId").toString()));
        }
        if (null != paramMap.get("carTypeId")) {
            carVO.setCarTypeId(Long.valueOf(paramMap.get("carTypeId").toString()));
        }
        pageVO = carService.getCarList(carVO, pageVO);
        baseVO.setData(pageVO);
        return baseVO;
    }

    /**
     * 新增申请
     *
     * @param carApplicantVO
     * @return
     */
    @RequestMapping("/addCarApplicant")
    public BaseVO addCarApplicant(@RequestBody CarApplicantVO carApplicantVO) {
        BaseVO baseVO = new BaseVO();
        try {
            carApplicantVO = carService.saveCarApplicant(carApplicantVO);
            baseVO.setData(carApplicantVO);
        } catch (RunTimeException e) {
            baseVO.setStatus(e.getMessage());
            baseVO.setMsg(ExceptionEnum.getExceptionEnum(e.getMessage()).getMsg());

        }
        return baseVO;
    }

    /**
     * 修改申请
     *
     * @param carApplicantVO
     * @return
     */
    @RequestMapping("/updateCarApplicant")
    public BaseVO updateCarApplicant(@RequestBody CarApplicantVO carApplicantVO) {
        BaseVO baseVO = new BaseVO();
        try {
            carApplicantVO = carService.saveCarApplicant(carApplicantVO);
            baseVO.setData(carApplicantVO);
        } catch (RunTimeException e) {
            e.printStackTrace();
        }
        return baseVO;
    }

    /**
     * 获取申请
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCarApplicantList")
    public BaseVO getCarApplicantList(@RequestBody Map paramMap) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        CarApplicantVO carApplicantVO = new CarApplicantVO();
        if (null != paramMap.get("applicantUserId")) {
            carApplicantVO.setApplicantUserId(Long.valueOf(paramMap.get("applicantUserId").toString()));
        }
        if (null != paramMap.get("status")) {
            carApplicantVO.setStatus(Integer.valueOf(paramMap.get("status").toString()));
        }
        pageVO = carService.getCarApplicantList(carApplicantVO, pageVO);
        baseVO.setData(pageVO);
        return baseVO;
    }

    /**
     * 获取申请信息
     *
     * @param carApplicantVO
     * @return
     */
    @RequestMapping("/getCarApplicant")
    public BaseVO getCarApplicant(@RequestBody CarApplicantVO carApplicantVO) {
        carApplicantVO = carService.getCarApplicantById(carApplicantVO.getId());
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carApplicantVO);
        return baseVO;
    }

    /**
     * 根据用户ID获取有效审批单
     *
     * @param carApprovalVO
     * @return
     */
    @RequestMapping("/getVALIDCarApprovalList")
    public BaseVO getVALIDCarApprovalList(@RequestBody CarApprovalVO carApprovalVO) {
        List<CarApprovalVO> carApprovalVOS = carService.getVALIDCarApprovalByUserId(carApprovalVO.getApprovalUserId());
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carApprovalVOS);
        return baseVO;
    }

    /**
     * 获取批单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCarApprovalList")
    public BaseVO getCarApprovalList(@RequestBody Map paramMap) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        CarApprovalVO carApprovalVO=new CarApprovalVO();
        if (null != paramMap.get("approvalUserId")) {
            carApprovalVO.setApprovalUserId(Long.valueOf(paramMap.get("approvalUserId").toString()));
        }
        if (null != paramMap.get("status")) {
            carApprovalVO.setStatus(Integer.valueOf(paramMap.get("status").toString()));
        }
        pageVO = carService.getCarApprovalList(carApprovalVO,pageVO);
        baseVO.setData(pageVO);
        return baseVO;
    }
    /**
     * 根据ID获取审批单据
     *
     * @param carApprovalVO
     * @return
     */

    @RequestMapping("/getCarApprovalById")
    public BaseVO getCarApprovalById(@RequestBody CarApprovalVO carApprovalVO) {
        carApprovalVO = carService.getCarApprovalById(carApprovalVO.getId());
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carApprovalVO);
        return baseVO;
    }

    /**
     * 审批
     *
     * @param carApprovalVO
     * @return
     */
    @RequestMapping("/carApproval")
    public BaseVO carApproval(@RequestBody CarApprovalVO carApprovalVO) {
        BaseVO baseVO = new BaseVO();
        try {
            carApprovalVO = carService.carApproval(carApprovalVO);
        } catch (RunTimeException e) {
            baseVO.setStatus(e.getMessage());
            baseVO.setMsg(ExceptionEnum.getExceptionEnum(e.getMessage()).getMsg());
        }
        baseVO.setData(carApprovalVO);
        return baseVO;
    }

    /**
     * 获取闲置车辆
     *
     * @param carApplicantVO
     * @return
     */
    @RequestMapping("/getStandByCar")
    public BaseVO getStandByCar(@RequestBody CarApplicantVO carApplicantVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.getStandByCar(carApplicantVO));
        return baseVO;
    }

    /**
     * 新增派车单
     *
     * @param carControlVO
     * @return
     */
    @RequestMapping("/addCarControl")
    public BaseVO addCarControl(@RequestBody CarControlVO carControlVO, Authentication auth) throws IOException {
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        carControlVO.setControlUser(user);
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.saveCarControl(carControlVO));
        return baseVO;
    }

    @RequestMapping("/updateCarControl")
    public BaseVO updateCarControl(@RequestBody CarControlVO carControlVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.saveCarControl(carControlVO));
        return baseVO;
    }

    @RequestMapping("/refuseCarApplicant")
    public BaseVO refuseCarApplicant(@RequestBody CarApplicantVO carApplicantVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(carService.refuseCarApplicant(carApplicantVO));
        return baseVO;
    }
}
