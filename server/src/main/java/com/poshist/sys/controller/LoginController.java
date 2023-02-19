package com.poshist.sys.controller;

import com.poshist.sys.service.CustomUserDetailsService;
import com.poshist.sys.vo.UserDetailVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author tank 2023/2/17
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @RequestMapping("/customerLogin")
    public String login(@RequestBody UserDetailVO vo, HttpServletResponse res) throws Exception {
        System.out.println("--------------------------------new login");
        UserDetails userDetails= customUserDetailsService.loadUserByUsername(vo.getUserName());
        if (BCrypt.checkpw(vo.getPassword(), userDetails.getPassword())) {
            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 100000))
                    .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                    .compact();
            res.addHeader("Authorization", "Bearer " + token);
            return "login";
        }
        System.out.println("--------------------------------  login fail");
       throw new Exception("notlogin");
    }
}
