package com.poshist.sys.service;

import com.poshist.common.Constant;
import com.poshist.common.utils.CommonUtils;
import com.poshist.sys.entity.*;
import com.poshist.sys.repository.*;
import com.poshist.sys.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private DictionaryDao dictionaryDao;
    private String defaultPassword = "123456";
    @Autowired
    private PicDao picDao;
    @Autowired
    private UserRoleDao userRoleDao;

    public void initPassword(Long id){
        User user=userDao.findById(id).get();
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        user.setPassword(encode.encode(defaultPassword));
        userDao.save(user);
    }
    public static void  main(String[] args) throws Exception {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        System.out.println(encode.encode("123456"));
    }
    public UserVO getUsetById(Long id){
        User user=userDao.findById(id).get();
        return new UserVO(user);
    }
    public User getUserByName(String userName) {
        User user = userDao.findUserByUserNameAndStatus(userName, Constant.VALID);

        return user;
    }

    public UserDetailVO getUserDetailVOByName(String userName) {
        User user = getUserByName(userName);
        UserDetailVO userVO = dtoToVo(user);
        userVO.setUser(user);
        userVO.setFunctions(analysisFunction(user.getUserRoles().get(0).getRole()));
        return userVO;
    }


    public QueryUserVO getUserList(QueryUserVO queryUserVO) {
        Pageable pageable = PageRequest.of(queryUserVO.getPageCount() - 1, queryUserVO.getPageSize());
        Page page = userDao.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("status"), 0));
                if (null != queryUserVO.getDepartmentId()) {
                    list.add(cb.equal(root.get("department"), departmentDao.findById(queryUserVO.getDepartmentId()).get()));
                }

                if (null != queryUserVO.getRealName() && "" != queryUserVO.getRealName()) {
                    list.add(cb.like(root.get("realName"), "%" + queryUserVO.getRealName() + "%"));
                }
                if (null != queryUserVO.getUserName() && "" != queryUserVO.getUserName()) {
                    list.add(cb.like(root.get("userName"), "%" + queryUserVO.getUserName() + "%"));
                }
                if(null!=queryUserVO.getRoleId()){
                    Join<User, UserRole> userUserRoleJoin=root.join("userRoles", JoinType.LEFT);
                    Join<UserRole, Role> join=userUserRoleJoin.join("role", JoinType.LEFT);
                    Path<String> roleId=join.get("id");
                    list.add(cb.equal(roleId, queryUserVO.getRoleId()));
                }
               return  CommonUtils.getPredicate(root, query, cb, list,"id");
            }
        }, pageable);
        Iterator it = page.iterator();
        while (it.hasNext()) {
            User user = (User) it.next();
            queryUserVO.addData(new UserVO(user));
        }
        queryUserVO.setDataCount(page.getTotalElements());
        return queryUserVO;
    }


    public DepartmentVO getDepartmentById(Long id) {
        Optional optional = departmentDao.findById(id);
        if (optional.isPresent()) {
            DepartmentVO departmentVO = new DepartmentVO((Department) optional.get());
            departmentVO.setChild(getChildDepartment(departmentVO));
            return departmentVO;
        }
        return null;
    }

    public List<DictionaryVO> getDictionaryByType(String type) {
        List<Dictionary> dictionaryInfos = dictionaryDao.getAllByStatusAndTypeOrderById(Constant.VALID, type);
        List<DictionaryVO> dictionaryVOS = new ArrayList();
        for (Dictionary dictionaryInfo : dictionaryInfos) {
            dictionaryVOS.add(new DictionaryVO(dictionaryInfo));
        }
        return dictionaryVOS;
    }

    public DepartmentVO addDepartment(DepartmentVO departmentVO) {
        Department department = new Department();
        departmentVO.toDTO(department);
        Optional optional = departmentDao.findById(departmentVO.getParentId());
        //处理父节点
        if (optional.isPresent()) {
            department.setParentDepartment((Department) optional.get());
            //生成code
            List<Department> child = departmentDao.findAllByParentDepartmentIdAndStatusOrderById(departmentVO.getParentId(), Constant.VALID);
            DecimalFormat df = new DecimalFormat("000");
            String code = ((Department) optional.get()).getCode() + df.format(child.size() + 1);
            department.setCode(code);
        }
        department.setStatus(Constant.VALID);
        departmentDao.save(department);
        return getDepartmentById(department.getId());
    }

    public UserVO saveUser(UserVO userVO) {
        User user = null;
        if (null == userVO.getId()) {
            user = new User();
            userVO.toDTO(user);
            BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
            user.setPassword(encode.encode(defaultPassword));

        } else {
            user = userDao.findById(userVO.getId()).get();
            String password = user.getPassword();
            String userName = user.getUserName();
            userVO.toDTO(user);
            user.setPassword(password);
            user.setUserName(userName);
        }


        //角色
        Role role = roleDao.findById(userVO.getRoleId()).get();
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        List<UserRole> userRoles = new ArrayList<UserRole>();
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        //职务
        if(null!=userVO.getPositionId()) {
            Dictionary position = dictionaryDao.findById(userVO.getPositionId()).get();
            user.setPosition(position);
        }
        //性别
        Dictionary gender = dictionaryDao.findById(userVO.getGenderId()).get();
        user.setGender(gender);
        //部门
        Department department = departmentDao.findById(userVO.getDepartmentId()).get();
        user.setDepartment(department);
        user.setStatus(Constant.VALID);
        user.setIsNew(Constant.VALID);
        userDao.save(user);
        return new UserVO(user);
    }


    private List<DepartmentVO> getChildDepartment(DepartmentVO departmentVO) {
        List<Department> departments = departmentDao.findAllByParentDepartmentIdAndStatusOrderById(departmentVO.getId(), Constant.VALID);
        List<DepartmentVO> departmentVOs = new ArrayList();
        for (Department department : departments) {
            DepartmentVO childVO = new DepartmentVO(department);
            childVO.setChild(getChildDepartment(childVO));
            departmentVOs.add(childVO);

        }
        return departmentVOs;
    }


    public boolean isPassword(UserVO userVO) {
        User user = userDao.findUserByUserNameAndStatus(userVO.getUserName(), Constant.VALID);
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        return encode.matches(userVO.getPassword(), user.getPassword());

    }

    public UserVO changePassword(UserVO userVO) {
        User user = userDao.findUserByUserNameAndStatus(userVO.getUserName(), Constant.VALID);
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        if (encode.matches(userVO.getOldPassword(), user.getPassword())) {
            user.setPassword(encode.encode(userVO.getPassword()));
            user.setIsNew(Constant.INVALID);
            userDao.save(user);
            return new UserVO(user);
        }
        return null;
    }


    public List<RoleVO> getAllRole() {
        List<Role> roles = roleDao.getAllByStatus("0");
        List<RoleVO> roleVOS = new ArrayList();
        for (Role role : roles) {
            roleVOS.add(new RoleVO(role));
        }
        return roleVOS;
    }


    /**
     * 获取指定用户上级角色
     *
     * @param user
     * @param roleId
     * @return
     */

    public User getUserByRole(User user, Long roleId) {
        User upUser = null;

        Department department=user.getDepartment();
        while (true) {
            if(Constant.DEPARTMENT_ROOT_ID.equals(department.getId())){
                break;
            }

            upUser = getUserByDepartmentAndRole(department, roleId);
            if (null != upUser) {
                break;
            }
            department=departmentDao.findById(department.getParentDepartment().getId()).get();
        }
        return upUser;
    }

    /**
     * 获取指定部门指定角色用户
     * @param department
     * @param roleId
     * @return
     */
    public  User getUserByDepartmentAndRole(Department department, Long roleId) {
        Role role = roleDao.findById(roleId).get();
        List<UserRole> userRoles=userRoleDao.findByRole(role);
        User user = userDao.findFirstByDepartmentAndUserRolesIn(department, userRoles);
        return user;
    }

    /**
     * 根据角色获取用户
     * @param roleId
     * @return
     */
    public List<User>getUserByRole(Long roleId){
        Role role = roleDao.findById(roleId).get();
        List<UserRole> userRoles=userRoleDao.findByRole(role);
        List <User> users=userDao.findAllByUserRolesIn(userRoles);
        return users;
    }

