package com.cheer.springmvc.web.controller;

import com.cheer.spring.mybatis.pojo.User;
import com.cheer.spring.mybatis.service.UserService;
import com.cheer.spring.mybatis.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@RequestMapping({"/system", "/"})
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // http://localhost:8080/springmvc/login
    @GetMapping("login")
    // @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        LOGGER.debug("login()-------------------->");
        return "login"; // login是逻辑视图名称，待会儿视图解析器把会把它转换成一个实际存在物理文件login.jsp
    }

    @PostMapping("login")
    public String login(@RequestParam("username") String username, @RequestParam String password,HttpServletRequest request) {
        LOGGER.debug("username={}, password{}", username, password);
        if (userService.checkLogin(username, password)) {
            User user = userService.find(username);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/getEmpList";
        } else {
            return "redirect:/login";
        }
    }


    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public String register( MultipartFile file,HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LOGGER.debug(username+":::::"+password);
        User user = new User();
        int insert = 0;
        try {
            user.setUsername(username);
            user.setPassword(StringUtils.encrypt(password));
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String avatar = username;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("E:/" + avatar + suffix));
            user.setAvatar(avatar + suffix);
            insert = userService.insert(user);
            if(insert>0){
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("out")
    public String out(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
}
