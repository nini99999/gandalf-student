package com.poshist.student.controller;

import com.poshist.common.RunTimeException;
import com.poshist.common.vo.BaseVO;
import com.poshist.common.vo.PageVO;
import com.poshist.student.service.StudentService;
import com.poshist.student.vo.ApplicantVO;
import com.poshist.student.vo.LeaveLimitVO;
import com.poshist.student.vo.LeaveVO;
import com.poshist.student.vo.StudentVO;
import com.poshist.sys.entity.User;
import com.poshist.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @RequestMapping("/changeStatus")
    public BaseVO changeStatus(@RequestBody LeaveVO leave){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(studentService.changeStatus(leave.getId(),leave.getStatus()));
        return baseVO;
    }
    @RequestMapping("/getLeaveViaList")
    public BaseVO getLeaveViaList(@RequestBody LeaveVO leave){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(studentService.getLeaveViaList(leave.getId()));
        return baseVO;
    }
    @RequestMapping("/updateLeaveLimit")
    public BaseVO updateLeaveLimit(@RequestBody LeaveLimitVO leaveLimitVO){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(studentService.saveLeaveLimit(leaveLimitVO));
        return baseVO;
    }
    @RequestMapping("/getLeaveLimitList")
    public BaseVO getLeaveLimitList(Authentication auth){
        BaseVO baseVO=new BaseVO();
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        baseVO.setData(studentService.getLeaveLimitList(user.getDepartment().getId()));
        return baseVO;
    }
    @RequestMapping("/getLeaveLimit")
    public BaseVO getLeaveLimit(@RequestBody LeaveLimitVO leaveLimitVO){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(studentService.getLeaveLimit(leaveLimitVO.getDepartmentId()));
        return baseVO;
    }
    @RequestMapping ("/upLoadStudentsPic")
    public BaseVO upLoadStudentsPic (@RequestParam("picZip") MultipartFile picZip) throws IOException {
        BaseVO baseVO = new BaseVO();
        studentService.upLoadStudentsPic(picZip);
        return baseVO;
    }
    @RequestMapping("/importStudent")
    public void importStudent(@RequestParam("studentFile")MultipartFile studentFile, HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("rs.xls".getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        studentService.importStudent(studentFile, out);
        out.flush();
        out.close();
    }
    @RequestMapping("/getLeaveListExcel")
    public void getLeaveListExcel(@RequestBody Map paramMap,Authentication auth, HttpServletResponse response) throws IOException {

        PageVO pageVO = new PageVO(paramMap);
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        LeaveVO leaveVO=new LeaveVO();
        if(null!=paramMap.get("status")) {
            leaveVO.setStatus((Integer) paramMap.get("status"));
        }
        if(null!=paramMap.get("departmentId")) {
            leaveVO.setDepartmentId(Long.valueOf( paramMap.get("departmentId").toString()));
        }
        if(null!=paramMap.get("creatUserId")){
            leaveVO.setCreatUserId(Long.valueOf( paramMap.get("creatUserId").toString()));
        }
        if(null!=paramMap.get("queryStartTime")){
            leaveVO.setQueryStartTime(Long.valueOf( paramMap.get("queryStartTime").toString()));
        }
        if(null!=paramMap.get("queryEndTime")){
            leaveVO.setQueryEndTime(Long.valueOf( paramMap.get("queryEndTime").toString()));
        }
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("leaveLog.xls".getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        studentService.getLeaveListExcel(pageVO,leaveVO,user.getDepartment().getId(),out);
        out.flush();
        out.close();
    }
    @RequestMapping("/getLeaveList")
    public BaseVO getLeaveList(@RequestBody Map paramMap,Authentication auth){
        BaseVO baseVO=new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        LeaveVO leaveVO=new LeaveVO();
        if(null!=paramMap.get("status")) {
            leaveVO.setStatus((Integer) paramMap.get("status"));
        }
        if(null!=paramMap.get("departmentId")) {
            leaveVO.setDepartmentId(Long.valueOf( paramMap.get("departmentId").toString()));
        }
        if(null!=paramMap.get("creatUserId")){
            leaveVO.setCreatUserId(Long.valueOf( paramMap.get("creatUserId").toString()));
        }
        if(null!=paramMap.get("queryStartTime")){
            leaveVO.setQueryStartTime(Long.valueOf( paramMap.get("queryStartTime").toString()));
        }
        if(null!=paramMap.get("queryEndTime")){
            leaveVO.setQueryEndTime(Long.valueOf( paramMap.get("queryEndTime").toString()));
        }
        baseVO.setData(studentService.getLeaveList(pageVO,leaveVO,user.getDepartment().getId()));
        return baseVO;
    }
    @RequestMapping("/getLeave")
    public BaseVO getLeave(@RequestBody LeaveVO leaveVO){
        BaseVO baseVO=new BaseVO();
        baseVO.setData(studentService.getLeaveById(leaveVO.getId()));
        return baseVO;
    }
    @RequestMapping("/addApplicant")
    public BaseVO addApplicant(@RequestBody ApplicantVO applicantVO, Authentication auth){
        BaseVO baseVO = new BaseVO();
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        try {
            applicantVO=studentService.saveApplicant(applicantVO,user);
        } catch (RunTimeException | IOException e) {
            baseVO.setMsg("超出请假申请比例限制");
            baseVO.setStatus("0001");
        }
        baseVO.setData(applicantVO);
        return baseVO;
    }
    /**
     * 新增学员
     *
     * @param studentVO
     * @return
     */
    @RequestMapping("/addStudent")
    public BaseVO addStudent(@RequestBody StudentVO studentVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(studentService.saveStudent(studentVO));
        return baseVO;
    }

    /**
     * 修改学员
     *
     * @param studentVO
     * @return
     */
    @RequestMapping("/updateStudent")
    public BaseVO updateStudent(@RequestBody StudentVO studentVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(studentService.saveStudent(studentVO));
        return baseVO;
    }
    /**
     * 删除学员
     *
     * @param studentVO
     * @return
     */
    @RequestMapping("/deleteStudent")
    public BaseVO deleteStudent(@RequestBody StudentVO studentVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(studentService.saveStudent(studentVO));
        return baseVO;
    }

    /**
     * 获取学员信息
     *
     * @param studentVO
     * @return
     */
    @RequestMapping("/getStudent")
    public BaseVO getStudent(@RequestBody StudentVO studentVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(studentService.getStudentById(studentVO.getId()));
        return baseVO;
    }

    @RequestMapping("/getStudentList")
    public BaseVO getStudentList(@RequestBody Map paramMap, Authentication auth) {
        BaseVO baseVO = new BaseVO();
        PageVO pageVO = new PageVO(paramMap);
        StudentVO studentVO = new StudentVO();
        if(null!=paramMap.get("departmentId")) {
            studentVO.setDepartmentId(Long.valueOf(paramMap.get("departmentId").toString()));
        }
        studentVO.setName((String) paramMap.get("name"));
        studentVO.setCode((String) paramMap.get("code"));
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        pageVO = studentService.getStudentList(studentVO, pageVO,user.getDepartment().getId());
        baseVO.setData(pageVO);
        return baseVO;
    }
}
