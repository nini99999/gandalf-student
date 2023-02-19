package com.poshist.BI.service;

import com.poshist.BI.vo.ParamVO;
import com.poshist.BI.vo.QueryVO;
import com.poshist.car.repository.CarControlDao;
import com.poshist.car.repository.CarOperationDao;
import com.poshist.student.repository.LeaveDao;
import com.poshist.student.repository.StudentDao;
import com.poshist.sys.repository.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BIService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CarControlDao carControlDao;
    @Autowired
    private CarOperationDao carOperationDao;
    @Autowired
    private LeaveDao leaveDao;
    @Autowired
    private DepartmentDao departmentDao;
    /**
     * 获取在读人数
     *
     * @return
     */
    public Integer getCountStudent() {
        Integer studentCount = studentDao.countAllByStatus(0);
        return studentCount;
    }

    /**
     * 校外人数
     *
     * @return
     */
    public Integer getCountStudentOutSchool() {
        Integer studentCount = studentDao.countAllByInStatus(0);
        return studentCount;
    }

    /**
     * 获取外勤车辆数
     */
    public Integer getCountCarControl() {
        Date now = new Date();
        Integer carCount = carControlDao.countAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStatus(now, now, 0);
        return carCount;
    }

    /**
     * 获取车辆保养数
     *
     * @return
     */
    public Integer getCountCarOperation() {
        Date now = new Date();
        Integer carCount = carOperationDao.countAllByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(now, now);
        return carCount;
    }

    /**
     * 每日派车数量
     * @param queryVO
     * @return
     */
    public List<ParamVO> getCountCarControlDaily(QueryVO queryVO){
        List<Object[]> objects=carControlDao.findCountCarControlDaily(queryVO.getMinTime(),queryVO.getMaxTime());
        List<ParamVO> paramVOS = toParamVOS(objects);
        return paramVOS;
    }

    private List<ParamVO> toParamVOS(List<Object[]> objects) {
        List<ParamVO> paramVOS = new ArrayList<>();
        for (Object[] object : objects) {
            ParamVO paramVO = new ParamVO();
            paramVO.setName(object[0].toString());
            paramVO.setValue(object[1].toString());
            paramVOS.add(paramVO);
        }
        return paramVOS;
    }

    /**
     * 部门拍车数量
     * @param queryVO
     * @return
     */
    public List<ParamVO>getCountCarControlDepartment(QueryVO queryVO){
      List<Object[]> objects=carControlDao.findCountCarControlDepartment(queryVO.getMinTime(),queryVO.getMaxTime());
        for (Object[] object:objects){
            object[0]=departmentDao.findFirstByCode(object[0].toString()).getName();
        }
        List<ParamVO> paramVOS = toParamVOS(objects);
        return paramVOS;
  }

    /**
     * 每日请假人数
     * @param queryVO
     * @return
     */
    public List<ParamVO> getCountStudentDaily(QueryVO queryVO){
        List<Object[]> objects=leaveDao.findCountLeaveDaily(queryVO.getMinTime(),queryVO.getMaxTime());
        List<ParamVO> paramVOS = toParamVOS(objects);
        return paramVOS;
    }
    /**
     * 部门请假数量
     * @param queryVO
     * @return
     */
    public List<ParamVO>getCountStudentDepartment(QueryVO queryVO){
        List<Object[]> objects=leaveDao.findCountLeaveDepartment(queryVO.getMinTime(),queryVO.getMaxTime());
        for (Object[] object:objects){
            object[0]=departmentDao.findFirstByCode(object[0].toString()).getName();
        }
        List<ParamVO> paramVOS = toParamVOS(objects);
        return paramVOS;
    }

}
