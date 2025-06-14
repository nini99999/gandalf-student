package com.poshist.student.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.poshist.common.Constant;
import com.poshist.common.RunTimeException;
import com.poshist.common.utils.CommonUtils;
import com.poshist.common.utils.ExcelUtils;
import com.poshist.common.vo.PageVO;
import com.poshist.soa.entity.Via;
import com.poshist.soa.repository.ViaDao;
import com.poshist.soa.service.GateService;
import com.poshist.soa.service.HikVisionService;
import com.poshist.soa.vo.ViaVO;
import com.poshist.student.entity.Applicant;
import com.poshist.student.entity.Leave;
import com.poshist.student.entity.LeaveLimit;
import com.poshist.student.entity.Student;
import com.poshist.student.repository.ApplicantDao;
import com.poshist.student.repository.LeaveDao;
import com.poshist.student.repository.LeaveLimitDao;
import com.poshist.student.repository.StudentDao;
import com.poshist.student.vo.ApplicantVO;
import com.poshist.student.vo.LeaveLimitVO;
import com.poshist.student.vo.LeaveVO;
import com.poshist.student.vo.StudentVO;
import com.poshist.sys.entity.Department;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.entity.User;
import com.poshist.sys.repository.DepartmentDao;
import com.poshist.sys.repository.DictionaryDao;
import com.poshist.sys.service.UserService;
import com.poshist.sys.vo.DepartmentVO;
import com.poshist.sys.vo.PicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private DictionaryDao dictionaryDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private LeaveDao leaveDao;
    @Autowired
    private LeaveLimitDao leaveLimitDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ViaDao viaDao;
    @Autowired
    private GateService gateService;
    @Autowired
    @Lazy
    private HikVisionService hikVisionService;

    public LeaveVO changeStatus(Long id, Integer status) {
        Leave leave = leaveDao.findById(id).get();
        leave.setStatus(status);
        leaveDao.save(leave);
        return new LeaveVO(leave);
    }

    public List<ViaVO> getLeaveViaList(Long LeaveId) {
        Leave leave = leaveDao.findById(LeaveId).get();
        Date startTime = CommonUtils.timeToDayStart(leave.getEstimateStartTime());
        Date endTime = CommonUtils.timeToDayEnd(leave.getEstimateEndTime());
        List<Via> vias = viaDao.getAllByStudentIdAndCardTypeAndViaTimeBetween(leave.getStudent().getId(), 0, startTime, endTime);
        List<ViaVO> viaVOS = new ArrayList<>();
        for (Via via : vias) {
            viaVOS.add(new ViaVO(via));
        }
        return viaVOS;
    }

    @Transactional
    public LeaveLimitVO saveLeaveLimit(LeaveLimitVO leaveLimitVO) {
        LeaveLimit leaveLimit = leaveLimitDao.findById(leaveLimitVO.getId()).get();
        leaveLimit.setLimitValue(leaveLimitVO.getLimitValue());
        leaveLimitDao.save(leaveLimit);

        return new LeaveLimitVO(leaveLimit);
    }

    public List<LeaveLimitVO> getLeaveLimitList(Long rootId) {
        List<LeaveLimitVO> leaveLimitVOS = new ArrayList<>();
        DepartmentVO departmentVO = userService.getDepartmentById(rootId);
        //管理员
        if (1 == departmentVO.getId()) {
            for (DepartmentVO depVO : departmentVO.getChild()) {
                if (!"职能部门".equals(depVO.getMemo())) {
                    leaveLimitVOS.add(getLeaveLimit(depVO.getId()));
                }
            }
        }
        //院系
        else {
            if (!"职能部门".equals(departmentVO.getMemo())) {
                leaveLimitVOS.add(getLeaveLimit(departmentVO.getId()));
            }
        }
        return leaveLimitVOS;
    }

    public LeaveLimitVO getLeaveLimit(Long departmentId) {
        Department department = departmentDao.findById(departmentId).get();
        LeaveLimit leaveLimit = leaveLimitDao.findFirstByDepartment(department);
        if (null == leaveLimit) {
            leaveLimit = new LeaveLimit();
            leaveLimit.setDepartment(department);
            leaveLimit.setLimitValue(15);
            leaveLimitDao.save(leaveLimit);
        }
        return new LeaveLimitVO(leaveLimit);
    }

    public void upLoadStudentsPic(MultipartFile picZip) throws IOException {
        ZipInputStream zis = new ZipInputStream(picZip.getInputStream());
        ZipEntry zipEntry;
        File tempFile = File.createTempFile("temp", "zip");
        picZip.transferTo(tempFile);
        ZipFile zipFile = new ZipFile(tempFile);
        while ((zipEntry = zis.getNextEntry()) != null) {
            String name = zipEntry.getName();
            name = name.substring(0, name.indexOf("."));
            Student student = studentDao.findFirstByCodeAndStatus(name, 0);
            if (null != student) {
                PicVO picVO = new PicVO();
                picVO.setObjectId(student.getId());
                picVO.setType(Constant.PIC_TYPE_STUDENT);
                InputStream is = zipFile.getInputStream(zipEntry);
                picVO.setData(IOUtils.toByteArray(is));
                userService.uploadPic(picVO);
                sendHikPerson(student, Base64.encode(picVO.getData()).replaceAll("data:image/jpeg;base64,/", "").replaceAll("data:image/png;base64,/", ""));
            }
        }
    }

    public void importStudent(MultipartFile studentFile, OutputStream outputStream) throws IOException, ParseException {
        Workbook wb = ExcelUtils.getWorkBook(studentFile);
        Sheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        Dictionary d11 = dictionaryDao.findById(11L).get();
        Dictionary d12 = dictionaryDao.findById(12L).get();
        Dictionary d20 = dictionaryDao.findById(20L).get();
        Dictionary d21 = dictionaryDao.findById(21L).get();
        Dictionary d22 = dictionaryDao.findById(22L).get();
        Dictionary d23 = dictionaryDao.findById(23L).get();
        Dictionary d24 = dictionaryDao.findById(24L).get();
        Dictionary d25 = dictionaryDao.findById(25L).get();
        Dictionary d26 = dictionaryDao.findById(26L).get();
        Dictionary d27 = dictionaryDao.findById(27L).get();
        Map<String, Department> departmentMap = new HashMap<>();
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Cell rsCell = row.createCell(20);

            //判断身份证号是否存在
            if (null != row.getCell(6) && StringUtils.isNotEmpty(getStringCellValue(row, 6))) {
                if (null != studentDao.findFirstByIdentityCode(getStringCellValue(row, 6))) {
                    rsCell.setCellValue("身份证号已存在");
                    continue;
                }
            }
            //判断学籍号或证件号是否存在
            if (null != row.getCell(3) && StringUtils.isNotEmpty(getStringCellValue(row, 3))) {
                if (null != studentDao.findFirstByCodeAndStatus(getStringCellValue(row, 3), 0)) {
                    rsCell.setCellValue("学员证号已存在");
                    continue;
                }
            }
//            if (null != row.getCell(4) && StringUtils.isNotEmpty(getStringCellValue(row, 4))) {
//                if (null != studentDao.findFirstByCode(getStringCellValue(row, 3))) {
//                    rsCell.setCellValue("学生证号已存在");
//                    continue;
//                }
//            }
            Student student = new Student();
            String cellValue = getStringCellValue(row, 1);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("学生类别为空");
                continue;
            }
            if ("本科生".equals(cellValue)) {
                student.setType(d20);
            } else if ("硕士生".equals(cellValue)) {
                student.setType(d21);
            } else if ("博士生".equals(cellValue)) {
                student.setType(d22);
            } else if ("规培生".equals(cellValue)) {
                student.setType(d23);
            } else if ("干部轮训学员".equals(cellValue)) {
                student.setType(d24);
            } else if ("留学生".equals(cellValue)) {
                student.setType(d25);
            } else if ("文职学员".equals(cellValue)) {
                student.setType(d26);
            } else if ("战士".equals(cellValue)) {
                student.setType(d27);
            }
            cellValue = getStringCellValue(row, 2);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("学生姓名为空");
                continue;
            } else {
                student.setName(cellValue);
            }

            cellValue = getStringCellValue(row, 3);
