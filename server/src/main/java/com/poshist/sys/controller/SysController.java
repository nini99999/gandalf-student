package com.poshist.sys.controller;

import com.poshist.common.vo.BaseVO;
import com.poshist.sys.entity.User;
import com.poshist.sys.service.UserService;
import com.poshist.sys.vo.DepartmentVO;
import com.poshist.sys.vo.DictionaryVO;
import com.poshist.sys.vo.QueryUserVO;
import com.poshist.sys.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys")
public class SysController {
    @Autowired
    UserService userService;

    @RequestMapping("/getUserById")
    public BaseVO getUserById(@RequestBody UserVO userVO) {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(userService.getUsetById(userVO.getId()));
        return baseVO;
    }

    /**
     * 初始化密码
     *
     * @param userVO
     * @return
     */
    @RequestMapping("/initPassword")
    public BaseVO initPassword(@RequestBody UserVO userVO) {
        BaseVO baseVO = new BaseVO();
        userService.initPassword(userVO.getId());
        return baseVO;
    }

    /**
     * 获取当期用户细信息
     *
     * @param auth
     * @return
     */
    @RequestMapping("/getUser")
    public BaseVO getUser(Authentication auth) {
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        UserVO userVO = new UserVO(user);
        BaseVO baseVO = new BaseVO();
        baseVO.setData(userVO);
        return baseVO;
    }

    /**
     * 获取用户列表
     *
     * @param queryUserVO
     * @return
     */
    @RequestMapping("/getUserList")
    public BaseVO getUserList(@RequestBody QueryUserVO queryUserVO) {
        queryUserVO = userService.getUserList(queryUserVO);
        BaseVO baseVO = new BaseVO();
        baseVO.setData(queryUserVO);
        return baseVO;
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @RequestMapping("/getRoleList")
    public BaseVO getRoleList() {
        BaseVO baseVO = new BaseVO();
        baseVO.setData(userService.getAllRole());
        return baseVO;
    }

    /**
     * 获取部门
     *
     * @param departmentVO
     * @return
     */

    @RequestMapping("/getDepartment")
    public BaseVO getDepartment(@RequestBody DepartmentVO departmentVO, Authentication auth) {
        BaseVO baseVO = new BaseVO();
        if (null == departmentVO.getId()) {
            baseVO.setStatus("00001");
            baseVO.setMsg("id不能为空");
            return baseVO;
        }
        String userName = (String) auth.getPrincipal();
        User user = userService.getUserByName(userName);
        if(5==user.getDepartment().getId()){
            departmentVO = userService.getDepartmentById(departmentVO.getId());
        }else {
            departmentVO = userService.getDepartmentById(user.getDepartment().getId());
        }
        if (null != departmentVO) {
            baseVO.setData(departmentVO);
        } else {
            baseVO.setStatus("00002");
            baseVO.setMsg("部门id不存在");
        }
        return baseVO;
    }

    /**
     * 获取字典信息
     *
     * @param dictionaryVO
     * @return
     */
    @RequestMapping("/getDictionary")
    public BaseVO getDictionary(@RequestBody DictionaryVO dictionaryVO) {
        BaseVO baseVO = new BaseVO();
        List<DictionaryVO> dictionaryVOS = userService.getDictionaryByType(dictionaryVO.getType());
        baseVO.setData(dictionaryVOS);
        return baseVO;
    }

    /**
     * 新增部门
     *
     * @param departmentVO
     * @return
     */
    @RequestMapping("/addDepartment")
    public BaseVO addDepartment(@RequestBody DepartmentVO departmentVO) {
        BaseVO baseVO = new BaseVO();
        departmentVO = userService.addDepartment(departmentVO);
        baseVO.setData(departmentVO);
        return baseVO;
    }

    /**
     * 跟胡用户名获取用户
     *
     * @param userVO
     * @return
     */
    @RequestMapping("/getUserByName")
    public BaseVO getUserByName(@RequestBody UserVO userVO) {
        User user = userService.getUserByName(userVO.getUserName());
        BaseVO baseVO = new BaseVO();
        if (null == user) {
            baseVO.setStatus("0001");
            baseVO.setMsg("该用户不存在");
        } else {
            userVO = new UserVO(user);
            baseVO.setData(userVO);
        }
        return baseVO;

    }

    /**
     * 新增用户
     *
     * @param userVO
     * @return
     */
    @RequestMapping("/addUser")
    public BaseVO addUser(@RequestBody UserVO userVO) {
        BaseVO baseVO = new BaseVO();
        userVO = userService.saveUser(userVO);
        baseVO.setData(userVO);
        return baseVO;
    }

    /**
     * 密码是否正确
     *
     * @param userVO
     * @return
     */
    @RequestMapping("/isPassword")
    public BaseVO isPassword(@RequestBody UserVO userVO, Authentication auth) {
        userVO.setUserName((String) auth.getPrincipal());
        boolean isPassword = userService.isPassword(userVO);

        BaseVO baseVO = new BaseVO();
        if (!isPassword) {
            baseVO.setStatus("0001");
            baseVO.setMsg("密码不符");
        }
        return baseVO;
    }

    @RequestMapping("/changePassword")
    public BaseVO changePassword(@RequestBody UserVO userVO, Authentication auth) {
        userVO.setUserName((String) auth.getPrincipal());
        BaseVO baseVO = new BaseVO();
        if (null == userService.changePassword(userVO)) {
            baseVO.setStatus("0001");
            baseVO.setMsg("密码不符");
        }
        return baseVO;
    }

    @RequestMapping("/updateUser")
    public BaseVO updateUser(@RequestBody UserVO userVO) {
        BaseVO baseVO = new BaseVO();
        userVO = userService.saveUser(userVO);
        baseVO.setData(userVO);
        return baseVO;

    }


}
