package com.poshist.test;

import com.poshist.car.service.CarService;
import com.poshist.soa.entity.Via;
import com.poshist.soa.repository.ViaDao;
import com.poshist.student.service.StudentService;
import com.poshist.sys.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

//    public static void  main(String[] args) throws IOException {
////        String pass = "111111";
////        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
////        String hashPass = encode.encode(pass);
////        System.out.println( encode.matches("111111",hashPass));
////        System.out.println(hashPass);
////        DecimalFormat df = new DecimalFormat("000");
////        System.out.println(df.format(1));
//        File file=new File("/home/poshist/1.xlsx");
//        WorkbookFactory.create(file,"",true);
//    }
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ViaDao viaDao;
    @org.junit.Test
    public void testvia(){
        Via via=viaDao.findById(52725l).get();
        studentService.studentVia(via);
    }
//    @org.junit.Test
//    public void testGetTopUser(){
//        User user=userService.getUserByName("test3");
//        User topUser=userService.getUserByRole(user, Constant.ROLE_FACULTY_LEAD);
//        System.out.println(topUser);
//    }


}