//    public User changeUserStatus(Long id) {
//        User user = userDao.findById(id).get();
//        if ("0".equals(user.getStatus())) {
//            user.setStatus(1);
//        } else {
//            user.setStatus(0);
//        }
//        userDao.save(user);
//        return user;
//    }

    private UserDetailVO dtoToVo(User user) {
        UserDetailVO userVO = new UserDetailVO();
        userVO.setId(user.getId());
        userVO.setUserName(user.getUserName());
        userVO.setPassword(user.getPassword());
        userVO.setRealName(user.getRealName());
        userVO.setMobile(user.getMobile());
        userVO.setEmail(user.getEmail());
        userVO.setRole(user.getUserRoles().get(0).getRole());
        return userVO;
    }

    private User voToDto(UserDetailVO userVO) {
        User user = new User();
        user.setId(userVO.getId());
        user.setUserName(userVO.getUserName());
        user.setPassword(userVO.getPassword());
        user.setRealName(userVO.getRealName());
        user.setMobile(userVO.getMobile());
        user.setEmail(userVO.getEmail());
        return user;
    }

    private List<FunctionVO> analysisFunction(Role role) {
        List<FunctionVO> functionVOS = new ArrayList<FunctionVO>();
        //构造一级菜单
        for (RoleFunction roleFunction : role.getRoleFunctions()) {
            if (roleFunction.getFunction().getId() != 0 && roleFunction.getFunction().getParentFunction().getId() == 0) {
                FunctionVO functionVO = new FunctionVO(roleFunction.getFunction());
                functionVOS.add(functionVO);
            }
        }
        //构造二级菜单
        for (RoleFunction roleFunction : role.getRoleFunctions()) {
            if (roleFunction.getFunction().getId() != 0 && roleFunction.getFunction().getParentFunction().getId() != 0) {
                for (FunctionVO parent : functionVOS) {
                    if (parent.getId() .equals(roleFunction.getFunction().getParentFunction().getId()) ) {
                        FunctionVO functionVO = new FunctionVO(roleFunction.getFunction());
                        parent.addChild(functionVO);
                    }
                }
            }
        }
        return functionVOS;
    }
    public PicVO uploadPic(PicVO picVO) {
        Pic pic = picDao.findFirstByTypeAndObjectId(picVO.getType(), picVO.getObjectId());
        if (null == pic) {
            pic = picVO.toDTO(new Pic());
        } else {
            pic.setData(picVO.getData());
        }
        picDao.save(pic);
        return new PicVO(pic);
    }

    public byte[] getPic(PicVO picVO) {
        Pic pic = picDao.findFirstByTypeAndObjectId(picVO.getType(), picVO.getObjectId());
        return pic.getData();
    }


}
