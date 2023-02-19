package com.poshist.car.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poshist.car.entity.*;
import com.poshist.car.repository.*;
import com.poshist.car.vo.*;
import com.poshist.common.Constant;
import com.poshist.common.ExceptionEnum;
import com.poshist.common.RunTimeException;
import com.poshist.common.queue.TTLQueueConfig;
import com.poshist.common.utils.CommonUtils;
import com.poshist.common.utils.ExcelUtils;
import com.poshist.common.vo.PageVO;
import com.poshist.soa.entity.Via;
import com.poshist.soa.service.GateService;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.entity.User;
import com.poshist.sys.repository.DepartmentDao;
import com.poshist.sys.repository.DictionaryDao;
import com.poshist.sys.repository.UserDao;
import com.poshist.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CarService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private UserService userService;
    @Autowired
    private CarDao carDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DictionaryDao dictionaryDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private CarApplicantDao carApplicantDao;
    @Autowired
    private CarApprovalDao carApprovalDao;
    @Autowired
    private CarControlDao carControlDao;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CarOperationDao carOperationDao;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private GateService gateService;

    /**
     * 每整5分处理车辆状态
     */
    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?")
    public void timerChangeCarStatus() {

        Date now = new Date();
        //处理运营
        List<CarOperation> carOperations = carOperationDao.findAllByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndStatus(now, now, 0);
        for (CarOperation carOperation : carOperations) {
            Car car = carOperation.getCar();
            car.setStatus(carOperation.getType());
            carOperation.setStatus(1);
            carDao.save(car);
            carOperationDao.save(carOperation);
        }
        carOperations = carOperationDao.findAllByStatus(1);
        for (CarOperation carOperation : carOperations) {
            if (carOperation.getEndTime().before(now)) {
                Car car = carOperation.getCar();
                Dictionary status = dictionaryDao.findById(102l).get();
                car.setStatus(status);
                carDao.save(car);
                carOperation.setStatus(2);
                carOperationDao.save(carOperation);
            }
        }
        //处理派车
        List<CarControl> carControls = carControlDao.findAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStatus(now, now, 0);
        for (CarControl carControl : carControls) {
            Car car = carControl.getCar();
            car.setStatus(carControl.getUseType());
            carDao.save(car);
        }

    }

    /**
     * 每5分钟处理白名单
     */
    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?")
    public void timerSendWhiteList() throws IOException {
        List<CarControl> carControls = carControlDao.findAllByStatus(0);
        for (CarControl carControl : carControls) {
            if (carControl.getEstimateStartTime().getTime() - new Date().getTime() <= 60 * 6 * 1000 && null == carControl.getThirdID()) {
                String thirdId = gateService.sendSJBlackWhiteList(carControl, 0);
                carControl.setThirdID(thirdId);
                carControlDao.save(carControl);
            }
        }
    }

    /**
     * 获取当前维修车辆
     *
     * @return
     */
    public List<CarVO> getRepairCar() {
        List<Dictionary> statusList = new ArrayList<>();
        statusList.add(dictionaryDao.findById(109l).get());
        statusList.add(dictionaryDao.findById(110l).get());
        statusList.add(dictionaryDao.findById(111l).get());

        return getCarByStatus(statusList);
    }

    /**
     * 获取当前保养车辆
     *
     * @return
     */
    public List<CarVO> getMaintainCar() {
        List<Dictionary> statusList = new ArrayList<>();
        statusList.add(dictionaryDao.findById(106l).get());
        statusList.add(dictionaryDao.findById(107l).get());
        statusList.add(dictionaryDao.findById(108l).get());

        return getCarByStatus(statusList);
    }

    /**
     * 获取当前以派车辆
     *
     * @return
     */
    public List<CarVO> getNowControlCar() {
        List<Dictionary> statusList = new ArrayList<>();
        statusList.add(dictionaryDao.findById(103l).get());
        statusList.add(dictionaryDao.findById(104l).get());
        statusList.add(dictionaryDao.findById(105l).get());
        return getCarByStatus(statusList);
    }

    /**
     * 获取当前待命车辆
     *
     * @return
     */
    public List<CarVO> getNowStandByCar() {
        List<Dictionary> statusList = new ArrayList<>();
        statusList.add(dictionaryDao.findById(102l).get());
        return getCarByStatus(statusList);
    }

    private List<CarVO> getCarByStatus(List<Dictionary> status) {
        List<Car> cars = carDao.findAllByStatusIn(status);
        List<CarVO> carVOS = new ArrayList<>();
        for (Car car : cars) {
            carVOS.add(new CarVO(car));
        }
        return carVOS;
    }

    public CarApplicantVO cancelCarApplicant(Long id) {
        CarApplicant carApplicant = carApplicantDao.findById(id).get();
        carApplicant.setStatus(Constant.CAR_APPLICANT_CANCEL);
        carApplicantDao.save(carApplicant);
        List<CarApproval> carApprovals = carApplicant.getCarApprovals();
        for (CarApproval carApproval : carApprovals) {
            carApproval.setStatus(Constant.CAR_APPLICANT_CANCEL);
            carApprovalDao.save(carApproval);
        }
        List<CarControl> carControls = carControlDao.findAllByCarApplicant(carApplicant);
        for (CarControl carControl : carControls) {
            carControl.setStatus(Constant.INVALID);
            carControlDao.save(carControl);
        }
        return new CarApplicantVO(carApplicant);

    }

    public CarOperationVO getCarOperationById(Long id) {
        CarOperation carOperation = carOperationDao.findById(id).get();
        return new CarOperationVO(carOperation);
    }

    public PageVO getCarOperationList(CarOperationVO carOperationVO, PageVO pageVO) {
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = carOperationDao.findAll(new Specification<CarOperation>() {
            @Override
            public Predicate toPredicate(Root<CarOperation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (StringUtils.isNotEmpty(carOperationVO.getCarNO())) {
                    Join<CarOperationVO, Car> join = root.join("car", JoinType.LEFT);
                    Path<String> carNO = join.get("carNO");
                    list.add(cb.like(carNO, "%" + carOperationVO.getCarNO() + "%"));
                }
                if (null != carOperationVO.getTypeId()) {
                    Dictionary type = dictionaryDao.findById(carOperationVO.getTypeId()).get();
                    list.add(cb.equal(root.get("type"), type));
                }

                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            CarOperation carOperation = (CarOperation) it.next();
            pageVO.addData(new CarOperationVO(carOperation));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    public void getCarControlListExcel(CarControlVO carControlVO, OutputStream outputStream) throws IOException {
        PageVO pageVO = new PageVO();
        pageVO.setPageCount(1);
        pageVO.setPageSize(80000);
        pageVO = getCarControlList(carControlVO, pageVO);
        String[] title = {"编号", "申请时间", "车辆牌照", "实际外出时间", "实际返回时间", "外出类型", "当前状态", "司机","行驶里程","预计外出时间","预计返回时间","申请人","申请人所在部门"};
        List<Object[]> datas = new ArrayList<>();
        Iterator values = pageVO.getData().iterator();
        while (values.hasNext()) {
            CarControlVO value = (CarControlVO) values.next();
            Object[] data = new Object[13];
            data[0] = value.getId();
            data[1] = CommonUtils.millisToSimpleStr(value.getEstimateStartTime());
            data[2] = value.getCarNO();
            data[3] = CommonUtils.millisToStr(value.getStartTime());
            data[4] = CommonUtils.millisToStr(value.getEndTime());
            data[5] = value.getUseTypeName();
            data[6] = value.getBackTypeName();
            data[7] = value.getDriverName();
            data[8] = value.getMileage();
            data[9] = CommonUtils.millisToStr(value.getEstimateStartTime());
            data[10] = CommonUtils.millisToStr(value.getEstimateEndTime());
            if(null==value.getCarApplicantId()){
                data[11]=value.getUseName();
                data[12]=value.getDepartmentName();
            }else{
                data[11]=value.getApplicantUserName();
                data[12]=value.getApplicantUserDepartment();
            }
            datas.add(data);
        }
        ExcelUtils.toExcel(title, datas, outputStream);

    }

    public PageVO getCarControlList(CarControlVO carControlVO, PageVO pageVO) {
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = carControlDao.findAll(new Specification<CarControl>() {
            @Override
            public Predicate toPredicate(Root<CarControl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(carControlVO.getCarNO())) {
                    Join<CarControl, Car> join = root.join("car", JoinType.LEFT);
                    Path<String> carNO = join.get("carNO");
                    list.add(cb.like(carNO, "%" + carControlVO.getCarNO() + "%"));
                }
                if (StringUtils.isNotEmpty(carControlVO.getDriverName())) {
                    list.add(cb.like(root.get("driverName"), "%" + carControlVO.getDriverName() + "%"));
                }
                if (null != carControlVO.getUseTypeId()) {
                    Dictionary userType = dictionaryDao.findById(carControlVO.getUseTypeId()).get();
                    list.add(cb.equal(root.get("useType"), userType));
                }
                if (null != carControlVO.getStartMaxTime()) {
                    list.add(cb.lessThanOrEqualTo(root.get("startTime"), new Date(carControlVO.getStartMaxTime())));
                }
                if (null != carControlVO.getStartMinTime()) {
                    list.add(cb.greaterThanOrEqualTo(root.get("startTime"), new Date(carControlVO.getStartMinTime())));
                }
                if (null != carControlVO.getEndMaxTime()) {
                    list.add(cb.lessThanOrEqualTo(root.get("endTime"), new Date(carControlVO.getEndMaxTime())));
                }
                if (null != carControlVO.getEndMinTime()) {
                    list.add(cb.greaterThanOrEqualTo(root.get("endTime"), new Date(carControlVO.getEndMinTime())));
                }
                if (null != carControlVO.getCarApplicantId()) {
                    CarApplicant carApplicant = carApplicantDao.findById(carControlVO.getCarApplicantId()).get();
                    list.add(cb.equal(root.get("carApplicant"), carApplicant));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            CarControl carControl = (CarControl) it.next();
            pageVO.addData(new CarControlVO(carControl));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    /**
     * 新建运营单
     *
     * @param carOperationVO
     * @return
     */
    public CarOperationVO saveCarOperation(CarOperationVO carOperationVO) {
        CarOperation carOperation = new CarOperation();
        carOperation = carOperationVO.toDTO(carOperation);
        Car car = carDao.findById(carOperationVO.getCarId()).get();
        carOperation.setCar(car);
        Dictionary type = dictionaryDao.findById(carOperationVO.getTypeId()).get();
        carOperation.setType(type);
        carOperation.setStatus(0);
        carOperationDao.save(carOperation);
        return new CarOperationVO(carOperation);
    }


    private List<CarOperation> getAllVALIDOperation(Date startTime, Date endTime) {

        String sql = "select * from t_car_operation_info c where NOT  ((c.end_time < str_to_date('" + CommonUtils.millisToStr(startTime.getTime()) + "' ,'%Y-%m-%d %T')) OR (c.start_time >str_to_date('" + CommonUtils.millisToStr(endTime.getTime()) + "' ,'%Y-%m-%d %T'))) and c.status=0";
        List<CarOperation> carOperations = em.createNativeQuery(sql, CarOperation.class).getResultList();
        return carOperations;
    }


    /**
     * 车辆归队
     *
     * @param carControlVO
     * @return
     */
    public CarControlVO carReturn(CarControlVO carControlVO) {
        CarControl carControl = carControlDao.findById(carControlVO.getId()).get();
        carControl = carControlVO.toDTO(carControl);

        carControl.setStatus(Constant.INVALID);

        Car car = carControl.getCar();
        //派车单记录当前派车里程
        carControl.setMileage(carControl.getMileage() - car.getMileage());
        car.setMileage(car.getMileage());
        car.setStatus(dictionaryDao.findById(Constant.STAND_BY).get());
        carControlDao.save(carControl);
        carDao.save(car);
        return new CarControlVO(carControl);
    }
//
//    /**
//     * 获取司机
//     *
//     * @return
//     */
//    public List<UserVO> getDriverList(CarApplicantVO carApplicantVO) {
//        List<User> users = userService.getUserByRole(Constant.ROLE_DRIVER);
//        List<UserVO> drivers = new ArrayList<>();
//        List<CarControl> carControls = null;
//        List<CarOperation> carOperations = null;
//        if (null != carApplicantVO.getId()) {
//            CarApplicant carApplicant = carApplicantDao.findById(carApplicantVO.getId()).get();
//            carControls = getAllVALIDControl(carApplicant.getStartTime(), carApplicant.getEndTime());
//            carOperations = getAllVALIDOperation(carApplicant.getStartTime(), carApplicant.getEndTime());
//        } else {
//            carControls = getAllVALIDControl(carApplicantVO.getStartTime(), carApplicantVO.getEndTime());
//            carOperations = getAllVALIDOperation(carApplicantVO.getStartTime(), carApplicantVO.getEndTime());
//        }
//
//        for (User user : users) {
//            if (!isDriverControl(user, carControls, carOperations))
//                drivers.add(new UserVO(user));
//        }
//
//        return drivers;
//    }

//    /**
//     * 司机是否已外派
//     *
//     * @param user
//     * @param carControls
//     * @return
//     */
//    private boolean isDriverControl(User user, List<CarControl> carControls, List<CarOperation> carOperations) {
//        for (CarControl carControl : carControls) {
//            if (user.getId() == carControl.getDriver().getId()) {
//                return true;
//            }
//        }
//        for (CarOperation carOperation : carOperations) {
//            if (user.getId() == carOperation.getDriver().getId()) {
//                return true;
//            }
//        }
//        return false;
//    }

    public CarControlVO getCarControlById(Long id) {
        CarControl carControl = carControlDao.findById(id).get();
        return new CarControlVO(carControl);
    }

    public CarApplicantVO refuseCarApplicant(CarApplicantVO carApplicantVO) {
        CarApplicant carApplicant = carApplicantDao.findById(carApplicantVO.getId()).get();
        carApplicant.setStatus(6);
        carApplicant.setRefuseReason(carApplicantVO.getRefuseReason());
        carApplicantDao.save(carApplicant);
        return new CarApplicantVO(carApplicant);
    }

    public CarControlVO saveCarControl(CarControlVO carControlVO) throws IOException {
        CarControl carControl;
        if (null == carControlVO.getId()) {
            carControl = new CarControl();
            carControl.setControlUser(carControlVO.getControlUser());

        } else {

            carControl = carControlDao.findById(carControlVO.getId()).get();
        }

        carControl = carControlVO.toDTO(carControl);
        if (null != carControlVO.getCarApplicantId()) {
            CarApplicant carApplicant = carApplicantDao.findById(carControlVO.getCarApplicantId()).get();
            carControl.setCarApplicant(carApplicant);
            carControl.setApplicantUser(carApplicant.getApplicantUser());
            carControl.setEstimateStartTime(carApplicant.getStartTime());
            carControl.setEstimateEndTime(carApplicant.getEndTime());
            carControl.setRouteStart(carApplicant.getRouteStart());
            carControl.setRouteMiddle(carApplicant.getRouteMiddle());
            carControl.setRouteEnd(carApplicant.getRouteEnd());
            carControl.setUseType(carApplicant.getUseType());
            carControl.setLeadMobile(carApplicant.getLeadMobile());
            carControl.setLeadName(carApplicant.getLeadName());
            carControl.setLeadPosition(carApplicant.getLeadPosition());
            carControl.setDepartment(carApplicant.getApplicantUser().getDepartment());
            //申请单是否已经派完
            Integer count = carControlDao.countAllByCarApplicantAndStatus(carApplicant, Constant.VALID);
            if (count + 1 == carApplicant.getCarCount()) {
                carApplicant.setStatus(Constant.CAR_APPLICANT_ALREADY);
                carApplicantDao.save(carApplicant);
            }
        } else {
            User applicantUser = userDao.findById(carControlVO.getApplicantUserId()).get();
            Dictionary useType = dictionaryDao.findById(carControlVO.getUseTypeId()).get();
            carControl.setUseType(useType);
            carControl.setApplicantUser(applicantUser);
            carControl.setDepartment(departmentDao.findById(carControlVO.getDepartmentId()).get());
        }
        Car car = carDao.findById(carControlVO.getCarId()).get();
        carControl.setCar(car);
        carControl.setStatus(Constant.VALID);
        carControlDao.save(carControl);
        //开始时间小于5分钟推送白名单
        if (carControl.getEstimateStartTime().getTime() - new Date().getTime() <= 60 * 5 * 1000) {
            String thirdId = gateService.sendSJBlackWhiteList(carControl, 0);
            carControl.setThirdID(thirdId);
        }
        //生成NO
        if (null == carControl.getControlCode()) {
            Integer count = carControlDao.countAllByDay(CommonUtils.dateToSimpleStr(carControl.getEstimateStartTime()));
            carControl.setControlCode(generateCarControlCode(count + 1));
        }
        carControl.setCarNo(carControl.getCar().getCarNO());
        carControlDao.save(carControl);
        carControlVO = new CarControlVO(carControl);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String data = mapper.writeValueAsString(carControlVO);
            //推送延迟提醒
            Date now = new Date();
            Long alertTime = carControl.getEstimateStartTime().getTime() - now.getTime() - (carControl.getAlertTime() * 60 * 1000);
            log.info("提醒时间-----------------" + CommonUtils.millisToStr(now.getTime() + alertTime));
            template.convertAndSend(TTLQueueConfig.ALERT_EXCHANGE, TTLQueueConfig.ALERT_QUEUE, "carControl-" + data, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", alertTime);
                    return message;
                }
            });

//
//            template.convertAndSend(TTLQueueConfig.ALERT_EXCHANGE, TTLQueueConfig.ALERT_QUEUE, "carControl-" + data, message -> {
//
//                // 设置超时时间 3000ms
//                message.getMessageProperties().setExpiration(alertTime.toString());
//                return message;
//            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return carControlVO;
    }

    private String generateCarControlCode(Integer count) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String code = format.format(new Date());
        DecimalFormat decimalFormat = new DecimalFormat("000");
        code += decimalFormat.format(count);
        return code;
    }

    public CarVO getCarById(Long id) {
        Car car = carDao.findById(id).get();
        return new CarVO(car);
    }

    public CarVO saveCar(CarVO carVO) {
        Car car;
        if (null == carVO.getId()) {
            car = new Car();
            Dictionary status = dictionaryDao.findById(Constant.STAND_BY).get();
            car.setStatus(status);
        } else {
            car = carDao.findById(carVO.getId()).get();
        }

        car = carVO.toDTO(car);
        User user = userDao.findById(carVO.getUserId()).get();
        Dictionary carType = dictionaryDao.findById(carVO.getCarTypeId()).get();
        Department department = departmentDao.findById(carVO.getDepartmentId()).get();
        car.setUser(user);
        car.setDepartment(department);
        car.setCarType(carType);

        carDao.save(car);
        return new CarVO(car);
    }

    public PageVO getCarList(CarVO carVO, PageVO pageVO) {
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = carDao.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(carVO.getCarNO())) {
                    list.add(cb.like(root.get("carNO"), "%" + carVO.getCarNO() + "%"));
                }
                if (null != carVO.getCarTypeId()) {
                    list.add(cb.equal(root.get("carType"), carVO.getCarTypeId()));
                }
                if (null != carVO.getStatusId()) {
                    list.add(cb.equal(root.get("status"), carVO.getStatusId()));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            Car car = (Car) it.next();
            pageVO.addData(new CarVO(car));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    public PageVO getCarApplicantList(CarApplicantVO carApplicantVO, PageVO pageVO) {
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = carApplicantDao.findAll(new Specification<CarApplicant>() {
            @Override
            public Predicate toPredicate(Root<CarApplicant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (null != carApplicantVO.getApplicantUserId()) {
                    User user = userDao.findById(carApplicantVO.getApplicantUserId()).get();
                    list.add(cb.equal(root.get("applicantUser"), user));
                }
                if (null != carApplicantVO.getStatus()) {
                    list.add(cb.equal(root.get("status"), carApplicantVO.getStatus()));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            CarApplicant carApplicant = (CarApplicant) it.next();
            pageVO.addData(new CarApplicantVO(carApplicant));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    public CarApplicantVO getCarApplicantById(Long id) {
        CarApplicant carApplicant = carApplicantDao.findById(id).get();
        return new CarApplicantVO(carApplicant);
    }

    /**
     * 根据用户获取有效审批单据
     *
     * @param userId
     * @return
     */
    public List<CarApprovalVO> getVALIDCarApprovalByUserId(Long userId) {
        User user = userDao.findById(userId).get();
        List<CarApproval> carApprovals = carApprovalDao.findByApprovalUserAndStatus(user, Constant.CAR_APPLICANT_ING);
        List<CarApprovalVO> carApprovalVOS = new ArrayList<>();
        for (CarApproval carApproval : carApprovals) {
            CarApprovalVO carApprovalVO = new CarApprovalVO(carApproval);
            carApprovalVO.setCarApplicant(new CarApplicantVO(carApproval.getCarApplicant()));
            carApprovalVOS.add(carApprovalVO);
        }
        return carApprovalVOS;
    }


    public PageVO getCarApprovalList(CarApprovalVO carApprovalVO, PageVO pageVO) {
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = carApprovalDao.findAll(new Specification<CarApproval>() {
            @Override
            public Predicate toPredicate(Root<CarApproval> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (null != carApprovalVO.getApprovalUserId()) {
                    User user = userDao.findById(carApprovalVO.getApprovalUserId()).get();
                    list.add(cb.equal(root.get("approvalUser"), user));
                }
                if (null != carApprovalVO.getStatus()) {
                    Join<CarApproval, CarApplicant> join = root.join("carApplicant", JoinType.LEFT);
                    Path<String> appStatus = join.get("status");
                    list.add(cb.equal(appStatus, carApprovalVO.getStatus()));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            CarApproval carApproval = (CarApproval) it.next();
            pageVO.addData(new CarApplicantVO(carApproval.getCarApplicant()));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    public CarApprovalVO getCarApprovalById(Long id) {
        CarApproval carApproval = carApprovalDao.findById(id).get();
        CarApprovalVO carApprovalVO = new CarApprovalVO(carApproval);
        carApprovalVO.setCarApplicant(new CarApplicantVO(carApproval.getCarApplicant()));
        return carApprovalVO;
    }


    public CarApplicantVO saveCarApplicant(CarApplicantVO carApplicantVO) throws RunTimeException {
        CarApplicant carApplicant;
        boolean isNew = true;
        if (null == carApplicantVO.getId()) {
            carApplicant = carApplicantVO.toDTO(new CarApplicant());
            User user = userDao.findById(carApplicantVO.getApplicantUserId()).get();
            carApplicant.setApplicantUser(user);
        } else {
            carApplicant = carApplicantDao.findById(carApplicantVO.getId()).get();
            carApplicant = carApplicantVO.toDTO(carApplicant);
            isNew = false;

        }
        carApplicant.setDepartment(carApplicant.getApplicantUser().getDepartment());
        Dictionary carType = dictionaryDao.findById(carApplicantVO.getCarTypeId()).get();
        carApplicant.setCarType(carType);
        Dictionary useType = dictionaryDao.findById(carApplicantVO.getUseTypeId()).get();
        carApplicant.setUseType(useType);
        carApplicant.setStatus(Constant.CAR_APPLICANT_INIT);
        carApplicant.setApplicantCode(CommonUtils.generateApplicantCode());
        carApplicantDao.save(carApplicant);
        if (isNew) {
            //创建审批单
            generateApproval(carApplicant);
        }
        return new CarApplicantVO(carApplicant);
    }

    /**
     * 创建审批单
     *
     * @param carApplicant
     * @throws RunTimeException
     */
    private void generateApproval(CarApplicant carApplicant) throws RunTimeException {
        //院系审批
        User facultyUser = userService.getUserByRole(carApplicant.getApplicantUser(), Constant.ROLE_FACULTY_LEAD);
        if (null == facultyUser) {
            throw new RunTimeException(ExceptionEnum.exceptionEnum_1001.getMsgCode());
        }
        CarApproval facultyApproval = new CarApproval();
        creatInitApproval(carApplicant, facultyUser, facultyApproval);
        facultyApproval.setStatus(Constant.CAR_APPLICANT_ING);
        facultyApproval.setCode("1");
        carApprovalDao.save(facultyApproval);
        //安全管理处审批
//        Department secDepartment = departmentDao.findById(Constant.DEPARTMENT_SECURITY_ID).get();
//        User secUser = userService.getUserByDepartmentAndRole(secDepartment, Constant.ROLE_SECURITY_LEAD);
//        if (null == secUser) {
//            throw new RunTimeException(ExceptionEnum.exceptionEnum_1002.getMsgCode());
//        }
//        CarApproval secApproval = new CarApproval();
//        creatInitApproval(carApplicant, secUser, secApproval);
//        secApproval.setCode("2");
//        carApprovalDao.save(secApproval);
//
//        if (Constant.INVALID == carApplicant.getIsArea()) {
//            //上海市外
//            Department schoolDepartment = departmentDao.findById(Constant.DEPARTMENT_SCHOOL_ID).get();
//            User schoolUser = userService.getUserByDepartmentAndRole(schoolDepartment, Constant.ROLE_SCHOOL_LEAD);
//            if (null == schoolUser) {
//                throw new RunTimeException(ExceptionEnum.exceptionEnum_1003.getMsgCode());
//            }
//            CarApproval schoolApproval = new CarApproval();
//            creatInitApproval(carApplicant, schoolUser, schoolApproval);
//            schoolApproval.setCode("3");
//            carApprovalDao.save(schoolApproval);
//        }
    }


    /**
     * 创建初始审批单
     *
     * @param carApplicant
     * @param facultyUser
     * @param facultyApproval
     */
    private void creatInitApproval(CarApplicant carApplicant, User facultyUser, CarApproval facultyApproval) {
        facultyApproval.setCarApplicant(carApplicant);
        facultyApproval.setApprovalUser(facultyUser);
        facultyApproval.setDepartment(facultyUser.getDepartment());
        facultyApproval.setStatus(Constant.CAR_APPLICANT_INIT);
    }

    /**
     * 审批
     *
     * @param carApprovalVO
     * @return
     */
    public CarApprovalVO carApproval(CarApprovalVO carApprovalVO) throws RunTimeException {
        CarApproval carApproval = carApprovalDao.findById(carApprovalVO.getId()).get();
        carApproval = carApprovalVO.toDTO(carApproval);
        CarApplicant carApplicant = carApproval.getCarApplicant();
        //申请超时处理
        if (Constant.CAR_APPLICANT_TIMEOUT == carApplicant.getStatus()) {
            carApproval.setStatus(Constant.CAR_APPLICANT_TIMEOUT);
            carApprovalDao.save(carApproval);
            throw new RunTimeException(ExceptionEnum.exceptionEnum_1004.getMsgCode());
        }
        carApproval.setApprovalTime(new Date());
        carApprovalDao.save(carApproval);
        carApplicant.setStatus(carApproval.getStatus());
        if (Constant.CAR_APPLICANT_PASS == carApproval.getStatus()) {
            String nextCode = String.valueOf(Integer.valueOf(carApproval.getCode()) + 1);
            CarApproval nextCarApproval = carApprovalDao.findFirstByCarApplicantAndCode(carApplicant, nextCode);
            if (null != nextCarApproval) {
                carApplicant.setStatus(Constant.CAR_APPLICANT_ING);
                nextCarApproval.setStatus(Constant.CAR_APPLICANT_ING);
                carApprovalDao.save(nextCarApproval);
            }
        }
        carApplicantDao.save(carApplicant);
        return new CarApprovalVO(carApproval);
    }

    /**
     * 获取待命车辆
     *
     * @return
     */
    public List<CarVO> getStandByCar(CarApplicantVO carApplicantVO) {
        Date startTime = null;
        Date endTime = null;
        Dictionary status = dictionaryDao.findById(114l).get();
        List<Car> cars = null;
        if (null != carApplicantVO.getId()) {
            CarApplicant carApplicant = carApplicantDao.findById(carApplicantVO.getId()).get();
            startTime = carApplicant.getStartTime();
            endTime = carApplicant.getEndTime();
            cars = carDao.findAllByStatusNotAndCarTypeOrderByMileage(status, carApplicant.getCarType());
        } else {
            startTime = new Date(carApplicantVO.getStartTime());
            endTime = new Date(carApplicantVO.getEndTime());
            cars = carDao.findAllByStatusNotOrderByMileage(status);
        }

        List<CarVO> carVOS = new ArrayList<>();
        List<CarControl> carControls = getAllVALIDControl(startTime, endTime);
        List<CarOperation> carOperations = getAllVALIDOperation(startTime, endTime);
        for (Car car : cars) {
            if (!isCarControl(car, carControls, carOperations, carApplicantVO.getControlId())) {
                carVOS.add(new CarVO(car));
            }

        }
        return carVOS;
    }

    private List<CarControl> getAllVALIDControl(Date startTime, Date endTime) {

        String sql = "select * from t_car_control_info c where NOT  ((c.estimate_end_time < str_to_date('" + CommonUtils.millisToStr(startTime.getTime()) + "' ,'%Y-%m-%d %T')) OR (c.estimate_start_time >str_to_date('" + CommonUtils.millisToStr(endTime.getTime()) + "' ,'%Y-%m-%d %T'))) and c.status=0";
        List<CarControl> carControls = em.createNativeQuery(sql, CarControl.class).getResultList();
        return carControls;
    }


    /**
     * 车辆是否已外派
     *
     * @param car
     * @param carControls
     * @return
     */
    private boolean isCarControl(Car car, List<CarControl> carControls, List<CarOperation> carOperations, Long controlId) {
        for (CarControl carControl : carControls) {

            if (car.getId() == carControl.getCar().getId()) {
                if (carControl.getId() != controlId) {
                    return true;
                }
            }
        }
        for (CarOperation carOperation : carOperations) {
            if (car.getId() == carOperation.getCar().getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理车辆闸机信息
     *
     * @param via
     */
    public void carVia(Via via) {
        if (0 == via.getViaResult()) {
            Car car = carDao.findFirstByCarNO(via.getCardCode());
            //没有对应车辆
            if (null == car) {
                return;
            }
            CarControl carControl = carControlDao.findFirstByCarAndStatusAndEstimateStartTimeLessThanEqual(car, 0, via.getViaTime());
            //没有对应派车单
            if (null == carControl) {
                return;
            }
            //车辆外出
            if (0 == via.getViaType()) {
                if (null == carControl.getStartTime()) {
                    carControl.setStartTime(via.getViaTime());
                    carControlDao.save(carControl);
                }
            }
            //车辆进入
            if (1 == via.getViaType()) {
                carControl.setEndTime(via.getViaTime());
                carControlDao.save(carControl);
            }
        }
    }
}