//            if (StringUtils.isEmpty(cellValue)) {
//                cellValue = getStringCellValue(row, 4);
//                if (StringUtils.isEmpty(cellValue)) {
//                    rsCell.setCellValue("学生学籍号或学员证号为空");
//                    continue;
//                }
//            }
            student.setCode(cellValue);

            cellValue = getStringCellValue(row, 4);
            if (StringUtils.isNotEmpty(cellValue)) {
                student.setNativePlace(cellValue);
            }

            cellValue = getStringCellValue(row, 5);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("性别为空");
                continue;
            }
            if ("男".equals(cellValue)) {
                student.setGender(d11);
            } else {
                student.setGender(d12);
            }

            cellValue = getStringCellValue(row, 6);
            if (StringUtils.isNotEmpty(cellValue)) {
                student.setIdentityCode(cellValue);
            }

            cellValue = getStringCellValue(row, 7);
            if (StringUtils.isNotEmpty(cellValue)) {
                student.setMobile(cellValue);
            }

            cellValue = getStringCellValue(row, 8);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("所属部门为空");
                continue;
            }
            Department department = departmentMap.get(cellValue);
            if (null == department) {
                department = departmentDao.findFirstByName(cellValue);
                departmentMap.put(cellValue, department);
            }
            cellValue = getStringCellValue(row, 9);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("所属学员大队为空");
                continue;
            }
            department = departmentDao.findFirstByNameAndParentDepartment(cellValue, department);
            if (department == null) {
                rsCell.setCellValue("所属学员大队不存在");
                continue;
            }
            cellValue = getStringCellValue(row, 10);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("所属学员队为空");
                continue;
            }
            department = departmentDao.findFirstByNameAndParentDepartment(cellValue, department);
            if (department == null) {
                rsCell.setCellValue("所属学员队不存在");
                continue;
            }
            student.setDepartment(department);

            String cellDate = row.getCell(16).getStringCellValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (null == cellDate) {
                rsCell.setCellValue("入学时间为空");
                continue;
            } else {
                student.setStartTime(sdf.parse(cellDate));
            }

            cellDate = row.getCell(17).getStringCellValue();
            if (null == cellDate) {
                rsCell.setCellValue("毕业时间为空");
                continue;
            } else {
                student.setEndTime(sdf.parse(cellDate));
            }
            student.setStatus(0);
            student.setHikVisionStatus(0);
            student.setJieShunStatus(0);
            cellValue = getStringCellValue(row, 18);
            if (StringUtils.isEmpty(cellValue)) {
                rsCell.setCellValue("一卡通号码为空");
                continue;
            } else {
                student.setCardCode(cellValue);
            }
            student.setInStatus(1);
            studentDao.save(student);
            // sendJieShunPerson(student);
            rsCell.setCellValue("导入成功");
        }
        wb.write(outputStream);
    }

    private void sendJieShunPerson(Student student) throws IOException {
        try {
            if (null == student.getId()) {
                String thirdId = gateService.sendPerson(student, 0);
                student.setThirdId(thirdId);
            } else {
                gateService.sendPerson(student, 1);
            }
            student.setJieShunStatus(1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        studentDao.save(student);
    }

    private void sendHikPerson(Student student, String face) {
        try {
            hikVisionService.sendPerson(student, face);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        studentDao.save(student);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/home/poshist/Downloads/1112.xls");
        Workbook wb = new HSSFWorkbook(new FileInputStream(file));
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(7);
        Cell cell = row.getCell(3);
        CellType cellType = cell.getCellType(); // 获取单元格类型
        if (cellType == CellType.BLANK) {
            System.out.println("BLANK");
        } else if (cellType == CellType.BOOLEAN) {
            System.out.println("BOOLEAN");
        } else if (cellType == CellType.NUMERIC) {
            System.out.println(String.valueOf(Math.round(cell.getNumericCellValue())));
        } else if (cellType == CellType.STRING) {
            System.out.println(cell.getStringCellValue());
        } else if (cellType == CellType.ERROR) {
            System.out.println("ERROR");
        } else if (cellType == CellType.FORMULA) {
            System.out.println("FORMULA");
        }
    }

    private String getStringCellValue(Row row, int i) {
        if (null == row.getCell(i)) {
            return "";
        }
        CellType cellType = row.getCell(i).getCellType();
        if (cellType == CellType.BLANK) {
            return "";
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(row.getCell(i).getBooleanCellValue());
        } else if (cellType == CellType.NUMERIC) {
            return String.valueOf(Math.round(row.getCell(i).getNumericCellValue()));
        } else if (cellType == CellType.STRING) {
            return row.getCell(i).getStringCellValue();
        } else if (cellType == CellType.ERROR) {
            return "";
        } else if (cellType == CellType.FORMULA) {
            return "";
        }
        return "";
    }

    /**
     * 处理请假状态
     */
    //  @Scheduled(cron = "0 0/5 * * * ?")
    public void timerProcessLeave() {
        //处理外出
        Date now = new Date();
        //请假有效期
        List<Leave> leaves = leaveDao.findAllByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqual(now, now);
        for (Leave leave : leaves) {
            if (0 == leave.getStudent().getInStatus()) {
                leave.setStatus(1);
                leave.setStartDate(leave.getStudent().getLastViaTime());
                leaveDao.save(leave);
            } else {
                if (0 != leave.getStatus()) {
                    leave.setStatus(2);
                    leaveDao.save(leave);
                }
            }
        }
        //超期
        Integer[] status = {1, 3};
        leaves = leaveDao.findAllByStatusNotInAndEstimateEndTimeLessThanEqual(status, now);
        for (Leave leave : leaves) {
            //未归
            if (0 == leave.getStudent().getInStatus()) {
                leave.setStatus(3);
            }
            //以归
            else {
                if (3 == leave.getStatus()) {
                    leave.setStatus(4);
                }
                if (leave.getStudent().getLastViaTime() != null) {
                    if (leave.getEstimateEndTime().after(leave.getStudent().getLastViaTime())) {
                        leave.setStatus(2);
                    }
                }
                leave.setEndDate(leave.getStudent().getLastViaTime());
            }
            leaveDao.save(leave);
        }
    }

    /**
     * 学生出入
     *
     * @param via
     */
    @Transactional

    public void studentVia(Via via) {
        Student student = studentDao.findById(via.getStudentId()).get();
        if (null != student) {
            student.setInStatus(via.getViaType());
            student.setLastViaTime(via.getViaTime());
            studentDao.save(student);
//            if (via.getViaType() == 0 && via.getViaResult() == 0) {
//                Leave leave = leaveDao.findFirstByEstimateStartTimeLessThanEqualAndEstimateEndTimeGreaterThanEqualAndStudent(via.getViaTime(), via.getViaTime(), student);
//                if (null == leave) {
//                    leave = leaveDao.findFirstByEstimateStartTimeBetweenAndStudent(CommonUtils.timeToDayStart(via.getViaTime()), CommonUtils.timeToDayEnd(via.getViaTime()),
//                            student);
//                    if (null != leave) {
//                        leave.setStatus(5);
//
//                        if (null == leave.getStartDate()) {
//                            leave.setStartDate(via.getViaTime());
//                        }
//                        leaveDao.save(leave);
//                    } else {
//                        leave = leaveDao.findFirstByEstimateEndTimeBetweenAndStudent(CommonUtils.timeToDayStart(via.getViaTime()), CommonUtils.timeToDayEnd(via.getViaTime()),
//                                student);
//                        if (null != leave) {
//                            leave.setStatus(5);
//                            if (null == leave.getStartDate()) {
//                                leave.setStartDate(via.getViaTime());
//                            }
//                            leaveDao.save(leave);
//                        }
//                    }
//                } else {
//                    if (null == leave.getStartDate()) {
//                        leave.setStartDate(via.getViaTime());
//                    }
//
//                    leaveDao.save(leave);
//                }
//
//            }

        }

    }


    /**
     * 获取申请单
     *
     * @param id
     * @return
     */
    public LeaveVO getLeaveById(Long id) {
        Leave leave = leaveDao.findById(id).get();
        return new LeaveVO(leave);
    }

    public void getLeaveListExcel(PageVO pageVO, LeaveVO leaveVO, Long userDepartmentId, OutputStream outputStream) throws IOException {
        pageVO.setPageCount(1);
        pageVO.setPageSize(80000);
        pageVO = getLeaveList(pageVO, leaveVO, userDepartmentId);
        String[] title = {"编号", "姓名", "预计外出时间", "预计返回时间", "实际外出时间", "实际返回时间", "状态", "所属单位"};
        List<Object[]> datas = new ArrayList<>();
        Iterator values = pageVO.getData().iterator();
        while (values.hasNext()) {
            leaveVO = (LeaveVO) values.next();
            Object[] data = new Object[8];
            data[0] = leaveVO.getId();
            data[1] = leaveVO.getStudentName();
            data[2] = CommonUtils.millisToStr(leaveVO.getEstimateStartTime());
            data[3] = CommonUtils.millisToStr(leaveVO.getEstimateStartTime());
            if (null != leaveVO.getStartDate()) {
                data[4] = CommonUtils.millisToStr(leaveVO.getStartDate());
            } else {
                data[4] = "--";
            }
            if (null != leaveVO.getEndDate()) {
                data[5] = CommonUtils.millisToStr(leaveVO.getEndDate());
            } else {
                data[5] = "--";
            }
            data[6] = leaveVO.getStatusName();
            data[7] = leaveVO.getDepartment();
            datas.add(data);
        }
        ExcelUtils.toExcel(title, datas, outputStream);
    }

    public PageVO getLeaveList(PageVO pageVO, LeaveVO leaveVO, Long userDepartmentId) {
        userDepartmentId = varDepartment(userDepartmentId);
        Long finalUserDepartmentId = userDepartmentId;
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = leaveDao.findAll(new Specification<Leave>() {
            @Override
            public Predicate toPredicate(Root<Leave> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Join<Leave, Student> studentJoin = root.join("student", JoinType.LEFT);
                Join<Student, Department> join = studentJoin.join("department", JoinType.LEFT);
                findInDepartment(list, join, finalUserDepartmentId);
                list.add(cb.equal(root.get("dataStatus"), 0));
                if (null != leaveVO.getStatus()) {
                    list.add(cb.equal(root.get("status"), leaveVO.getStatus()));
                }
                if (null != leaveVO.getDepartmentId()) {
                    list.add(cb.equal(join.get("id"), leaveVO.getDepartmentId()));
                }
                if (null != leaveVO.getCreatUserId()) {
                    Join<Leave, Applicant> applicantJoin = root.join("applicant", JoinType.LEFT);
                    Join<Applicant, User> userJoin = applicantJoin.join("createUser", JoinType.LEFT);
                    list.add(cb.equal(userJoin.get("id"), leaveVO.getCreatUserId()));
                }
                if (null != leaveVO.getQueryStartTime()) {
                    list.add(cb.lessThanOrEqualTo(root.get("estimateStartTime"), new Date(leaveVO.getQueryStartTime())));
                }
                if (null != leaveVO.getQueryEndTime()) {
                    list.add(cb.greaterThanOrEqualTo(root.get("estimateStartTime"), new Date(leaveVO.getQueryEndTime())));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            Leave leave = (Leave) it.next();
            pageVO.addData(new LeaveVO(leave));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }

    /**
     * 验证部门权限
     *
     * @param userDepartmentId
     * @return
     */

    public Long varDepartment(Long userDepartmentId) {
//        if (Constant.DEPARTMENT_SCHOOL_ID == userDepartmentId || Constant.DEPARTMENT_SECURITY_ID == userDepartmentId) {
//            userDepartmentId = null;
//        }
        if (userDepartmentId <= 10) {
            userDepartmentId = null;
        }
        return userDepartmentId;
    }

    public void findInDepartment(List<Predicate> list, Join<Student, Department> join, Long userDepartmentId) {
        Path<Long> departmentId = join.get("id");
        if (null != userDepartmentId) {
            Department rootDepartment = departmentDao.findById(userDepartmentId).get();
            List<Department> departments = departmentDao.findAllByStatusAndAndCodeLike(Constant.VALID, "%" + rootDepartment.getCode() + "%");

            List<Long> ids = new ArrayList<>();
            for (Department department : departments) {
                ids.add(department.getId());
            }

            list.add(departmentId.in(ids));
        }
    }

    public void deleteLeave(Long id) {
        Leave leave = leaveDao.findById(id).get();
        leave.setDataStatus(1);
        leaveDao.save(leave);
        try {
            Date now = new Date();
            leave.setEstimateStartTime(DateUtil.offsetMinute(now, -10));
            leave.setEstimateEndTime(now);
            hikVisionService.sendDoor(leave);
        } catch (Exception ex) {
            log.error("发送海康门禁失败:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 创建请假申请
     *
     * @param applicantVO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApplicantVO saveApplicant(ApplicantVO applicantVO, User user) throws RunTimeException, IOException {
        Applicant applicant = new Applicant();
        applicant = applicantVO.toDTO(applicant);
        String[] studentIds = applicantVO.getApplicantUserId().split(",");
        applicant.setCreateUser(user);
        applicantDao.save(applicant);
        applicantVO = new ApplicantVO(applicant);
        for (String studentId : studentIds) {
            Leave leave = new Leave();
            leave.setEstimateStartTime(applicant.getStartTime());
            leave.setEstimateEndTime(applicant.getEndTime());
            leave.setApplicant(applicant);
            leave.setStatus(0);
            leave.setDataStatus(0);
            Student student = studentDao.findById(Long.valueOf(studentId)).get();
            leave.setStudent(student);
            try {
                hikVisionService.sendDoor(leave);
            } catch (Exception ex) {
                log.error("发送海康门禁失败:{}", ex.getMessage(), ex);
            }
            leaveDao.save(leave);
            applicantVO.addLeaveVO(new LeaveVO(leave));
        }

//        Department department = user.getDepartment();
//        if (department.getCode().length() != 6) {
//            department = departmentDao.findFirstByCode(department.getCode().substring(0, 6));
//        }
//        LeaveLimitVO leaveLimitVO = getLeaveLimit(department.getId());
//        Date startTime = new Date();
//        startTime.setTime(applicant.getStartTime().getTime());
//        Date endTime = new Date();
//        endTime.setTime(applicant.getEndTime().getTime());
//        List<String> datas = CommonUtils.splitData(startTime, endTime);
//        for (String data : datas) {
//            Integer leaveCount = leaveDao.countDepartmentCodeAndDate(department.getCode(), data);
//            Integer studentCount = studentDao.countDepartmentCode(department.getCode());
//            if (leaveCount / studentCount * 100 > leaveLimitVO.getLimitValue()) {
//                throw new RunTimeException("0001");
//            }
//        }
        return applicantVO;
    }

    /**
     * 保存学员信息
     *
     * @param studentVO
     * @return
     */
    public StudentVO saveStudent(StudentVO studentVO) throws IOException {
        Student student;
        if (null != studentVO.getId()) {
            student = studentDao.findById(studentVO.getId()).get();
        } else {
            student = new Student();
        }
        student = studentVO.toDTO(student);
        Dictionary type = dictionaryDao.findById(studentVO.getTypeId()).get();
        student.setType(type);
        Dictionary gender = dictionaryDao.findById(studentVO.getGenderId()).get();
        student.setGender(gender);
        Department department = departmentDao.findById(studentVO.getDepartmentId()).get();
        student.setDepartment(department);
        student.setStatus(Constant.VALID);
        if (null == student.getInStatus()) {
            student.setInStatus(1);
        }
        studentDao.save(student);
        // sendJieShunPerson(student);
        return new StudentVO(student);
    }

    public StudentVO getStudentById(Long id) {
        Student student = studentDao.findById(id).get();
        return new StudentVO(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentDao.findById(id).get();
        student.setStatus(Constant.INVALID);
        studentDao.save(student);
        try {
            hikVisionService.deletePerson(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public PageVO getStudentList(StudentVO studentVO, PageVO pageVO, Long userDepartmentId, Boolean isSuccess) {
        userDepartmentId = varDepartment(userDepartmentId);
        Long finalUserDepartmentId = userDepartmentId;
        Pageable pageable = PageRequest.of(pageVO.getPageCount() - 1, pageVO.getPageSize());
        Page page = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Join<Student, Department> join = root.join("department", JoinType.LEFT);
                Path<Long> departmentId = join.get("id");
                findInDepartment(list, join, finalUserDepartmentId);
                list.add(cb.equal(root.get("status"), 0));
                if (null != studentVO.getDepartmentId()) {
                    list.add(cb.equal(departmentId, studentVO.getDepartmentId()));
                }
                if (Boolean.TRUE.equals(isSuccess)) {
                    list.add(cb.isNotNull(root.get("faceId")));
                } else if (Boolean.FALSE.equals(isSuccess)) {
                    list.add(cb.isNull(root.get("faceId")));
                }
                if (null != studentVO.getCode()) {
                    list.add(cb.like(root.get("code"), "%" + studentVO.getCode() + "%"));
                }
                if (null != studentVO.getName()) {
                    list.add(cb.like(root.get("name"), "%" + studentVO.getName() + "%"));
                }
                return CommonUtils.getPredicate(root, query, cb, list, "id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            Student student = (Student) it.next();
            pageVO.addData(new StudentVO(student));
        }
        pageVO.setDataCount(page.getTotalElements());
        return pageVO;
    }
}
